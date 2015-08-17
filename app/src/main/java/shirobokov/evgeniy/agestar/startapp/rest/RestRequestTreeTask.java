package shirobokov.evgeniy.agestar.startapp.rest;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import shirobokov.evgeniy.agestar.startapp.R;
import shirobokov.evgeniy.agestar.startapp.activities.MainActivity;
import shirobokov.evgeniy.agestar.startapp.models.Tree;

/**
 * Created by Евгений on 12.08.2015.
 */
public class RestRequestTreeTask extends AsyncTask<Void, Void, List<Tree>> {

    private final static String URL_RESOURCE = "https://money.yandex.ru/api/categories-list";
    private final static Integer TIMEOUT = 1500;
    private Exception error;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.error = null;
    }

    @Override
    protected List<Tree> doInBackground(Void... params) {
        try {
            SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
            factory.setConnectTimeout(TIMEOUT);
            factory.setReadTimeout(TIMEOUT);
            RestTemplate restTemplate = new RestTemplate(factory);
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            ResponseEntity<Tree[]> treeArrayEntity = restTemplate.getForEntity(URL_RESOURCE, Tree[].class);
            Tree[] treeArray = treeArrayEntity.getBody();
            List<Tree> treeList = new ArrayList<>();
            for (Tree tree : treeArray) {
                treeList.add(tree);
            }
            return treeList;
        } catch (Exception e) {
            e.printStackTrace();
            this.error = e;
            return null;
        }
    }

    public Exception getException() {
        return this.error;
    }

    @Override
    protected void onPostExecute(List<Tree> treeArray) {
        super.onPostExecute(treeArray);
    }
}
