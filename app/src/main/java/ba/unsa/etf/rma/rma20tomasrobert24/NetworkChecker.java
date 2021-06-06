package ba.unsa.etf.rma.rma20tomasrobert24;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkChecker {

    public static boolean isConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager!=null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if(activeNetworkInfo!=null){
                if(activeNetworkInfo.getType()==ConnectivityManager.TYPE_WIFI){
                    return true;
                }
                else return activeNetworkInfo.getType()==ConnectivityManager.TYPE_MOBILE;
            }
        }
        //return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        return false;
    }
}
