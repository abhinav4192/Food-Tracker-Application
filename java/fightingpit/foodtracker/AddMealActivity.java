package fightingpit.foodtracker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Calendar;
import java.util.List;

import fightingpit.foodtracker.DB.FoodItemsDbMethods;
import fightingpit.foodtracker.DB.MealDbMethods;
import fightingpit.foodtracker.DB.MealTypeDbMethods;


public class AddMealActivity extends ActionBarActivity{

    private Spinner aSpinner;
    private TextView dateView;
    private Calendar calendar;
    private int year, month, day;
    private String mealDate;
    private Button addMealToTableButton;
    private AutoCompleteTextView autoText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);

        populateMealTypeSpinner();
        initializeDatePicker();
        populateAutoTextFoodItems();

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

    private void populateAutoTextFoodItems(){
        // TODO: 06-Sep-15 Chek where to implement. Maybe in a new dialogue/activity.
        autoText = (AutoCompleteTextView) findViewById(R.id.autoText1);
        FoodItemsDbMethods aFoodItemDbHandler = new FoodItemsDbMethods(this);
        List<String> aFoodItemList = aFoodItemDbHandler.getAllFoodItems();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, aFoodItemList);

        autoText.setAdapter(adapter);
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
        MealDbMethods aMealDbHandler = new MealDbMethods(this);
        MealTypeDbMethods aMealTypeDbHandler = new MealTypeDbMethods(this);
        FoodItemsDbMethods aFoodItemDbHandler = new FoodItemsDbMethods(this);

        // TODO: 06-Sep-15 Validate all data first, then perform write operations.
        boolean isMealAdded = aMealDbHandler.addNewMeal(
                aMealTypeDbHandler.getMealTypeIdFromMealTypeName(aSpinner.getSelectedItem().toString()),
                mealDate);
        if(isMealAdded == false){
            Toast.makeText(this,"Meal Already Added",Toast.LENGTH_SHORT).show();
        } else{
            String aFoodItemName = autoText.getText().toString();
            int aFoodItemId = aFoodItemDbHandler.getFoodItemId(aFoodItemName);
            // TODO: 05-Sep-15 Fix This Code
            if(aFoodItemId == -1){
                if(aFoodItemDbHandler.addFoodItem(aFoodItemName)){
                    // TODO: 05-Sep-15  Add Meal and Food.
                    Toast.makeText(this,"Food Added",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this,"Failed to add food",Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this,"Food Already present",Toast.LENGTH_SHORT).show();
            }
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
