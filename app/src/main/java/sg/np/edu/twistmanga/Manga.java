package sg.np.edu.twistmanga;

import java.util.List;

public class Manga {
    private String mangaTitle;
    private String image_url;
    private String category;

    public String getTitle(){return mangaTitle; }
    public String getImage(){return image_url; }
    public String getCategory(){return category; }

    public Manga(String mangaTitle, String image_url, String category) {
        this.mangaTitle = mangaTitle;
        this.image_url = image_url;
        this.category = category;
    }
}
