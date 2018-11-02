package com.stayfit.app.stayfitBharat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by mayank on 20/04/18.
 */

public class DownloadUrl {

    //This class is going to retrieve data from the URL using HTTP URL Connection and File Handling Methods

    public String readUrl(String myUrl) throws IOException {

        String data = "";
        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;

        try {
            URL url = new URL(myUrl); //Created the URL
            urlConnection = (HttpURLConnection) url.openConnection(); //Opened the Connection
            urlConnection.connect(); //Connected

            inputStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();
            br.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            inputStream.close();
            urlConnection.disconnect();
        }

        return data;

        //Data return will be in JSON format so we will get this data using HTTPUrlConnection.
    }
}
