package sg.np.edu.twistmanga;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;


public class settings extends AppCompatActivity {
    RecyclerView rv;
    ArrayList<String> data;
    itemadapter adapter;

    // Creates settings activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar tool = findViewById(R.id.my_toolbar);
        setSupportActionBar(tool);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        tool.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(settings.this,MainActivity.class);
                startActivity(intent);
            }
        });

        data= new ArrayList<>();
        data.add("Rate us on Google Play");
        data.add("Follow us");
        data.add("Email Support");
        data.add("Create Account");
        data.add("Version 2.0");
        data.add("Tips & Guides");
        data.add("About Us");
        data.add("Disclaimer");
        rv = findViewById(R.id.settingsRV);
        adapter =  new itemadapter(this,data);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        rv.setLayoutManager(layout);
        rv.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv.getContext(),
                layout.getOrientation());
        rv.addItemDecoration(dividerItemDecoration);
        rv.setAdapter(adapter);


    }
}
