package ba.unsa.etf.rma.rma20tomasrobert24;

public interface IAccountPresenter {
    Account get();
    void getAccount(String query);
    void updateBudget();
    void editAccount(Account account);
}
