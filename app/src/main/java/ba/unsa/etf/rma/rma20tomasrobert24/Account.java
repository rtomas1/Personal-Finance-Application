package ba.unsa.etf.rma.rma20tomasrobert24;

public class Account {
    Double budget;
    int totalLimit;
    int monthLimit;

    public Account(double budget, int totalLimit, int monthLimit) {
        this.budget = budget;
        this.totalLimit = totalLimit;
        this.monthLimit = monthLimit;
    }

    public Double getBudget() {
        return new Double(budget);
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public Integer getTotalLimit() {
        return new Integer(totalLimit);
    }

    public void setTotalLimit(int totalLimit) {
        this.totalLimit = totalLimit;
    }

    public Integer getMonthLimit() {
        return new Integer(monthLimit);
    }

    public void setMonthLimit(int monthLimit) {
        this.monthLimit = monthLimit;
    }
}
