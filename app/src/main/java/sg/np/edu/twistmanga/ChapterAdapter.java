package sg.np.edu.twistmanga;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ViewHolder>{

    private List<String> chapterlist;
    private List<String> chapterIdList;
    private Context c;
    public TextView txt;

    public ChapterAdapter(ArrayList<String> chapterlist, ArrayList<String> chapterIdList, Context context) {
        this.c = context;
        this.chapterlist = chapterlist;
        this.chapterIdList = chapterIdList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chapter_layout,parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.txt.setText(" Chapter "+ chapterlist.get(position));
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent readMangaIntent = new Intent(c, ReadManga.class);
                String chapterId = chapterIdList.get(position);
                readMangaIntent.putExtra("chapId", chapterId);
                c.startActivity(readMangaIntent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return chapterlist.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public LinearLayout linearLayout;
        public TextView txt;
        public ViewHolder(View itemView)
        {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.chapter_layout);
            txt= itemView.findViewById(R.id.chaptertv);
        }

    }
}
