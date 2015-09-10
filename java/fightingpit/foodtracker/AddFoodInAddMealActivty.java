package fightingpit.foodtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import java.util.List;

import fightingpit.foodtracker.DB.FoodItemsDbMethods;

public class AddFoodInAddMealActivty extends AppCompatActivity {

    Button addFood;
    private AutoCompleteTextView autoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_in_add_meal_activty);

        populateAutoTextFoodItems();
        addFood = (Button) findViewById(R.id.bt_addFoodFromAddMeal);
        addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aOutput = autoText.getText().toString();
                Intent output = new Intent();
                output.putExtra("added_food", aOutput);
                setResult(RESULT_OK, output);
                finish();
            }
        });
    }

    private void populateAutoTextFoodItems(){
        // TODO: 06-Sep-15 Chek where to implement. Maybe in a new dialogue/activity.
        autoText = (AutoCompleteTextView) findViewById(R.id.at_getFoodItem);
        FoodItemsDbMethods aFoodItemDbHandler = new FoodItemsDbMethods(this);
        List<String> aFoodItemList = aFoodItemDbHandler.getAllFoodItems();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, aFoodItemList);

        autoText.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_add_food_in_add_meal_activty, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
