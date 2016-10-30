package co.salutary.mobisaude.activities;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
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

import org.json.JSONArray;
import org.json.JSONException;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import co.salutary.mobisaude.R;
import co.salutary.mobisaude.adapters.GenericListAdapter;
import co.salutary.mobisaude.adapters.GenericListAdapter.Item;
import co.salutary.mobisaude.adapters.GenericListAdapter.Row;
import co.salutary.mobisaude.adapters.GenericListAdapter.Section;
import co.salutary.mobisaude.util.JsonUtils;

public class SingleSelectionListActivity extends ListActivity {

    private static final String TAG = SingleSelectionListActivity.class.getSimpleName();

    private GenericListAdapter adapter = new GenericListAdapter();
    private GestureDetector mGestureDetector;
    private List<Object[]> alphabet = new ArrayList<>();
    private HashMap<String, Integer> sections = new HashMap<>();
    private int sideIndexHeight;
    private static float sideIndexX;
    private static float sideIndexY;
    private int indexListSize;

    private EditText mSearchText;
    private ImageView mSearchButton;
    private ArrayList<String> mStringList = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_generic_select_list);
        mSearchText = (EditText) findViewById(R.id.generic_select_list_search_text);
        mSearchButton = (ImageView) findViewById(R.id.generic_select_list_search_button);

        Intent intent = getIntent();
        if(intent != null){
            Bundle extras = intent.getExtras();
            if(extras != null){
                String tipoESString = extras.getString("values");
                try{
                    HashMap<String, String> tiposES = JsonUtils.fromJsonArraytoDomainHashMap(new JSONArray(tipoESString));
                    mStringList = new ArrayList<>(tiposES.values());
                    Collections.sort(mStringList);
                } catch (JSONException e){
                    Log.e(TAG, e.getMessage(),e);
                }
            }
        }

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
                    carregarLista();
                }
            }
        });

        mSearchButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSearch();
            }
        });

        mGestureDetector = new GestureDetector(this, new SideIndexGestureListener());

        carregarLista();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (adapter.getRows().get(position) instanceof Item) {
            Item item = (Item) adapter.getRows().get(position);
            setResult(item.id);
            finish();
        }
    }

    private void onClickSearch(){
        if (mSearchText.getText().length() != 0) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(mSearchText.getWindowToken(), 0);
            mSearchText.setText(getString(R.string.empty));
            mSearchButton.setImageResource(android.R.drawable.ic_menu_search);
            carregarLista();
        } else {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            mSearchText.setFocusable(true);
            mSearchButton.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
        }
    }

    private void carregarLista() {
        List<Row> rows = new ArrayList<>();
        int start = 0;
        int end;
        String previousLetter = null;
        Object[] tmpIndexItem;
        Pattern numberPattern = Pattern.compile("[0-9]");

        for (String str : mStringList) {

            String firstLetter = str.substring(0, 1);
            firstLetter = Normalizer.normalize(firstLetter, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
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

            rows.add(new Item(str, mStringList.indexOf(str)));
            previousLetter = firstLetter;
        }


        if (previousLetter != null) {
            tmpIndexItem = new Object[3];
            tmpIndexItem[0] = previousLetter.toUpperCase(Locale.UK);
            tmpIndexItem[1] = start;
            tmpIndexItem[2] = rows.size() - 1;
            alphabet.add(tmpIndexItem);
        }

        adapter.setRows(rows);
        setListAdapter(adapter);

        updateList();
    }

    private void setSearchResult(String strTarget) {
        try {
            adapter = new GenericListAdapter();

            for (String str : mStringList) {
                if (str.toLowerCase(Locale.getDefault()).contains(strTarget.toLowerCase())) {
                    adapter.addItem(new Item(str, mStringList.indexOf(str)));
                }
            }

            setListAdapter(adapter);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    private void updateList() {
        LinearLayout sideIndex = (LinearLayout) findViewById(R.id.generic_select_list_index);
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
                    carregarLista();
                }

                displayListItem();

                return false;
            }
        });
    }

    private void displayListItem() {
        LinearLayout sideIndex = (LinearLayout) findViewById(R.id.generic_select_list_index);
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