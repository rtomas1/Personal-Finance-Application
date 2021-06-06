package ba.unsa.etf.rma.rma20tomasrobert24;

import java.util.ArrayList;

public interface ITransactionListView {
    void setTransactions(ArrayList<Transaction> transactions);
    void notifyTransactionListDataSetChanged();
}
