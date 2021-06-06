package ba.unsa.etf.rma.rma20tomasrobert24;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


public class BudgetFragment extends Fragment {
    private EditText editBudget;
    private EditText editLimit;
    private EditText editMonthLimit;
    private TextView budgetText;
    private TextView limitText;
    private TextView monthLimitText;

    private IAccountPresenter presenter;

    public IAccountPresenter getPresenter() {
        if (presenter == null) {
            presenter = new AccountPresenter(getActivity());
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

        Account account=getPresenter().get();

        editBudget.setText(account.getBudget().toString());
        editMonthLimit.setText(account.getMonthLimit().toString());
        editLimit.setText(account.getTotalLimit().toString());

        editLimit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editLimit.getText() != null){
                    int limit=Integer.parseInt(editLimit.getText().toString());
                    getPresenter().changeLimit(limit);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (editLimit.getText() != null){
                    int limit=Integer.parseInt(editLimit.getText().toString());
                    getPresenter().changeLimit(limit);
                }
            }
        });

        editMonthLimit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editMonthLimit.getText() != null){
                    int limit=Integer.parseInt(editMonthLimit.getText().toString());
                    getPresenter().changeLimit(limit);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (editMonthLimit.getText() != null){
                    int limit=Integer.parseInt(editMonthLimit.getText().toString());
                    getPresenter().changeLimit(limit);
                }
            }
        });

        return fragmentView;
    }
}
