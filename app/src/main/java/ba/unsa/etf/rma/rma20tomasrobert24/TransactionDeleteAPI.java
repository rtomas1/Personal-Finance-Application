package ba.unsa.etf.rma.rma20tomasrobert24;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class TransactionDeleteAPI extends AsyncTask<String, Integer, Void> {
    private String root="http://rma20-app-rmaws.apps.us-west-1.starter.openshift-online.com/account/00c9387f-7fb3-46a3-8b9d-d0b3609a6a67/transactions/";
    private OnTransactionDeleteDone caller;
    Transaction transaction;

    public TransactionDeleteAPI(OnTransactionDeleteDone p, Transaction transaction) {
        caller = p;
        this.transaction = transaction;
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

    @Override
    protected Void doInBackground(String... strings) {
        String url1 = String.format("%s%s", root, Integer.toString(transaction.getId()));
        URL url=null;
        try {
            url = new URL(url1);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("DELETE");
            urlConnection.setDoOutput(true);
            urlConnection.connect();
            InputStream inputStream=new BufferedInputStream(urlConnection.getInputStream());
            String result=convertStreamToString(inputStream);
            JSONObject jsonObject=new JSONObject(result);
            Log.e("result:", jsonObject.getString("success"));
            inputStream.close();
            urlConnection.disconnect();
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void onPostExecute(Void aVoid){
        super.onPostExecute(aVoid);
        caller.onDeleteTransaction(transaction);
    }

    public interface OnTransactionDeleteDone{
        public void onDeleteTransaction(Transaction transaction);
    }
}
