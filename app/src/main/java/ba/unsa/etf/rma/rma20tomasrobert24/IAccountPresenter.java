package ba.unsa.etf.rma.rma20tomasrobert24;

public interface IAccountPresenter {
    Account get();
    void changeLimit(int limit);
    void changeMonthLimit(int limit);
}
