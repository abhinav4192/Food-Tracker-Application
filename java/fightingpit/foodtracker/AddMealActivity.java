package fightingpit.foodtracker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import fightingpit.foodtracker.CustomLists.ListAdapterFoodInAddMeal;
import fightingpit.foodtracker.CustomLists.ListAdapterSymptomsInAddMeal;
import fightingpit.foodtracker.CustomLists.ListFoodInAddMeal;
import fightingpit.foodtracker.CustomLists.ListSymptomsInAddMeal;
import fightingpit.foodtracker.DB.FoodItemsDbMethods;
import fightingpit.foodtracker.DB.MealDbMethods;
import fightingpit.foodtracker.DB.MealFoodDbMethods;
import fightingpit.foodtracker.DB.MealSymptomsDbMethods;
import fightingpit.foodtracker.DB.MealTypeDbMethods;
import fightingpit.foodtracker.DB.SymptomsDbMethods;


public class AddMealActivity extends ActionBarActivity{

    private Spinner mMealSelectorSpinner;
    private TextView mSelectedDateDisplay;
    private Calendar calendar;
    private int year, month, day;
    private String mealDate;
    private Button mAddMealToTableButton;
    private Button mAddFoodToMealButton;

    private List<ListFoodInAddMeal> mFoodList = new ArrayList<>();
    private ListView mFoodListToDisplay;
    ListAdapterFoodInAddMeal mFoodListAdapter;

    private List<ListSymptomsInAddMeal> mSymptomsList = new ArrayList<>();
    private ListView mSymptomsListToDisplay;
    ListAdapterSymptomsInAddMeal mSymptomsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);

        Log.d("ABG", "onCreate");

        populateMealTypeSpinner();
        initializeDatePicker();

        // Get Add Meal and setOnclickListener.
        mAddMealToTableButton = (Button) findViewById(R.id.bt_addMealToTable);
        mAddMealToTableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewMeal();
            }
        });

        // Set Title of Activity
        getSupportActionBar().setTitle("Add Meal");


        //
        mAddFoodToMealButton = (Button) findViewById(R.id.bt_addFoodToMeal);
        mAddFoodToMealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), AddFoodInAddMealActivty.class);
                startActivityForResult(i, 101);
            }
        });

        mFoodListToDisplay =(ListView) findViewById(R.id.listAddFoodInMeal);
        mFoodListAdapter = new ListAdapterFoodInAddMeal(this,mFoodList);
        mFoodListToDisplay.setAdapter(mFoodListAdapter);

        SymptomsDbMethods aSymptomsDbHandler = new SymptomsDbMethods(this);
        List<String> mSymptomListFromDB = aSymptomsDbHandler.getAllSymptoms();

        for(String aListItem : mSymptomListFromDB){
            mSymptomsList.add(new ListSymptomsInAddMeal(aListItem));
        }
        mSymptomsListToDisplay =(ListView) findViewById(R.id.listAllSymptoms);
        mSymptomsListAdapter = new ListAdapterSymptomsInAddMeal(this,mSymptomsList);
        mSymptomsListToDisplay.setAdapter(mSymptomsListAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 101 && resultCode == RESULT_OK && data != null) {

            boolean aAddToFoodList = true;
            Log.d("ABGK",data.getStringExtra("added_food"));
            for(ListFoodInAddMeal aAlreadyAddedFood:mFoodList){
                Log.d("ABGK",aAlreadyAddedFood.getText());
                if(aAlreadyAddedFood.getText().equals(data.getStringExtra("added_food"))){
                    Toast.makeText(this,"Food item already added.",Toast.LENGTH_LONG).show();
                    aAddToFoodList = false;
                    break;
                }
            }
            if(aAddToFoodList){
                mFoodList.add(new ListFoodInAddMeal(data.getStringExtra("added_food")));
                mFoodListAdapter.notifyDataSetChanged();
            }
        }
        if(mFoodList.size()> 2){
            mAddFoodToMealButton.setVisibility(View.GONE);
        }
        //mSymptomsListAdapter.notifyDataSetChanged();

    }

    public void updateSymptomValue(int Index, int value){

        // Keep track of values of symptoms. Prevents values of symptoms from
        // being discarded when the view is redrawn.
        mSymptomsList.get(Index).setValueFromSeekbar(value);
        mSymptomsList.get(Index).setDisplayValueInTextbar(String.valueOf(value));
    }

    public void removeFoodFromMeal(int index) {
        mFoodList.remove(index);
        mFoodListAdapter.notifyDataSetChanged();
        if(mFoodList.size()<3){
            mAddFoodToMealButton.setVisibility(View.VISIBLE);
        }
    }



    private void populateMealTypeSpinner() {
        mMealSelectorSpinner = (Spinner) findViewById(R.id.spinner1);
        MealTypeDbMethods aMealDbHandler = new MealTypeDbMethods(this);
        List<String> aMealList = aMealDbHandler.getAllMealType();

        ArrayAdapter<String> datmFoodListAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, aMealList);
        datmFoodListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mMealSelectorSpinner.setAdapter(datmFoodListAdapter);
    }

        @Override
        protected void onStart() {
            super.onStart();
            Log.d("ABG", "onStart");
        }

        @Override
        protected void onResume() {
            super.onResume();
            Log.d("ABG", "onResume");
        }

        @Override
        protected void onPause() {
            super.onPause();
            Log.d("ABG", "onPause");
        }

        @Override
        protected void onStop() {
            super.onStop();
            Log.d("ABG", "onStop");
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            Log.d("ABG", "onDestroy");
        }

    private void initializeDatePicker(){
        mSelectedDateDisplay = (TextView) findViewById(R.id.bt_pickDate);
        mSelectedDateDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });

        // Set Today Date on Date Picker
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        // Toast.makeText(this, calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()),Toast.LENGTH_LONG).show();
        ShowDate(year, month, day);
    }

    public void ShowDate(int year, int month, int day){
        mSelectedDateDisplay.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
        mealDate = (new StringBuilder().append(year).append(month).append(day)).toString();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // No menu needed.
        return true;
    }

    private void addNewMeal(){

        // All DB Handlers
        MealDbMethods aMealDbHandler = new MealDbMethods(this);
        MealTypeDbMethods aMealTypeDbHandler = new MealTypeDbMethods(this);
        FoodItemsDbMethods aFoodItemDbHandler = new FoodItemsDbMethods(this);
        MealFoodDbMethods aMealFoodDbHandler = new MealFoodDbMethods(this);
        SymptomsDbMethods aSymptomDbHandler = new SymptomsDbMethods(this);
        MealSymptomsDbMethods aMealSymptomsDbHandler = new MealSymptomsDbMethods(this);

        // Variable to control to insert into Db.
        boolean aCommitToDb = true;

        // Get MealTypeId from MealTypeName
        Integer aMealTypeId = aMealTypeDbHandler.getMealTypeIdFromMealTypeName(mMealSelectorSpinner.getSelectedItem().toString());

        if(aMealDbHandler.isMealAlreadyAdded(aMealTypeId,mealDate)){
            Toast.makeText(this,"Meal Already Added",Toast.LENGTH_SHORT).show();
            aCommitToDb = false;
        }

        if(aCommitToDb){
            // Add the Meal to the Table.
            aMealDbHandler.addNewMeal(aMealTypeId, mealDate);
            // Get MealId of Meal just added.
            int aMealId = aMealDbHandler.getMealId(aMealTypeId, mealDate);

            // Adding foodItems to DB.
            for(ListFoodInAddMeal aFoodItemName: mFoodList){
                // Get FoodItemId of Food
                int aFoodItemId = aFoodItemDbHandler.getFoodItemId(aFoodItemName.getText());
                if(aFoodItemId == -1){
                    // Food Item Not present in DB. Add Food Item in DB
                    aFoodItemDbHandler.addFoodItem(aFoodItemName.getText());
                    // Get food item from just added food.
                    aFoodItemId = aFoodItemDbHandler.getFoodItemId(aFoodItemName.getText());
                }
                // Add foodItem to Meal.
                aMealFoodDbHandler.addFoodToMeals(aMealId,aFoodItemId);
            }

            // Adding Symptoms to DB.
            for(ListSymptomsInAddMeal aSymptom: mSymptomsList){
                // Add only if Symptom Value is greater than zero
                if(aSymptom.getValueFromSeekbar() > 0){
                    aMealSymptomsDbHandler.addSymptomValueToMeal(
                            aMealId,
                            aSymptomDbHandler.getSymptomId(aSymptom.getName()),
                            aSymptom.getValueFromSeekbar()
                    );
                }
            }
            this.finish();
        }
    }
    // Code below is for DatePicker.
    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);


            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            ((AddMealActivity) getActivity()).ShowDate(year,month,day);
        }
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }
}
