package sg.np.edu.twistmanga;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ItemViewHolder extends RecyclerView.ViewHolder {
    public TextView txt;
    public View v;

    public ItemViewHolder(View view){
        super(view); //parent class constructor
        txt= view.findViewById(R.id.textView9);
        v = view;
    }
}
