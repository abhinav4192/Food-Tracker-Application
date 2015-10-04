package fightingpit.foodtracker.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fightingpit.foodtracker.CustomLists.GenericContainer;

/**
 * Created by AG on 04-Oct-15.
 */
public class FoodAttributeDbMethods {

    private DatabaseHelper dbHelper;

    // Constructor.
    public FoodAttributeDbMethods(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public boolean addFoodAttribute(String iAttributeName, String iAttributeType,String iAttributeUnit){

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues aVal = new ContentValues();
        aVal.put(DatabaseContract.FoodAttribute.ATTRIBUTE_NAME,iAttributeName);
        aVal.put(DatabaseContract.FoodAttribute.ATTRIBUTE_TYPE,iAttributeType);
        aVal.put(DatabaseContract.FoodAttribute.ATTRIBUTE_UNIT,iAttributeUnit);

        boolean aReturn = true;
        try{
            db.insertOrThrow(DatabaseContract.FoodAttribute.TABLE_NAME, null, aVal);
        }catch (SQLiteConstraintException e){
            aReturn = false;
        }
        db.close();
        return aReturn;
    }

    public int getFoodAttributeId(String iAttributeName){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {DatabaseContract.FoodAttribute.ATTRIBUTE_ID};
        String selection = DatabaseContract.FoodAttribute.ATTRIBUTE_NAME + "=?";
        String[] selectionArgs = {iAttributeName};

        Cursor c = db.query(DatabaseContract.FoodAttribute.TABLE_NAME, projection, selection,selectionArgs, null, null, null);
        int aReturnValue = -1;

        if(c.getCount()>0){
            // TODO: 06-Sep-15 Change/Check the if check.
            c.moveToFirst();
            aReturnValue = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.FoodAttribute.ATTRIBUTE_ID));
        }
        db.close();
        return aReturnValue;
    }

    public List<GenericContainer> getAllFoodAttributeTypes(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {DatabaseContract.FoodAttribute.ATTRIBUTE_NAME,
                DatabaseContract.FoodAttribute.ATTRIBUTE_TYPE,
                DatabaseContract.FoodAttribute.ATTRIBUTE_UNIT};
//        String sortOrder =
//                DatabaseContract.FoodAttribute.ATTRIBUTE_NAME + " DESC";

        Cursor c = db.query(DatabaseContract.FoodAttribute.TABLE_NAME, projection, null, null, null, null, null);

        List<GenericContainer> aReturnList = new ArrayList<>();
        c.moveToFirst();
        while(!c.isAfterLast()){
            aReturnList.add(new GenericContainer(c.getString(c.getColumnIndexOrThrow(DatabaseContract.FoodAttribute.ATTRIBUTE_NAME)),
                    c.getString(c.getColumnIndexOrThrow(DatabaseContract.FoodAttribute.ATTRIBUTE_TYPE)),
                            c.getString(c.getColumnIndexOrThrow(DatabaseContract.FoodAttribute.ATTRIBUTE_UNIT))));
            c.moveToNext();
        }
        return aReturnList;

    }
}
