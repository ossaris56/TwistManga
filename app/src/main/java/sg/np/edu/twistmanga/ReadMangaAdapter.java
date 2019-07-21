package sg.np.edu.twistmanga;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Picasso;

import java.util.Map;

public class ReadMangaAdapter extends PagerAdapter {
    private Context context;
    private Map<String, String> pages;

    public ReadMangaAdapter(Context context, Map<String, String> pages) {
        this.context = context;
        this.pages = pages;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.manga_page, collection, false);
        ImageView mangaPage = layout.findViewById(R.id.mangaPage);
        String imageURL = "https://cdn.mangaeden.com/mangasimg/" + pages.get(Integer.toString(position));
        Picasso.get().load(imageURL).placeholder(R.drawable.noimage).into(mangaPage);
        //Media.setImageFromUrl(mangaPage, imageUrl);//call to GlideApp or Picasso to load the image into the ImageView
        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem( ViewGroup container, int position, @NonNull Object view) {
        container.removeView((View) view);
    }

    @Override
    public int getCount() {
        return this.pages.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
}
}
