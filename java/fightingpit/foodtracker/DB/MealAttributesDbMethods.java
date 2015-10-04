package fightingpit.foodtracker.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by AG on 04-Oct-15.
 */
public class MealAttributesDbMethods {
    private DatabaseHelper dbHelper;

    // Constructor.
    public MealAttributesDbMethods(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public boolean addMealAttributes(int iMealFoodId, int iAttributeId, String iAttributeValue, String iQuanatityUnit){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues aVal = new ContentValues();
        aVal.put(DatabaseContract.MealAttribute.MEAL_FOOD_ID,iMealFoodId);
        aVal.put(DatabaseContract.MealAttribute.ATTRIBUTE_ID,iAttributeId);
        aVal.put(DatabaseContract.MealAttribute.ATTRIBUTE_VALUE,iAttributeValue);
        aVal.put(DatabaseContract.MealAttribute.QUANTITY_UNIT,iQuanatityUnit);

        boolean aReturn = true;
        try{
            db.insertOrThrow(DatabaseContract.MealAttribute.TABLE_NAME, null, aVal);
        }catch (SQLiteConstraintException e){
            aReturn = false;
        }
        db.close();
        return aReturn;

    }
}
