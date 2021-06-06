package ba.unsa.etf.rma.rma20tomasrobert24;



import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class TransactionListInteractor implements ITransactionListInteractor{
    @Override
    public ArrayList<Transaction> get() {
        return TransactionModel.transactions;
    }

    @Override
    public ArrayList<Transaction> getTransactions(Date date, int filterId, int sortId){
        ArrayList<Transaction> transactions=getTransactionsByMonth(date);
        transactions=getFiltered(filterId, transactions);
        getSorted(sortId, transactions);
        return transactions;
    }

    @Override
    public void updateTransaction(Transaction transaction, int position){
        TransactionModel.transactions.set(position, transaction);
    }

    @Override
    public void addTransaction(Transaction transaction){
        TransactionModel.transactions.add(transaction);
    }

    @Override
    public double getOutcomeByMonth(int month){
        ArrayList<Transaction> transactions=new ArrayList<>();
        Date currentDate=new Date();
        for(Transaction t:TransactionModel.transactions){
            if (t.getDate().getMonth()==month && t.getDate().getYear()==currentDate.getYear() && (t.getType().equals(Type.REGULARPAYMENT) || t.getType().equals(Type.INDIVIDUALPAYMENT) || t.getType().equals(Type.PURCHASE))){
                transactions.add(t);
            }
        }
        double sum=0;
        for(Transaction t:transactions){
            sum+=t.getAmount();
        }
        return sum;
    }

    @Override
    public double getIncomeByMonth(int month){
        ArrayList<Transaction> transactions=new ArrayList<>();
        Date currentDate=new Date();
        for(Transaction t:TransactionModel.transactions){
            if (t.getDate().getMonth()==month && t.getDate().getYear()==currentDate.getYear() && (t.getType().equals(Type.REGULARINCOME) || t.getType().equals(Type.INDIVIDUALINCOME) )){
                transactions.add(t);
            }
        }
        double sum=0;
        for(Transaction t:transactions){
            sum+=t.getAmount();
        }
        return sum;
    }

    @Override
    public double getOutcomeByDay(int day){
        ArrayList<Transaction> transactions=new ArrayList<>();
        Date currentDate=new Date();
        for(Transaction t:TransactionModel.transactions){
            if (t.getDate().getDay()==day && t.getDate().getYear()==currentDate.getYear()&& t.getDate().getMonth()==currentDate.getMonth() && (t.getType().equals(Type.REGULARPAYMENT) || t.getType().equals(Type.INDIVIDUALPAYMENT) || t.getType().equals(Type.PURCHASE))){
                transactions.add(t);
            }
        }
        double sum=0;
        for(Transaction t:transactions){
            sum+=t.getAmount();
        }
        return sum;
    }

    @Override
    public double getIncomeByDay(int day){
        ArrayList<Transaction> transactions=new ArrayList<>();
        Date currentDate=new Date();
        for(Transaction t:TransactionModel.transactions){
            if (t.getDate().getDay()==day && t.getDate().getYear()==currentDate.getYear()&& t.getDate().getMonth()==currentDate.getMonth() && (t.getType().equals(Type.REGULARINCOME) || t.getType().equals(Type.INDIVIDUALINCOME) )){
                transactions.add(t);
            }
        }
        double sum=0;
        for(Transaction t:transactions){
            sum+=t.getAmount();
        }
        return sum;
    }

    @Override
    public double getOutcomeByWeek(int week){
        ArrayList<Transaction> transactions=new ArrayList<>();
        Date currentDate=new Date();
        for(Transaction t:TransactionModel.transactions){
            if (t.getDate().getYear()==currentDate.getYear()&& t.getDate().getMonth()==currentDate.getMonth() && (t.getType().equals(Type.REGULARPAYMENT) || t.getType().equals(Type.INDIVIDUALPAYMENT) || t.getType().equals(Type.PURCHASE))){
                Calendar calendar=Calendar.getInstance();
                calendar.setTime(t.getDate());
                if(week==calendar.get(Calendar.WEEK_OF_MONTH)) {
                    transactions.add(t);
                }
            }
        }
        double sum=0;
        for(Transaction t:transactions){
            sum+=t.getAmount();
        }
        return sum;
    }

    @Override
    public double getIncomeByWeek(int week){
        ArrayList<Transaction> transactions=new ArrayList<>();
        Date currentDate=new Date();
        for(Transaction t:TransactionModel.transactions){
            if (t.getDate().getYear()==currentDate.getYear()&& t.getDate().getMonth()==currentDate.getMonth() && (t.getType().equals(Type.REGULARINCOME) || t.getType().equals(Type.INDIVIDUALINCOME) )){
                Calendar calendar=Calendar.getInstance();
                calendar.setTime(t.getDate());
                if(week==calendar.get(Calendar.WEEK_OF_MONTH)) {
                    transactions.add(t);
                }
            }
        }
        double sum=0;
        for(Transaction t:transactions){
            sum+=t.getAmount();
        }
        return sum;
    }


    private ArrayList<Transaction> getTransactionsByMonth(Date date)
    {
        ArrayList<Transaction> transactions=new ArrayList<>();
        for(Transaction t:TransactionModel.transactions){
            if(t==null || t.getTitle()=="") continue;
            if(t.getDate().getMonth()==date.getMonth() && t.getDate().getYear()==date.getYear()){
                transactions.add(t);
            }
        }
        return transactions;
    }



    private ArrayList<Transaction> getFiltered(int id, ArrayList<Transaction> monthlyTransactions){
        ArrayList<Transaction> transactions=new ArrayList<>();
        if(id==0){
            return monthlyTransactions;
        }
        else if(id==1){
            for(Transaction t: monthlyTransactions){
                if(t.getType()==Type.INDIVIDUALINCOME){
                    transactions.add(t);
                }
            }
        }
        else if(id==2){
            for(Transaction t:monthlyTransactions){
                if(t.getType()==Type.INDIVIDUALPAYMENT){
                    transactions.add(t);
                }
            }
        }
        else if(id==3){
            for(Transaction t:monthlyTransactions){
                if(t.getType()==Type.PURCHASE){
                    transactions.add(t);
                }
            }
        }
        else if(id==4){
            for(Transaction t:monthlyTransactions){
                if(t.getType()==Type.REGULARINCOME){
                    transactions.add(t);
                }
            }
        }
        else{
            for(Transaction t:monthlyTransactions){
                if(t.getType()==Type.REGULARPAYMENT){
                    transactions.add(t);
                }
            }
        }
        return transactions;
    }


    private void getSorted(int id, ArrayList<Transaction> transactions){
        if(id==0){
            return;
        }
        else if(id==1){
            for(int i=0;i<transactions.size()-1;i++){
                for(int j=0;j<transactions.size()-i-1;j++){
                    if(transactions.get(j).getAmount()>transactions.get(j+1).getAmount()){
                        Collections.swap(transactions, j, j+1);
                    }
                }
            }
        }
        else if(id==2){
            for(int i=0;i<transactions.size()-1;i++){
                for(int j=0;j<transactions.size()-i-1;j++){
                    if(transactions.get(j).getAmount()<transactions.get(j+1).getAmount()){
                        Collections.swap(transactions, j, j+1);
                    }
                }
            }
        }
        else if(id==3){
            for(int i=0;i<transactions.size()-1;i++){
                for(int j=0;j<transactions.size()-i-1;j++){
                    if(1==transactions.get(j).getTitle().compareTo(transactions.get(j+1).getTitle())){
                        Collections.swap(transactions, j, j+1);
                    }
                }
            }
        }
        else if(id==4){
            for(int i=0;i<transactions.size()-1;i++){
                for(int j=0;j<transactions.size()-i-1;j++){
                    if(1!=transactions.get(j).getTitle().compareTo(transactions.get(j+1).getTitle())){
                        Collections.swap(transactions, j, j+1);
                    }
                }
            }
        }
        else if(id==5){
            for(int i=0;i<transactions.size()-1;i++){
                for(int j=0;j<transactions.size()-i-1;j++){
                    if(1==transactions.get(j).getDate().compareTo(transactions.get(j+1).getDate())){
                        Collections.swap(transactions, j, j+1);
                    }
                }
            }
        }
        else{
            for(int i=0;i<transactions.size()-1;i++){
                for(int j=0;j<transactions.size()-i-1;j++){
                    if(1!=transactions.get(j).getDate().compareTo(transactions.get(j+1).getDate())){
                        Collections.swap(transactions, j, j+1);
                    }
                }
            }
        }
    }

}
