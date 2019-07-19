package sg.np.edu.twistmanga;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {
    private static final String TAG = "MyDBHandler";
    public static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "favouriteDB.db";
    public static final String ACCOUNTS = "Favourites";
    public static final String COLUMN_TITLE = "Title";
    public static final String COLUMN_URL = "Url";

    public DBHandler(Context context,
                     String name,
                     SQLiteDatabase.CursorFactory factory,
                     int version)
    {
        super(context, DATABASE_NAME,factory,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ACCOUNTS_TABLE = "CREATE TABLE "+ACCOUNTS+
                " ("+ COLUMN_TITLE+" TEXT,"+COLUMN_URL+" TEXT"+")";
        db.execSQL(CREATE_ACCOUNTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ACCOUNTS);
        onCreate(db);
    }

    public void addToFavourites(Manga manga){
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, manga.getTitle());
        values.put(COLUMN_URL,manga.getImage());
        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(ACCOUNTS, null, values);
        db.close();
    }

    public void deleteFromFavourites(Manga manga){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ACCOUNTS, COLUMN_TITLE + "=\"" + manga.getTitle()+"\"",null);
        db.close();
    }

    public boolean isFavourite(String title){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " +ACCOUNTS+ " WHERE "
                + COLUMN_TITLE +"= ?";
        Cursor cursor = db.rawQuery(query,new String[]{title});

        if (cursor.getCount() <= 0)
        {
            cursor.close();
            return false;
        }
        cursor.close();
        db.close();
        return true;
    }
}
