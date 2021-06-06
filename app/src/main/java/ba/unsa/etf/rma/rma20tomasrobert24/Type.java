package ba.unsa.etf.rma.rma20tomasrobert24;

public enum Type {
    INDIVIDUALPAYMENT, REGULARPAYMENT, PURCHASE, INDIVIDUALINCOME, REGULARINCOME;
    public static int getPosition(Type type){
        switch (type){
            case INDIVIDUALINCOME: return 0;
            case INDIVIDUALPAYMENT: return 1;
            case PURCHASE: return 2;
            case REGULARINCOME: return 3;
            case REGULARPAYMENT: return 4;
        }
        return 0;
    }

    public static Type getType(int position){
        switch (position){
            case 0: return INDIVIDUALINCOME;
            case 1: return INDIVIDUALPAYMENT;
            case 2: return PURCHASE;
            case 3: return REGULARINCOME;
            case 4: return REGULARPAYMENT;
        }
        return INDIVIDUALINCOME;
    }
}
