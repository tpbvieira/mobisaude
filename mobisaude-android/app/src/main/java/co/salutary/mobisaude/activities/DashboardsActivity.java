package co.salutary.mobisaude.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import co.salutary.mobisaude.R;
import co.salutary.mobisaude.adapters.GenericListAdapter;
import co.salutary.mobisaude.adapters.StringListAdapter;
import co.salutary.mobisaude.config.Settings;
import co.salutary.mobisaude.controller.ClientCache;
import co.salutary.mobisaude.controller.ServiceBroker;
import co.salutary.mobisaude.controller.TokenManager;
import co.salutary.mobisaude.model.EstabelecimentoSaude;
import co.salutary.mobisaude.util.MobiSaudeAppException;

import static android.graphics.Color.rgb;

public class DashboardsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = new Object() {
    }.getClass().getName();

    public static final int[] BLUE_GRADIENT_COLORS = {
            rgb(173,216,230), rgb(135,206,250), rgb(0,191,255), rgb(30,144,255), rgb(65,105,225), rgb(0,0,225)
    };
    private static final int NONE = 0;
    private static final int STATE = 1;
    private static final int CITY = 2;
    private static final int TYPE = 3;

    private View mProgressView;
    private PieChart mChart;

    private UpdateChartTask mUpdateChartTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // UI: generic
        setContentView(R.layout.activity_dashboards);
        mProgressView = findViewById(R.id.dashboards_progress_bar);
        showProgress(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // UI: spinner
        List<String> tiposDashboardList = new ArrayList<>();
        tiposDashboardList.add(getString(R.string.dashboard_select));
        tiposDashboardList.add(getString(R.string.estado));
        tiposDashboardList.add(getString(R.string.cidade));
        tiposDashboardList.add(getString(R.string.tipo_es));
        StringListAdapter stringListAdapter = new StringListAdapter(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, tiposDashboardList);
        stringListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner mTipoDashboard = (Spinner) findViewById(R.id.dashboard_options_spinner);
        mTipoDashboard.setAdapter(stringListAdapter);
        mTipoDashboard.setOnItemSelectedListener(this);

        //UI: pie chart
        mChart = (PieChart) findViewById(R.id.generic_pie_chart);
        mChart.setUsePercentValues(true);
        mChart.setExtraOffsets(5, 10, 5, 5);
        mChart.setDragDecelerationFrictionCoef(0.95f);
        mChart.setDescription(getString(R.string.dashboard_evaluation_desc));
        mChart.setDrawCenterText(false);
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.WHITE);
        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);
        mChart.setHoleRadius(58f);
        mChart.setTransparentCircleRadius(61f);
        mChart.setRotationAngle(0);
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);
        mChart.setEntryLabelColor(Color.WHITE);
        mChart.setEntryLabelTextSize(12f);
        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        Legend legend = mChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setDrawInside(false);
        legend.setXEntrySpace(7f);
        legend.setYEntrySpace(0f);
        legend.setYOffset(0f);

        showProgress(false);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(mUpdateChartTask != null){
            mUpdateChartTask.cancel(true);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

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
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {

        if(pos == NONE){
            mChart.setEnabled(false);
            mChart.setVisibility(View.INVISIBLE);
        }else{
//            mUpdateChartTask = new UpdateChartTask(pos);
//            mUpdateChartTask.execute();
            mChart.setEnabled(true);
            mChart.setVisibility(View.VISIBLE);
            Map<Integer, Integer> values = new HashMap<>();
            values.put(0, 10);
            values.put(1, 20);
            values.put(2, 30);
            values.put(3, 40);
            values.put(4, 50);
            values.put(5, 60);
            setData(values);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // do nothing
    }

    private void setData(Map<Integer, Integer> values) {

        // Entries
        Set<Integer> starsSet = values.keySet();
        Integer[] sortedStars = starsSet.toArray(new Integer[starsSet.size()]);
        Arrays.sort(sortedStars);
        ArrayList<PieEntry> entries = new ArrayList<>();
        for(Integer star: sortedStars){
            entries.add(new PieEntry(Float.valueOf(values.get(star)), star.toString()));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Estrelas");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        // Colors
        ArrayList<Integer> colors = new ArrayList<>();
        for (int color : BLUE_GRADIENT_COLORS) {
            colors.add(color);
        }
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);

        mChart.setData(data);
        mChart.highlightValues(null);
        mChart.invalidate();

    }

    public class UpdateChartTask extends AsyncTask<Void, Void, Boolean> {

        private String mErrorMsg = null;
        private String mWarningMsg = null;
        private int mChartType;
        private Map<Integer, Integer> mValues = new HashMap<>();

        UpdateChartTask(int chartType) {
            mChartType = chartType;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress(true);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            boolean ok = true;

            Settings settings = new Settings(getApplicationContext());

            String token = settings.getPreferenceValue(Settings.TOKEN);
            if (token == null || token.isEmpty()) {
                TokenManager.gerarToken(getApplicationContext());//renew token and saves into preferences
                token = settings.getPreferenceValue(Settings.TOKEN);
            }

//            EstabelecimentoSaude es = ServiceBroker.getInstance(getApplicationContext()).getES(token, idEs);
//            if(es != null) {
//                bookmark.add(es);
//            }

            return ok;
        }

        @Override
        protected void onPostExecute(final Boolean success) {

            if (success && mValues != null) {
                setData(mValues);
            }else{
                Toast.makeText(getApplicationContext(), mErrorMsg, Toast.LENGTH_SHORT).show();
                finish();
            }

            if (mWarningMsg != null) {
                Toast.makeText(getApplicationContext(), mWarningMsg, Toast.LENGTH_SHORT).show();
            }

            showProgress(false);
        }

        @Override
        protected void onCancelled() {
            showProgress(false);
        }

    }

}