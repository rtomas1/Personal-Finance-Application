package ba.unsa.etf.rma.rma20tomasrobert24;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class TransactionListPresenter implements ITransactionListPresenter, TransactionGetAPI.OnTransactionsGetDone , TransactionDeleteAPI.OnTransactionDeleteDone, TransactionPostAPI.OnTransactionPostDone{
    private ITransactionListView view;
    private ITransactionListInteractor interactor;
    private SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
    private Context context;

    public TransactionListPresenter(ITransactionListView view, Context context) {
        this.view       = view;
        this.interactor = new TransactionListInteractor();
        this.context    = context;
    }

    public TransactionListPresenter(Context context){
        this.interactor = new TransactionListInteractor();
        this.context    = context;
    }
    @Override
    public void refreshTransactions() {
        view.setTransactions(interactor.get());
        view.notifyTransactionListDataSetChanged();
    }
    @Override
    public void refreshTransactions(Date date, int filterId, int sortId){
        view.setTransactions(interactor.getTransactions(date, filterId, sortId));
        view.notifyTransactionListDataSetChanged();
    }
    @Override
    public void updateTransaction(String query,Transaction transaction){
        if(NetworkChecker.isConnected(context)) {
            new TransactionPostAPI(this, transaction).execute(query);
        }
        else{
            ContentResolver cr=context.getApplicationContext().getContentResolver();
            Uri uri=Uri.parse("content://rma.provider.transactions/elements");
            ContentValues cv=new ContentValues();
            cv.put(TransactionDBOpenHelper.TRANSACTION_ID, transaction.getId());
            cv.put(TransactionDBOpenHelper.TRANSACTION_DATE, sdf.format(transaction.getDate()));
            cv.put(TransactionDBOpenHelper.TRANSACTION_AMOUNT, transaction.getAmount());
            cv.put(TransactionDBOpenHelper.TRANSACTION_TITLE, transaction.getTitle());
            cv.put(TransactionDBOpenHelper.TRANSACTION_TYPE, dajTip(transaction.getType()));
            cv.put(TransactionDBOpenHelper.TRANSACTION_ITEM_DESCRIPTION, transaction.getItemDescription());
            if(transaction.getType()==Type.REGULARPAYMENT || transaction.getType()==Type.REGULARINCOME){
                cv.put(TransactionDBOpenHelper.TRANSACTION_INTERVAL, transaction.getTransactionInterval());
                cv.put(TransactionDBOpenHelper.TRANSACTION_END_DATE, sdf.format(transaction.getEndDate()));
            }
            else{
                cv.put(TransactionDBOpenHelper.TRANSACTION_INTERVAL, 0);
                cv.put(TransactionDBOpenHelper.TRANSACTION_END_DATE, "null");
            }
            cv.put(TransactionDBOpenHelper.TRANSACTION_CHANGE, "update");
            cr.insert(uri, cv);
            int pos=getPosition(transaction);
            if(pos==-1){
                return;
            }
            TransactionModel.transactions.set(pos, transaction);
        }
    }

    @Override
    public void getTransactions(String query){
        new TransactionGetAPI(this).execute(query);
    }

    @Override
    public void deleteTransaction(String query, Transaction transaction){
        if(NetworkChecker.isConnected(context)) {
            new TransactionDeleteAPI(this, transaction).execute(query);
        }
        else{
            ContentResolver cr=context.getApplicationContext().getContentResolver();
            Uri uri=Uri.parse("content://rma.provider.transactions/elements");
            ContentValues cv=new ContentValues();
            cv.put(TransactionDBOpenHelper.TRANSACTION_ID, transaction.getId());
            cv.put(TransactionDBOpenHelper.TRANSACTION_DATE, sdf.format(transaction.getDate()));
            cv.put(TransactionDBOpenHelper.TRANSACTION_AMOUNT, transaction.getAmount());
            cv.put(TransactionDBOpenHelper.TRANSACTION_TITLE, transaction.getTitle());
            cv.put(TransactionDBOpenHelper.TRANSACTION_TYPE, dajTip(transaction.getType()));
            cv.put(TransactionDBOpenHelper.TRANSACTION_ITEM_DESCRIPTION, transaction.getItemDescription());
            if(transaction.getType()==Type.REGULARPAYMENT || transaction.getType()==Type.REGULARINCOME){
                cv.put(TransactionDBOpenHelper.TRANSACTION_INTERVAL, transaction.getTransactionInterval());
                cv.put(TransactionDBOpenHelper.TRANSACTION_END_DATE, sdf.format(transaction.getEndDate()));
            }
            else{
                cv.put(TransactionDBOpenHelper.TRANSACTION_INTERVAL, 0);
                cv.put(TransactionDBOpenHelper.TRANSACTION_END_DATE, "null");
            }
            cv.put(TransactionDBOpenHelper.TRANSACTION_CHANGE, "delete");
            cr.insert(uri, cv);
            TransactionModel.transactions.remove(transaction);
        }
    }
    @Override
    public void addTransaction(Transaction transaction){
        if(NetworkChecker.isConnected(context)){
            new TransactionPostAPI(this, transaction).execute("");
        }
        else{
            ContentResolver cr=context.getApplicationContext().getContentResolver();
            Uri uri=Uri.parse("content://rma.provider.transactions/elements");
            ContentValues cv=new ContentValues();
            transaction.setId(newId());
            cv.put(TransactionDBOpenHelper.TRANSACTION_ID, transaction.getId());
            cv.put(TransactionDBOpenHelper.TRANSACTION_DATE, sdf.format(transaction.getDate()));
            cv.put(TransactionDBOpenHelper.TRANSACTION_AMOUNT, transaction.getAmount());
            cv.put(TransactionDBOpenHelper.TRANSACTION_TITLE, transaction.getTitle());
            cv.put(TransactionDBOpenHelper.TRANSACTION_TYPE, dajTip(transaction.getType()));
            cv.put(TransactionDBOpenHelper.TRANSACTION_ITEM_DESCRIPTION, transaction.getItemDescription());
            if(transaction.getType()==Type.REGULARPAYMENT || transaction.getType()==Type.REGULARINCOME){
                cv.put(TransactionDBOpenHelper.TRANSACTION_INTERVAL, transaction.getTransactionInterval());
                cv.put(TransactionDBOpenHelper.TRANSACTION_END_DATE, sdf.format(transaction.getEndDate()));
            }
            else{
                cv.put(TransactionDBOpenHelper.TRANSACTION_INTERVAL, 0);
                cv.put(TransactionDBOpenHelper.TRANSACTION_END_DATE, "null");
            }
            cv.put(TransactionDBOpenHelper.TRANSACTION_CHANGE, "add");
            cr.insert(uri, cv);
            TransactionModel.transactions.add(transaction);
        }
    }

    @Override
    public double getOutcomeByMonth(int month){
        return interactor.getOutcomeByMonth(month);
    }

    @Override
    public double getIncomeByMonth(int month){
        return interactor.getIncomeByMonth(month);
    }

    @Override
    public double getOutcomeByDay(int day){
        return interactor.getOutcomeByDay(day);
    }

    @Override
    public double getIncomeByDay(int day){
        return interactor.getIncomeByDay(day);
    }

    @Override
    public double getOutcomeByWeek(int week){
        return interactor.getOutcomeByWeek(week);
    }

    @Override
    public double getIncomeByWeek(int week){
        return interactor.getIncomeByWeek(week);
    }

    @Override
    public void onTransactionGet(ArrayList<Transaction> results) {
        TransactionModel.transactions.clear();
        TransactionModel.transactions.addAll(results);
        Date date=new Date();
        view.setTransactions(interactor.getTransactions(date,0,0));
        view.notifyTransactionListDataSetChanged();
    }

    @Override
    public void onDeleteTransaction(Transaction transaction) {
        TransactionModel.transactions.remove(transaction);
    }

    @Override
    public void onTransactionPost(Transaction transaction) {
        for(int i=0; i<TransactionModel.transactions.size();i++){
            if(TransactionModel.transactions.get(i).getId()==transaction.getId()){
                TransactionModel.transactions.set(i, transaction);
            }
        }

    }

    private String dajTip(Type tip) {
        if (tip==Type.REGULARINCOME) return "REGULARINCOME";
        else if (tip==Type.REGULARPAYMENT) return "REGULARPAYMENT";
        else if (tip==Type.PURCHASE) return "PURCHASE";
        else if (tip==Type.INDIVIDUALPAYMENT) return "INDIVIDUALPAYMENT";
        else if (tip==Type.INDIVIDUALINCOME) return "INDIVIDUALINCOME";
        else return  "INDIVIDUALINCOME";
    }


    private int newId(){
        int max=0;
        for(Transaction t:TransactionModel.transactions){
            if(t.getId()>max) max=t.getId();
        }
        max++;
        return max;
    }
    private int getPosition(Transaction transaction){
        for(Transaction t:TransactionModel.transactions){
            if(t.getId()==transaction.getId()){
                return TransactionModel.transactions.indexOf(t);
            }
        }
        return -1;
    }
}
