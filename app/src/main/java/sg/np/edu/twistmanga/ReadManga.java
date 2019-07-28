package sg.np.edu.twistmanga;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
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

import java.util.HashMap;
import java.util.Map;

public class ReadManga extends AppCompatActivity {

    String chapterId;
    ReadMangaAdapter readMangaAdapter;
    ViewPager vp;
    Map<String, String> pages = new HashMap<String, String>();
    String URL_DATA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_manga);

        vp = findViewById(R.id.mangaVP);
        Bundle extras = getIntent().getExtras();
        chapterId = extras.getString("chapId");
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
