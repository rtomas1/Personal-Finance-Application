package ba.unsa.etf.rma.rma20tomasrobert24;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class TransactionListFragment extends Fragment implements ITransactionListView{
    private Button buttonlijevo;
    private Button buttondesno;
    private ListView listTransactions;
    private EditText textDate;
    private Spinner spinnerFilter;
    private Spinner spinnerSort;
    private Button buttonAddTransaction;

    private ITransactionListPresenter transactionListPresenter;

    private TransactionListAdapter transactionListAdapter;
    private SpinnerAdapter adapterFilter;
    private SpinnerAdapter adapterSort;
    private Account account;
    private EditText editBudget;
    private EditText editLimit;

    private Date currentDate=new Date();

    public ITransactionListPresenter getPresenter() {
        if (transactionListPresenter == null) {
            transactionListPresenter = new TransactionListPresenter(this, getActivity());
        }
        return transactionListPresenter;
    }

    private OnItemClick onItemClick;
    public interface OnItemClick {
        public void onItemClicked(Transaction transaction, TransactionListFragment transactionListFragment);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView=inflater.inflate(R.layout.fragment_transaction_list, container, false);
        SimpleDateFormat formatDate=new SimpleDateFormat("MMMM, yyyy", Locale.US);
        textDate=(EditText) fragmentView.findViewById(R.id.editText);
        textDate.setText(formatDate.format(currentDate));
        transactionListAdapter=new TransactionListAdapter(getActivity(), R.layout.list_element, new ArrayList<Transaction>());
        listTransactions= (ListView) fragmentView.findViewById(R.id.listView);
        listTransactions.setAdapter(transactionListAdapter);
        getPresenter().refreshTransactions(currentDate, 0, 0);

        final String [] ClipcodesText = new String[]{"Filter by:","Individual income", "Individual payment", "Purchase", "Regular income", "Regular payment"};
        Integer [] ClipcodesImage = new Integer[]{R.drawable.blank,R.drawable.individual_income, R.drawable.individual_payment, R.drawable.purchase, R.drawable.regular_income, R.drawable.regular_payment};
        spinnerFilter = (Spinner)fragmentView.findViewById(R.id.spinner);
        adapterFilter = new SpinnerAdapter(getActivity(), R.layout.filter_element, ClipcodesText, ClipcodesImage);
        spinnerFilter.setAdapter(adapterFilter);

        spinnerFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                transactionListAdapter=new TransactionListAdapter(getActivity(), R.layout.list_element, new ArrayList<Transaction>());
                listTransactions.setAdapter(transactionListAdapter);
                getPresenter().refreshTransactions(currentDate, spinnerFilter.getSelectedItemPosition(), spinnerSort.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                transactionListAdapter=new TransactionListAdapter(getActivity(), R.layout.list_element, new ArrayList<Transaction>());
                listTransactions.setAdapter(transactionListAdapter);
                getPresenter().refreshTransactions(currentDate, spinnerFilter.getSelectedItemPosition(), spinnerSort.getSelectedItemPosition());
            }

        });

        final String [] sortText = new String[]{"Sort by:","Price - Ascending","Price - Descending","Title - Ascending","Title - Descending", "Date - Ascending", "Date - Descending"};
        Integer [] imagesSort = new Integer[]{R.drawable.blank,R.drawable.blank, R.drawable.blank, R.drawable.blank, R.drawable.blank, R.drawable.blank, R.drawable.blank};
        spinnerSort = (Spinner)fragmentView.findViewById(R.id.spinner2);
        adapterSort = new SpinnerAdapter(getActivity(), R.layout.filter_element, sortText, imagesSort);
        spinnerSort.setAdapter(adapterSort);


        spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                transactionListAdapter=new TransactionListAdapter(getActivity(), R.layout.list_element, new ArrayList<Transaction>());
                listTransactions.setAdapter(transactionListAdapter);
                getPresenter().refreshTransactions(currentDate, spinnerFilter.getSelectedItemPosition(), spinnerSort.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                transactionListAdapter=new TransactionListAdapter(getActivity(), R.layout.list_element, new ArrayList<Transaction>());
                listTransactions.setAdapter(transactionListAdapter);
                getPresenter().refreshTransactions(currentDate, spinnerFilter.getSelectedItemPosition(), spinnerSort.getSelectedItemPosition());
            }
        });

        buttonlijevo=(Button) fragmentView.findViewById(R.id.buttonlijevo);
        buttonlijevo.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(currentDate);
                cal.add(Calendar.MONTH, -1);
                currentDate = cal.getTime();
                SimpleDateFormat formatDate=new SimpleDateFormat("MMMM, yyyy", Locale.US);
                textDate.setText(formatDate.format(currentDate));
                transactionListAdapter=new TransactionListAdapter(getActivity(), R.layout.list_element, new ArrayList<Transaction>());
                listTransactions.setAdapter(transactionListAdapter);
                getPresenter().refreshTransactions(currentDate, spinnerFilter.getSelectedItemPosition(), spinnerSort.getSelectedItemPosition());
            }
        });

        buttondesno=(Button) fragmentView.findViewById(R.id.buttondesno);
        buttondesno.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(currentDate);
                cal.add(Calendar.MONTH, 1);
                currentDate = cal.getTime();
                SimpleDateFormat formatDate=new SimpleDateFormat("MMMM, yyyy", Locale.US);
                textDate.setText(formatDate.format(currentDate));
                transactionListAdapter=new TransactionListAdapter(getActivity(), R.layout.list_element, new ArrayList<Transaction>());

                listTransactions.setAdapter(transactionListAdapter);
                getPresenter().refreshTransactions(currentDate, spinnerFilter.getSelectedItemPosition(), spinnerSort.getSelectedItemPosition());
            }
        });
        listTransactions.setOnItemClickListener(listItemClickListener);

        buttonAddTransaction=(Button) fragmentView.findViewById(R.id.button2);

        buttonAddTransaction.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Transaction newTransaction=new Transaction(currentDate, new Double(0),"",Type.INDIVIDUALPAYMENT, "", 0, null);
                getPresenter().addTransaction(newTransaction);
                onItemClick.onItemClicked(newTransaction, TransactionListFragment.this);
            }
        });
        account=new Account(5680,10000,2000);
        editBudget=(EditText) fragmentView.findViewById(R.id.budget);
        editLimit=(EditText) fragmentView.findViewById(R.id.limit);
        editLimit.setText(account.getMonthLimit().toString());
        editBudget.setText(account.getBudget().toString());

        onItemClick=(OnItemClick) getActivity();
        return fragmentView;
    }

    @Override
    public void setTransactions(ArrayList<Transaction> transactions) {
        transactionListAdapter.setTransactions(transactions);
    }

    @Override
    public void notifyTransactionListDataSetChanged() {
    }

    private AdapterView.OnItemClickListener listItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Transaction transaction = transactionListAdapter.getTransaction(position);
            onItemClick.onItemClicked(transaction, TransactionListFragment.this);
        }
    };


}
