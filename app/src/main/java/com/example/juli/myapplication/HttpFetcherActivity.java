package com.example.juli.myapplication;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

public class HttpFetcherActivity extends Activity implements LoaderManager.LoaderCallbacks<List<Article>> {
    public final List<Article> articles = new ArrayList<>();
    private ArrayAdapter<Article> adapter;

    @Override
    public Loader<List<Article>> onCreateLoader(int id, Bundle args) { //id -
        return new DataLoader(this);
    }
    @Override
    public void onLoadFinished(Loader<List<Article>> loader, List<Article> articles) {
        this.articles.clear();
        this.articles.addAll(articles);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<List<Article>> loader) {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_fetcher);
        Button okBtn = (Button) findViewById(R.id.ok_btn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLoaderManager().restartLoader(0, null, HttpFetcherActivity.this);
            }
        });

        ListView listView = (ListView) findViewById(R.id.articles_list);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, articles);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Article article = (Article) parent.getItemAtPosition(position);
                String url = article.getLink();
                Intent openBrowserIntent = new Intent(Intent.ACTION_VIEW);
                openBrowserIntent.setData(Uri.parse(url));
                startActivity(openBrowserIntent);
            }
        });
        getLoaderManager().initLoader(0, null, this);
    }

    private static final String TAG = "SGUnews";
}
