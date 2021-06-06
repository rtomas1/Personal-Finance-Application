package ba.unsa.etf.rma.rma20tomasrobert24;

import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;


public class TransactionDetailFragment extends Fragment {
    private Button saveButton;
    private Button deleteButton;
    private EditText editTitle;
    private EditText editAmount;
    private EditText editDate;
    private EditText editDescription;
    private EditText editEndDate;
    private EditText editInterPeriod;
    private Spinner spinner;
    private TextInputLayout titleView;
    private TextInputLayout  amountView;
    private TextInputLayout  dateView;
    private TextInputLayout  endDateView;
    private TextInputLayout descriptionView;
    private TextInputLayout  transactionIntervalView;
    private EditText offlineMode;

    private boolean online = false;

    private Transaction returnedTransaction;
    private Transaction transaction;
    private int transactionPosition;
    private SimpleDateFormat format=new SimpleDateFormat("dd-MM-yyyy");

    private SpinnerAdapter spinnerAdapter;
    private AlertDialog alert;

    private ITransactionDetailPresenter presenter;
    private ITransactionListPresenter listPresenter;
    private IAccountPresenter accountPresenter;

    public ITransactionDetailPresenter getPresenter() {
        if (presenter == null) {
            presenter = new TransactionDetailPresenter(getActivity());
        }
        return presenter;
    }

    public ITransactionListPresenter getListPresenter(){
        if (listPresenter == null) {
            listPresenter = new TransactionListPresenter(getActivity());
        }
        return listPresenter;
    }
     public IAccountPresenter getAccountPresenter(){
        if(accountPresenter==null){
            accountPresenter=new AccountPresenter(getActivity());
        }
        return accountPresenter;
     }

    private OnReturn onReturn;
    public interface OnReturn {
        public void onSave(TransactionDetailFragment transactionDetailFragment);
        public void onDelete(TransactionDetailFragment transactionDetailFragment);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView=inflater.inflate(R.layout.fragment_transaction_detail, container, false);
        if (getArguments() != null && getArguments().containsKey("transaction")) {
            getPresenter().setTransaction(getArguments().getParcelable("transaction"));
            transactionPosition=getArguments().getInt("position");
            saveButton = (Button) fragmentView.findViewById(R.id.saveButton);
            deleteButton = (Button) fragmentView.findViewById(R.id.deleteButton);
            editTitle = (EditText) fragmentView.findViewById(R.id.titleEditView);
            editAmount = (EditText) fragmentView.findViewById(R.id.amountEditText);
            editDate = (EditText) fragmentView.findViewById(R.id.dateEditText);
            editDescription = (EditText) fragmentView.findViewById(R.id.descEditText);
            editEndDate = (EditText) fragmentView.findViewById(R.id.edEditText);
            editInterPeriod = (EditText) fragmentView.findViewById(R.id.interEditText);
            spinner = (Spinner) fragmentView.findViewById(R.id.typeSpinner);
            titleView = (TextInputLayout) fragmentView.findViewById(R.id.titleTextView);
            amountView = (TextInputLayout) fragmentView.findViewById(R.id.amountTextView);
            dateView = (TextInputLayout) fragmentView.findViewById(R.id.dateTextView);
            endDateView = (TextInputLayout) fragmentView.findViewById(R.id.edTextView);
            descriptionView = (TextInputLayout) fragmentView.findViewById(R.id.descTextView);
            transactionIntervalView = (TextInputLayout) fragmentView.findViewById(R.id.interTextView);
            offlineMode = (EditText) fragmentView.findViewById(R.id.offlineEditText);

            if(!NetworkChecker.isConnected(getContext())) {
                online=false;
                if (getPresenter().getTransaction().getTitle().equals("")) {
                    offlineMode.setText("Offline dodavanje");
                } else {
                    offlineMode.setText("Offline izmjena");
                }
            }


            onReturn=(OnReturn) getActivity();



            final String[] ClipcodesText = new String[]{"Individual income", "Individual payment", "Purchase", "Regular income", "Regular payment"};
            Integer[] ClipcodesImage = new Integer[]{R.drawable.individual_income, R.drawable.individual_payment, R.drawable.purchase, R.drawable.regular_income, R.drawable.regular_payment};
            spinnerAdapter = new SpinnerAdapter(getActivity(), R.layout.filter_element, ClipcodesText, ClipcodesImage);
            spinner.setAdapter(spinnerAdapter);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long itemID) {
                    if (position >= 0 && position < 5) {
                        if (!(position == 3 || position == 4)) {
                            editEndDate.setText("");
                            editInterPeriod.setText("");
                            editEndDate.setEnabled(false);
                            editInterPeriod.setEnabled(false);
                        } else {
                            editEndDate.setEnabled(true);
                            editInterPeriod.setEnabled(true);
                        }
                        if (position == 0 || position == 3) {
                            editDescription.setText("");
                            editDescription.setEnabled(false);
                        } else {
                            editDescription.setEnabled(true);
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            transaction=getPresenter().getTransaction();
            spinner.setSelection(Type.getPosition(transaction.getType()));

            editTitle.setText(transaction.getTitle());
            editAmount.setText(Double.toString(transaction.getAmount()));

            editDate.setText(format.format(transaction.getDate()));
            if (transaction.getType() == Type.REGULARINCOME || transaction.getType() == Type.REGULARPAYMENT) {
                editEndDate.setText(format.format(transaction.getEndDate()));
                editInterPeriod.setText(Integer.toString(transaction.getTransactionInterval()));

            } else {
                editEndDate.setText("");
                editInterPeriod.setText("");
            }

            if (!(transaction.getType() == Type.INDIVIDUALINCOME || transaction.getType() == Type.REGULARINCOME)) {
                editDescription.setText(transaction.getItemDescription());
            } else {
                editDescription.setText("");
            }


            editTitle.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (editTitle.getText() != null) validateTitle(editTitle.getText().toString());
                    else validateTitle("");
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (editTitle.getText() != null) validateTitle(editTitle.getText().toString());
                    else validateTitle("");
                }
            });

            editAmount.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (editAmount.getText() != null)
                        validateAmount(editAmount.getText().toString());
                    else validateAmount("");
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (editAmount.getText() != null)
                        validateAmount(editAmount.getText().toString());
                    else validateAmount("");
                }
            });
            editDate.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (editDate.getText() != null) validateDate(editDate.getText().toString());
                    else validateDate("");
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (editDate.getText() != null) validateDate(editDate.getText().toString());
                    else validateDate("");
                }
            });
            editEndDate.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (editEndDate.isEnabled()) {
                        if (editEndDate.getText() != null)
                            validateDate(editEndDate.getText().toString());
                        else validateDate("");
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (editEndDate.isEnabled()) {
                        if (editEndDate.getText() != null)
                            validateDate(editEndDate.getText().toString());
                        else validateDate("");
                    }
                }
            });

            editInterPeriod.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (editInterPeriod.isEnabled()) {
                        if (editInterPeriod.getText() != null)
                            validateInterval(editInterPeriod.getText().toString());
                        else validateInterval("");
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (editInterPeriod.isEnabled()) {
                        if (editInterPeriod.getText() != null)
                            validateInterval(editInterPeriod.getText().toString());
                        else validateInterval("null");
                    }
                }
            });
            editDescription.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (editDescription.isEnabled()) {
                        validateDescription(editDescription.getText().toString());
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (editDescription.isEnabled()) {
                        validateDescription(editDescription.getText().toString());
                    }
                }
            });

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Type type = Type.getType(spinner.getSelectedItemPosition());
                    if (type == Type.REGULARINCOME || type == Type.REGULARPAYMENT) {
                        if (!validateTitle(editTitle.getText().toString()) || !validateAmount(editAmount.getText().toString()) || !validateDate(editDate.getText().toString()) || !validateDate(editEndDate.getText().toString()) || !validateInterval(editInterPeriod.getText().toString()))
                            return;
                    } else {
                        if (!validateTitle(editTitle.getText().toString()) || !validateAmount(editAmount.getText().toString()) || !validateDate(editDate.getText().toString()))
                            return;
                    }
                    double amount = Double.parseDouble(editAmount.getText().toString());
                    if (type == Type.REGULARPAYMENT || type == Type.REGULARINCOME) {
                        if (editDescription.getText() == null) editDescription.setText("");
                        try {
                            returnedTransaction =
                                    new Transaction(transaction.getId(),format.parse(editDate.getText().toString()),
                                            Double.parseDouble(editAmount.getText().toString()),
                                            editTitle.getText().toString(),
                                            Type.getType(spinner.getSelectedItemPosition()),
                                            editDescription.getText().toString(),
                                            Integer.parseInt(editInterPeriod.getText().toString()),
                                            format.parse(editEndDate.getText().toString()));

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    } else if (type == Type.INDIVIDUALINCOME) {
                        try {
                            returnedTransaction =
                                    new Transaction(transaction.getId(),format.parse(editDate.getText().toString()),
                                            Double.parseDouble(editAmount.getText().toString()),
                                            editTitle.getText().toString(),
                                            Type.getType(spinner.getSelectedItemPosition()),
                                            null,
                                            0,
                                            null);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            returnedTransaction =
                                    new Transaction(transaction.getId(),format.parse(editDate.getText().toString()),
                                            Double.parseDouble(editAmount.getText().toString()),
                                            editTitle.getText().toString(),
                                            Type.getType(spinner.getSelectedItemPosition()),
                                            editDescription.getText().toString(),
                                            0,
                                            null);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                    getListPresenter().updateTransaction("update", returnedTransaction);
                    getAccountPresenter().updateBudget();
                    onReturn.onSave(TransactionDetailFragment.this);
                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                    builder1.setMessage("Are you sure that you want to delete this transaction?");
                    builder1.setCancelable(true);
                    builder1.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    getListPresenter().deleteTransaction("",transaction);
                                    getAccountPresenter().updateBudget();
                                    onReturn.onDelete( TransactionDetailFragment.this);
                                    dialog.cancel();
                                }
                            });
                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    alert = builder1.create();
                    alert.show();
                }
            });
        }


        return fragmentView;
    }
        private boolean validateTitle(String title){
            if(title.length() < 3){
                editTitle.setError("Title name is too short! ");
                return false;
            }
            else if(title.length() > 15){
                editTitle.setError("Title name is too long!");
                return false;
            }
            else{
                titleView.setError(null);
                titleView.setDefaultHintTextColor(ColorStateList.valueOf(Color.parseColor("#00ff00")));
                ColorStateList colorStateList = ColorStateList.valueOf(Color.parseColor("#00ff00"));
                ViewCompat.setBackgroundTintList(titleView, colorStateList);
            }
            return true;
        }

        private boolean validateAmount(String amount){
            String regex = "^\\d*\\.\\d+|\\d+\\.\\d*";
            if(amount.matches(regex) | amount.matches("[1-9][0-9]*")) {
                amountView.setError(null);
                amountView.setDefaultHintTextColor(ColorStateList.valueOf(Color.parseColor("#00ff00")));
                ColorStateList colorStateList = ColorStateList.valueOf(Color.parseColor("#00ff00"));
                ViewCompat.setBackgroundTintList(amountView, colorStateList);
                return true;
            }
            else {
                amountView.setError("Wrong input!");
            }
            return false;
        }

        private boolean validateDate(String date){
            String regex="^(((0[1-9]|[12][0-9]|30)[-/]?(0[13-9]|1[012])|31[-/]?(0[13578]|1[02])|(0[1-9]|1[0-9]|2[0-8])[-/]?02)[-/]?[0-9]{4}|29[-/]?02[-/]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$";
            date.trim();
            if(date.matches(regex)) {
                dateView.setError(null);
                dateView.setDefaultHintTextColor(ColorStateList.valueOf(Color.parseColor("#00ff00")));
                ColorStateList colorStateList = ColorStateList.valueOf(Color.parseColor("#00ff00"));
                ViewCompat.setBackgroundTintList(dateView, colorStateList);
                return true;
            }
            return false;
        }

        private boolean validateInterval(String interval){
            if(interval.matches("[1-9][0-9]*")){
                transactionIntervalView.setError(null);
                transactionIntervalView.setDefaultHintTextColor(ColorStateList.valueOf(Color.parseColor("#00ff00")));
                ColorStateList colorStateList = ColorStateList.valueOf(Color.parseColor("#00ff00"));
                ViewCompat.setBackgroundTintList(transactionIntervalView, colorStateList);
                return true;
            }
            else{
                transactionIntervalView.setError("Wrong input!");
            }
            return false;
        }

        private boolean validateDescription(String description){
            descriptionView.setDefaultHintTextColor(ColorStateList.valueOf(Color.parseColor("#00ff00")));
            ColorStateList colorStateList = ColorStateList.valueOf(Color.parseColor("#00ff00"));
            ViewCompat.setBackgroundTintList(descriptionView, colorStateList);
            return true;
        }

        public void setOffline(){
            online=false;
        }
}
