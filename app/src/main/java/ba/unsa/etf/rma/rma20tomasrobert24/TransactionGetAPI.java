package ba.unsa.etf.rma.rma20tomasrobert24;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TransactionGetAPI extends AsyncTask<String, Integer, Void> {
    private String root="http://rma20-app-rmaws.apps.us-west-1.starter.openshift-online.com/account/00c9387f-7fb3-46a3-8b9d-d0b3609a6a67/transactions/?page=";
    ArrayList<Transaction> transactions;
    private OnTransactionsGetDone caller;

    public TransactionGetAPI(OnTransactionsGetDone p) {
        caller = p;
        transactions = new ArrayList<Transaction>();
    };

    public String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new
                InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
        } finally {
            try {
                is.close();
            } catch (IOException e) {
            }
        }
        return sb.toString();
    }

    private Type dajTipTransakcije(int id){
        switch (id){
            case 1:
                return Type.REGULARPAYMENT;
            case 2:
                return Type.REGULARINCOME;
            case 3:
                return Type.PURCHASE;
            case 4:
                return Type.INDIVIDUALINCOME;
            case 5:
                return Type.INDIVIDUALPAYMENT;
        }
        return Type.PURCHASE;
    }

    @Override
    protected Void doInBackground(String... strings) {
        try {
            int page = 0;
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            while (true) {
                String url1 = root+page;
                URL url=new URL(url1);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
                urlConnection.setRequestProperty("Accept-Charset", "UTF-8");
                urlConnection.setDoInput(true);
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                String result = convertStreamToString(in);
                JSONObject jo = new JSONObject(result);
                JSONArray results = jo.getJSONArray("transactions");
                if(results.length()==0) break;
                for (int i = 0; i < results.length(); i++) {
                    JSONObject transaction = results.getJSONObject(i);
                    int id=transaction.getInt("id");
                    Double amount=transaction.getDouble("amount");
                    String title=transaction.getString("title");
                    Date date=null;
                    try{
                        date=sdf.parse(transaction.getString("date"));
                    }
                    catch (ParseException e){
                        e.printStackTrace();
                    }
                    String description=transaction.getString("itemDescription");
                    Type type=dajTipTransakcije(transaction.getInt("TransactionTypeId"));
                    int interval=0;
                    if(type==Type.REGULARINCOME || type==Type.REGULARPAYMENT){
                        interval=transaction.getInt("transactionInterval");
                    }
                    String ed=transaction.getString("endDate");
                    Date endDate=null;
                    if(ed!=null){
                        try{
                            endDate=sdf.parse(transaction.getString("endDate"));
                        }
                        catch (ParseException e){
                            e.printStackTrace();
                        }
                    }
                    transactions.add(new Transaction(id, date, amount, title, type, description, interval, endDate));
                }
                page++;
            }
        }
        catch (MalformedURLException e) {
            e.printStackTrace();}
        catch (IOException e) {
            e.printStackTrace(); }
        catch (JSONException e) {
            e.printStackTrace();}
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid){
        super.onPostExecute(aVoid);
        caller.onTransactionGet(transactions);
    }

    public interface OnTransactionsGetDone{
        public void onTransactionGet(ArrayList<Transaction> results);
    }
}
