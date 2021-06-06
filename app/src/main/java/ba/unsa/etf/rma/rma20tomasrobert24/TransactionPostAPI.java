package ba.unsa.etf.rma.rma20tomasrobert24;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;

import static ba.unsa.etf.rma.rma20tomasrobert24.Type.INDIVIDUALINCOME;
import static ba.unsa.etf.rma.rma20tomasrobert24.Type.INDIVIDUALPAYMENT;
import static ba.unsa.etf.rma.rma20tomasrobert24.Type.PURCHASE;
import static ba.unsa.etf.rma.rma20tomasrobert24.Type.REGULARINCOME;
import static ba.unsa.etf.rma.rma20tomasrobert24.Type.REGULARPAYMENT;

public class TransactionPostAPI extends AsyncTask<String, Integer, Void> {
    private String root="http://rma20-app-rmaws.apps.us-west-1.starter.openshift-online.com/account/00c9387f-7fb3-46a3-8b9d-d0b3609a6a67/transactions";
    Transaction transaction;
    private OnTransactionPostDone caller;

    public TransactionPostAPI(OnTransactionPostDone p, Transaction transaction) {
        caller = p;
        this.transaction = transaction;
    };

    private int dajTipTransakcije(Type type){
        switch (type){
            case REGULARPAYMENT:
                return 1;
            case REGULARINCOME:
                return 2;
            case PURCHASE:
                return 3;
            case INDIVIDUALINCOME:
                return 4;
            case INDIVIDUALPAYMENT:
                return 5;
        }
        return 1;
    }

    private String tranToJSON(Transaction transaction) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        StringBuilder sb = new StringBuilder("{");
        sb.append("\"title\": " + "\"" + transaction.getTitle() + "\"");
        sb.append(",");
        sb.append("\"amount\": " + transaction.getAmount());
        sb.append(",");
        sb.append("\"date\": " + "\"" + sdf.format(transaction.getDate()) + "\"");
        if (transaction.getItemDescription() != null && !transaction.getItemDescription().equals("null") && !transaction.getItemDescription().equals("")) {
            sb.append(",");
            sb.append("\"itemDescription\": " + "\"" + transaction.getItemDescription() +"\"");
        }
        if (transaction.getType() == REGULARINCOME || transaction.getType() == REGULARPAYMENT) {
            sb.append(",");
            sb.append("\"transactionInterval\": " + transaction.getTransactionInterval());
            sb.append(",");
            sb.append("\"endDate\": " + "\"" + sdf.format(transaction.getEndDate()) + "\"");
        }
        sb.append(",");
        sb.append("\"TransactionTypeId\": " + dajTipTransakcije(transaction.getType()));
        sb.append("}");
        return sb.toString();
    }


    @Override
    protected Void doInBackground(String... strings) {
        Log.e("test", Integer.toString(transaction.getId()));
        String url1 = root;
        if(strings[0].equals("update")){
            url1=url1+"/"+transaction.getId();
        }
        URL url=null;
        try {
            url = new URL(url1);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            String request=tranToJSON(transaction);
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
        caller.onTransactionPost(transaction);
    }

    public interface OnTransactionPostDone{
        public void onTransactionPost(Transaction transaction);
    }
}
