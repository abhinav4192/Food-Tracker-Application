package fightingpit.foodtracker.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by AG on 06-Sep-15.
 */
public class SymptomsDbMethods {

    private DatabaseHelper dbHelper;
    // Context to be used throughout the class.
    private Context context;
    // Constructor.
    public SymptomsDbMethods(Context context) {
        this.context=context;
        dbHelper = new DatabaseHelper(context);
    }

    public boolean addSymptomName(String iSymptomName){
        boolean aReturnValue =true;

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //db.setForeignKeyConstraintsEnabled(true);
        ContentValues aVal = new ContentValues();
        aVal.put(DatabaseContract.Symptoms.SYMPTOM_NAME,iSymptomName);
        try{
            db.insertOrThrow(DatabaseContract.Symptoms.TABLE_NAME, null, aVal);
        }catch(SQLiteConstraintException e){
            aReturnValue = false;
        }
        return aReturnValue;
    }
}
