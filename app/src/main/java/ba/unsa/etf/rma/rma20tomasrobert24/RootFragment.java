package ba.unsa.etf.rma.rma20tomasrobert24;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class RootFragment extends Fragment {

    private static final String TAG = "RootFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_root, container, false);
        getChildFragmentManager().beginTransaction().replace(R.id.root_frame1, new TransactionListFragment()).addToBackStack(null).commit();

        return view;
    }

}
