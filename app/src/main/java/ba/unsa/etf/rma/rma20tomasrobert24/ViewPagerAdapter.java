package ba.unsa.etf.rma.rma20tomasrobert24;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    FragmentManager fragmentManager;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        this.fragmentManager=fm;
    }
    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    @Override
    public Fragment getItem(int position) {
        if(position %3==0){
            return new RootFragment();
        }
        else if(position %3==1){
            return new BudgetFragment();
        }
        else if(position%3==2){
            return new GraphsFragment();
        }

        return null;
    }
    /*
    Znam da bas nije najbolje rjesenje da bi mogao swipe sa grafova na pocetni fragment, ali je ovo jedino sto mi radi sa viewPagerom
     */

    @Override
    public int getCount() {
        return 300;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }
}
