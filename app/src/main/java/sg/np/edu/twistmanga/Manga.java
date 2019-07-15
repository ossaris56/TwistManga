package sg.np.edu.twistmanga;

import java.util.List;

public class Manga {
    private String mangaTitle;
    private String image_url;

    public String getTitle(){return mangaTitle; }
    public String getImage(){return image_url; }

    public Manga(String mangaTitle, String image_url) {
        this.mangaTitle = mangaTitle;
        this.image_url = image_url;
    }
}
