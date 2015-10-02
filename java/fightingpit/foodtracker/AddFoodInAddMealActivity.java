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
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fightingpit.foodtracker.CustomLists.ListAdapterNameAndDeleteIcon;
import fightingpit.foodtracker.CustomLists.ListAdapterSingleElement;
import fightingpit.foodtracker.CustomLists.ListSingleElement;
import fightingpit.foodtracker.DB.FoodIngredientsDbMethods;
import fightingpit.foodtracker.DB.FoodItemsDbMethods;
import fightingpit.foodtracker.DB.IngredientsDbMethods;

public class AddFoodInAddMealActivity extends Activity {

    private AutoCompleteTextView mAddFoodAutoText;
    private AutoCompleteTextView mAddIngredientAutoText;
    private TextView mAddFoodTextUpper;
    private List<ListSingleElement> mIngredientListFromUser = new ArrayList<>();
    private ListView mIngredientListView;
    private MenuItem mActionAddFoodButton;
    private ImageView mAddIngredientButton;
    private ListAdapterSingleElement mAdapterSingleElement;
    private ListAdapterNameAndDeleteIcon mAdapterNameAndDeleteIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_in_add_meal_activty);

        // Setting title of Activity
        getActionBar().setTitle("Add Food Item");

        // Populate the mAddFoodAutoText with already existing food items.
        populateAddFoodAutoTextFoodItems();

        mAddFoodTextUpper = (TextView) findViewById(R.id.tv_add_food_text_upper);
        mIngredientListView = (ListView) findViewById(R.id.lv_ingredientsList);

        mAddIngredientButton = (ImageView) findViewById(R.id.ll_addIngredientButton);
        mAddIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addIngredientToList();
            }
        });
        mAddIngredientButton.setVisibility(View.GONE);

        handleIngredientAutoText();
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
                if (actionId == EditorInfo.IME_ACTION_DONE) {
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
                    decideIngredientDisplay();
                } else {
                    // Hide menu Item. Disable add Food option.
                    mActionAddFoodButton.setVisible(false);
                }
            }
        });
    }

    public void handleIngredientAutoText(){
        mAddIngredientAutoText = (AutoCompleteTextView) findViewById(R.id.ll_addIngredient);

        // Done clicked for Keyboard
        mAddIngredientAutoText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // Hide the keyboard.
                    View aView = getCurrentFocus();
                    if (aView != null) {
                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(aView.getWindowToken(), 0);
                    }
                    // Clear focus
                    mAddIngredientAutoText.clearFocus();
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
                    //mAddIngredientButton.setVisibility(View.VISIBLE);
                    // Show menu Items. Enabling add Food option.
                    mActionAddFoodButton.setVisible(true);
                    // Hide the keyboard.
                    View aView = getCurrentFocus();
                    if (aView != null) {
                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(aView.getWindowToken(), 0);
                    }
                    // Clear focus
                    mAddFoodAutoText.clearFocus();

                } else {
                    // Hide menu Item. Disable add Food option.
                    mActionAddFoodButton.setVisible(false);
                    //mAddIngredientButton.setVisibility(View.GONE);
                }
            }
        });
        mAddIngredientAutoText.setVisibility(View.GONE);
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
        // Handle List Item Click.
        decideIngredientDisplay();
    }

    public void decideIngredientDisplay() {
        String aFoodItemName = mAddFoodAutoText.getText().toString();
        if (CommonUtils.newInstance().islengthValid(aFoodItemName)) {
            aFoodItemName = CommonUtils.newInstance().makeProperFormat(aFoodItemName);
            FoodItemsDbMethods aFoodItemDbHandler = new FoodItemsDbMethods(this);
            int aFoodItemId = aFoodItemDbHandler.getFoodItemId(aFoodItemName);
            if (aFoodItemId == -1) {
                // Item does not exits
                mAddIngredientAutoText.setVisibility(View.VISIBLE);
                mAddIngredientButton.setVisibility(View.VISIBLE);


                // Clear existing Ingredient List.
//                mIngredientListFromUser.clear();
//                if(mAdapterNameAndDeleteIcon!=null){
//
//                    mAdapterNameAndDeleteIcon.notifyDataSetChanged();
//                    setListViewHeightBasedOnChildren(mIngredientListView);
//                }

                if(mIngredientListFromUser.size()==0){
                    String aDisplay = "<small>You are adding a new food item. You can add ingredients to the food item.</small>";
                    mAddFoodTextUpper.setText(Html.fromHtml(aDisplay));
                    mAddFoodTextUpper.setVisibility(View.VISIBLE);
                }
                mIngredientListView = (ListView) findViewById(R.id.lv_ingredientsList);
                mAdapterNameAndDeleteIcon = new ListAdapterNameAndDeleteIcon(this, mIngredientListFromUser);
                mIngredientListView.setAdapter(mAdapterNameAndDeleteIcon);

            } else {
                // Item Exists
                mAddIngredientAutoText.setVisibility(View.GONE);
                mAddIngredientButton.setVisibility(View.GONE);

                FoodIngredientsDbMethods aFoodIngredientsDbHandler = new FoodIngredientsDbMethods(this);
                List<String> aIngredientIdList = aFoodIngredientsDbHandler.getAllIngredientsInFood(aFoodItemId);
                if (aIngredientIdList.size() > 0) {
                    String aIngredientDisplayValue = "";
                    IngredientsDbMethods aIngredientsDbHandler = new IngredientsDbMethods(this);
                    int count = 0;
                    for (String ingredientId : aIngredientIdList) {
                        if (count == 0) {
                            aIngredientDisplayValue = aIngredientDisplayValue + "Contains: " + aIngredientsDbHandler.getIngredientName(ingredientId);
                        } else {
                            aIngredientDisplayValue = aIngredientDisplayValue + " , " + aIngredientsDbHandler.getIngredientName(ingredientId);
                        }
                        count++;
                    }
                    mAddFoodTextUpper.setText(aIngredientDisplayValue);
                } else {
                    mAddFoodTextUpper.setText("No ingredient Added");
                }
                mAddFoodTextUpper.setVisibility(View.VISIBLE);
            }

        } else {
            //mAddIngredientLayout.setVisibility(View.GONE);
            //mAddFoodTextUpper.setVisibility(View.GONE);
        }
    }

    public void addIngredientToList() {

        // Hide the keyboard.
        View aView = getCurrentFocus();
        if (aView != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(aView.getWindowToken(), 0);
        }
        // Clear focus
        mAddFoodAutoText.clearFocus();

        boolean aAddIngredient = true;
        String aAddedIngredientName = mAddIngredientAutoText.getText().toString();

        if (CommonUtils.newInstance().islengthValid(aAddedIngredientName)) {
            aAddedIngredientName = CommonUtils.newInstance().makeProperFormat(aAddedIngredientName);
            for (ListSingleElement aAlreadyAddedIngredient : mIngredientListFromUser) {
                if (aAlreadyAddedIngredient.getText().equals(aAddedIngredientName)) {
                    aAddIngredient = false;
                    Toast.makeText(this, "Ingredient already added.", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        } else {
            Toast.makeText(this, "Enter a Valid name", Toast.LENGTH_SHORT).show();
            aAddIngredient = false;
        }
        if (aAddIngredient) {
            mIngredientListFromUser.add(new ListSingleElement(aAddedIngredientName));
            for (ListSingleElement aAlreadyAddedIngredient : mIngredientListFromUser) {
                Log.d("ABGU", aAlreadyAddedIngredient.getText().toString());
            }

            mAdapterNameAndDeleteIcon.notifyDataSetChanged();
            setListViewHeightBasedOnChildren(mIngredientListView);

            mAddFoodTextUpper.setText(Html.fromHtml("<b>Contains:</b>"));

            mAddIngredientAutoText.setHint("Add Another Ingredient");
            mAddIngredientAutoText.setText("");
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

//            for(String aAddedIngredientName:mIngredientListFromUser){
//                // TODO: 01-Oct-15 Get Auto populated Ingredients.
//                // Add ing to db
//                // Add ing to food.
//                aIngredientsDbHandler.addIngredientName(aAddedIngredientName);
//            }
            setResult(RESULT_OK, output);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    // Hack from stackoverflow to Dynamically Change Ingredient List size.
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null){
            return;
        }

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, AbsListView.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}