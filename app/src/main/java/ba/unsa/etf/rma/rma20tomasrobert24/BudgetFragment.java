package ba.unsa.etf.rma.rma20tomasrobert24;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class BudgetFragment extends Fragment implements IAccountView{
    private EditText editBudget;
    private EditText editLimit;
    private EditText editMonthLimit;
    private TextView budgetText;
    private TextView limitText;
    private TextView monthLimitText;
    private Button saveButton;
    private double monthLimit=AccountModel.account.getMonthLimit();
    private double limit=AccountModel.account.getTotalLimit();

    private IAccountPresenter presenter;
    private Account account=AccountModel.account;

    public IAccountPresenter getPresenter() {
        if (presenter == null) {
            presenter = new AccountPresenter(this, getActivity());
        }
        return presenter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView= inflater.inflate(R.layout.fragment_budget, container, false);
        editBudget=fragmentView.findViewById(R.id.budgetEditText);
        editLimit=fragmentView.findViewById(R.id.limitEditText);
        editMonthLimit=fragmentView.findViewById(R.id.monthLimitEditText);
        budgetText=fragmentView.findViewById(R.id.budgetTextView);
        limitText=fragmentView.findViewById(R.id.limitTextView);
        monthLimitText=fragmentView.findViewById(R.id.monthLimitTextView);
        saveButton=fragmentView.findViewById(R.id.button);
        if(NetworkChecker.isConnected(getContext())){
            getPresenter().getAccount("");
        }
        account=getPresenter().get();

        editBudget.setText(account.getBudget().toString());
        editMonthLimit.setText(account.getMonthLimit().toString());
        editLimit.setText(account.getTotalLimit().toString());

        editLimit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editLimit.getText() != null && !editLimit.getText().toString().equals("")){
                    double limitt=Double.parseDouble(editLimit.getText().toString());
                    limit=limitt;
                    //getPresenter().changeLimit(limit);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (editLimit.getText() != null && !editLimit.getText().toString().equals("")){
                    double limitt=Double.parseDouble(editLimit.getText().toString());
                    limit=limitt;
                    //getPresenter().changeLimit(limit);
                }
            }
        });

        editMonthLimit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editMonthLimit.getText() != null && !editMonthLimit.getText().toString().equals("")){
                    double limitt=Double.parseDouble(editMonthLimit.getText().toString());
                    monthLimit=limitt;
                    //getPresenter().changeLimit(limit);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (editMonthLimit.getText() != null && !editMonthLimit.getText().toString().equals("")){
                    double limitt=Double.parseDouble(editMonthLimit.getText().toString());
                    monthLimit=limitt;
                    //getPresenter().changeLimit(limit);
                }
            }
        });
        saveButton.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPresenter().editAccount(new Account(AccountModel.account.getBudget(), new Double(limit), new Double(monthLimit)));
            }
        });


        return fragmentView;
    }


    @Override
    public void setAccount(Account account) {
        this.account.setMonthLimit(account.getMonthLimit());
        this.account.setTotalLimit(account.getTotalLimit());
        this.account.setBudget(account.getBudget());
        editBudget.setText(account.getBudget().toString());
        editMonthLimit.setText(account.getMonthLimit().toString());
        editLimit.setText(account.getTotalLimit().toString());
    }

    @Override
    public void notifyAccountChanged() {

    }
}
