package co.salutary.mobisaude.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import co.salutary.mobisaude.R;
import co.salutary.mobisaude.adapters.GenericListAdapter;
import co.salutary.mobisaude.adapters.GenericListAdapter.Item;
import co.salutary.mobisaude.adapters.GenericListAdapter.Row;
import co.salutary.mobisaude.adapters.GenericListAdapter.Section;
import co.salutary.mobisaude.adapters.StringListAdapter;
import co.salutary.mobisaude.config.Settings;
import co.salutary.mobisaude.controller.ClientCache;
import co.salutary.mobisaude.model.EstabelecimentoSaude;
import co.salutary.mobisaude.util.JsonUtils;

public class HealthPlaceSelectionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = new Object() {
    }.getClass().getName();

    //ui
    private View mContentView;
    private View mProgressView;
    private Spinner mTipoESSpiner;
    private EditText mSearchText;
    private ImageView mSearchButton;
    private ListView mESListView;

    // ListView
    public static final int SELECTED = 1;
    private GestureDetector mGestureDetector;
    private GenericListAdapter mListAdapterModel = new GenericListAdapter();
    private List<Object[]> alphabet = new ArrayList<>();
    private HashMap<String, Integer> sections = new HashMap<>();
    private static float sideIndexX;
    private static float sideIndexY;
    private int sideIndexHeight;
    private int indexListSize;

    //context
    private ClientCache clientCache;
    private Settings settings;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Contextual information
        Context context = this.getApplicationContext();
        settings = new Settings(context);
        settings.setPreferenceValues(Settings.ID_TIPO_ESTABELECIMENTO_SAUDE, null);
        clientCache = ClientCache.getInstance();

        // UI: General
        setContentView(R.layout.activity_healthplace_selection);
        mProgressView = findViewById(R.id.hp_select_list_progress_bar);
        mContentView = findViewById(R.id.hp_select_list_content);
        showProgress(true);

        // Fill spinner with tipoES
        try {
            String tipoESString = settings.getPreferenceValues(Settings.TIPOS_ESTABELECIMENTO_SAUDE);
            HashMap<String, String> tiposES = JsonUtils.fromJsonArraytoDomainHashMap(new JSONArray(tipoESString));
            ArrayList<String> tipoESList = new ArrayList<>(tiposES.values());
            Collections.sort(tipoESList);
            StringListAdapter stringListAdapter = new StringListAdapter(context, android.R.layout.simple_spinner_dropdown_item, tipoESList);
            stringListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mTipoESSpiner = (Spinner) findViewById(R.id.hp_type_spinner);
            mTipoESSpiner.setAdapter(stringListAdapter);
            mTipoESSpiner.setOnItemSelectedListener(this);
            mTipoESSpiner.setSelection(9 - 1);//unidade básica de saúde
            mTipoESSpiner.setSelected(true);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
        }

        // UI: map button
        ImageView mMapButton = (ImageView) findViewById(R.id.hp_select_list_map_button);
        mMapButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int idTipoES = (int)mTipoESSpiner.getSelectedItemId();
                settings.setPreferenceValues(Settings.ID_TIPO_ESTABELECIMENTO_SAUDE, Integer.toString(idTipoES + 1));
                startActivity(MapsActivity.class);
            }
        });

        // UI: search imput text
        mSearchText = (EditText) findViewById(R.id.hp_select_list_search_text);
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

        // UI: search button
        mSearchButton = (ImageView) findViewById(R.id.hp_select_list_search_button);
        mSearchButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });
        mGestureDetector = new GestureDetector(this, new SideIndexGestureListener());

        // UI: list
        mESListView = (ListView)findViewById(R.id.hp_select_list);
        mESListView.setAdapter(mListAdapterModel);
        mESListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> a, View v,int position, long id){

                if (mListAdapterModel.getRows().get(position) instanceof Item) {
                    Item item = (Item) mListAdapterModel.getRows().get(position);
                    settings.setPreferenceValue(Settings.ID_ESTABELECIMENTO_SAUDE, Integer.toString(item.id));
                    setResult(SELECTED);
                    startActivity(HealthPlaceActivity.class);
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        loadData();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) { }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    private void loadData() {

        int start= 0;
        int end;
        String previousLetter = null;
        Object[] tmpIndexItem;
        List<Row> rows = new ArrayList<>();
        Pattern numberPattern = Pattern.compile("[0-9]");

        List<EstabelecimentoSaude> esList = clientCache.getListESByCidade();
        if(mTipoESSpiner.isSelected()){
            List<EstabelecimentoSaude> esByTypeList = new ArrayList<>();
            for(EstabelecimentoSaude es : esList ){
                if(es.getIdTipoEstabelecimentoSaude() == (mTipoESSpiner.getSelectedItemId() + 1)){
                    esByTypeList.add(es);
                }
            }
            esList = esByTypeList;
        }

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

        mListAdapterModel.setRows(rows);
        mESListView.setAdapter(mListAdapterModel);

        updateList();
        showProgress(false);
    }

    private void updateList() {
        LinearLayout sideIndex = (LinearLayout) findViewById(R.id.hp_select_list_index);
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
            mListAdapterModel = new GenericListAdapter();

            List<EstabelecimentoSaude> esList = clientCache.getListESByCidade();
            if(mTipoESSpiner.isSelected()){
                List<EstabelecimentoSaude> esByTypeList = new ArrayList<>();
                for(EstabelecimentoSaude es : esList ){
                    if(es.getIdTipoEstabelecimentoSaude() == (mTipoESSpiner.getSelectedItemId() + 1)){
                        esByTypeList.add(es);
                    }
                }
                esList = esByTypeList;
            }

            for (EstabelecimentoSaude es : esList) {
                if (es.getNomeFantasia().toLowerCase(Locale.getDefault()).contains(str.toLowerCase())) {
                    mListAdapterModel.addItem(new Item(es.getNomeFantasia(), es.getIdCnes()));
                }
            }

            mESListView.setAdapter(mListAdapterModel);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    private void displayListItem() {
        LinearLayout sideIndex = (LinearLayout) findViewById(R.id.hp_select_list_index);
        sideIndexHeight = sideIndex.getHeight();
        double pixelPerIndexItem = (double) sideIndexHeight / indexListSize;

        int itemPosition = (int) (sideIndexY / pixelPerIndexItem);

        if (itemPosition < alphabet.size()) {
            Object[] indexItem = alphabet.get(itemPosition);
            int subitemPosition = sections.get(indexItem[0]);
            mESListView.setSelection(subitemPosition);
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

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mContentView.setVisibility(show ? View.GONE : View.VISIBLE);
            mContentView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mContentView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mContentView.setVisibility(show ? View.GONE : View.VISIBLE);
        }

    }

    private void startActivity(final Class<? extends Activity> activity) {
        if (activity != null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(HealthPlaceSelectionActivity.this, activity));
                }
            }, 300);
        }
    }

}