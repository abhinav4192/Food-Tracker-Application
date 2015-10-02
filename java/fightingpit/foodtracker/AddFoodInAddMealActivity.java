package fightingpit.foodtracker;

import android.content.Intent;
import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
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

public class AddFoodInAddMealActivity extends Activity {

    private AutoCompleteTextView mAddFoodAutoText;
    TextView mTextView1;
    TextView mTextView2;
    LinearLayout mAddIngredientLayout;
    TextView mIngredientDetails;
    EditText mGetIngredient;
    Button mAddIngredient;
    private List<String> mIngredientListFromUser = new ArrayList<>();
    MenuItem mActionAddFoodButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_in_add_meal_activty);

        // Setting title of Activity
        getActionBar().setTitle("Add Food Item");

        // Populate the mAddFoodAutoText with already existing food items.
        populateAddFoodAutoTextFoodItems();

        mIngredientDetails = (TextView) findViewById(R.id.tv_add_food_text1);
        mIngredientDetails.setVisibility(View.GONE);

        mAddIngredientLayout = (LinearLayout) findViewById(R.id.ll_addIngredientLayout);
        mAddIngredientLayout.setVisibility(View.GONE);

        mGetIngredient = (EditText) findViewById(R.id.ll_addIngredient);
/*        mAddIngredient = (Button) findViewById(R.id.bt_addIngredient);
        mAddIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addIngredientToList();
            }
        });*/

        mTextView1 = (TextView) findViewById(R.id.tv_add_food_text1);
    }

    private void populateAddFoodAutoTextFoodItems() {

        mAddFoodAutoText = (AutoCompleteTextView) findViewById(R.id.at_getFoodItem);

        // Get all food items and add to list.
        FoodItemsDbMethods aFoodItemDbHandler = new FoodItemsDbMethods(this);
        List<String> aFoodItemList = aFoodItemDbHandler.getAllFoodItems();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, aFoodItemList);
        mAddFoodAutoText.setAdapter(adapter);

        // An item from List is clicked.
        mAddFoodAutoText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                handleAddFoodAutoTextDoneAction();
            }
        });

        // Done clicked for Keyboard
        mAddFoodAutoText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    handleAddFoodAutoTextDoneAction();
                    return true;
                }
                return false;
            }
        });

        // On focus change.
        mAddFoodAutoText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    // Show menu Items. Enabling add Food option.
                    mActionAddFoodButton.setVisible(true);
                    // Hide Keyboard
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    decideIngredientDisplay();
                }else{
                    // Hide menu Item. Disable add Food option.
                    mActionAddFoodButton.setVisible(false);
                }
            }
        });
    }

    public void handleAddFoodAutoTextDoneAction(){
        // Hide the keyboard.
        View aView = getCurrentFocus();
        if (aView != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(aView.getWindowToken(), 0);
        }
        // Clear focus
        mAddFoodAutoText.clearFocus();
        // Handle List Item Click.
        decideIngredientDisplay();
    }

    public void decideIngredientDisplay() {
        String aFoodItemName = mAddFoodAutoText.getText().toString().trim();
        if(aFoodItemName.length() >0){
            FoodItemsDbMethods aFoodItemDbHandler = new FoodItemsDbMethods(this);
            int aFoodItemId = aFoodItemDbHandler.getFoodItemId(aFoodItemName);
            if(aFoodItemId == -1){
                // Item does not exits
                mAddIngredientLayout.setVisibility(View.GONE);
                mIngredientDetails.setVisibility(View.VISIBLE);

                String input = " <big><b>Contains:</b> <br> &nbsp;&nbsp;&nbsp;Ingredient1<br>&nbsp;&nbsp;&nbsp;Ingredient2<br>&nbsp;&nbsp;&nbsp;Test Ingredient";
                mTextView1.setText(Html.fromHtml(input));

            } else {
                // Item Exists
                mAddIngredientLayout.setVisibility(View.GONE);

                FoodIngredientsDbMethods aFoodIngredientsDbHandler = new FoodIngredientsDbMethods(this);
                List<String> aIngredientIdList = aFoodIngredientsDbHandler.getAllIngredientsInFood(aFoodItemId);
                if(aIngredientIdList.size()>0){
                    String aIngredientDisplayValue="";
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
            mAddIngredientLayout.setVisibility(View.GONE);
            mIngredientDetails.setVisibility(View.GONE);
        }
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





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_food_in_add_meal_activty, menu);
        mActionAddFoodButton = menu.findItem(R.id.action_addFood);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_addFood) {
            final IngredientsDbMethods aIngredientsDbHandler = new IngredientsDbMethods(this);
            String aOutput = mAddFoodAutoText.getText().toString();
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
