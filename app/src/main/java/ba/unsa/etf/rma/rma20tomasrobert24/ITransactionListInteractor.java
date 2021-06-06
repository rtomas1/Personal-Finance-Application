package ba.unsa.etf.rma.rma20tomasrobert24;

import java.util.ArrayList;
import java.util.Date;

public interface ITransactionListInteractor {
    ArrayList<Transaction> get();
    ArrayList<Transaction> getTransactions(Date date, int filterId, int sortId);
    void updateTransaction(Transaction transaction, int position);
    void addTransaction(Transaction transaction);
    double getOutcomeByMonth(int month);
    double getIncomeByMonth(int month);
    double getOutcomeByDay(int day);
    double getIncomeByDay(int day);
    double getOutcomeByWeek(int week);
    double getIncomeByWeek(int week);
}
