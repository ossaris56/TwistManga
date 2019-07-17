package sg.np.edu.twistmanga;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MangaAdapter mangaAdapter;
    List<Manga> mangaList;
    RecyclerViewAdapter adapter;
    private static final String URL_DATA = "https://www.mangaeden.com/api/list/0/?p=1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.mangaRV);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new SpacesItemDecoration(1));
        mangaList = new ArrayList<>();
        loadUrlJson();
        adapter = new RecyclerViewAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                //View view, int position
                Intent intent = new Intent(MainActivity.this,Manga_details.class);
                startActivity(intent);
                Toast tt = Toast.makeText(MainActivity.this, "This is working",Toast.LENGTH_LONG);
                tt.show();
            }
        });
    }

    private void loadUrlJson() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_DATA, new Response.Listener<String>() {
           @Override
           public void onResponse(String response) {
               progressDialog.dismiss();
               try {
                   JSONObject jsonObject = new JSONObject(response);

                   JSONArray array = jsonObject.getJSONArray("manga");

                   for (int i=0; i < array.length(); i++) {
                       JSONObject jo = array.getJSONObject(i);

                       Manga manga = new Manga(jo.getString("t"), ("https://cdn.mangaeden.com/mangasimg/" + jo.getString("im")));
                       mangaList.add(manga);
                   }
                   mangaAdapter = new MangaAdapter(mangaList, getApplicationContext());
                   recyclerView.setAdapter(mangaAdapter);
               }
               catch (JSONException e) {
                   e.printStackTrace();
               }
           }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error ) {
                Toast.makeText(MainActivity.this, "Error" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}
