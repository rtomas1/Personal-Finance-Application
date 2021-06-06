package ba.unsa.etf.rma.rma20tomasrobert24;

import android.content.Context;

import java.util.Date;

public interface ITransactionListPresenter {
    void refreshTransactions();
    void getTransactions(String query);
    void updateTransaction(String query, Transaction transaction);
    void deleteTransaction(String query, Transaction transaction);
    void refreshTransactions(Date date, int filterId, int sortId);
    void addTransaction(Transaction transaction);
    double getOutcomeByMonth(int month);
    double getIncomeByMonth(int month);
    double getOutcomeByDay(int day);
    double getIncomeByDay(int day);
    double getOutcomeByWeek(int week);
    double getIncomeByWeek(int week);

}
