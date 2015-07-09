package app.pomis.infogetter;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.util.Log;


import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * NetworkUtils.
 *
 * @author ccollins
 *
 */
public final class NetworkUtils {

    private NetworkUtils() {
    }

    public static String getIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException e) {
            // Log.e(Constants.LOG_TAG, e.getMessage(), e);
        }
        return null;
    }

    public static boolean connectionPresent(final ConnectivityManager cMgr) {
        if (cMgr != null) {
            NetworkInfo netInfo = cMgr.getActiveNetworkInfo();
            if ((netInfo != null) && (netInfo.getState() != null)) {
                return netInfo.getState().equals(State.CONNECTED);
            } else {
                return false;
            }
        }
        return false;
    }
}