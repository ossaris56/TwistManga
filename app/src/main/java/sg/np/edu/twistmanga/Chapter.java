package sg.np.edu.twistmanga;

import java.util.ArrayList;

public class Chapter {
    public String chapId;
    public int chapNum;
    public String[] chapImages = {};

    public Chapter(String chapId, int chapNum, String[] chapImages) {
        this.chapId = chapId;
        this.chapNum = chapNum;
        this.chapImages = chapImages;
    }

    // Gets the chapter ID
    public String getChapId(){return chapId; }

    // Gets the chapter number
    public int getChapNum(){return chapNum; }

    // Gets the chapter images
    public String[] getChapImages(){return chapImages; }

}
