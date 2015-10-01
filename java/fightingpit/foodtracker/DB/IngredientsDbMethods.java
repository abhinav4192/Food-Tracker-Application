package fightingpit.foodtracker.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by AG on 06-Sep-15.
 */
public class IngredientsDbMethods {

    private DatabaseHelper dbHelper;
    // Context to be used throughout the class.
    private Context context;
    // Constructor.
    public IngredientsDbMethods(Context context) {
        this.context=context;
        dbHelper = new DatabaseHelper(context);
    }

    public boolean addIngredientName(String iIngredientName){
        boolean aReturnValue =true;

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //db.setForeignKeyConstraintsEnabled(true);
        ContentValues aVal = new ContentValues();
        aVal.put(DatabaseContract.Ingredients.INGREDIENT_NAME,iIngredientName);
        try{
            db.insertOrThrow(DatabaseContract.Ingredients.TABLE_NAME, null, aVal);
        }catch(SQLiteConstraintException e){
            aReturnValue = false;
        }
        return aReturnValue;
    }

    public int getIngredientId(String iIngredientName){

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {DatabaseContract.Ingredients.INGREDIENT_ID};
        String selection = DatabaseContract.Ingredients.INGREDIENT_NAME + "=?";
        String[] selectionArgs = {iIngredientName};

        Cursor c = db.query(DatabaseContract.Ingredients.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
        int aReturnValue = -1;

        if(c.getCount()>0){
            // TODO: 06-Sep-15 Change/Check the if check.
            c.moveToFirst();
            aReturnValue = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.Ingredients.INGREDIENT_ID));
        }
        return aReturnValue;
    }

    public String getIngredientName(String iIngredientId){

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {DatabaseContract.Ingredients.INGREDIENT_NAME};
        String selection = DatabaseContract.Ingredients.INGREDIENT_ID + "=?";
        String[] selectionArgs = {iIngredientId};

        Cursor c = db.query(DatabaseContract.Ingredients.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        String aReturnValue = new String();
        if(c.getCount()>0){
            // TODO: 06-Sep-15 Change/Check the if check.
            c.moveToFirst();
            aReturnValue = c.getString(c.getColumnIndexOrThrow(DatabaseContract.Ingredients.INGREDIENT_NAME));

        }
        return aReturnValue;
    }
}
