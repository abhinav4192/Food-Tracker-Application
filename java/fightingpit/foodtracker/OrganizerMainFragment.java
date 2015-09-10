package fightingpit.foodtracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fightingpit.foodtracker.DB.FoodItemsDbMethods;
import fightingpit.foodtracker.DB.IngredientsDbMethods;
import fightingpit.foodtracker.DB.MealTypeDbMethods;
import fightingpit.foodtracker.DB.SymptomsDbMethods;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrganizerMainFragment extends Fragment implements View.OnClickListener{

    View view;
    private EditText inputTextFromTextView;
    private Button addMealType, addFoodItem, addIngredient, addSymptom;


    public OrganizerMainFragment() {
        // Required empty public constructor
    }

    public static OrganizerMainFragment newInstance() {
        OrganizerMainFragment fragment = new OrganizerMainFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_organizer_main, container, false);
        addMealType = (Button) view.findViewById(R.id.bt_addMealType);
        addMealType.setOnClickListener(this);

        addFoodItem = (Button) view.findViewById(R.id.bt_addFoodItem);
        addFoodItem.setOnClickListener(this);

        addIngredient = (Button) view.findViewById(R.id.bt_addIngredient);
        addIngredient.setOnClickListener(this);

        addSymptom = (Button) view.findViewById(R.id.bt_addSymptom);
        addSymptom.setOnClickListener(this);

        inputTextFromTextView = (EditText) view.findViewById(R.id.et_inputText);
        inputTextFromTextView.setText(Html.fromHtml("<b>" + "Bold TEXT" + "</b>" +  "<br />" +
                "<small>" + "Small" + "</small>" + "<br />" +
                "<small>" + "TEST Text" + "</small>"));

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        NavigationDrawerFragment mNavigationDrawerFragment = (NavigationDrawerFragment)
                getActivity().getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        if(!mNavigationDrawerFragment.isDrawerOpen()) {
            menu.clear();
            inflater.inflate(R.menu.organizer_fragment, menu);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onClick(View v) {
        //do what you want to do when button is clicked
        switch (v.getId()) {
            case R.id.bt_addMealType:
                addMealType();
                break;
            case R.id.bt_addFoodItem:
                addFoodItem();
                break;
            case R.id.bt_addIngredient:
                addIngredient();
                break;
            case R.id.bt_addSymptom:
                addSymptom();
                break;
        }
    }

    public void addMealType(){
        MealTypeDbMethods aMealDbHandler = new MealTypeDbMethods(getActivity().getBaseContext());
        String aInput = inputTextFromTextView.getText().toString().trim();
        if (aInput.length() > 1) {
            aInput = aInput.toLowerCase();
            aInput = aInput.substring(0,1).toUpperCase() + aInput.substring(1);
            if(aMealDbHandler.addMealType(aInput)){
                inputTextFromTextView.setText(null);
                View aView = getActivity().getCurrentFocus();
                if (aView != null) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(aView.getWindowToken(), 0);
                }
                Toast.makeText(getActivity().getBaseContext(), "Added", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getActivity().getBaseContext(), "Already Added", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(getActivity().getBaseContext(), "Enter a Valid Name", Toast.LENGTH_SHORT).show();
        }

    }

    public void addFoodItem(){
        FoodItemsDbMethods aFoodItemHandler = new FoodItemsDbMethods(getActivity().getBaseContext());
        String aInput = inputTextFromTextView.getText().toString().trim();
        if (aInput.length() > 1) {
            aInput = aInput.toLowerCase();
            aInput = aInput.substring(0,1).toUpperCase() + aInput.substring(1);
            if(aFoodItemHandler.addFoodItem(aInput)){
                inputTextFromTextView.setText(null);
                View aView = getActivity().getCurrentFocus();
                if (aView != null) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(aView.getWindowToken(), 0);
                }
                Toast.makeText(getActivity().getBaseContext(), "Added", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getActivity().getBaseContext(), "Already Added", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(getActivity().getBaseContext(), "Enter a Valid Name", Toast.LENGTH_SHORT).show();
        }
    }

    public void addIngredient(){
        IngredientsDbMethods aIngredientDbHandler = new IngredientsDbMethods(getActivity().getBaseContext());
        String aInput = inputTextFromTextView.getText().toString().trim();
        if (aInput.length() > 1) {
            aInput = aInput.toLowerCase();
            aInput = aInput.substring(0,1).toUpperCase() + aInput.substring(1);
            if(aIngredientDbHandler.addIngredientName(aInput)){
                inputTextFromTextView.setText(null);
                View aView = getActivity().getCurrentFocus();
                if (aView != null) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(aView.getWindowToken(), 0);
                }
                Toast.makeText(getActivity().getBaseContext(), "Added", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getActivity().getBaseContext(), "Already Added", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(getActivity().getBaseContext(), "Enter a Valid Name", Toast.LENGTH_SHORT).show();
        }

    }

    public void addSymptom(){
        SymptomsDbMethods aSymptomsDbHandler = new SymptomsDbMethods(getActivity().getBaseContext());
        String aInput = inputTextFromTextView.getText().toString().trim();
        if (aInput.length() > 1) {
            aInput = aInput.toLowerCase();
            aInput = aInput.substring(0,1).toUpperCase() + aInput.substring(1);
            if(aSymptomsDbHandler.addSymptomName(aInput)){
                inputTextFromTextView.setText(null);
                View aView = getActivity().getCurrentFocus();
                if (aView != null) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(aView.getWindowToken(), 0);
                }
                Toast.makeText(getActivity().getBaseContext(), "Added", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getActivity().getBaseContext(), "Already Added", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity().getBaseContext(), "Enter a Valid Name", Toast.LENGTH_SHORT).show();
        }

    }
}
