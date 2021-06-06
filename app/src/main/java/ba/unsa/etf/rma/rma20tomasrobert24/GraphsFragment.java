package ba.unsa.etf.rma.rma20tomasrobert24;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Date;


public class GraphsFragment extends Fragment {
    private TextView chooseTimeUnit;
    private Spinner spinnerTime;
    private BarChart potrosnjaChart;
    private BarChart zaradaChart;
    private BarChart ukupnoChart;

    private SpinnerAdapter adapterTime;

    private ITransactionListPresenter transactionListPresenter;
    public ITransactionListPresenter getPresenter() {
        if (transactionListPresenter == null) {
            transactionListPresenter = new TransactionListPresenter(getActivity());
        }
        return transactionListPresenter;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView= inflater.inflate(R.layout.fragment_graphs, container, false);
        chooseTimeUnit=fragmentView.findViewById(R.id.chooseTextView);
        spinnerTime=fragmentView.findViewById(R.id.spinnerTime);
        potrosnjaChart=fragmentView.findViewById(R.id.potrosnjaChart);
        zaradaChart=fragmentView.findViewById(R.id.zaradaChart);
        ukupnoChart=fragmentView.findViewById(R.id.ukupnoChart);


        ArrayList<BarEntry> potrosnja=new ArrayList<>();
        potrosnja.add(new BarEntry(1, (float) getPresenter().getOutcomeByMonth(1)));
        potrosnja.add(new BarEntry(2, (float) getPresenter().getOutcomeByMonth(2)));
        potrosnja.add(new BarEntry(3, (float) getPresenter().getOutcomeByMonth(3)));
        potrosnja.add(new BarEntry(4, (float) getPresenter().getOutcomeByMonth(4)));
        potrosnja.add(new BarEntry(5, (float) getPresenter().getOutcomeByMonth(5)));
        potrosnja.add(new BarEntry(6, (float) getPresenter().getOutcomeByMonth(6)));
        potrosnja.add(new BarEntry(7, (float) getPresenter().getOutcomeByMonth(7)));
        potrosnja.add(new BarEntry(8, (float) getPresenter().getOutcomeByMonth(8)));
        potrosnja.add(new BarEntry(9, (float) getPresenter().getOutcomeByMonth(9)));
        potrosnja.add(new BarEntry(10, (float) getPresenter().getOutcomeByMonth(10)));
        potrosnja.add(new BarEntry(11, (float) getPresenter().getOutcomeByMonth(11)));
        potrosnja.add(new BarEntry(12, (float) getPresenter().getOutcomeByMonth(12)));

        BarDataSet barDataSetPotrosnja=new BarDataSet(potrosnja, "Outcome");
        barDataSetPotrosnja.setColor(ColorTemplate.rgb("ff0000"));
        BarData dataPotrosnja=new BarData(barDataSetPotrosnja);
        potrosnjaChart.setData(dataPotrosnja);

        ArrayList<BarEntry> zarada=new ArrayList<>();
        zarada.add(new BarEntry(1, (float) getPresenter().getIncomeByMonth(1)));
        zarada.add(new BarEntry(2, (float) getPresenter().getIncomeByMonth(2)));
        zarada.add(new BarEntry(3, (float) getPresenter().getIncomeByMonth(3)));
        zarada.add(new BarEntry(4, (float) getPresenter().getIncomeByMonth(4)));
        zarada.add(new BarEntry(5, (float) getPresenter().getIncomeByMonth(5)));
        zarada.add(new BarEntry(6, (float) getPresenter().getIncomeByMonth(6)));
        zarada.add(new BarEntry(7, (float) getPresenter().getIncomeByMonth(7)));
        zarada.add(new BarEntry(8, (float) getPresenter().getIncomeByMonth(8)));
        zarada.add(new BarEntry(9, (float) getPresenter().getIncomeByMonth(9)));
        zarada.add(new BarEntry(10, (float) getPresenter().getIncomeByMonth(10)));
        zarada.add(new BarEntry(11, (float) getPresenter().getIncomeByMonth(11)));
        zarada.add(new BarEntry(12, (float) getPresenter().getIncomeByMonth(12)));

        BarDataSet barDataSetZarada=new BarDataSet(zarada, "Income");
        barDataSetZarada.setColor(ColorTemplate.rgb("ff0000"));
        BarData dataZarada=new BarData(barDataSetZarada);
        zaradaChart.setData(dataZarada);


        ArrayList<BarEntry> ukupno=new ArrayList<>();
        ukupno.add(new BarEntry(1, (float) (getPresenter().getIncomeByMonth(1)-getPresenter().getOutcomeByMonth(1))));
        ukupno.add(new BarEntry(2, (float) (getPresenter().getIncomeByMonth(2)-getPresenter().getOutcomeByMonth(2))));
        ukupno.add(new BarEntry(3, (float) (getPresenter().getIncomeByMonth(3)-getPresenter().getOutcomeByMonth(3))));
        ukupno.add(new BarEntry(4, (float) (getPresenter().getIncomeByMonth(4)-getPresenter().getOutcomeByMonth(4))));
        ukupno.add(new BarEntry(5, (float) (getPresenter().getIncomeByMonth(5)-getPresenter().getOutcomeByMonth(5))));
        ukupno.add(new BarEntry(6, (float) (getPresenter().getIncomeByMonth(6)-getPresenter().getOutcomeByMonth(6))));
        ukupno.add(new BarEntry(7, (float) (getPresenter().getIncomeByMonth(7)-getPresenter().getOutcomeByMonth(7))));
        ukupno.add(new BarEntry(8, (float) (getPresenter().getIncomeByMonth(8)-getPresenter().getOutcomeByMonth(8))));
        ukupno.add(new BarEntry(9, (float) (getPresenter().getIncomeByMonth(9)-getPresenter().getOutcomeByMonth(9))));
        ukupno.add(new BarEntry(10, (float) (getPresenter().getIncomeByMonth(10)-getPresenter().getOutcomeByMonth(10))));
        ukupno.add(new BarEntry(11, (float) (getPresenter().getIncomeByMonth(11)-getPresenter().getOutcomeByMonth(11))));
        ukupno.add(new BarEntry(12, (float) (getPresenter().getIncomeByMonth(12)-getPresenter().getOutcomeByMonth(12))));

        BarDataSet barDataSetUkupno=new BarDataSet(ukupno, "Total");
        barDataSetUkupno.setColor(ColorTemplate.rgb("ff0000"));
        BarData dataUkupno=new BarData(barDataSetUkupno);
        ukupnoChart.setData(dataUkupno);

        final String [] ClipcodesText = new String[]{"Month","Day", "Week"};
        Integer [] ClipcodesImage = new Integer[]{R.drawable.blank, R.drawable.blank, R.drawable.blank};
        adapterTime = new SpinnerAdapter(getActivity(), R.layout.filter_element, ClipcodesText, ClipcodesImage);
        spinnerTime.setAdapter(adapterTime);

        spinnerTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(position==0){
                    ArrayList<BarEntry> potrosnja=new ArrayList<>();
                    potrosnja.add(new BarEntry(1, (float) getPresenter().getOutcomeByMonth(1)));
                    potrosnja.add(new BarEntry(2, (float) getPresenter().getOutcomeByMonth(2)));
                    potrosnja.add(new BarEntry(3, (float) getPresenter().getOutcomeByMonth(3)));
                    potrosnja.add(new BarEntry(4, (float) getPresenter().getOutcomeByMonth(4)));
                    potrosnja.add(new BarEntry(5, (float) getPresenter().getOutcomeByMonth(5)));
                    potrosnja.add(new BarEntry(6, (float) getPresenter().getOutcomeByMonth(6)));
                    potrosnja.add(new BarEntry(7, (float) getPresenter().getOutcomeByMonth(7)));
                    potrosnja.add(new BarEntry(8, (float) getPresenter().getOutcomeByMonth(8)));
                    potrosnja.add(new BarEntry(9, (float) getPresenter().getOutcomeByMonth(9)));
                    potrosnja.add(new BarEntry(10, (float) getPresenter().getOutcomeByMonth(10)));
                    potrosnja.add(new BarEntry(11, (float) getPresenter().getOutcomeByMonth(11)));
                    potrosnja.add(new BarEntry(12, (float) getPresenter().getOutcomeByMonth(12)));

                    BarDataSet barDataSetPotrosnja=new BarDataSet(potrosnja, "Outcome");
                    barDataSetPotrosnja.setColor(ColorTemplate.rgb("ff0000"));
                    BarData dataPotrosnja=new BarData(barDataSetPotrosnja);
                    potrosnjaChart.setData(dataPotrosnja);
                    potrosnjaChart.invalidate();

                    ArrayList<BarEntry> zarada=new ArrayList<>();
                    zarada.add(new BarEntry(1, (float) getPresenter().getIncomeByMonth(1)));
                    zarada.add(new BarEntry(2, (float) getPresenter().getIncomeByMonth(2)));
                    zarada.add(new BarEntry(3, (float) getPresenter().getIncomeByMonth(3)));
                    zarada.add(new BarEntry(4, (float) getPresenter().getIncomeByMonth(4)));
                    zarada.add(new BarEntry(5, (float) getPresenter().getIncomeByMonth(5)));
                    zarada.add(new BarEntry(6, (float) getPresenter().getIncomeByMonth(6)));
                    zarada.add(new BarEntry(7, (float) getPresenter().getIncomeByMonth(7)));
                    zarada.add(new BarEntry(8, (float) getPresenter().getIncomeByMonth(8)));
                    zarada.add(new BarEntry(9, (float) getPresenter().getIncomeByMonth(9)));
                    zarada.add(new BarEntry(10, (float) getPresenter().getIncomeByMonth(10)));
                    zarada.add(new BarEntry(11, (float) getPresenter().getIncomeByMonth(11)));
                    zarada.add(new BarEntry(12, (float) getPresenter().getIncomeByMonth(12)));

                    BarDataSet barDataSetZarada=new BarDataSet(zarada, "Income");
                    barDataSetZarada.setColor(ColorTemplate.rgb("ff0000"));
                    BarData dataZarada=new BarData(barDataSetZarada);
                    zaradaChart.setData(dataZarada);
                    zaradaChart.invalidate();


                    ArrayList<BarEntry> ukupno=new ArrayList<>();
                    ukupno.add(new BarEntry(1, (float) (getPresenter().getIncomeByMonth(1)-getPresenter().getOutcomeByMonth(1))));
                    ukupno.add(new BarEntry(2, (float) (getPresenter().getIncomeByMonth(2)-getPresenter().getOutcomeByMonth(2))));
                    ukupno.add(new BarEntry(3, (float) (getPresenter().getIncomeByMonth(3)-getPresenter().getOutcomeByMonth(3))));
                    ukupno.add(new BarEntry(4, (float) (getPresenter().getIncomeByMonth(4)-getPresenter().getOutcomeByMonth(4))));
                    ukupno.add(new BarEntry(5, (float) (getPresenter().getIncomeByMonth(5)-getPresenter().getOutcomeByMonth(5))));
                    ukupno.add(new BarEntry(6, (float) (getPresenter().getIncomeByMonth(6)-getPresenter().getOutcomeByMonth(6))));
                    ukupno.add(new BarEntry(7, (float) (getPresenter().getIncomeByMonth(7)-getPresenter().getOutcomeByMonth(7))));
                    ukupno.add(new BarEntry(8, (float) (getPresenter().getIncomeByMonth(8)-getPresenter().getOutcomeByMonth(8))));
                    ukupno.add(new BarEntry(9, (float) (getPresenter().getIncomeByMonth(9)-getPresenter().getOutcomeByMonth(9))));
                    ukupno.add(new BarEntry(10, (float) (getPresenter().getIncomeByMonth(10)-getPresenter().getOutcomeByMonth(10))));
                    ukupno.add(new BarEntry(11, (float) (getPresenter().getIncomeByMonth(11)-getPresenter().getOutcomeByMonth(11))));
                    ukupno.add(new BarEntry(12, (float) (getPresenter().getIncomeByMonth(12)-getPresenter().getOutcomeByMonth(12))));

                    BarDataSet barDataSetUkupno=new BarDataSet(ukupno, "Total");
                    barDataSetUkupno.setColor(ColorTemplate.rgb("ff0000"));
                    BarData dataUkupno=new BarData(barDataSetUkupno);
                    ukupnoChart.setData(dataUkupno);
                    ukupnoChart.invalidate();
                }
                else if(position==1){
                    ArrayList<BarEntry> potrosnja=new ArrayList<>();
                    for(int i=1;i<=31;i++){
                        potrosnja.add(new BarEntry(i, (float) getPresenter().getOutcomeByDay(i)));
                    }

                    BarDataSet barDataSetPotrosnja=new BarDataSet(potrosnja, "Outcome");
                    barDataSetPotrosnja.setColor(ColorTemplate.rgb("ff0000"));
                    BarData dataPotrosnja=new BarData(barDataSetPotrosnja);
                    potrosnjaChart.setData(dataPotrosnja);
                    potrosnjaChart.invalidate();

                    ArrayList<BarEntry> zarada=new ArrayList<>();
                    for(int i=1;i<=31;i++){
                        zarada.add(new BarEntry(i, (float) getPresenter().getIncomeByDay(i)));
                    }

                    BarDataSet barDataSetZarada=new BarDataSet(zarada, "Income");
                    barDataSetZarada.setColor(ColorTemplate.rgb("ff0000"));
                    BarData dataZarada=new BarData(barDataSetZarada);
                    zaradaChart.setData(dataZarada);
                    zaradaChart.invalidate();


                    ArrayList<BarEntry> ukupno=new ArrayList<>();
                    for(int i=1;i<=31;i++){
                        ukupno.add(new BarEntry(i, (float) (getPresenter().getIncomeByDay(i)-getPresenter().getOutcomeByDay(i))));
                    }

                    BarDataSet barDataSetUkupno=new BarDataSet(ukupno, "Total");
                    barDataSetUkupno.setColor(ColorTemplate.rgb("ff0000"));
                    BarData dataUkupno=new BarData(barDataSetUkupno);
                    ukupnoChart.setData(dataUkupno);
                    ukupnoChart.invalidate();
                }
                else if(position==2){
                    ArrayList<BarEntry> potrosnja=new ArrayList<>();
                    for(int i=1;i<=6;i++){
                        potrosnja.add(new BarEntry(i, (float) getPresenter().getOutcomeByWeek(i)));
                    }

                    BarDataSet barDataSetPotrosnja=new BarDataSet(potrosnja, "Outcome");
                    barDataSetPotrosnja.setColor(ColorTemplate.rgb("ff0000"));
                    BarData dataPotrosnja=new BarData(barDataSetPotrosnja);
                    potrosnjaChart.setData(dataPotrosnja);
                    potrosnjaChart.invalidate();

                    ArrayList<BarEntry> zarada=new ArrayList<>();
                    for(int i=1;i<=6;i++){
                        zarada.add(new BarEntry(i, (float) getPresenter().getIncomeByWeek(i)));
                    }

                    BarDataSet barDataSetZarada=new BarDataSet(zarada, "Income");
                    barDataSetZarada.setColor(ColorTemplate.rgb("ff0000"));
                    BarData dataZarada=new BarData(barDataSetZarada);
                    zaradaChart.setData(dataZarada);
                    zaradaChart.invalidate();


                    ArrayList<BarEntry> ukupno=new ArrayList<>();
                    for(int i=1;i<=6;i++){
                        ukupno.add(new BarEntry(i, (float) (getPresenter().getIncomeByWeek(i)-getPresenter().getOutcomeByWeek(i))));
                    }

                    BarDataSet barDataSetUkupno=new BarDataSet(ukupno, "Total");
                    barDataSetUkupno.setColor(ColorTemplate.rgb("ff0000"));
                    BarData dataUkupno=new BarData(barDataSetUkupno);
                    ukupnoChart.setData(dataUkupno);
                    ukupnoChart.invalidate();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


        return fragmentView;
    }
}
