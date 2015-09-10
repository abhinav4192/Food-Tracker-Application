package fightingpit.foodtracker.DB;

import android.content.ContentValues;
import android.content.Context;
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
}
