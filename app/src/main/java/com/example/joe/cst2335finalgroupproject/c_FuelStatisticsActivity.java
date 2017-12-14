package com.example.joe.cst2335finalgroupproject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class c_FuelStatisticsActivity extends Activity {

    private ListView lvFuelStatistics;
    private ArrayList<c_FuelStats> gasPurchasesPerMonth;
    private FuelStatisticsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_activity_fuel_statistics);


        Bundle extras = getIntent().getExtras();
        Bundle data = extras.getBundle("data");

        gasPurchasesPerMonth = data.getParcelableArrayList("gasPurchasesPerMonth");
        lvFuelStatistics = findViewById(R.id.lvFuelStatistics);
        adapter = new FuelStatisticsAdapter(this);
        lvFuelStatistics.setAdapter(adapter);


        double prevMonthGasPriceAvg = data.getDouble("prevMonthGasPriceAvg");
        final TextView tvPrevMonthAvgGasPrice = findViewById(R.id.tvPrevMonthAvgGasPrice);
        tvPrevMonthAvgGasPrice.setText(String.format("$ %.2f", prevMonthGasPriceAvg));


        double prevMonthGasPriceTot = data.getDouble("prevMonthGasPriceTot");
        final TextView tvPrevMonthTotalGas = findViewById(R.id.tvPrevMonthTotalGas);
        tvPrevMonthTotalGas.setText(String.format("$ %.2f", prevMonthGasPriceTot));

        // TODO TESTING
        /* for (c_FuelStats cfs : gasPurchasesPerMonth){
            Log.i(cfs.getMonth(), String.valueOf(cfs.getAvg()));
        } */
    }

    private class FuelStatisticsAdapter extends ArrayAdapter<c_FuelStats> {

        public FuelStatisticsAdapter(Context context) {
            super(context, 0);
        }

        @Override
        public int getCount() {
            return gasPurchasesPerMonth.size();
        }

        @Override
        public c_FuelStats getItem(int position) {
            return gasPurchasesPerMonth.get(position);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View view = convertView;

            if (view == null){
                LayoutInflater inflater = c_FuelStatisticsActivity.this.getLayoutInflater();
                view = inflater.inflate(R.layout.c_fuel_statistic_row, null);
            }

            c_FuelStats stats = getItem(position);

            TextView c_StatsLabel = view.findViewById(R.id.c_StatsLabel);
            c_StatsLabel.setText(stats.getMonth());

            TextView c_StatsPurchase = view.findViewById(R.id.c_StatsPurchase);
            c_StatsPurchase.setText(String.format("$ %.2f", stats.getAvg()));

            return view;
        }
    }
}