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
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
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

    private static final String URL_DATA = "https://www.mangaeden.com/api/list/0/?l=200";
    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;
    DBHandler db = new DBHandler(this,null,null,1);
    ArrayList ongoingList;
    List<Manga> completedList;
    TextView numManga;
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

        ListView mListView = (ListView) findViewById(R.id.list);
        TextView mEmptyView = (TextView) findViewById(R.id.emptyView);
        handleIntent(getIntent());

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

                //Retrieve manga with ongoing status
                if(id == R.id.ongoingmanga)
                {
                    //display manga with ongoing status
                    ongoingList = new ArrayList<Manga>();
                    for(Manga m: mangaList){

                        if(Integer.parseInt(m.getStatus()) == 1){

                            ongoingList.add(m);

                        }

                    }

                    mangaAdapter = new MangaAdapter(ongoingList, getApplicationContext(),db);
                    recyclerView.setAdapter(mangaAdapter);

                    DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
                    drawerLayout.closeDrawer(Gravity.LEFT);

                    mangaAdapter.notifyDataSetChanged();
                }

                //Retrieve manga with completed status
                if(id == R.id.completedmanga)
                {
                    //display manga with completed status
                    completedList = new ArrayList<Manga>();
                    for(Manga m: mangaList){

                        if(Integer.parseInt(m.getStatus()) == 2){

                            completedList.add(m);

                        }

                    }

                    mangaAdapter = new MangaAdapter(completedList, getApplicationContext(),db);
                    recyclerView.setAdapter(mangaAdapter);

                    DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
                    drawerLayout.closeDrawer(Gravity.LEFT);

                    mangaAdapter.notifyDataSetChanged();
                }
                if(id == R.id.settings)
                {
                    Intent intent = new Intent(getApplicationContext(),settings.class);
                    startActivity(intent);
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
        numManga = (TextView)findViewById(R.id.numManga);
        loadUrlJson();

        //sort by button
        sortBtn = (Button)findViewById(R.id.sortBtn);
        sortBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sortAlertDialog();

            }
        });

        //filter by button
        filterBtn = (Button)findViewById(R.id.filterBtn);
        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //filterAlertDialog

            }
        });

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

                   //manga counter
                   int mCount = 0;

                   for (int i=0; i < array.length(); i++) {
                       JSONObject jo = array.getJSONObject(i);

                       Manga manga = new Manga(jo.getString("t"), ("https://cdn.mangaeden.com/mangasimg/" + jo.getString("im")), jo.getString("c"),jo.getString("s"),jo.getString("i"));
                       mangaList.add(manga);
                       mCount += 1;
                   }

                   //shows number of manga in app
                   numManga.setText(mCount + " Manga");

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
        }
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
