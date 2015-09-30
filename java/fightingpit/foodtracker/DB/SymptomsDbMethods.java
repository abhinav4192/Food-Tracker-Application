package fightingpit.foodtracker.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

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

    public List<String> getAllSymptoms(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {DatabaseContract.Symptoms.SYMPTOM_NAME};
        Cursor c = db.query(DatabaseContract.Symptoms.TABLE_NAME, projection, null, null, null, null, null);

        List<String> aReturnList = new ArrayList<String>();
        c.moveToFirst();
        while(!c.isAfterLast()){
            aReturnList.add(c.getString(c.getColumnIndexOrThrow(DatabaseContract.Symptoms.SYMPTOM_NAME)));
            c.moveToNext();
        }
        return aReturnList;
    }

    public int getSymptomId(String iSymptomName){

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {DatabaseContract.Symptoms.SYMPTOM_ID};
        String selection = DatabaseContract.Symptoms.SYMPTOM_NAME + "=?";
        String[] selectionArgs = {iSymptomName};

        Cursor c = db.query(DatabaseContract.Symptoms.TABLE_NAME, projection, selection,selectionArgs, null, null, null);
        int aReturnValue = -1;

        if(c.getCount()>0){
            // TODO: 06-Sep-15 Change/Check the if check.
            c.moveToFirst();
            aReturnValue = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.Symptoms.SYMPTOM_ID));
        }
        return aReturnValue;
    }
}
