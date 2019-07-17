package sg.np.edu.twistmanga;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MangaAdapter extends RecyclerView.Adapter<MangaAdapter.ViewHolder>{

    private List<Manga> mangaList;
    private Context context;

    public MangaAdapter(List<Manga> mangaList, Context context) {
        this.mangaList = mangaList;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.manga_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Manga manga = mangaList.get(position);
        holder.mangaTitle.setText(manga.getTitle());

        Picasso.get().load(manga.getImage()).placeholder(R.drawable.noimage).into(holder.mangaImg);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mangaDetailsIntent = new Intent(context, Manga_details.class);
                mangaDetailsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(mangaDetailsIntent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return mangaList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView mangaImg;
        public TextView mangaTitle;
        public LinearLayout linearLayout;


        public ViewHolder(View itemView)
        {
            super(itemView);
            mangaImg = itemView.findViewById(R.id.mangaImg);
            mangaTitle = itemView.findViewById(R.id.mangaTitle);
            linearLayout = itemView.findViewById(R.id.mangaLinear);
        }

    }
}
