package fightingpit.foodtracker.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AG on 05-Sep-15.
 */
public class FoodItemsDbMethods {

    private DatabaseHelper dbHelper;
    private Context context; // Context to be used throughout the class.

    // Constructor.
    public FoodItemsDbMethods(Context context) {
        this.context=context;
        dbHelper = new DatabaseHelper(context);
    }

    public boolean addFoodItem(String iFoodItemName){

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues aVal = new ContentValues();
        aVal.put(DatabaseContract.FoodItems.FOOD_ITEM_NAME,iFoodItemName);

        boolean aReturn = true;
        try{
            db.insertOrThrow(DatabaseContract.FoodItems.TABLE_NAME, null, aVal);
        }catch (SQLiteConstraintException e){
            aReturn = false;
        }
        db.close();
        return aReturn;
    }

    /**
     * Get FoodItemId by FoodItemName
     * @param iFoodItemName Food Item Name for which Food Item ID will be returned.
     * @return FoodItemId if FoodItemName exists in table. Otherwise returns -1
     */
    public int getFoodItemId(String iFoodItemName){

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {DatabaseContract.FoodItems.FOOD_ITEM_ID};
        String selection = DatabaseContract.FoodItems.FOOD_ITEM_NAME + "=?";
        String[] selectionArgs = {iFoodItemName};

        Cursor c = db.query(
                DatabaseContract.FoodItems.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        int aReturnValue = -1;
        if(c.getCount()>0){
            // TODO: 06-Sep-15 Change/Check the if check.
            c.moveToFirst();
            aReturnValue = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.FoodItems.FOOD_ITEM_ID));
        }
        return aReturnValue;
    }

    public List<String> getAllFoodItems(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {DatabaseContract.FoodItems.FOOD_ITEM_NAME};
        Cursor c = db.query(DatabaseContract.FoodItems.TABLE_NAME, projection, null, null, null, null, null);

        List<String> aReturnList = new ArrayList<String>();
        c.moveToFirst();
        while(!c.isAfterLast()){
            aReturnList.add(c.getString(c.getColumnIndexOrThrow(DatabaseContract.FoodItems.FOOD_ITEM_NAME)));
            c.moveToNext();
        }
        return aReturnList;
    }
}
