package ba.unsa.etf.rma.rma20tomasrobert24;

import android.content.Context;

import java.util.Date;


public class TransactionListPresenter implements ITransactionListPresenter {
    private ITransactionListView view;
    private ITransactionListInteractor interactor;
    private Context context;

    public TransactionListPresenter(ITransactionListView view, Context context) {
        this.view       = view;
        this.interactor = new TransactionListInteractor();
        this.context    = context;
    }

    public TransactionListPresenter(Context context){
        this.interactor = new TransactionListInteractor();
        this.context    = context;
    }
    @Override
    public void refreshTransactions() {
        view.setTransactions(interactor.get());
        view.notifyTransactionListDataSetChanged();
    }
    @Override
    public void refreshTransactions(Date date, int filterId, int sortId){
        view.setTransactions(interactor.getTransactions(date, filterId, sortId));
        view.notifyTransactionListDataSetChanged();
    }
    @Override
    public void updateTransaction(Transaction transaction, int position){
        interactor.updateTransaction(transaction, position);
    }
    @Override
    public void addTransaction(Transaction transaction){
        interactor.addTransaction(transaction);
    }

    @Override
    public double getOutcomeByMonth(int month){
        return interactor.getOutcomeByMonth(month);
    }

    @Override
    public double getIncomeByMonth(int month){
        return interactor.getIncomeByMonth(month);
    }

    @Override
    public double getOutcomeByDay(int day){
        return interactor.getOutcomeByDay(day);
    }

    @Override
    public double getIncomeByDay(int day){
        return interactor.getIncomeByDay(day);
    }

    @Override
    public double getOutcomeByWeek(int week){
        return interactor.getOutcomeByWeek(week);
    }

    @Override
    public double getIncomeByWeek(int week){
        return interactor.getIncomeByWeek(week);
    }

}
