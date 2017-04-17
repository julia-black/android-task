package com.example.juli.myapplication;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpFetcherActivity extends Activity {
    private int loadedCount = 0;

    private void loadData(String url){
        new FetchTask(url).execute();
    }
    private class FetchTask extends AsyncTask<Void, Void, String> {

        private final String url;

        public FetchTask(String url) {
            this.url = url;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(Void... params) {
            //исполняется в параллельном потоке
            String res = null;
            try {
                URL url = new URL(this.url);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                try{
                    InputStream inputStream = conn.getInputStream();
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    try {
                        byte[] buf = new byte[32 * 1024];
                        while (true){
                            int bytesRead = inputStream.read(buf);
                            if(bytesRead < 0)
                                break;
                            outputStream.write(buf);
                        }
                        res = outputStream.toString("UTF-8");
                    }
                    finally {
                        inputStream.close();
                        outputStream.close();
                    }
                }
                finally{
                    conn.disconnect();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return res;
        }
        @Override
        protected void onPostExecute(String response) {
            //то что на главном потоке
            TextView responseText = (TextView) findViewById(R.id.response_text);
            responseText.setText(response != null ? response : "Error");

        }
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_fetcher);
        Button okBtn = (Button) findViewById(R.id.ok_btn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText urlEdit = (EditText) findViewById(R.id.url_edit);
                loadData(urlEdit.getText().toString());
            }
        });
    }
}
