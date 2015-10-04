package fightingpit.foodtracker;

import android.content.Intent;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fightingpit.foodtracker.CustomLists.ListAdapterNameAndDeleteIcon;
import fightingpit.foodtracker.CustomLists.ListAdapterNameAndSeekBar;
import fightingpit.foodtracker.CustomLists.ListAdapterSingleElement;
import fightingpit.foodtracker.CustomLists.ListAdapterTextEdittextText;
import fightingpit.foodtracker.DB.FoodAttributeDbMethods;
import fightingpit.foodtracker.DB.FoodIngredientsDbMethods;
import fightingpit.foodtracker.DB.FoodItemsDbMethods;
import fightingpit.foodtracker.CustomLists.GenericContainer;
import fightingpit.foodtracker.DB.IngredientsDbMethods;

public class AddFoodInAddMealActivity extends Activity {

    private AutoCompleteTextView mAddFoodAutoText;
    private AutoCompleteTextView mAddIngredientAutoText;
    private TextView mAddFoodTextUpper;
    private TextView mTextViewContains;
    private TextView mTextViewFoodProperties;
    private List<GenericContainer> mIngredientCompleteList = new ArrayList<>();
    private ListView mIngredientListView;
    private MenuItem mActionAddFoodButton;
    private ListAdapterSingleElement mAdapterSingleElement;
    private ListAdapterNameAndDeleteIcon mAdapterNameAndDeleteIcon;
    private RelativeLayout mIngredientRelativeLayout;
    private String mCurrentFoodItem="";
    private Spinner mQuantityUnitSpinner;
    private LinearLayout mPropertiesLayout;
    private ListView mPropertyValueListView;
    private ListView mPropertySliderListView;
    private ListAdapterTextEdittextText mPropertyValueAdapter;
    private ListAdapterNameAndSeekBar mPropertySliderAdaper;

    private final String mFirstTimeFood="You are adding this food item for the first time. " +
            "You can add ingredients to the food item";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_in_add_meal_activty);

        // Setting title of Activity
        getActionBar().setTitle("Add Food Item");

        // Populate the mAddFoodAutoText with already existing food items.
        populateAddFoodAutoTextFoodItems();

        // Get Contains View. Do not Show it.
        mTextViewContains = (TextView) findViewById(R.id.tv_contains);
        mTextViewContains.setVisibility(View.GONE);

        // Get upper textView.
        mAddFoodTextUpper = (TextView) findViewById(R.id.tv_add_food_text_upper);
        mAddFoodTextUpper.setVisibility(View.GONE);

        // Get listView
        mIngredientListView = (ListView) findViewById(R.id.lv_ingredientsList);

        // Get Ingredient Add layout. And Do not Show it.
        mIngredientRelativeLayout = (RelativeLayout) findViewById(R.id.rl_ingredientsList);
        mIngredientRelativeLayout.setVisibility(View.GONE);

        // Get Food Properties View. Do not Show it.
        mTextViewFoodProperties = (TextView) findViewById(R.id.tv_food_properties);
        mTextViewFoodProperties.setVisibility(View.GONE);

        // Ger PropertiesLayout.
        mPropertiesLayout = (LinearLayout) findViewById(R.id.ll_food_properties);
        mPropertiesLayout.setVisibility(View.GONE);

        // Get button which will add ingredients.
        ImageView mAddIngredientButton = (ImageView) findViewById(R.id.ll_addIngredientButton);
        mAddIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addIngredientToList();
            }
        });

        // Get ingredient AutoText.
        mAddIngredientAutoText = (AutoCompleteTextView) findViewById(R.id.ll_addIngredient);
        populateMealTypeSpinner();

        mPropertyValueListView = (ListView) findViewById(R.id.lv_aafiama_prop_value);
        mPropertyValueListView.setVisibility(View.GONE);

        mPropertySliderListView = (ListView) findViewById(R.id.lv_aafiama_prop_slider);
        mPropertySliderListView.setVisibility(View.GONE);
    }


    private void populateMealTypeSpinner() {
        mQuantityUnitSpinner = (Spinner) findViewById(R.id.sp_quantity_unit_spinner);

        List<String> aMealList = new ArrayList<>();
        aMealList.add("gram");
        aMealList.add("millilitre");
        aMealList.add("cup");
        aMealList.add("piece");
        aMealList.add("ounce");
        aMealList.add("litre");


        ArrayAdapter<String> datmFoodListAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, aMealList);
        datmFoodListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mQuantityUnitSpinner.setAdapter(datmFoodListAdapter);
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
                if (actionId == EditorInfo.IME_ACTION_GO) {
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
                if (!hasFocus) {
                    // Show menu Items. Enabling add Food option.
                    mActionAddFoodButton.setVisible(true);
                    // Hide Keyboard
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    // Clear IngredientsList
                    clearIngredientList();

                    decideIngredientDisplay();
                } else {
                    // Hide menu Item. Disable add Food option.
                    mActionAddFoodButton.setVisible(false);
                }
            }
        });
    }

    public void handleAddFoodAutoTextDoneAction() {
        // Hide the keyboard.
        View aView = getCurrentFocus();
        if (aView != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(aView.getWindowToken(), 0);
        }
        // Clear focus
        mAddFoodAutoText.clearFocus();
        // Clear IngredientsList
        clearIngredientList();
        // Handle List Item Click.
        decideIngredientDisplay();
    }

    public void clearIngredientList(){
        // Reaching here means Food Item has been added/modified.
        // Clear existing in Ingredient List only if Food Item has been modified.

        boolean toClearIngredientsList = false;
        if(!CommonUtils.islengthValid(mAddFoodAutoText.getText().toString())){
            toClearIngredientsList = true;
            mTextViewContains.setVisibility(View.GONE);
        }else if(CommonUtils.islengthValid(mCurrentFoodItem) &&
                !mCurrentFoodItem.equals(CommonUtils.makeProperFormat(mAddFoodAutoText.getText().toString()))){
            toClearIngredientsList = true;
        }
        if(toClearIngredientsList){
            mIngredientCompleteList.clear();
            if(mAdapterNameAndDeleteIcon!=null){
                mAdapterNameAndDeleteIcon.notifyDataSetChanged();
            }
            if(mAdapterSingleElement!=null){
                mAdapterSingleElement.notifyDataSetChanged();
            }
            mAddIngredientAutoText.setText("");
            CommonUtils.setListViewHeightBasedOnChildren(mIngredientListView);
        }
    }

    public void decideIngredientDisplay() {
        // Get Food name entered in AutoText
        String aFoodItemName = mAddFoodAutoText.getText().toString();
        if (CommonUtils.islengthValid(aFoodItemName)) {

            // Make contains tag visible
            mTextViewContains.setVisibility(View.VISIBLE);
            aFoodItemName = CommonUtils.makeProperFormat(aFoodItemName);
            mCurrentFoodItem = aFoodItemName;
            FoodItemsDbMethods aFoodItemDbHandler = new FoodItemsDbMethods(this);
            int aFoodItemId = aFoodItemDbHandler.getFoodItemId(aFoodItemName);
            if(mIngredientCompleteList.size()==0){
                if (aFoodItemId == -1) {
                    // Item does not exits. Make Add ingredient UI visible.
                    mIngredientRelativeLayout.setVisibility(View.VISIBLE);

                    // Set upper text display.
                    mAddFoodTextUpper.setText(mFirstTimeFood);
                    mAddFoodTextUpper.setVisibility(View.VISIBLE);

                    // Set Hint in Ingredient AutoText
                    mAddIngredientAutoText.setHint("Add Ingredient (Optional)");

                    // Fill adapter with empty List.
                    mAdapterNameAndDeleteIcon = new ListAdapterNameAndDeleteIcon(this, mIngredientCompleteList);
                    mIngredientListView.setAdapter(mAdapterNameAndDeleteIcon);
                    CommonUtils.setListViewHeightBasedOnChildren(mIngredientListView);

                    // Populate ingredients Autotext.
                    handleIngredientAutoText();
                } else {
                    // Item Exists. Make Add ingredient UI hidden.
                    mIngredientRelativeLayout.setVisibility(View.GONE);

                    FoodIngredientsDbMethods aFoodIngredientsDbHandler = new FoodIngredientsDbMethods(this);
                    List<String> aIngredientIdList = aFoodIngredientsDbHandler.getAllIngredientsInFood(aFoodItemId);
                    if (aIngredientIdList.size() > 0) {
                        mAddFoodTextUpper.setVisibility(View.GONE);

                        IngredientsDbMethods aIngredientsDbHandler = new IngredientsDbMethods(this);
                        for (String ingredientId : aIngredientIdList) {
                            mIngredientCompleteList.add(new GenericContainer(aIngredientsDbHandler.getIngredientName(ingredientId)));
                        }

                        mAdapterSingleElement = new ListAdapterSingleElement(this, mIngredientCompleteList);
                        mIngredientListView.setAdapter(mAdapterSingleElement);
                        CommonUtils.setListViewHeightBasedOnChildren(mIngredientListView);
                    } else {
                        mAddFoodTextUpper.setVisibility(View.VISIBLE);
                        mAddFoodTextUpper.setText("No Ingredient Added");
                    }
                }

                handleFoodProperty();

            }
        } else {
            mIngredientRelativeLayout.setVisibility(View.GONE);
            mAddFoodTextUpper.setVisibility(View.GONE);
            mTextViewFoodProperties.setVisibility(View.GONE);
            mPropertiesLayout.setVisibility(View.GONE);
            mPropertyValueListView.setVisibility(View.GONE);
            mPropertySliderListView.setVisibility(View.GONE);
        }
    }

    public void handleFoodProperty(){
        mTextViewFoodProperties.setVisibility(View.VISIBLE);
        mPropertiesLayout.setVisibility(View.VISIBLE);

        List<GenericContainer> aPropertyValueList = new ArrayList<GenericContainer>();
        List<GenericContainer> aPropertySliderList = new ArrayList<GenericContainer>();
        FoodAttributeDbMethods aFoodAttributeDbHandler = new FoodAttributeDbMethods(this);
        for(GenericContainer aListItem:aFoodAttributeDbHandler.getAllFoodAttributeTypes()){
            if(aListItem.getStringParam2().equals("v")){
                aPropertyValueList.add(new GenericContainer(aListItem.getStringParam1(),aListItem.getStringParam3()));
            }else{
                aPropertySliderList.add(new GenericContainer(0,aListItem.getStringParam1(),"0"));
            }
        }

        if(aPropertyValueList.size()>0){
            mPropertyValueListView.setVisibility(View.VISIBLE);
            mPropertyValueAdapter = new ListAdapterTextEdittextText(this, aPropertyValueList);
            mPropertyValueListView.setAdapter(mPropertyValueAdapter);
            CommonUtils.setListViewHeightBasedOnChildren(mPropertyValueListView);
        }
        if(aPropertySliderList.size()>0){
            mPropertySliderListView.setVisibility(View.VISIBLE);
            mPropertySliderAdaper = new ListAdapterNameAndSeekBar(this, aPropertySliderList);
            mPropertySliderListView.setAdapter(mPropertySliderAdaper);
            CommonUtils.setListViewHeightBasedOnChildren(mPropertySliderListView);
        }
    }

    public void handleIngredientAutoText(){

        // Get all Ingredients items and add to Autotext list.
        IngredientsDbMethods aIngredientsDbHandler = new IngredientsDbMethods(this);
        List<String> aIngredientList = aIngredientsDbHandler.getAllIngredients();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, aIngredientList);
        mAddIngredientAutoText.setAdapter(adapter);


        // An item from List is clicked.
        mAddIngredientAutoText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                addIngredientToList();
            }
        });

        // Done clicked on Keyboard
        mAddIngredientAutoText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    // Add item to Added Ingredient List.
                    addIngredientToList();
                    return true;
                }
                return false;
            }
        });

        mAddIngredientAutoText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    // Show menu Items. Enabling add Food option.
                    mActionAddFoodButton.setVisible(true);
                    // Hide the keyboard.
                    View aView = getCurrentFocus();
                    if (aView != null) {
                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(aView.getWindowToken(), 0);
                    }

                } else {
                    // Hide menu Item. Disable add Food option.
                    mActionAddFoodButton.setVisible(false);
                }
            }
        });
    }

    public void addIngredientToList() {

        // Hide the keyboard.
        View aView = getCurrentFocus();
        if (aView != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(aView.getWindowToken(), 0);
        }

        mAddIngredientAutoText.clearFocus();
        mIngredientListView.requestFocus();


        boolean aAddIngredient = true;
        String aAddedIngredientName = mAddIngredientAutoText.getText().toString();

        if (CommonUtils.islengthValid(aAddedIngredientName)) {
            aAddedIngredientName = CommonUtils.makeProperFormat(aAddedIngredientName);
            for (GenericContainer aAlreadyAddedIngredient : mIngredientCompleteList) {
                if (aAlreadyAddedIngredient.getStringParam1().equals(aAddedIngredientName)) {
                    aAddIngredient = false;
                    Toast.makeText(this, "Ingredient already added", Toast.LENGTH_SHORT).show();
                    mAddIngredientAutoText.setText("");
                    break;
                }
            }
        } else {
            Toast.makeText(this, "Enter a Valid name", Toast.LENGTH_SHORT).show();
            aAddIngredient = false;
        }
        if (aAddIngredient) {
            mIngredientCompleteList.add(new GenericContainer(aAddedIngredientName));
            mAdapterNameAndDeleteIcon.notifyDataSetChanged();
            CommonUtils.setListViewHeightBasedOnChildren(mIngredientListView);

            if(mIngredientCompleteList.size()==1){
                mAddFoodTextUpper.setVisibility(View.GONE);
                mAddIngredientAutoText.setHint("Add Another Ingredient");
            }

            mAddIngredientAutoText.setText("");


        }
    }

    public void removeIngredientFromFood(int iIndex){
        mIngredientCompleteList.remove(iIndex);
        if(mIngredientCompleteList.size()==0){
            mAddFoodTextUpper.setText(mFirstTimeFood);
            mAddFoodTextUpper.setVisibility(View.VISIBLE);
            mAddIngredientAutoText.setHint("Add Ingredient (Optional)");
        }
        mAdapterNameAndDeleteIcon.notifyDataSetChanged();
        CommonUtils.setListViewHeightBasedOnChildren(mIngredientListView);
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
        int id = item.getItemId();

        if (id == R.id.action_addFood) {
            String aOutput = mAddFoodAutoText.getText().toString();
            if(CommonUtils.islengthValid(aOutput))
            {
                aOutput=CommonUtils.makeProperFormat(aOutput);
                Intent output = new Intent();
                output.putExtra("added_food", aOutput);
                setResult(RESULT_OK, output);
                addFoodAndIngredientsToDb();
                finish();
            }
            else{
                Toast.makeText(this, "Enter a valid Food Item", Toast.LENGTH_SHORT).show();
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addFoodAndIngredientsToDb(){

        // Actually Add Ingredients and Food Items to DB
        String aFoodName = CommonUtils.makeProperFormat(mAddFoodAutoText.getText().toString());
        FoodItemsDbMethods aFoodItemDbHandler = new FoodItemsDbMethods(this);
        int aFoodItemId = aFoodItemDbHandler.getFoodItemId(aFoodName);
        if(aFoodItemId == -1){
            // Add only if FoodItem not present
            aFoodItemDbHandler.addFoodItem(aFoodName);
            // Add Food Item to Food Item Table.
            aFoodItemId = aFoodItemDbHandler.getFoodItemId(aFoodName);

            IngredientsDbMethods aIngredientsDbHandler = new IngredientsDbMethods(this);
            FoodIngredientsDbMethods aFoodIngredientsDbHandler = new FoodIngredientsDbMethods(this);
            for (GenericContainer aAddedIngredient : mIngredientCompleteList) {
                String aIngredientName = aAddedIngredient.getStringParam1();

                int aIngredientId = aIngredientsDbHandler.getIngredientId(aIngredientName);
                if(aIngredientId == -1 ){
                    // Ingredient does not exists in DB. Add to Ingredient DB Table.
                    aIngredientsDbHandler.addIngredientName(aIngredientName);
                    aIngredientId = aIngredientsDbHandler.getIngredientId(aIngredientName);
                }
                // Add to FoodIngredients DB Table.
                aFoodIngredientsDbHandler.addIngredientsToFood(aFoodItemId,aIngredientId);
            }
        }
    }
}