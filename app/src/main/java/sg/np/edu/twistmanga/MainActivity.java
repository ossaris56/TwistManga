package sg.np.edu.twistmanga;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MangaAdapter mangaAdapter;
    List<Manga> mangaList;
    RecyclerViewAdapter adapter;
    private static final String URL_DATA = "https://www.mangaeden.com/api/list/0/?p=1";
    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        dl = (DrawerLayout)findViewById(R.id.drawer_layout);
        abdt = new ActionBarDrawerToggle(this,dl,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        abdt.setDrawerIndicatorEnabled(true);

        dl.addDrawerListener(abdt);
        abdt.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView nav_view = (NavigationView)findViewById(R.id.nav_view);

        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                if(id == R.id.fav)
                {
                    Toast.makeText(MainActivity.this,"It worked",Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return abdt.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
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

                   //Sort in alphabetical order
                   Collections.sort(mangaList, new Comparator<Manga>() {
                       @Override
                       public int compare(Manga m1, Manga m2) {
                           return m1.getTitle().compareTo(m2.getTitle());
                       }
                   });

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
