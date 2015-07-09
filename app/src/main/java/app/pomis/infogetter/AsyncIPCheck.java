package app.pomis.infogetter;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by romanismagilov on 30.06.15.
 */
public class AsyncIPCheck extends AsyncTask<String, String, String> {

    @Override
    protected String doInBackground(String... uri) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response;
        String responseString = null;
        try {
            response = httpclient.execute(new HttpGet(uri[0]));
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                responseString = out.toString();
            } else {
                //Closes the connection.
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (ClientProtocolException e) {
        } catch (IOException e) {
        }
        return responseString;
    }

    String ip = "";
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.d("435678", "Response: " + result);
        parseMyBullshit(result);
        //DrawerActivity.getInstance().onCatsDownloaded();
    }

    void parseMyBullshit(String result) {

        try {
            JSONObject jObject = new JSONObject(result.substring(result.indexOf("{")));
            ip+="IP: "+jObject.getString("query")+"("+jObject.getString("countryCode")+")";
            MainActivity.getInstance().setIP(ip);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
