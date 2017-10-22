package com.project.dwidianayu.getpagesource;


import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetSource extends AsyncTaskLoader<String>{

    String LinkUrl,result;
    boolean cancel = false;

    public GetSource(Context context, String linkUrl) {
        super(context);
        this.LinkUrl = linkUrl;
    }

    @Override
    public String loadInBackground() {
        String rebString = null;
        InputStream in = null;
        HttpURLConnection connect = null;
        try {
            URL url = new URL(LinkUrl);
            connect = (HttpURLConnection)url.openConnection();
            connect.setReadTimeout(1000);
            connect.setConnectTimeout(2000);
            connect.setRequestMethod("GET");
            connect.connect();

            if (connect.getResponseCode()==HttpURLConnection.HTTP_OK){
                in = connect.getInputStream();
                if (in == null){
                    return "Error";
                }
                BufferedReader bufRead = new BufferedReader(new InputStreamReader(in));

                StringBuilder build = new StringBuilder();
                String line ="";

                while ((line = bufRead.readLine()) !=null)
                {
                    build.append(line+"\n");
                }
                rebString = build.toString();
            }
            else {
                return "Error Response Code"+connect.getResponseCode();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }

        finally {
            try {
                if (in != null){
                    in.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
            connect.disconnect();
        }
        return rebString;
    }

    @Override
    protected void onStartLoading() {
        if(result!=null || cancel){
            deliverResult(result);
        }else{
            forceLoad();
        }
    }

    @Override
    public void deliverResult(String data) {
        super.deliverResult(data);
        result=data;
    }

    @Override
    public void onCanceled(String data) {
        super.onCanceled(data);
        cancel = true;
    }
}