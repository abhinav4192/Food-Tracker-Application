package fightingpit.foodtracker.DB;

import android.content.ContentValues;
import android.content.Context;
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
}
