package ba.unsa.etf.rma.rma20tomasrobert24;

import android.content.Context;

public class AccountPresenter  implements  IAccountPresenter{
    Context context;

    public AccountPresenter(Context context) {
        this.context    = context;
    }
    @Override
    public void changeLimit(int limit){
        AccountModel.account.setTotalLimit(limit);
    }
    @Override
    public void  changeMonthLimit(int limit){
        AccountModel.account.setMonthLimit(limit);
    }
    @Override
    public Account get(){
        return AccountModel.account;
    }
}
