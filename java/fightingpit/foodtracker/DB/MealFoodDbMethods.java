package fightingpit.foodtracker.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by AG on 09-Sep-15.
 */
public class MealFoodDbMethods {

    private DatabaseHelper dbHelper;

    // Context to be used throughout the class.
    private Context context;

    // Constructor.
    public MealFoodDbMethods(Context context) {
        this.context=context;
        dbHelper = new DatabaseHelper(context);
    }


    public boolean addFoodToMeals(int iMealId, int iFoodItemId){

        boolean aReturnValue =true;

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //db.setForeignKeyConstraintsEnabled(true);
        ContentValues aVal = new ContentValues();
        aVal.put(DatabaseContract.MealFood.MEAL_ID,iMealId);
        aVal.put(DatabaseContract.MealFood.FOOD_ITEM_ID, iFoodItemId);
        try{
            db.insertOrThrow(DatabaseContract.MealFood.TABLE_NAME, null, aVal);
        }catch(SQLiteConstraintException e){
            aReturnValue = false;
        }
        return aReturnValue;
    }

    public int getSymptomId(String iSymptomName){

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {DatabaseContract.Symptoms.SYMPTOM_ID};
        String selection = DatabaseContract.Symptoms.SYMPTOM_NAME + "=?";
        String[] selectionArgs = {iSymptomName};

        Cursor c = db.query(
                DatabaseContract.Symptoms.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        int aReturnValue = -1;
        if(c.getCount()>0){
            // TODO: 06-Sep-15 Change/Check the if check.
            c.moveToFirst();
            aReturnValue = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.FoodItems.FOOD_ITEM_ID));
        }
        return aReturnValue;
    }
}
