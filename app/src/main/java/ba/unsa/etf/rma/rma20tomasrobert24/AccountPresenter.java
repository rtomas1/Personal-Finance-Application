package ba.unsa.etf.rma.rma20tomasrobert24;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.view.View;

import java.util.Calendar;
import java.util.Date;

public class AccountPresenter  implements  IAccountPresenter, AccountGetAPI.OnAccountGetDone, AccountPostAPI.OnAccountPostDone{
    Context context;
    IAccountView view;


    public AccountPresenter(IAccountView view, Context context) {
        this.context    = context;
        this.view=view;
    }

    public AccountPresenter(Context context){
        this.context=context;
    }

    @Override
    public void getAccount(String query){
        new AccountGetAPI(this).execute(query);
    }

    @Override
    public void onDoneGet(Account result) {
        AccountModel.account=result;
        view.setAccount(result);
        view.notifyAccountChanged();
    }

    @Override
    public void editAccount(Account account){
        AccountModel.account=account;
        if(NetworkChecker.isConnected(context)){
            new AccountPostAPI(this, AccountModel.account, true).execute("");
        }
        else{
            ContentResolver cr=context.getApplicationContext().getContentResolver();
            Uri uri=Uri.parse("content://rma.provider.account/elements");
            ContentValues cv=new ContentValues();
            cv.put(TransactionDBOpenHelper.ACCOUNT_ID, account.getBudget());
            cv.put(TransactionDBOpenHelper.ACCOUNT_TOTAL_LIMIT, account.getTotalLimit());
            cv.put(TransactionDBOpenHelper.ACCOUNT_MONTH_LIMIT, account.getMonthLimit());
            cr.insert(uri, cv);
            AccountModel.account=account;
        }
    }

    @Override
    public void updateBudget(){
        double budget=0;
        for(Transaction t:TransactionModel.transactions){
            if(t.getType().equals(Type.PURCHASE) || t.getType().equals(Type.INDIVIDUALPAYMENT)){
                budget-=t.getAmount();
            }
            else if(t.getType().equals(Type.INDIVIDUALINCOME)){
                budget+=t.getAmount();
            }
            else if(t.getType().equals(Type.REGULARINCOME)){
                Date start = (Date) t.getDate().clone();
                Date endDate = (Date) t.getEndDate().clone();
                while (endDate.after(start)) {
                    budget+=t.getAmount();
                    Calendar myCal = Calendar.getInstance();
                    myCal.setTime(start);
                    myCal.add(Calendar.DATE, t.getTransactionInterval());
                    start = myCal.getTime();
                }
            }
            else{
                Date start = (Date) t.getDate().clone();
                Date endDate = (Date) t.getEndDate().clone();
                while (endDate.after(start)) {
                    budget-=t.getAmount();
                    Calendar myCal = Calendar.getInstance();
                    myCal.setTime(start);
                    myCal.add(Calendar.DATE, t.getTransactionInterval());
                    start = myCal.getTime();
                }
            }
        }
        AccountModel.account.setBudget(budget);
        if(NetworkChecker.isConnected(context)){
            new AccountPostAPI(this, AccountModel.account, false).execute("");
        }
        else{
            ContentResolver cr=context.getApplicationContext().getContentResolver();
            Uri uri=Uri.parse("content://rma.provider.account/elements");
            ContentValues cv=new ContentValues();
            cv.put(TransactionDBOpenHelper.ACCOUNT_ID, AccountModel.account.getBudget());
            cv.put(TransactionDBOpenHelper.ACCOUNT_TOTAL_LIMIT, AccountModel.account.getTotalLimit());
            cv.put(TransactionDBOpenHelper.ACCOUNT_MONTH_LIMIT, AccountModel.account.getMonthLimit());
            cr.insert(uri, cv);
        }
    }

    @Override
    public Account get(){
        return AccountModel.account;
    }

    @Override
    public void onDonePost(Account result, boolean edit) {
        AccountModel.account=result;
        if(edit) {
            view.setAccount(result);
            view.notifyAccountChanged();
        }
    }
}
