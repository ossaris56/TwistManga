    package sg.np.edu.twistmanga;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

    // Tips and Tricks Page
    public class emptyactivity extends AppCompatActivity {
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emptyactivity);

        Toolbar tool = findViewById(R.id.my_toolbar);
        setSupportActionBar(tool);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        tool.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(emptyactivity.this,settings.class);
                startActivity(intent);
            }
        });

        tv = findViewById(R.id.empty);
        Intent intent = getIntent();
        String help = intent.getStringExtra("help");
        tv.setText(help);
    }
}
