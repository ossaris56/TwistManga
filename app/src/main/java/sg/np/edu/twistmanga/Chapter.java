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

    public String getChapId(){return chapId; }
    public int getChapNum(){return chapNum; }
    public String[] getChapImages(){return chapImages; }

}
