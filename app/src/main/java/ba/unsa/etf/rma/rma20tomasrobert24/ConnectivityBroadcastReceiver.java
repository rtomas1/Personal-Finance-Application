package ba.unsa.etf.rma.rma20tomasrobert24;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConnectivityBroadcastReceiver extends BroadcastReceiver implements TransactionDeleteAPI.OnTransactionDeleteDone, TransactionPostAPI.OnTransactionPostDone, AccountPostAPI.OnAccountPostDone {
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public ConnectivityBroadcastReceiver() {

    }


    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() == null) {
            Toast toast = Toast.makeText(context, "Disconnected", Toast.LENGTH_SHORT);
            toast.show();

        } else {
            Toast toast = Toast.makeText(context, "Connected", Toast.LENGTH_SHORT);
            toast.show();
            Cursor cursor = getTransactionCursor(context);
            while (cursor.moveToNext()) {
                int id = 0;
                if (!cursor.getString(cursor.getColumnIndex(TransactionDBOpenHelper.TRANSACTION_CHANGE)).equals("add")) {
                    id = cursor.getInt(cursor.getColumnIndex(TransactionDBOpenHelper.TRANSACTION_ID));
                }
                String title = cursor.getString(cursor.getColumnIndex(TransactionDBOpenHelper.TRANSACTION_TITLE));
                Double amount = Double.valueOf(cursor.getString(cursor.getColumnIndex(TransactionDBOpenHelper.TRANSACTION_AMOUNT)));
                Type type = dajTip(cursor.getString(cursor.getColumnIndex(TransactionDBOpenHelper.TRANSACTION_TYPE)));
                Date date = null;
                Date endDate = null;
                try {
                    date = sdf.parse(cursor.getString(cursor.getColumnIndex(TransactionDBOpenHelper.TRANSACTION_DATE)));
                    if (type == Type.REGULARINCOME || type == Type.REGULARPAYMENT) {
                        endDate = sdf.parse(cursor.getString(cursor.getColumnIndex(TransactionDBOpenHelper.TRANSACTION_END_DATE)));
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String description = cursor.getString(cursor.getColumnIndex(TransactionDBOpenHelper.TRANSACTION_ITEM_DESCRIPTION));
                int interval = 0;
                if (type == Type.REGULARINCOME || type == Type.REGULARPAYMENT) {
                    interval = cursor.getInt(cursor.getColumnIndex(TransactionDBOpenHelper.TRANSACTION_INTERVAL));
                }
                Transaction transaction = null;
                if (id != 0) {
                    transaction = new Transaction(id, date, amount, title, type, description, interval, endDate);
                } else {
                    transaction = new Transaction(date, amount, title, type, description, interval, endDate);
                }
                if (cursor.getString(cursor.getColumnIndex(TransactionDBOpenHelper.TRANSACTION_CHANGE)).equals("add")) {
                    TransactionPostAPI api = new TransactionPostAPI(this, transaction);
                    api.execute("");
                } else if (cursor.getString(cursor.getColumnIndex(TransactionDBOpenHelper.TRANSACTION_CHANGE)).equals("update")) {
                    TransactionPostAPI api = new TransactionPostAPI(this, transaction);
                    api.execute("update");
                } else if (cursor.getString(cursor.getColumnIndex(TransactionDBOpenHelper.TRANSACTION_CHANGE)).equals("delete")) {
                    TransactionDeleteAPI api = new TransactionDeleteAPI(this, transaction);
                    api.execute("");
                }
            }
            Uri uri = Uri.parse("content://rma.provider.transactions/elements");
            ContentResolver cr = context.getApplicationContext().getContentResolver();
            cr.delete(uri, null, null);
            Cursor accCursor=getAccountCursor(context);
            while (accCursor.moveToNext()){
                Double budget=Double.valueOf(cursor.getString(cursor.getColumnIndex(TransactionDBOpenHelper.ACCOUNT_BUDGET)));
                Double totalLimit=Double.valueOf(cursor.getString(cursor.getColumnIndex(TransactionDBOpenHelper.ACCOUNT_TOTAL_LIMIT)));
                Double monthLimit=Double.valueOf(cursor.getString(cursor.getColumnIndex(TransactionDBOpenHelper.ACCOUNT_MONTH_LIMIT)));
                Account account=new Account(budget, totalLimit, monthLimit);
                AccountPostAPI api=new AccountPostAPI(this, account, true);
                api.execute("");
            }
            Uri uri1=Uri.parse("content://rma.provider.account/elements");
            ContentResolver cr1=context.getApplicationContext().getContentResolver();
            cr1.delete(uri1, null, null);
        }
    }

    public Cursor getTransactionCursor(Context context) {
        ContentResolver cr = context.getApplicationContext().getContentResolver();
        String[] columns = new String[]{
                TransactionDBOpenHelper.TRANSACTION_INTERNAL_ID, TransactionDBOpenHelper.TRANSACTION_ID, TransactionDBOpenHelper.TRANSACTION_TITLE,
                TransactionDBOpenHelper.TRANSACTION_AMOUNT, TransactionDBOpenHelper.TRANSACTION_DATE, TransactionDBOpenHelper.TRANSACTION_ITEM_DESCRIPTION,
                TransactionDBOpenHelper.TRANSACTION_INTERVAL, TransactionDBOpenHelper.TRANSACTION_END_DATE, TransactionDBOpenHelper.TRANSACTION_TYPE,
                TransactionDBOpenHelper.TRANSACTION_CHANGE};
        Uri adress = Uri.parse("content://rma.provider.transactions/elements");
        return cr.query(adress, columns, null, null, null);
    }

    public Cursor getAccountCursor(Context context) {
        ContentResolver cr = context.getApplicationContext().getContentResolver();
        String[] columns = new String[]{
                TransactionDBOpenHelper.ACCOUNT_ID, TransactionDBOpenHelper.ACCOUNT_TOTAL_LIMIT, TransactionDBOpenHelper.ACCOUNT_MONTH_LIMIT};
        Uri adress = Uri.parse("content://rma.provider.account/elements");
        return cr.query(adress, columns, null, null, null);
    }


    private Type dajTip(String tip) {
        if (tip.equals("REGULARINCOME")) return Type.REGULARINCOME;
        else if (tip.equals("REGULARPAYMENT")) return Type.REGULARPAYMENT;
        else if (tip.equals("PURCHASE")) return Type.PURCHASE;
        else if (tip.equals("INDIVIDUALPAYMENT")) return Type.INDIVIDUALPAYMENT;
        else if (tip.equals("INDIVIDUALINCOME")) return Type.INDIVIDUALINCOME;
        else return Type.INDIVIDUALINCOME;
    }

    @Override
    public void onDeleteTransaction(Transaction transaction) {

    }

    @Override
    public void onTransactionPost(Transaction transaction) {

    }

    @Override
    public void onDonePost(Account result, boolean edit) {

    }
}
