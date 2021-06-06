package ba.unsa.etf.rma.rma20tomasrobert24;

import android.content.Context;
import android.os.Parcelable;


public class TransactionDetailPresenter implements ITransactionDetailPresenter {
    private Context context;

    private Transaction transaction;


    public TransactionDetailPresenter(Context context) {
        this.context    = context;
    }

    @Override
    public void create(Transaction transac) {
        this.transaction = new Transaction(transac.getDate(), transac.getAmount(), transac.getTitle(), transac.getType(), transac.getItemDescription(), transac.getTransactionInterval(), transac.getEndDate());
    }

    @Override
    public Transaction getTransaction() {
        return transaction;
    }

    @Override
    public void setTransaction(Parcelable transaction){
        this.transaction=(Transaction) transaction;
    }
}
