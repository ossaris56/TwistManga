package sg.np.edu.twistmanga;

import java.util.ArrayList;


public class Manga_chapters {
    private static String pre="https://cdn.mangaeden.com/mangasimg/%s";
    String[] aka;
    String[] aka_alias;
    String alias;
    String artist;
    String[] artist_kw;
    String author;
    String[] author_kw;
    boolean autoManga;
    boolean baka;
    int chapters_len;
    long created;
    String description;
    int hits;
    String image;
    String imageURL;
    long last_chapter_date;
    int released;
    String startsWith;
    int status;
    String title;
    String[] title_kw;
    int type;
    boolean updatedKeywords;
    String url;
    ArrayList<ChapterInfo> chaptersConverted;
    ArrayList<String[]> chapters;

    public String[] getAka() {
        return aka;
    }

    public String[] getAkaAlias() {
        return aka_alias;
    }

    public String getAlias() {
        return alias;
    }

    public String getArtist() {
        return artist;
    }

    public String[] getArtistKw() {
        return artist_kw;
    }

    public String getAuthor() {
        return author;
    }

    public String[] getAuthorKw() {
        return author_kw;
    }

    public boolean isAutoManga() {
        return autoManga;
    }

    public boolean isBaka() {
        return baka;
    }

//    public ArrayList<ChapterInfo> getChapters() {
//        if (chaptersConverted.isEmpty()) chapters.forEach(this::addChapter);
//        return chaptersConverted;
//    }

//    private void addChapter(String[] tmp) {
//        ChapterInfo chapter = new ChapterInfo();
//        chapter.number = Integer.parseInt(tmp[0]);
//        chapter.date = tmp[1];
//        chapter.title = tmp[2];
//        chapter.id = tmp[3];
//        chaptersConverted.add(chapter);
//    }

    public int getChapters_len() {
        return chapters_len;
    }

    public long getCreated() {
        return created;
    }

    public String getDescription() {
        return description;
    }

    public int getHits() {
        return hits;
    }

    public String getImage() {
        return String.format(pre, image);
    }

    public String getImageURL() {
        return imageURL;
    }

    public long getLastChapterDate() {
        return last_chapter_date;
    }

    public int getReleased() {
        return released;
    }

    public String getStartsWith() {
        return startsWith;
    }

    public int getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public String[] getTitleKw() {
        return title_kw;
    }

    public int getType() {
        return type;
    }

    public boolean isUpdatedKeywords() {
        return updatedKeywords;
    }

    public String getUrl() {
        return url;
    }

    public Manga_chapters(String author, ArrayList<ChapterInfo> chapterInfos, String description) {
        this.author = author;
        this.chaptersConverted = chapterInfos;
        this.description = description;
    }
}