package com.android.casopisinterfon.interfon;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.casopisinterfon.interfon.internet.DownloadInterface;
import com.android.casopisinterfon.interfon.internet.NetworkManager;
import com.android.casopisinterfon.interfon.model.Article;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DownloadInterface{

    List<String> list = new ArrayList<>();
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(arrayAdapter);
        
        NetworkManager manager = NetworkManager.getInstance(this);
        manager.downloadArticles(0, this);
    }

    @Override
    public void onDownloadSuccess(JSONObject response) {
        try {
            JSONArray array = response.getJSONArray(Article.POSTS);
            int size = array.length();
            for (int i = 0; i < size; i++) {
                list.add(array.getJSONObject(i).getString(Article.POST_TITLE));
            }
            arrayAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDownloadFailed(String error) {

    }
}
