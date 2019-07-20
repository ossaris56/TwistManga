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

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.ViewHolder>{

    private List<Manga> mangaList;
    private Context context;
    private DBHandler db;

    public FavouritesAdapter(List<Manga> mangaList, Context context, DBHandler db) {
        this.mangaList = mangaList;
        this.context = context;
        this.db = db;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.manga_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Manga manga = mangaList.get(position);
        holder.mangaTitle.setText(manga.getTitle());

        //Add/remove manga from favourites
        holder.fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!db.isFavourite(manga.getTitle()))
                {
                    db.addToFavourites(manga);
                    holder.fav.setImageResource(R.drawable.ic_favorite_red_24dp);
                    Toast.makeText(view.getContext(), "Added to favourites",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    db.deleteFromFavourites(manga);
                    holder.fav.setImageResource(R.drawable.ic_favorite_black_24dp);
                    mangaList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, getItemCount());
                    notifyDataSetChanged();
                    Toast.makeText(view.getContext(), "Removed from favourites",Toast.LENGTH_SHORT).show();
                }
            }
        });

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
        public ImageView mangaImg,fav;
        public TextView mangaTitle;
        public LinearLayout linearLayout;


        public ViewHolder(View itemView)
        {
            super(itemView);
            mangaImg = itemView.findViewById(R.id.mangaImg);
            mangaTitle = itemView.findViewById(R.id.mangaTitle);
            linearLayout = itemView.findViewById(R.id.mangaLinear);
            fav = itemView.findViewById(R.id.fav);
        }

    }
}
