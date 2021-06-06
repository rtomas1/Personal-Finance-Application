package ba.unsa.etf.rma.rma20tomasrobert24;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class TransactionListAdapter extends ArrayAdapter<Transaction> {
    private int resource;
    public TextView titleView;
    public TextView amountView;
    public ImageView imageView;

    public TransactionListAdapter(@NonNull Context context, int _resource, ArrayList<Transaction> items) {
        super(context, _resource,items);
        resource = _resource;
    }

    public void setTransactions(ArrayList<Transaction> transactions) {
        this.addAll(transactions);
    }

    public Transaction getTransaction(int position) {return this.getItem(position);}

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LinearLayout newView;
        if (convertView == null) {
            newView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater li;
            li = (LayoutInflater)getContext().
                    getSystemService(inflater);
            li.inflate(resource, newView, true);
        } else {
            newView = (LinearLayout)convertView;
        }

        Transaction transaction = getItem(position);

        titleView = newView.findViewById(R.id.title);
        amountView = newView.findViewById(R.id.amount);
        imageView = newView.findViewById(R.id.icon);
        titleView.setText(transaction.getTitle());
        amountView.setText(transaction.getAmount().toString());

        if(transaction.getType()==Type.INDIVIDUALINCOME){
            imageView.setImageResource(R.drawable.individual_income);
        }
        else if(transaction.getType()==Type.INDIVIDUALPAYMENT){
            imageView.setImageResource(R.drawable.individual_payment);
        }
        else if(transaction.getType()==Type.PURCHASE){
            imageView.setImageResource(R.drawable.purchase);
        }
        else if(transaction.getType()==Type.REGULARINCOME){
            imageView.setImageResource(R.drawable.regular_income);
        }
        else if(transaction.getType()==Type.REGULARPAYMENT){
            imageView.setImageResource(R.drawable.regular_payment);
        }

        return newView;
    }
}
