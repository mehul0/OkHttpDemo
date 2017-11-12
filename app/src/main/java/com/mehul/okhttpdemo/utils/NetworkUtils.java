package com.mehul.okhttpdemo.utils;

import com.fasterxml.jackson.core.JsonGenerationException;

import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

/**
 * Created by Micky on 11/12/2017.
 */

public class NetworkUtils {
    public static final String WS_URL = "https://jsonplaceholder.typicode.com/posts/1";


    public static void doNetworkProcessPost(String request_str,
                                            StringBuilder responseStrBuilder, String web_service_method)
            throws SocketTimeoutException, JsonGenerationException, IOException {
        URL url = new URL(WS_URL + web_service_method);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        try {
            httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestMethod("POST");

            if (request_str != null) {
                OutputStream os = httpURLConnection.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os);
                osw.write(request_str);
                osw.flush();
                osw.close();
                os.close();
            }

            InputStream is;

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                is = new BufferedInputStream(httpURLConnection.getInputStream());
            } else {
                is = new BufferedInputStream(httpURLConnection.getErrorStream());
            }

            BufferedReader streamReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

            String line = null;

            while ((line = streamReader.readLine()) != null) {
                responseStrBuilder.append(line);
            }

            streamReader.close();
            is.close();
        } finally {
            if (httpURLConnection != null) httpURLConnection.disconnect();
        }
    }

    public static void doNetworkProcessGet(String request_str
            , StringBuilder responseStrBuilder /*, String web_service_method*/)
            throws SocketTimeoutException, JsonGenerationException, IOException, JSONException {

        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {
            URL url = new URL(request_str);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));
            StringBuffer buffer = new StringBuffer();

            String line = " ";
            while ((line = reader.readLine()) != null) {
                responseStrBuilder.append(line);
            }
        } finally {
            if (connection != null) connection.disconnect();
        }

    }
}
