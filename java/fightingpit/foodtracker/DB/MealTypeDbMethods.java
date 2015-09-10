package fightingpit.foodtracker.DB;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by AG on 03-Sep-15.
 *
 * Exposes methods to read and write data from MEAL_TYPE Database Table.
 */
public class MealTypeDbMethods {

    private DatabaseHelper dbHelper;

    // Context to be used throughout the class.
    private Context context;

    // Constructor.
    public MealTypeDbMethods(Context context) {
        this.context=context;
        dbHelper = new DatabaseHelper(context);
    }


    /**
     * Add a new meal to MEAL_TYPE table.
     * @param iMealType Name of the Meal Type
     * @return true if meal was added, false if not.
     */
    public boolean addMealType(String iMealType){
        boolean aReturn = true;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //db.setForeignKeyConstraintsEnabled(true);

        ContentValues aVal = new ContentValues();
        aVal.put(DatabaseContract.MealType.MEAL_TYPE_NAME,iMealType);

        try {
            db.insertOrThrow(DatabaseContract.MealType.TABLE_NAME, null, aVal);
        }
        catch(SQLiteConstraintException e){
            aReturn = false;
        }
        return aReturn;
    }

    public void updateMealTypeName(String iMealTypeOld, String iMealTypeNew){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        //db.setForeignKeyConstraintsEnabled(true);

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.MealType.MEAL_TYPE_NAME, "iMealTypeNew");

        // Which row to update, based on the ID
        String selection = DatabaseContract.MealType.MEAL_TYPE_NAME + "=?";
        String[] selectionArgs = { iMealTypeOld };

        db.update(
                DatabaseContract.MealType.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    public void deleteMealType(String iMealType){
        // Define 'where' part of query.
        String selection = DatabaseContract.MealType.MEAL_TYPE_NAME + "=?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { iMealType};
        // Issue SQL statement.
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //db.setForeignKeyConstraintsEnabled(true);
        db.delete(DatabaseContract.MealType.TABLE_NAME, selection, selectionArgs);
    }

    public List<String> getAllMealType()
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {DatabaseContract.MealType.MEAL_TYPE_NAME};
        Cursor c = db.query(
                DatabaseContract.MealType.TABLE_NAME, projection, null, null, null, null, null);

        c.moveToFirst();
        List<String> aAllMealTypes = new ArrayList<String>();
        while(!c.isAfterLast()){
            aAllMealTypes.add(c.getString(c.getColumnIndexOrThrow(DatabaseContract.MealType.MEAL_TYPE_NAME)));
            c.moveToNext();
        }
        return aAllMealTypes;
    }

    /**
     * To get total number of Meal Types present in MEAL_TYPE table
     * @return number of Meal Types present in MEAL_TYPE table
     */
    public Integer getNumberOfMealType()
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor aCursor = db.query(
                DatabaseContract.MealType.TABLE_NAME, null, null, null, null, null, null);

        return aCursor.getCount();


    }

    public Integer getMealTypeIdFromMealTypeName(String iMealTypeName)
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {DatabaseContract.MealType.MEAL_TYPE_ID};
        String selection = DatabaseContract.MealType.MEAL_TYPE_NAME + "=?";
        String[] selectionArgs = {iMealTypeName};

        Cursor c = db.query(
                DatabaseContract.MealType.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );
        c.moveToFirst();
        return c.getInt(c.getColumnIndexOrThrow(DatabaseContract.MealType.MEAL_TYPE_ID));
    }
}



