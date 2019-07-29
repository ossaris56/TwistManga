package sg.np.edu.twistmanga;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ReadManga extends AppCompatActivity {

    String chapterId;
    ReadMangaAdapter readMangaAdapter;
    ViewPager vp;
    Map<String, String> pages = new HashMap<String, String>();
    String URL_DATA;
    ArrayList<String> chapterlist;
    private boolean isLastPageSwiped;
    private int counterPageScroll;
    int chappos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_manga);

        vp = findViewById(R.id.mangaVP);
        getIntent().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Bundle extras = getIntent().getExtras();
        chapterId = extras.getString("chapId");
        chapterlist = extras.getStringArrayList("nxtchapID");
        chappos = chapterlist.indexOf(chapterId);
        URL_DATA = "https://www.mangaeden.com/api/chapter/" + chapterId + "/";
        Log.d("URL DATA", URL_DATA);
        loadUrlJson();
    }

    // Fetches manga images from the api and displays them in a viewpager
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

                   JSONArray array = jsonObject.getJSONArray("images");

                   for (int i=0; i < array.length(); i++) {
                       JSONArray jo = array.getJSONArray(i);
                       pages.put(jo.getString(0), jo.getString(1));
                   }

                   Log.d("PAGES :", pages.get("0"));

                   readMangaAdapter = new ReadMangaAdapter(getApplicationContext(), pages);
                   vp.setAdapter(readMangaAdapter);
                   vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                       @Override
                       public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                           if ((position == vp.getAdapter().getCount()-1) && positionOffset == 0 && !isLastPageSwiped){
                               if(counterPageScroll != 0){
                                   isLastPageSwiped=true;
                                   chappos--;
                                   if(chappos>=0) {
                                       chapterId = chapterlist.get(chappos);
                                       int number = chapterlist.size() - chappos;
                                       Toast tt = Toast.makeText(getApplicationContext(), "Chapter "+number, Toast.LENGTH_LONG);
                                       tt.show();
                                       Intent intent = new Intent(getApplicationContext(), ReadManga.class);
                                       intent.putExtra("chapId", chapterId);
                                       intent.putStringArrayListExtra("nxtchapID", chapterlist);
                                       startActivity(intent);
                                   }
                                   else{
                                       Toast tt = Toast.makeText(getApplicationContext(), "Last Chapter reached", Toast.LENGTH_LONG);
                                       tt.show();
                                   }
                               }
                               counterPageScroll++;
                           }
                           else if(position == 0 && positionOffset == 0 && !isLastPageSwiped)
                           {
                               if(counterPageScroll<0) {
                                   isLastPageSwiped=true;
                                   chappos++;
                                   Log.d("ReadManga", "onPageScrolled: " + chappos);
                                   isLastPageSwiped = true;
                                   if (chappos < chapterlist.size()) {
                                       chapterId = chapterlist.get(chappos);
                                       int number = chapterlist.size() - chappos;
                                       Toast tt = Toast.makeText(getApplicationContext(), "Chapter " + number, Toast.LENGTH_LONG);
                                       tt.show();
                                       Intent intent = new Intent(getApplicationContext(), ReadManga.class);
                                       intent.putExtra("chapId", chapterId);
                                       intent.putStringArrayListExtra("nxtchapID", chapterlist);
                                       startActivity(intent);
                                   }
                               }
                               counterPageScroll--;
                           }
                           else{
                               counterPageScroll=0;
                           }
                       }

                       @Override
                       public void onPageSelected(int position) {

                       }

                       @Override
                       public void onPageScrollStateChanged(int state) {

                       }
                   });

               }
               catch (JSONException e) {
                   e.printStackTrace();
               }
           }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error ) {
                Toast.makeText(ReadManga.this, "Error" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
