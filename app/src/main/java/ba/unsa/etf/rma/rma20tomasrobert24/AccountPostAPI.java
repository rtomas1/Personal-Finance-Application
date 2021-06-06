package ba.unsa.etf.rma.rma20tomasrobert24;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AccountPostAPI extends AsyncTask<String, Integer, Void> {
    private String root="http://rma20-app-rmaws.apps.us-west-1.starter.openshift-online.com/account/00c9387f-7fb3-46a3-8b9d-d0b3609a6a67";
    Account account;
    private boolean edit;
    private OnAccountPostDone caller;

    public AccountPostAPI(OnAccountPostDone p, Account account, boolean edit) {
        caller = p;
        this.account = account;
        this.edit=edit;
    };

    private String accountToJSON(Account account) {
        StringBuilder sb = new StringBuilder("{");
        sb.append("\"budget\": " + account.getBudget() );
        sb.append(",");
        sb.append("\"totalLimit\": " + account.getTotalLimit());
        sb.append(",");
        sb.append("\"monthLimit\": " +  account.getMonthLimit());
        sb.append("}");
        return sb.toString();
    }

    @Override
    protected Void doInBackground(String... strings) {
        String url1 = root;
        URL url=null;
        try {
            url = new URL(url1);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setDoOutput(true);
            String request=accountToJSON(account);
            try(OutputStream outputStream=urlConnection.getOutputStream()){
                byte[] input=request.getBytes("utf-8");
                outputStream.write(input,0, input.length);
            }
            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(urlConnection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid){
        super.onPostExecute(aVoid);
        caller.onDonePost(account, edit);
    }

    public interface OnAccountPostDone{
        public void onDonePost(Account result, boolean edit);
    }
}
