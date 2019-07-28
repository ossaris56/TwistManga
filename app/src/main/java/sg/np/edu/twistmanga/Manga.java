package sg.np.edu.twistmanga;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Manga implements Parcelable {
    private String mangaTitle;
    private String image_url;
    private ArrayList<String> category;
    private String status;
    private String id;

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    // Manga class constructor
    protected Manga(Parcel in) {
        mangaTitle = in.readString();
        image_url = in.readString();
        category = in.readArrayList(Manga.class.getClassLoader());
        status = in.readString();
        id = in.readString();
    }

    public static final Creator<Manga> CREATOR = new Creator<Manga>() {
        @Override
        public Manga createFromParcel(Parcel in) {
            return new Manga(in);
        }

        @Override
        public Manga[] newArray(int size) {
            return new Manga[size];
        }
    };

    public String getTitle(){return mangaTitle; }
    public String getImage(){return image_url; }
    public ArrayList<String> getCategory(){return category; }
    public String getStatus(){return status;}
    public String getId(){return id;}

    public Manga(String mangaTitle, String image_url, ArrayList<String> category, String status, String id) {
        this.mangaTitle = mangaTitle;
        this.image_url = image_url;
        this.category = category;
        this.status = status;
        this.id = id;
    }
    public void setMangaTitle(String MangaTitle){
        mangaTitle = MangaTitle;
    }

    public void setImage_url(String Image_url){
        image_url = Image_url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mangaTitle);
        parcel.writeString(image_url);
        parcel.writeList(category);
        parcel.writeString(status);
        parcel.writeString(id);
    }
}
