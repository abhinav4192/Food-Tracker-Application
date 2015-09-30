package fightingpit.foodtracker.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by AG on 29-Sep-15.
 */

public class MealSymptomsDbMethods {

    private DatabaseHelper dbHelper;
    // Context to be used throughout the class.
    private Context context;
    // Constructor.
    public MealSymptomsDbMethods(Context context) {
        this.context=context;
        dbHelper = new DatabaseHelper(context);
    }

    public boolean addSymptomValueToMeal(int iMealId, int iSymptomId, int iSymptomValue){
        boolean aReturnValue =true;

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //db.setForeignKeyConstraintsEnabled(true);
        ContentValues aVal = new ContentValues();
        aVal.put(DatabaseContract.MealSymptoms.MEAL_ID,iMealId);
        aVal.put(DatabaseContract.MealSymptoms.SYMPTOM_ID,iSymptomId);
        aVal.put(DatabaseContract.MealSymptoms.SYMPTOM_VALUE,iSymptomValue);
        try{
            db.insertOrThrow(DatabaseContract.MealSymptoms.TABLE_NAME, null, aVal);
        }catch(SQLiteConstraintException e){
            aReturnValue = false;
        }
        return aReturnValue;
    }
}