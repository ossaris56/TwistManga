package sg.np.edu.twistmanga;


public class ChapterInfo {
    int number;
    String date;
    String title;
    String id;

    public int getChapterNumber() {
        return number;
    }

    public String getChapterDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public ChapterInfo(int number, String date, String title, String id) {
        this.number = number;
        this.date = date;
        this.title = title;
        this.id = id;
    }

//    public ArrayList<Page> getPages() throws IOException {
//        return RequestHandler.instance().query(String.format("chapter/%s", getId()), Chapter.class).getPages();
//    }
}
