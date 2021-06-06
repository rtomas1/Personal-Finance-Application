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

public class AccountGetAPI extends AsyncTask<String, Integer, Void> {
    private String root="http://rma20-app-rmaws.apps.us-west-1.starter.openshift-online.com/account/00c9387f-7fb3-46a3-8b9d-d0b3609a6a67";
    Account account;
    private OnAccountGetDone caller;


    public AccountGetAPI(OnAccountGetDone p) {
        caller = p;
        account = null;
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
        String url1 = root;
        URL url=null;
        try {
            url = new URL(url1);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
            urlConnection.setRequestProperty("Accept-Charset", "UTF-8");
            urlConnection.setDoInput(true);
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            String result = convertStreamToString(in);
            JSONObject acc = new JSONObject(result);
            Double budget=acc.getDouble("budget");
            Double totalLimit=acc.getDouble("totalLimit");
            Double monthLimit=acc.getDouble("monthLimit");
            account=new Account(budget, totalLimit, monthLimit);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid){
        super.onPostExecute(aVoid);
        caller.onDoneGet(account);
    }

    public interface OnAccountGetDone{
        public void onDoneGet(Account result);
    }
}
