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

public class Favourites extends AppCompatActivity {

    RecyclerView recyclerView;
    FavouritesAdapter favouritesAdapter;
    List<Manga> mangaList;
    private static final String URL_DATA = "https://www.mangaeden.com/api/list/0";
    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;
    DBHandler db = new DBHandler(this,null,null,1);
    ArrayList ongoingList;
    ArrayList completedList;
    TextView numFavs;
    Button sortBtn;
    AlertDialog sortAlert;
    CharSequence[] sortList = {" Name Ascending "," Name Descending "};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

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
                    int mCount = 0;
                    for(Manga m: mangaList){

                        if(Integer.parseInt(m.getStatus()) <= 1){

                            ongoingList.add(m);
                            mCount += 1;

                        }

                    }

                    //shows number of manga favs
                    numFavs.setText(mCount + " Manga");

                    favouritesAdapter = new FavouritesAdapter(ongoingList, getApplicationContext(),db);
                    recyclerView.setAdapter(favouritesAdapter);

                    DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
                    drawerLayout.closeDrawer(Gravity.LEFT);

                    favouritesAdapter.notifyDataSetChanged();
                }

                //Retrieve manga with completed status
                if(id == R.id.completedmanga)
                {
                    //display manga with completed status
                    completedList = new ArrayList<Manga>();
                    int mCount = 0;
                    for(Manga m: mangaList){

                        if(Integer.parseInt(m.getStatus()) == 2){

                            completedList.add(m);
                            mCount += 1;

                        }

                    }

                    //shows number of manga favs
                    numFavs.setText(mCount + " Manga");

                    favouritesAdapter = new FavouritesAdapter(completedList, getApplicationContext(),db);
                    recyclerView.setAdapter(favouritesAdapter);

                    DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
                    drawerLayout.closeDrawer(Gravity.LEFT);

                    favouritesAdapter.notifyDataSetChanged();
                }
                if(id == R.id.settings)
                {
                    Intent intent = new Intent(getApplicationContext(),settings.class);
                    startActivity(intent);
                }
                return true;
            }
        });

        recyclerView = findViewById(R.id.favRV);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new SpacesItemDecoration(1));
        mangaList = new ArrayList<>();
        numFavs = (TextView)findViewById(R.id.numManga);
        showFavourites();

        //sort by button
        sortBtn = (Button)findViewById(R.id.sortBtn);
        sortBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sortAlertDialog();

            }
        });
    }

    //Navbar needs this. Don't touch it jabier.
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return abdt.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    public void showFavourites(){
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
                        ArrayList<String> categories = new ArrayList<>();
                        if (jo.getJSONArray("c") != null) {
                            for (int in=0; in< jo.getJSONArray("c").length(); in++) {
                                   categories.add(jo.getJSONArray("c").getString(in));
                            }
                        }
                        if(db.isFavourite(jo.getString("t"))){
                            Manga manga = new Manga(jo.getString("t"), ("https://cdn.mangaeden.com/mangasimg/" + jo.getString("im")), categories, jo.getString("s"),jo.getString("i"));
                            mangaList.add(manga);
                            mCount += 1;
                        }
                    }

                    //show number of manga favs
                    numFavs.setText(mCount + " Manga");

                    favouritesAdapter = new FavouritesAdapter(mangaList, getApplicationContext(),db);
                    recyclerView.setAdapter(favouritesAdapter);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error ) {
                Toast.makeText(getApplicationContext(), "Error" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    //sorting options method
    public void sortAlertDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(Favourites.this);
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
                        favouritesAdapter.notifyDataSetChanged();
                        break;

                    case 1:
                        //Sort according to name descending
                        Collections.sort(mangaList, new Comparator<Manga>() {
                            @Override
                            public int compare(Manga m1, Manga m2) {

                                return m2.getTitle().compareToIgnoreCase(m1.getTitle());
                            }
                        });
                        favouritesAdapter.notifyDataSetChanged();
                        break;
                }
                sortAlert.dismiss();
            }
        });
        sortAlert = builder.create();
        sortAlert.show();
    }
}

