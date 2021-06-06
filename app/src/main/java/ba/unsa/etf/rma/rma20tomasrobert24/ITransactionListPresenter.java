package ba.unsa.etf.rma.rma20tomasrobert24;

import java.util.Date;

public interface ITransactionListPresenter {
    void refreshTransactions();
    void refreshTransactions(Date date, int filterId, int sortId);
    void updateTransaction(Transaction transaction, int position);
    void addTransaction(Transaction transaction);
    double getOutcomeByMonth(int month);
    double getIncomeByMonth(int month);
    double getOutcomeByDay(int day);
    double getIncomeByDay(int day);
    double getOutcomeByWeek(int week);
    double getIncomeByWeek(int week);

}
