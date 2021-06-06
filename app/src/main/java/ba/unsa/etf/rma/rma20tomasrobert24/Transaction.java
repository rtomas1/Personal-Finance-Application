package ba.unsa.etf.rma.rma20tomasrobert24;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction implements Serializable, Parcelable {
    Date date;
    Double amount;
    String title;
    Type type;
    String itemDescription;
    int transactionInterval;
    Date endDate;


    public Transaction(Date date, Double amount, String title, Type type, String itemDescription, int transactionInterval, Date endDate) {
        this.date = date;
        this.amount = amount;
        this.title = title;
        this.type = type;
        if(type.equals(Type.INDIVIDUALINCOME) || type.equals(Type.REGULARINCOME)){
            this.itemDescription=null;
        }
        else{
            this.itemDescription=itemDescription;
        }
        if(type.equals(Type.REGULARINCOME) || type.equals(Type.REGULARPAYMENT)){
            this.transactionInterval=transactionInterval;
        }
        else{
            this.transactionInterval=0;
        }
        if(type.equals(Type.REGULARINCOME) || type.equals(Type.REGULARPAYMENT)){
            this.endDate=endDate;
        }
        else{
            this.endDate=null;
        }
    }
    public Transaction(Parcel parcel){
        long pomocniDate=parcel.readLong();
        date=new Date(pomocniDate);
        amount=parcel.readDouble();
        title=parcel.readString();
        type=(Type)parcel.readSerializable();
        itemDescription=parcel.readString();
        transactionInterval=parcel.readInt();
        long pomocniEndDate=parcel.readLong();
        endDate=new Date(pomocniEndDate);
    }

    public static final Creator<Transaction> CREATOR = new Creator<Transaction>() {
        @Override
        public Transaction createFromParcel(Parcel in) {
            return new Transaction(in);
        }

        @Override
        public Transaction[] newArray(int size) {
            return new Transaction[size];
        }
    };

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public Integer getTransactionInterval() {
        return new Integer(transactionInterval);
    }

    public void setTransactionInterval(int transactionInterval) {
        this.transactionInterval = transactionInterval;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy");
        dest.writeString(simpleDateFormat.format(date));
        dest.writeDouble(amount);
        dest.writeString(title);
        dest.writeSerializable(type);
        if(!(type==Type.INDIVIDUALINCOME || type==Type.REGULARINCOME)){
            dest.writeString(itemDescription);
        }
        if(type==Type.REGULARINCOME || type==Type.REGULARPAYMENT) {
            dest.writeInt(transactionInterval);
            dest.writeString(simpleDateFormat.format(endDate));
        }
    }
}
