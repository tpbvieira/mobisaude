package co.salutary.mobisaude.fragments;


import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import co.salutary.mobisaude.R;
import co.salutary.mobisaude.util.JsonUtils;

public class LineChartDialogFragment extends DialogFragment {

    private static final String TAG = new Object() {
    }.getClass().getName();

    private static String mTitle;

    public static LineChartDialogFragment newInstance(Map<String, String> values, String title) {
        LineChartDialogFragment dialogFragment = new LineChartDialogFragment();

        mTitle = title;
        // Supply num input as an argument.
        Bundle args = new Bundle();

        String strValues = JsonUtils.mapToString(values);
        args.putString("values", strValues);

        dialogFragment.setArguments(args);

        return dialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View dialogFragment = inflater.inflate(R.layout.activity_linechart, container, false);

        Map<String, String> values;
        try{
            values = JsonUtils.stringToMap(getArguments().getString("values"));
        } catch (Exception e){
            Log.e(TAG, e.getMessage(),e);
            return dialogFragment;
        }

        LineChart mChart = (LineChart) dialogFragment.findViewById(R.id.chart1);
        this.getDialog().setTitle(mTitle);
        mChart.setDrawGridBackground(false);

        // enable scaling and dragging
        mChart.setTouchEnabled(true);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);

        mChart.setDescription(getString(R.string.avaliacoes_media_mes));

        // average line
        float sum = 0;
        Set<String> keys = values.keySet();
        for(String key: keys){
            sum = sum + Float.valueOf(values.get(key));
        }
        float mean = sum/values.size();
        LimitLine avgLine = new LimitLine(mean, getString(R.string.avaliacao_media));
        avgLine.setLineWidth(4f);
        avgLine.enableDashedLine(10f, 10f, 0f);
        avgLine.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        avgLine.setTextSize(10f);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.removeAllLimitLines();
        leftAxis.addLimitLine(avgLine);
        leftAxis.setDrawZeroLine(false);
        leftAxis.setDrawLimitLinesBehindData(true);

        mChart.getAxisRight().setEnabled(false);

        // add data
        setData(mChart, values);

        mChart.animateX(2500);

        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);

        // dont forget to refresh the drawing
        mChart.invalidate();

        return dialogFragment;
    }

    private void setData(LineChart mChart, Map<String, String> values) {

        ArrayList<Entry> entryValues = new ArrayList<>();

        SortedSet<String> sortedKeys = new TreeSet<String>();
        sortedKeys.addAll(values.keySet());

        for (String key: sortedKeys) {
            entryValues.add(new Entry(Float.valueOf(key), Float.valueOf(values.get(key))));
        }

        LineDataSet dataSet;
        if (mChart.getData() != null && mChart.getData().getDataSetCount() > 0) {
            dataSet = (LineDataSet)mChart.getData().getDataSetByIndex(0);
            dataSet.setValues(entryValues);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            // create a dataset, give it a type and set the line to be drawn like this "- - -"
            dataSet = new LineDataSet(entryValues, "DataSet");
            dataSet.enableDashedLine(10f, 5f, 0f);
            dataSet.enableDashedHighlightLine(10f, 5f, 0f);
            dataSet.setColor(Color.BLACK);
            dataSet.setCircleColor(Color.BLACK);
            dataSet.setLineWidth(1f);
            dataSet.setCircleRadius(3f);
            dataSet.setDrawCircleHole(false);
            dataSet.setValueTextSize(9f);
            dataSet.setDrawFilled(true);
            dataSet.setVisible(true);
            if (Utils.getSDKInt() >= 18) {
                // fill drawable only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(this.getDialog().getContext(), R.drawable.fade_red);
                dataSet.setFillDrawable(drawable);
            }
            else {
                dataSet.setFillColor(Color.BLACK);
            }

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(dataSet); // add the datasets

            // create a data object with the datasets
            LineData data = new LineData(dataSets);

            // set data
            mChart.setData(data);
        }

    }
}