package sg.np.edu.twistmanga;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
    private static final String URL_DATA = "https://www.mangaeden.com/api/list/0/?l=200";
    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;
    DBHandler db = new DBHandler(this,null,null,1);
    Button sortBtn;
    AlertDialog sortAlert;
    CharSequence[] sortList = {" Name Ascending "," Name Descending "};
    Button filterBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //sort by button
        sortBtn = (Button)findViewById(R.id.sortBtn);
        sortBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortAlertDialog();
            }
        });

        //Adds navbar
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

                //Brings user to Main page
                if(id == R.id.home)
                {
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }

                //Brings user to favourite page
                if(id == R.id.fav)
                {
                    Intent intent = new Intent(getApplicationContext(),Favourites.class);
                    startActivity(intent);
                }
                //Sorts mangaList according to name, but adds another mangaList when clicked on a 2nd time
                if(id == R.id.sortasecend)
                {
                    //Sort according to name ascending
                    Collections.sort(mangaList, new Comparator<Manga>() {
                        @Override
                        public int compare(Manga m1, Manga m2) {

                            return m1.getTitle().compareTo(m2.getTitle());
                        }
                    });
                    DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
                    drawerLayout.closeDrawer(Gravity.LEFT);
                    mangaAdapter.notifyDataSetChanged();
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
    }

    //Navbar needs this. Don't touch it jabier.
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

                       Manga manga = new Manga(jo.getString("t"), ("https://cdn.mangaeden.com/mangasimg/" + jo.getString("im")), jo.getString("c"),jo.getString("s"),jo.getString("i"));
                       mangaList.add(manga);
                   }

                   mangaAdapter = new MangaAdapter(mangaList, getApplicationContext(),db);
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

    public void sortAlertDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Sort By...?");

        builder.setSingleChoiceItems(sortList, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int item) {

                switch(item)
                {
                    case 0:
                        //Sort according to name ascending
                        Collections.sort(mangaList, new Comparator<Manga>() {
                            @Override
                            public int compare(Manga m1, Manga m2) {

                                return m1.getTitle().compareTo(m2.getTitle());
                            }
                        });
                        mangaAdapter.notifyDataSetChanged();
                        break;

                    case 1:
                        //Sort according to name descending
                        Collections.sort(mangaList, new Comparator<Manga>() {
                            @Override
                            public int compare(Manga m1, Manga m2) {

                                return m2.getTitle().compareToIgnoreCase(m1.getTitle());
                            }
                        });
                        mangaAdapter.notifyDataSetChanged();
                        break;
                }
                sortAlert.dismiss();
            }
        });
        sortAlert = builder.create();
        sortAlert.show();
    }
}
