package ba.unsa.etf.rma.rma20tomasrobert24;

import android.os.Parcelable;

import java.util.Date;

public interface ITransactionDetailPresenter {
    void create(Transaction transac);
    Transaction getTransaction();
    void setTransaction(Parcelable transaction);
}
