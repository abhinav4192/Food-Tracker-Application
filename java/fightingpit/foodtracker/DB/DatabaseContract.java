package fightingpit.foodtracker.DB;

import android.provider.BaseColumns;

/**
 * Created by AG on 02-Sep-15.
 */

public final class DatabaseContract {

    public static final  int    DATABASE_VERSION    = 1;
    public static final  String DATABASE_NAME       = "database.db";
    private static final String PRIMARY_KEY         = "PRIMARY KEY";
    private static final String FOREIGN_KEY         = "FOREIGN KEY";
    private static final String UNIQUE              = "UNIQUE";
    private static final String REFERENCES          = "REFERENCES ";
    private static final String COMMA_SEP           = ", ";


    public static final String[] SQL_CREATE_TABLE_ARRAY = {
            MealType.CREATE_TABLE,
            Meal.CREATE_TABLE,
            Symptoms.CREATE_TABLE,
            MealSymptoms.CREATE_TABLE,
            FoodItems.CREATE_TABLE,
            Ingredients.CREATE_TABLE,
            FoodIngredients.CREATE_TABLE,
            MealFood.CREATE_TABLE,
            FoodAttribute.CREATE_TABLE,
            MealAttribute.CREATE_TABLE
    };

    public static final String[] SQL_DROP_TABLE_ARRAY = {
            MealType.DROP_TABLE,
            Meal.DROP_TABLE,
            Symptoms.DROP_TABLE,
            MealSymptoms.DROP_TABLE,
            FoodItems.DROP_TABLE,
            Ingredients.DROP_TABLE,
            FoodIngredients.DROP_TABLE,
            MealFood.DROP_TABLE,
            FoodAttribute.DROP_TABLE,
            MealAttribute.DROP_TABLE
    };


    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private DatabaseContract() {}

    public static abstract class MealType implements BaseColumns {

        public static final String TABLE_NAME     = "MEAL_TYPE";
        public static final String MEAL_TYPE_ID   = "MEAL_TYPE_ID";
        public static final String MEAL_TYPE_NAME = "MEAL_TYPE_NAME";

        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                MEAL_TYPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
                MEAL_TYPE_NAME + " TEXT NOT NULL" + COMMA_SEP +
                UNIQUE + " (" + MEAL_TYPE_NAME + ")" +
                " )";

        public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static abstract class Meal implements BaseColumns {

        public static final String TABLE_NAME   = "MEAL";
        public static final String MEAL_ID      = "MEAL_ID";
        public static final String MEAL_DATE    = "MEAL_DATE";
        public static final String MEAL_TYPE_ID = "MEAL_TYPE_ID";

        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                MEAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
                MEAL_DATE + " TEXT NOT NULL" + COMMA_SEP +
                MEAL_TYPE_ID + " INT NOT NULL" + COMMA_SEP +
                UNIQUE + " (" + MEAL_DATE + COMMA_SEP + MEAL_TYPE_ID + ")" + COMMA_SEP +
                FOREIGN_KEY + " (" + MEAL_TYPE_ID + ") " +REFERENCES + MealType.TABLE_NAME + " (" + MealType.MEAL_TYPE_ID +") ON DELETE CASCADE " +
                " )";

        public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static abstract class Symptoms implements BaseColumns {

        public static final String TABLE_NAME   = "SYMPTOMS";
        public static final String SYMPTOM_ID   = "SYMPTOM_ID";
        public static final String SYMPTOM_NAME = "SYMPTOM_NAME";
        //public static final String SYMPTOM_TYPE = "SYMPTOM_TYPE";

        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                SYMPTOM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
                SYMPTOM_NAME + " TEXT NOT NULL" + COMMA_SEP +
                //SYMPTOM_TYPE + " TEXT NOT NULL" + COMMA_SEP +
                UNIQUE + " (" + SYMPTOM_NAME + ")" +
                " )";

        public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }


    public static abstract class MealSymptoms implements BaseColumns {

        public static final String TABLE_NAME    = "MEAL_SYMPTOMS";
        public static final String MEAL_ID       = "MEAL_ID";
        public static final String SYMPTOM_ID    = "SYMPTOM_ID";
        public static final String SYMPTOM_VALUE = "SYMPTOM_VALUE";

        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                MEAL_ID + " INTEGER NOT NULL" + COMMA_SEP +
                SYMPTOM_ID + " INTEGER NOT NULL" + COMMA_SEP +
                SYMPTOM_VALUE + " TEXT NOT NULL" + COMMA_SEP +
                PRIMARY_KEY + " (" + MEAL_ID + COMMA_SEP + SYMPTOM_ID + ")" + COMMA_SEP +
                FOREIGN_KEY + " (" + MEAL_ID + ") " +REFERENCES + Meal.TABLE_NAME + " (" + Meal.MEAL_ID +") ON DELETE CASCADE " + COMMA_SEP +
                FOREIGN_KEY + " (" + SYMPTOM_ID + ") " +REFERENCES + Symptoms.TABLE_NAME + " (" + Symptoms.SYMPTOM_ID +") ON DELETE CASCADE " +
                " )";

        public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static abstract class FoodItems implements BaseColumns {

        public static final String TABLE_NAME     = "FOOD_ITEMS";
        public static final String FOOD_ITEM_NAME = "FOOD_ITEM_NAME";
        public static final String FOOD_ITEM_ID   = "FOOD_ITEM_ID";

        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                FOOD_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
                FOOD_ITEM_NAME + " TEXT NOT NULL" + COMMA_SEP +
                UNIQUE + " (" + FOOD_ITEM_NAME + ")" +
                " )";

        public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static abstract class Ingredients implements BaseColumns {

        public static final String TABLE_NAME      = "INGREDIENTS";
        public static final String INGREDIENT_NAME = "INGREDIENT_NAME";
        public static final String INGREDIENT_ID   = "INGREDIENT_ID";


        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                INGREDIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
                INGREDIENT_NAME + " TEXT NOT NULL" + COMMA_SEP +
                UNIQUE + " (" + INGREDIENT_NAME + ")" +
                " )";

        public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static abstract class FoodIngredients implements BaseColumns {

        public static final String TABLE_NAME    = "FOOD_INGREDIENTS";
        public static final String FOOD_ITEM_ID  = "FOOD_ITEM_ID";
        public static final String INGREDIENT_ID = "INGREDIENT_ID";

        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                FOOD_ITEM_ID + " INTEGER NOT NULL" + COMMA_SEP +
                INGREDIENT_ID + " INTEGER NOT NULL" + COMMA_SEP +
                PRIMARY_KEY + " (" + FOOD_ITEM_ID + COMMA_SEP + INGREDIENT_ID + ")" + COMMA_SEP +
                FOREIGN_KEY + " (" + FOOD_ITEM_ID + ") " +REFERENCES + FoodItems.TABLE_NAME + " (" + FoodItems.FOOD_ITEM_ID +") ON DELETE CASCADE " + COMMA_SEP +
                FOREIGN_KEY + " (" + INGREDIENT_ID + ") " +REFERENCES + Ingredients.TABLE_NAME + " (" + Ingredients.INGREDIENT_ID +") ON DELETE CASCADE " +
                " )";

        public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static abstract class MealFood implements BaseColumns {

        public static final String TABLE_NAME   = "MEAL_FOOD";
        public static final String MEAL_FOOD_ID = "MEAL_FOOD_ID";
        public static final String MEAL_ID      = "MEAL_ID";
        public static final String FOOD_ITEM_ID = "FOOD_ITEM_ID";


        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                MEAL_FOOD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
                MEAL_ID + " INTEGER NOT NULL" + COMMA_SEP +
                FOOD_ITEM_ID + " INTEGER NOT NULL" + COMMA_SEP +
                UNIQUE + " (" + MEAL_ID + COMMA_SEP + FOOD_ITEM_ID + ")" + COMMA_SEP +
                FOREIGN_KEY + " (" + MEAL_ID + ") " +REFERENCES + Meal.TABLE_NAME + " (" + Meal.MEAL_ID +") ON DELETE CASCADE " + COMMA_SEP +
                FOREIGN_KEY + " (" + FOOD_ITEM_ID + ") " +REFERENCES + FoodItems.TABLE_NAME + " (" + FoodItems.FOOD_ITEM_ID +") ON DELETE CASCADE " +
                " )";

        public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static abstract class FoodAttribute implements BaseColumns {

        public static final String TABLE_NAME      = "FOOD_ATTRIBUTE";
        public static final String ATTRIBUTE_ID   = "ATTRIBUTE_ID";
        public static final String ATTRIBUTE_NAME = "ATTRIBUTE_NAME";
        public static final String ATTRIBUTE_TYPE = "ATTRIBUTE_TYPE";
        public static final String ATTRIBUTE_UNIT       = "ATTRIBUTE_UNIT";


        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                ATTRIBUTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
                ATTRIBUTE_NAME + " TEXT NOT NULL" + COMMA_SEP +
                ATTRIBUTE_TYPE + " TEXT NOT NULL" + COMMA_SEP +
                ATTRIBUTE_UNIT + " TEXT" + COMMA_SEP +
                UNIQUE + " (" + ATTRIBUTE_NAME + ")" +
                " )";

        public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static abstract class MealAttribute implements BaseColumns {

        public static final String TABLE_NAME           = "MEAL_ATTRIBUTE";
        public static final String MEAL_FOOD_ID         = "MEAL_FOOD_ID";
        public static final String ATTRIBUTE_ID         = "ATTRIBUTE_ID";
        public static final String ATTRIBUTE_VALUE      = "ATTRIBUTE_VALUE";
        public static final String QUANTITY_UNIT       = "ATTRIBUTE_UNIT";

        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                MEAL_FOOD_ID + " INTEGER NOT NULL " + COMMA_SEP +
                ATTRIBUTE_ID + " INTEGER NOT NULL" + COMMA_SEP +
                ATTRIBUTE_VALUE + " TEXT NOT NULL" + COMMA_SEP +
                QUANTITY_UNIT + " TEXT" + COMMA_SEP +
                PRIMARY_KEY + " (" + MEAL_FOOD_ID + COMMA_SEP + ATTRIBUTE_ID + ")" + COMMA_SEP +
                FOREIGN_KEY + " (" + MEAL_FOOD_ID + ") " +REFERENCES + MealFood.TABLE_NAME + " (" + MealFood.MEAL_FOOD_ID +") ON DELETE CASCADE " + COMMA_SEP +
                FOREIGN_KEY + " (" + ATTRIBUTE_ID + ") " +REFERENCES + FoodAttribute.TABLE_NAME + " (" + FoodAttribute.ATTRIBUTE_ID +") ON DELETE CASCADE " +
                " )";

        public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

}