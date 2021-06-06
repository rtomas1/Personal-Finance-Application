package ba.unsa.etf.rma.rma20tomasrobert24;

public class Account {
    Double budget;
    Double totalLimit;
    Double monthLimit;

    public Account(double budget, double totalLimit, double monthLimit) {
        this.budget = budget;
        this.totalLimit = totalLimit;
        this.monthLimit = monthLimit;
    }
    public Account(Double budget, Double totalLimit, Double monthLimit){
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

    public Double getTotalLimit() {
        return new Double(totalLimit);
    }

    public void setTotalLimit(double totalLimit) {
        this.totalLimit = totalLimit;
    }

    public Double getMonthLimit() {
        return new Double(monthLimit);
    }

    public void setMonthLimit(double monthLimit) {
        this.monthLimit = monthLimit;
    }
}
