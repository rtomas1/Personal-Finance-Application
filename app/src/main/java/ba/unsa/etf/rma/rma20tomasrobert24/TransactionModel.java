package ba.unsa.etf.rma.rma20tomasrobert24;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TransactionModel {
    public static ArrayList<Transaction> transactions=new ArrayList<Transaction>(){
        {
            SimpleDateFormat format=new SimpleDateFormat("dd-MM-yyyy");
            try {
                Date d1=format.parse("25-3-2020");
                Date d2=format.parse("21-4-2020");
                Date d3=format.parse("24-2-2020");
                Date d4=format.parse("01-4-2020");
                Date d5=format.parse("24-1-2020");
                Date d6=format.parse("22-5-2020");
                Date d7=format.parse("21-3-2020");
                Date d8=format.parse("20-4-2020");
                Date d9=format.parse("18-2-2020");

                add(new Transaction(d1, new Double(200), "Donacija", Type.INDIVIDUALPAYMENT, "Humanitarna donacija", 0, null));
                add(new Transaction(d2, new Double(1000), "Poklon", Type.INDIVIDUALINCOME, "Od tetke iz Beca", 0, null));
                add(new Transaction(d3, new Double(30), "Maska", Type.PURCHASE, "Za lice, zbog corone", 0, null));
                add(new Transaction(d4, new Double(1000), "Racuni", Type.REGULARPAYMENT, "Placeni racuni", 30, d6));
                add(new Transaction(d5, new Double(2000), "Plata", Type.REGULARINCOME, "Plata", 31, d1));
                add(new Transaction(d6, new Double(2500), "Laptop", Type.PURCHASE, "HP Omen", 0, null));
                add(new Transaction(d7, new Double(5000), "Poklon", Type.INDIVIDUALINCOME, "Od druge tetke", 0, null));
                add(new Transaction(d8, new Double(400), "Taksa", Type.INDIVIDUALPAYMENT, "Porez neki ", 0, null));
                add(new Transaction(d9, new Double(2000), "Prodaja", Type.INDIVIDUALINCOME, "Prodat golf 2", 0, null));
                add(new Transaction(d1, new Double(3000), "TV", Type.PURCHASE, "51 incha", 0, null));
                add(new Transaction(d2, new Double(1000), "Rata kredita", Type.REGULARPAYMENT, "Rata kredita", 20, d6));
                add(new Transaction(d3, new Double(800), "Prodaja", Type.REGULARINCOME, "Prodat auto na rate", 30, d1));
                add(new Transaction(d4, new Double(250), "Frizider", Type.PURCHASE, "LG", 0, null));
                add(new Transaction(d5, new Double(5000), "Dobitak", Type.INDIVIDUALINCOME, "Na kladionici", 0, null));
                add(new Transaction(d6, new Double(400), "Gubitak", Type.INDIVIDUALPAYMENT, "Na kladionici", 0, null));
                add(new Transaction(d7, new Double(20), "Karte", Type.PURCHASE, "Bicycle karte", 0, null));
                add(new Transaction(d8, new Double(30), "Hrana", Type.PURCHASE, "Hrana", 0, null));
                add(new Transaction(d9, new Double(1000), "Odjeca", Type.PURCHASE, "Samo gucci", 0, null));
                add(new Transaction(d1, new Double(80), "Tastatura", Type.REGULARINCOME, "Mehanicka", 0, null));
                add(new Transaction(d2, new Double(250), "Igrica", Type.PURCHASE, "Red dead redemption", 0, null));
            }
            catch (ParseException e){
                e.printStackTrace();
            }
        }
    };
}
