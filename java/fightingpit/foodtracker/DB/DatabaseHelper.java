package fightingpit.foodtracker.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by AG on 02-Sep-15.
 */


public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context) {
        super(context, DatabaseContract.DATABASE_NAME, null, DatabaseContract.DATABASE_VERSION);
    }

    // Method is called during creation of the database
    @Override
    public void onCreate(SQLiteDatabase db) {
        for(int i=0; i < DatabaseContract.SQL_CREATE_TABLE_ARRAY.length; i++) {
            db.execSQL(DatabaseContract.SQL_CREATE_TABLE_ARRAY[i]);
        }

    }

    // Method is called during an upgrade of the database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for(int i=0; i < DatabaseContract.SQL_DROP_TABLE_ARRAY.length; i++) {
            db.execSQL(DatabaseContract.SQL_DROP_TABLE_ARRAY[i]);
        }
        onCreate(db);
    }
}