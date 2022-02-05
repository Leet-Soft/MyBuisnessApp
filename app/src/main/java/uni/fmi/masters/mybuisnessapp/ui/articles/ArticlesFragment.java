package uni.fmi.masters.mybuisnessapp.ui.articles;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import uni.fmi.masters.mybuisnessapp.R;
import uni.fmi.masters.mybuisnessapp.entity.ArticleEntity;


public class ArticlesFragment extends Fragment {

    ArrayList<ArticleEntity> articles = new ArrayList<>();
    RecyclerView articlesRV;
    EditText filterET;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_articles, container, false);

        filterET = view.findViewById(R.id.filterEditText);
        articlesRV = view.findViewById(R.id.articlesRecyclerView);


        new ArticlesAsyncTask(filterET.getText().toString()).execute();

        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    private class ArticlesAsyncTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog = new ProgressDialog(getContext());
        String filter;

        public ArticlesAsyncTask(String filter) {
            this.filter = filter;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog.setTitle("Loading all articles...");
            dialog.show();

            articles.clear();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            //GetArticlesList?name={name}&barcode={barcode}
            String urlString = "http://192.168.0.127:8989/GetArticlesList?name= " + filter + "&barcode=" + filter;

            HttpURLConnection urlConnection = null;

            try{
                URL url = new URL(urlString);

                urlConnection = (HttpURLConnection) url.openConnection();

                BufferedInputStream stream = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

                String line = reader.readLine();

                if(line != null){
                    JSONArray array = new JSONArray(line);

                    for(int i = 0; i < array.length(); i++){

                        JSONObject jobject = array.getJSONObject(i);

                        ArticleEntity article = new ArticleEntity();
                        article.id = jobject.getInt("id");
                        article.barcode = jobject.getString("barcode");
                        article.name = jobject.getString("name");
                        article.price = jobject.getDouble("price");

                        articles.add(article);
                    }
                }
            }catch (IOException | JSONException e){
                Log.e("Error", e.getMessage());
            }finally {
                if(urlConnection != null)
                    urlConnection.disconnect();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);

            dialog.hide();
        }
    }
}