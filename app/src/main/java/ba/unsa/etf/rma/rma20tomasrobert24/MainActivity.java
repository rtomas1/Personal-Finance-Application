package ba.unsa.etf.rma.rma20tomasrobert24;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;


import android.app.FragmentTransaction;
import android.os.Bundle;

import android.widget.FrameLayout;



public class MainActivity extends AppCompatActivity implements TransactionListFragment.OnItemClick, TransactionDetailFragment.OnReturn{

    private boolean twoPaneMode;
    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;
    private TransactionListFragment transactionListFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FrameLayout details = findViewById(R.id.transaction_detail);


        if (details != null) {
            twoPaneMode = true;
            TransactionDetailFragment detailFragment = (TransactionDetailFragment) fragmentManager.findFragmentById(R.id.transaction_detail);
            if (detailFragment==null) {
                detailFragment = new TransactionDetailFragment();
                fragmentManager.beginTransaction().
                        replace(R.id.transaction_detail,detailFragment)
                        .commit();
            }
        } else {
            twoPaneMode=false;
            viewPager = findViewById(R.id.view_pager);
            viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
            viewPager.setAdapter(viewPagerAdapter);
        }

        if(!twoPaneMode){
            Fragment listFragment = fragmentManager.findFragmentByTag("list");
            if (listFragment==null){
                listFragment = new TransactionListFragment();
                transactionListFragment = (TransactionListFragment) listFragment;
                fragmentManager.beginTransaction()
                        .replace(R.id.transaction_list,listFragment,"list")
                        .commit();
            }
            else{
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        }
    }

    @Override
    public void onItemClicked(Transaction transaction, TransactionListFragment transactionListFragment) {
        Bundle arguments = new Bundle();
        arguments.putParcelable("transaction", transaction);
        int pos=TransactionModel.transactions.indexOf(transaction);
        arguments.putInt("position", pos);
        TransactionDetailFragment detailFragment = new TransactionDetailFragment();
        detailFragment.setArguments(arguments);
        if (twoPaneMode){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.transaction_detail, detailFragment)
                    .commit();
        }
        else{
            transactionListFragment.getFragmentManager().beginTransaction()
                    .replace(R.id.root_frame1,detailFragment)
                    .addToBackStack(null)
                    .commit();
            viewPagerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onSave(Transaction transaction, int position, TransactionDetailFragment transactionDetailFragment){
        TransactionModel.transactions.set(position, transaction);
        TransactionListFragment listFragment = new TransactionListFragment();
        if (twoPaneMode){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.transaction_list, listFragment)
                    .commit();
        }
        else{
            transactionDetailFragment.getFragmentManager().beginTransaction()
                    .replace(R.id.root_frame1,listFragment)
                    .addToBackStack(null)
                    .commit();
        }

    }

    @Override
    public void onDelete(int position, TransactionDetailFragment transactionDetailFragment){
        TransactionModel.transactions.remove(position);
        TransactionListFragment listFragment = new TransactionListFragment();
        if (twoPaneMode){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.transaction_list, listFragment)
                    .commit();
        }
        else{
            transactionDetailFragment.getFragmentManager().beginTransaction()
                    .replace(R.id.root_frame1,listFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        if(!twoPaneMode && viewPager.getCurrentItem() == 0) {
            FragmentManager fm = getSupportFragmentManager();
            for (Fragment frag : fm.getFragments()) {
                if (frag.isVisible()) {
                    FragmentManager childFm = frag.getChildFragmentManager();
                    if (childFm.getBackStackEntryCount() > 0) {
                        childFm.popBackStack();
                        return;
                    }
                }
            }
        }
        super.onBackPressed();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }







}
