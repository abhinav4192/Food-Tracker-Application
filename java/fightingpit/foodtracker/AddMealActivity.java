package fightingpit.foodtracker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fightingpit.foodtracker.DB.FoodItemsDbMethods;
import fightingpit.foodtracker.DB.MealDbMethods;
import fightingpit.foodtracker.DB.MealFoodDbMethods;
import fightingpit.foodtracker.DB.MealTypeDbMethods;


public class AddMealActivity extends ActionBarActivity{

    private Spinner aSpinner;
    private TextView dateView;
    private Calendar calendar;
    private int year, month, day;
    private String mealDate;
    private Button addMealToTableButton;
    private Button aBtn;
    private List<String> aFoodList;
    private TextView aFoodDisplay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);

        Log.d("ABG", "onCreate");
        if(savedInstanceState != null){
            Log.d("ABG", savedInstanceState.getString("test"));
        }

        populateMealTypeSpinner();
        initializeDatePicker();


        aFoodList = new ArrayList<>();


        // Get Add Meal and setOnclickListener.
        // TODO: 06-Sep-15 This is basically a save button. Change name if required.
        addMealToTableButton = (Button) findViewById(R.id.bt_addMealToTable);
        addMealToTableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewMeal();
            }
        });

        getSupportActionBar().setTitle("Add Food to Meal");


        aBtn = (Button) findViewById(R.id.bt_addFoodToMeal);
        aBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), AddFoodInAddMealActivty.class);
                startActivityForResult(i, 101);
            }
        });

        aFoodDisplay = (TextView) findViewById(R.id.tv_displayFoods);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 101 && resultCode == RESULT_OK && data != null) {
            aFoodList.add(data.getStringExtra("added_food"));
        }
    }

    private void populateMealTypeSpinner() {
        aSpinner = (Spinner) findViewById(R.id.spinner1);
        MealTypeDbMethods aMealDbHandler = new MealTypeDbMethods(this);
        List<String> aMealList = aMealDbHandler.getAllMealType();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, aMealList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        aSpinner.setAdapter(dataAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("ABG", "onStart");
        if(aFoodList.size()>2){
            aBtn.setVisibility(View.GONE);
        }
        else {
            aBtn.setVisibility(View.VISIBLE);
        }
        String aFDisplay = new String();
        for(int i = 0; i < aFoodList.size();i++){
            if(i!=0) {
                aFDisplay += "<br />";
            }
            aFDisplay += aFoodList.get(i);
        }
        aFoodDisplay.setText(Html.fromHtml(aFDisplay));

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
        dateView = (TextView) findViewById(R.id.bt_pickDate);
        dateView.setOnClickListener(new View.OnClickListener() {
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
        dateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
        mealDate = (new StringBuilder().append(year).append(month).append(day)).toString();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // No menu needed.
        return true;
    }

    private void addNewMeal(){
        Log.d("ABG", "addNewMeal1");
        MealDbMethods aMealDbHandler = new MealDbMethods(this);
        MealTypeDbMethods aMealTypeDbHandler = new MealTypeDbMethods(this);
        FoodItemsDbMethods aFoodItemDbHandler = new FoodItemsDbMethods(this);
        MealFoodDbMethods aMealFoodDbHandler = new MealFoodDbMethods(this);

        boolean aCommitToDb = true;

        // Get MealTypeId from MealTypeName
        Integer aMealTypeId = aMealTypeDbHandler.getMealTypeIdFromMealTypeName(aSpinner.getSelectedItem().toString());

        // TODO: 06-Sep-15 Validate all data first, then perform write operations.
        if(aMealDbHandler.isMealAlreadyAdded(aMealTypeId,mealDate)){
            Toast.makeText(this,"Meal Already Added",Toast.LENGTH_SHORT).show();
            aCommitToDb = false;
        }

        final Set<String> set1 = new HashSet<>(aFoodList);
        if(aFoodList.size() > set1.size()){
            Toast.makeText(this,"A food Item is added twice",Toast.LENGTH_SHORT).show();
            aCommitToDb = false;
        }

        if(aCommitToDb){
            // Add the Meal to the Table.
            aMealDbHandler.addNewMeal(aMealTypeId, mealDate);

            // Get MealId of Meal just added.
            int aMealId = aMealDbHandler.getMealId(aMealTypeId, mealDate);

            for(String aFoodItemName: aFoodList){
                // Get FoodItemId of Food
                int aFoodItemId = aFoodItemDbHandler.getFoodItemId(aFoodItemName);
                if(aFoodItemId == -1){
                    // Food Item Not present in DB. Add Food Item in DB
                    aFoodItemDbHandler.addFoodItem(aFoodItemName);
                    // Get food item from just added food.
                    aFoodItemId = aFoodItemDbHandler.getFoodItemId(aFoodItemName);
                }
                // Add foodItem to Meal.
                aMealFoodDbHandler.addFoodToMeals(aMealId,aFoodItemId);
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
