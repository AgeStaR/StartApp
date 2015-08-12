package shirobokov.evgeniy.agestar.startapp.rest;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import shirobokov.evgeniy.agestar.startapp.R;
import shirobokov.evgeniy.agestar.startapp.activities.MainActivity;
import shirobokov.evgeniy.agestar.startapp.models.Tree;

/**
 * Created by Евгений on 12.08.2015.
 */
public class RestRequestTreeTask extends AsyncTask<Void, Void, Tree[]> {

    private final static String URL_RESOURCE = "https://money.yandex.ru/api/categories-list";

    private MainActivity activity;

    public RestRequestTreeTask(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Tree[] doInBackground(Void... params) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            ResponseEntity<Tree[]> responseEntity = restTemplate.getForEntity(URL_RESOURCE, Tree[].class);
            Tree[] tree = responseEntity.getBody();
            return tree;
        } catch (Exception e) {
            Log.e("MainActivity", e.getMessage(), e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Tree[] tree) {
        super.onPostExecute(tree);
        TextView text = (TextView) activity.findViewById(R.id.content_label);
        text.setText("100");
    }
}