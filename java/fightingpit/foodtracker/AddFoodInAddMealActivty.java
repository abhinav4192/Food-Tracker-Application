package fightingpit.foodtracker;

import android.content.Intent;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fightingpit.foodtracker.DB.FoodIngredientsDbMethods;
import fightingpit.foodtracker.DB.FoodItemsDbMethods;
import fightingpit.foodtracker.DB.IngredientsDbMethods;

public class AddFoodInAddMealActivty extends Activity {

    Button addFood;
    private AutoCompleteTextView autoText;
    LinearLayout aLL;
    TextView mIngredientDetails;
    EditText mGetIngredient;
    Button mAddIngredient;
    List<String> mIngredientListFromUser = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_in_add_meal_activty);

        populateAutoTextFoodItems();
        addFood = (Button) findViewById(R.id.bt_addFoodFromAddMeal);
        final IngredientsDbMethods aIngredientsDbHandler = new IngredientsDbMethods(this);
        addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aOutput = autoText.getText().toString();
                Intent output = new Intent();
                output.putExtra("added_food", aOutput);

                for(String aAddedIngredientName:mIngredientListFromUser){
                    // TODO: 01-Oct-15 Get Auto populated Ingredients.
                    // Add ing to db
                    // Add ing to food.
                    aIngredientsDbHandler.addIngredientName(aAddedIngredientName);
                }
                setResult(RESULT_OK, output);
                finish();
            }
        });

        mIngredientDetails = (TextView) findViewById(R.id.tv_ingredientDetails);
        mIngredientDetails.setVisibility(View.GONE);

        aLL = (LinearLayout) findViewById(R.id.ll_ingredientAddView);
        aLL.setVisibility(View.GONE);

        mGetIngredient = (EditText) findViewById(R.id.et_insertIngredient);
        mAddIngredient = (Button) findViewById(R.id.bt_addIngredient);
        mAddIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addIngredientToList();
            }
        });
    }

    public void addIngredientToList(){

        boolean aAddIngredient = true;
        String aAddedIngredientName = mGetIngredient.getText().toString();
        aAddedIngredientName = CommonUtils.newInstance().makeProperFormat(aAddedIngredientName);
        if(CommonUtils.newInstance().islengthValid(aAddedIngredientName)){
            for(String aAlreadyAddedIngredient: mIngredientListFromUser){
                if(aAlreadyAddedIngredient.equals(aAddedIngredientName)){
                    aAddIngredient = false;
                    Toast.makeText(this,"Ingredient already added.",Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        }else{
            Toast.makeText(this,"Enter a Valid name",Toast.LENGTH_SHORT).show();
            aAddIngredient = false;
        }
        if(aAddIngredient){
            mIngredientListFromUser.add(aAddedIngredientName);
        }
    }

    private void populateAutoTextFoodItems() {
        autoText = (AutoCompleteTextView) findViewById(R.id.at_getFoodItem);
        FoodItemsDbMethods aFoodItemDbHandler = new FoodItemsDbMethods(this);
        List<String> aFoodItemList = aFoodItemDbHandler.getAllFoodItems();

//        for(int i=0; i< aFoodItemList.size();i++){
//            int aFoodItemId = aFoodItemDbHandler.getFoodItemId(aFoodItemList.get(i));
//
//            FoodIngredientsDbMethods aFoodIngredientsDbHandler = new FoodIngredientsDbMethods(this);
//            List<String> aIngredientIdList = aFoodIngredientsDbHandler.getAllIngredientsInFood(aFoodItemId);
//            if(aIngredientIdList.size()>0){
//                IngredientsDbMethods aIngredientsDbHandler = new IngredientsDbMethods(this);
//                int count =0;
//                for(String ingredientId:aIngredientIdList){
//                    if (count ==0){
//                        aFoodItemList.set(i,aFoodItemList.get(i) + " | Contains: "+aIngredientsDbHandler.getIngredientName(ingredientId));
//                    }else {
//                        aFoodItemList.set(i, aFoodItemList.get(i) + " , "+ aIngredientsDbHandler.getIngredientName(ingredientId));
//                    }
//                    count++;
//                }
//            }
//        }


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, aFoodItemList);
        autoText.setAdapter(adapter);

        autoText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                handleIngredients();
            }
        });
//        autoText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        autoText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                handleIngredients();
            }
        });
    }

    public void handleIngredients() {
        String aFoodItemName = autoText.getText().toString().trim();
        if(aFoodItemName.length() >0){
            FoodItemsDbMethods aFoodItemDbHandler = new FoodItemsDbMethods(this);
            int aFoodItemId = aFoodItemDbHandler.getFoodItemId(aFoodItemName);
            if(aFoodItemId == -1){
                // Item does not exits
                aLL.setVisibility(View.VISIBLE);
                mIngredientDetails.setVisibility(View.GONE);

            } else {
                // Item Exists
                aLL.setVisibility(View.GONE);

                FoodIngredientsDbMethods aFoodIngredientsDbHandler = new FoodIngredientsDbMethods(this);
                List<String> aIngredientIdList = aFoodIngredientsDbHandler.getAllIngredientsInFood(aFoodItemId);
                if(aIngredientIdList.size()>0){
                    String aIngredientDisplayValue = new String("");
                    IngredientsDbMethods aIngredientsDbHandler = new IngredientsDbMethods(this);
                    int count =0;
                    for(String ingredientId:aIngredientIdList){
                        if (count ==0){
                            aIngredientDisplayValue = aIngredientDisplayValue + "Contains: "+aIngredientsDbHandler.getIngredientName(ingredientId);
                        }else {
                            aIngredientDisplayValue = aIngredientDisplayValue + " , "+ aIngredientsDbHandler.getIngredientName(ingredientId);
                        }
                        count++;
                    }
                    mIngredientDetails.setText(aIngredientDisplayValue);
                }else{
                    mIngredientDetails.setText("No ingredient Added");
                }
                mIngredientDetails.setVisibility(View.VISIBLE);
            }

        }else{
            aLL.setVisibility(View.GONE);
            mIngredientDetails.setVisibility(View.GONE);
        }
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
