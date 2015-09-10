package fightingpit.foodtracker.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

/**
 * Created by AG on 05-Sep-15.
 */
public class MealDbMethods {

    private DatabaseHelper dbHelper;

    // Context to be used throughout the class.
    private Context context;

    // Constructor.
    public MealDbMethods(Context context) {
        this.context=context;
        dbHelper = new DatabaseHelper(context);
    }

    public boolean addNewMeal(Integer iMealTypeId, String iDate){

        boolean aReturnValue =true;

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //db.setForeignKeyConstraintsEnabled(true);
        ContentValues aVal = new ContentValues();
        aVal.put(DatabaseContract.Meal.MEAL_TYPE_ID,iMealTypeId);
        aVal.put(DatabaseContract.Meal.MEAL_DATE, iDate);
        try{
            db.insertOrThrow(DatabaseContract.Meal.TABLE_NAME, null, aVal);
        }catch(SQLiteConstraintException e){
            aReturnValue = false;
        }
        return aReturnValue;
    }

    public boolean isMealAlreadyAdded(Integer iMealTypeId, String iDate){
        boolean aReturnValue = false;

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selection = DatabaseContract.Meal.MEAL_TYPE_ID + "=? AND " + DatabaseContract.Meal.MEAL_DATE + "=?";
        String[] selectionArgs = {iMealTypeId.toString(),iDate};

        Cursor c = db.query(DatabaseContract.Meal.TABLE_NAME, null, selection, selectionArgs, null, null, null);
        if(c.getCount() > 0){
            aReturnValue = true;
        }
        return aReturnValue;
    }

    public int getMealId(Integer iMealTypeId, String iDate){

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selection = DatabaseContract.Meal.MEAL_TYPE_ID + "=? AND " + DatabaseContract.Meal.MEAL_DATE + "=?";
        String[] selectionArgs = {iMealTypeId.toString(),iDate};

        Cursor c = db.query(DatabaseContract.Meal.TABLE_NAME, null, selection, selectionArgs, null, null, null);
        c.moveToFirst();
        return c.getInt(c.getColumnIndexOrThrow(DatabaseContract.Meal.MEAL_ID));

    }
}
