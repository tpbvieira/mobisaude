package co.salutary.mobisaude.activities;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import co.salutary.mobisaude.R;
import co.salutary.mobisaude.adapters.ListAdapterModel;
import co.salutary.mobisaude.adapters.ListAdapterModel.Item;
import co.salutary.mobisaude.adapters.ListAdapterModel.Row;
import co.salutary.mobisaude.adapters.ListAdapterModel.Section;
import co.salutary.mobisaude.controller.UserController;
import co.salutary.mobisaude.db.EsDAO;
import co.salutary.mobisaude.db.LocalDataBase;
import co.salutary.mobisaude.model.EstabelecimentoSaude;

public class HealthPlaceSelectionListActivity extends ListActivity {

    private static final String TAG = new Object() {
    }.getClass().getName();

	public static final int SELECTED = 1;

    private GestureDetector mGestureDetector;
    private ListAdapterModel listAdapterModel = new ListAdapterModel();
    private List<Object[]> alphabet = new ArrayList<Object[]>();
    private HashMap<String, Integer> sections = new HashMap<String, Integer>();
    private static float sideIndexX;
    private static float sideIndexY;
    private int sideIndexHeight;
    private int indexListSize;

    private EditText mSearchText;
    private ImageView mSearchButton;

    private LocalDataBase userDataBase;
    private UserController userController;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_select_list);

        TextView title = (TextView) findViewById(R.id.select_list_title);
        title.setText(R.string.estabelecimentos_saude);

        mSearchText = (EditText) findViewById(R.id.select_list_search_text);
        mSearchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mSearchText.getText().length() != 0) {
                    mSearchButton.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
                    String spnId = mSearchText.getText().toString();
                    setSearchResult(spnId);
                } else {
                    mSearchButton.setImageResource(android.R.drawable.ic_menu_search);
                    loadData();
                }
            }
        });

        mSearchButton = (ImageView) findViewById(R.id.select_list_search_button);
        mSearchButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSearch();
            }
        });

        userDataBase = LocalDataBase.getInstance();
        userController = UserController.getInstance();
 		mGestureDetector = new GestureDetector(this, new SideIndexGestureListener());

 		loadData();
    }


	@Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

        if (listAdapterModel.getRows().get(position) instanceof Item) {
            Item item = (Item) listAdapterModel.getRows().get(position);
            EstabelecimentoSaude es = new EsDAO((userDataBase)).getESByIdCnes(item.id);
            userController.setEstabelecimentoSaude(es);
            setResult(SELECTED);
            finish();
        }
	}

	private void onClickSearch(){
		if (mSearchText.getText().length() != 0) {
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(mSearchText.getWindowToken(), 0);
            mSearchText.setText(getString(R.string.empty));
            mSearchButton.setImageResource(android.R.drawable.ic_menu_search);
			loadData();
		} else {
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            mSearchText.setFocusable(true);
            mSearchButton.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
		}
	}

	private void loadData() {
        int start = 0,end = 0;
        String previousLetter = null;
        Object[] tmpIndexItem;
        List<Row> rows = new ArrayList<Row>();
        Pattern numberPattern = Pattern.compile("[0-9]");

        List<EstabelecimentoSaude> esList = userController.getListEstabelecimentoSaudes();
        Collections.sort(esList, new Comparator<EstabelecimentoSaude>() {
            @Override
            public int compare(EstabelecimentoSaude e1, EstabelecimentoSaude e2) {
                String nome1 = Normalizer.normalize(e1.getNomeFantasia(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
                String nome2 = Normalizer.normalize(e2.getNomeFantasia(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
                return nome1.compareToIgnoreCase(nome2);
            }
        });

        for (EstabelecimentoSaude es : esList) {
            String nomeFantasia = es.getNomeFantasia();
            String firstLetterOrin = nomeFantasia.substring(0, 1);
            String firstLetter = Normalizer.normalize(firstLetterOrin, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");

            if (numberPattern.matcher(firstLetter).matches()) {
                firstLetter = "#";
            }

            if (previousLetter != null && !firstLetter.equals(previousLetter)) {
                end = rows.size() - 1;
                tmpIndexItem = new Object[3];
                tmpIndexItem[0] = previousLetter.toUpperCase(Locale.UK);
                tmpIndexItem[1] = start;
                tmpIndexItem[2] = end;
                alphabet.add(tmpIndexItem);

                start = end + 1;
            }

            if (!firstLetter.equals(previousLetter)) {
                rows.add(new Section(firstLetter));
                sections.put(firstLetter, start);
            }

            rows.add(new Item(nomeFantasia, es.getIdCnes()));
            previousLetter = firstLetter;
        }

        if (previousLetter != null) {
            tmpIndexItem = new Object[3];
            tmpIndexItem[0] = previousLetter.toUpperCase(Locale.UK);
            tmpIndexItem[1] = start;
            tmpIndexItem[2] = rows.size() - 1;
            alphabet.add(tmpIndexItem);
        }

        listAdapterModel.setRows(rows);
        setListAdapter(listAdapterModel);

        updateList();
    }

    private void updateList() {
        LinearLayout sideIndex = (LinearLayout) findViewById(R.id.select_list_index);
        sideIndex.removeAllViews();
        indexListSize = alphabet.size();

        if (indexListSize < 1) {
            return;
        }

        int indexMaxSize = (int) Math.floor(sideIndex.getHeight() / 20);
        int tmpIndexListSize = indexListSize;

        while (tmpIndexListSize > indexMaxSize) {
            tmpIndexListSize = tmpIndexListSize / 2;
        }

        double delta;
        if (tmpIndexListSize > 0) {
            delta = indexListSize / tmpIndexListSize;
        } else {
            delta = 1;
        }

        TextView tmpTV;
        for (double i = 1; i <= indexListSize; i = i + delta) {
            Object[] tmpIndexItem = alphabet.get((int) i - 1);
            String tmpLetter = tmpIndexItem[0].toString();

            tmpTV = new TextView(this);
            tmpTV.setText(tmpLetter);
            tmpTV.setGravity(Gravity.CENTER);
            tmpTV.setTextSize(12);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
            tmpTV.setLayoutParams(params);
            sideIndex.addView(tmpTV);
        }

        sideIndexHeight = sideIndex.getHeight();

        sideIndex.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                sideIndexX = event.getX();
                sideIndexY = event.getY();

                if (0 != mSearchText.getText().length()) {
                    mSearchText.setText(getString(R.string.empty));
                    loadData();
                }

                displayListItem();

                return false;
            }
        });
    }

	private void setSearchResult(String str) {
		try {
			listAdapterModel = new ListAdapterModel();
            for (EstabelecimentoSaude es : userController.getListEstabelecimentoSaudes()) {
                if (es.getNomeFantasia().toLowerCase(Locale.getDefault()).contains(str.toLowerCase())) {
                    listAdapterModel.addItem(new Item(es.getNomeFantasia(), es.getIdCnes()));
                }
            }
			setListAdapter(listAdapterModel);
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
	}

	private void displayListItem() {
        LinearLayout sideIndex = (LinearLayout) findViewById(R.id.select_list_index);
        sideIndexHeight = sideIndex.getHeight();
        double pixelPerIndexItem = (double) sideIndexHeight / indexListSize;

        int itemPosition = (int) (sideIndexY / pixelPerIndexItem);

        if (itemPosition < alphabet.size()) {
            Object[] indexItem = alphabet.get(itemPosition);
            int subitemPosition = sections.get(indexItem[0]);
            getListView().setSelection(subitemPosition);
        }
    }

	private class SideIndexGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            sideIndexX = sideIndexX - distanceX;
            sideIndexY = sideIndexY - distanceY;

            if (sideIndexX >= 0 && sideIndexY >= 0) {
                displayListItem();
            }
            return super.onScroll(e1, e2, distanceX, distanceY);
        }
    }

}