package fightingpit.foodtracker.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AG on 30-Sep-15.
 */

public class FoodIngredientsDbMethods {

    private DatabaseHelper dbHelper;
    // Context to be used throughout the class.
    private Context context;
    // Constructor.
    public FoodIngredientsDbMethods(Context context) {
        this.context=context;
        dbHelper = new DatabaseHelper(context);
    }

    public boolean addIngredientsToFood(int iFoodItemId, int iIngredientId){
        boolean aReturnValue =true;

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //db.setForeignKeyConstraintsEnabled(true);
        ContentValues aVal = new ContentValues();
        aVal.put(DatabaseContract.FoodIngredients.FOOD_ITEM_ID,iFoodItemId);
        aVal.put(DatabaseContract.FoodIngredients.INGREDIENT_ID, iIngredientId);
        try{
            db.insertOrThrow(DatabaseContract.FoodIngredients.TABLE_NAME, null, aVal);
        }catch(SQLiteConstraintException e){
            aReturnValue = false;
        }
        return aReturnValue;
    }

    public List<String> getAllIngredientsInFood(int iFoodItemId){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {DatabaseContract.FoodIngredients.INGREDIENT_ID};
        String selection = DatabaseContract.FoodIngredients.FOOD_ITEM_ID + "=?";
        String[] selectionArgs = {String.valueOf(iFoodItemId)};
        Cursor c = db.query(DatabaseContract.FoodIngredients.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        List<String> aReturnList = new ArrayList<String>();
        c.moveToFirst();
        while(!c.isAfterLast()){
            aReturnList.add(c.getString(c.getColumnIndexOrThrow(DatabaseContract.FoodIngredients.INGREDIENT_ID)));
            c.moveToNext();
        }
        return aReturnList;
    }
}