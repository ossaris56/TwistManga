package sg.np.edu.twistmanga;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.view.menu.MenuBuilder;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class itemadapter extends RecyclerView.Adapter<ItemViewHolder>{
    ArrayList<String> data;
    Context c;
    int clickcount=0;
    public itemadapter(Context c, ArrayList<String>data){
        this.c = c ;
        this.data = data;
    }
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.settings_item, parent, false);
        return new ItemViewHolder(item);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        String s = data.get(position);
        holder.txt.setText(s);
        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position == 0){
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id=sg.np.edu.twistmanga"));
                    view.getContext().startActivity(browserIntent);
                }
                if(position == 1){
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.instagram.com/twistmanga00/"));
                    view.getContext().startActivity(browserIntent);
                }
                if(position == 2){
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("mailto:twistmanga@gmail.com"));
                    view.getContext().startActivity(browserIntent);
                }
                if(position == 3){
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.instagram.com/twistmanga00/"));
                    view.getContext().startActivity(browserIntent);
                }
                if(position == 4){
                    clickcount=clickcount+1;
                    if(clickcount==10)
                    {
                        //first time clicked to do this
                        Toast.makeText(view.getContext(),"Developer Mode Unlocked", Toast.LENGTH_LONG).show();
                    }
                }
                if(position == 5){
                    Intent intent =  new Intent(view.getContext(),emptyactivity.class);
                    String tips = "To read the manga chapters,click the specific manga that you enjoy " +
                            "and choose a chapter number, start reading from left to right and enjoy!\n" +
                            "If you are interested,click on the top right icon to filter the mangas!";
                    intent.putExtra("help",tips);
                    view.getContext().startActivity(intent);
                }
                if(position == 6){
                    Intent intent =  new Intent(view.getContext(),emptyactivity.class);
                    String tips = "We are just a avid manga reader just like you. Hope you enjoy your manga reads here! Thank you for " +
                            "browsing at TwistManga!";
                    intent.putExtra("help",tips);
                    view.getContext().startActivity(intent);
                }
                if(position == 7){
                    Intent intent =  new Intent(view.getContext(),emptyactivity.class);
                    String tips = "All the contents hosted on this website are obtained from the internet " +
                            "or are uploaded by the users. We do not own any of the content found on this app.\n" +
                            "In no event we are not liable in the damage or any loss brought forth to the users in using this app. " +
                            "All the contents are meant for entertainment and for the people of all age." +
                            "\nShould any of the clients have complaints on the content hosted in this site you may contact us by dropping" +
                            "us a email by clicking Email Support or drop us a review at Google Play Store. ";
                    intent.putExtra("help",tips);
                    view.getContext().startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
