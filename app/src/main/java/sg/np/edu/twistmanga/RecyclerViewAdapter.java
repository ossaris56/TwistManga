package sg.np.edu.twistmanga;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static android.content.ContentValues.TAG;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    Context context;
    List<Manga> MangaList;
    LayoutInflater inflater;
    RecyclerViewItemClickListener recyclerViewItemClickListener;



    public class ViewHolder extends RecyclerView.ViewHolder {
        public int position=0;
        public ViewHolder(View v) {
        super(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewItemClickListener.onItemClick(v,position);
                Log.d(TAG, "onClick:Its working eh ");
            }
        });
        }
    }
    //Set method of OnItemClickListener object
    public void setOnItemClickListener(RecyclerViewItemClickListener recyclerViewItemClickListener){
        this.recyclerViewItemClickListener=recyclerViewItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    //Constructor of RecyclerViewAdapter
    //It obtains model list coming from MainActivity here


}
