package fightingpit.foodtracker;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fightingpit.foodtracker.DB.MealTypeDbMethods;


/**
 * A simple {@link Fragment} subclass.
 */
public class FoodDiaryMainFragment extends Fragment implements View.OnClickListener {

    View view;

    public FoodDiaryMainFragment() {
        // Required empty public constructor
    }

    public static FoodDiaryMainFragment newInstance() {
        FoodDiaryMainFragment fragment = new FoodDiaryMainFragment();
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
        view = inflater.inflate(R.layout.fragment_food_diary_main, container, false);

        Button aButton = (Button) view.findViewById(R.id.tv_button);
        aButton.setOnClickListener(this);
        Button aButton1 = (Button) view.findViewById(R.id.tv_button_del);
        aButton1.setOnClickListener(this);
        Button aButton2 = (Button) view.findViewById(R.id.bt_addMeal);
        aButton2.setOnClickListener(this);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        NavigationDrawerFragment mNavigationDrawerFragment = (NavigationDrawerFragment)
                getActivity().getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        if(!mNavigationDrawerFragment.isDrawerOpen()) {
            menu.clear();
            inflater.inflate(R.menu.food_dairy_fragment, menu);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onClick(View v) {
        //do what you want to do when button is clicked
        switch (v.getId()) {
            case R.id.tv_button:
                addMealType();
                break;
            case R.id.tv_button_del:
                //deleteFoodItem();
                break;
            case R.id.bt_addMeal:
                startAddMealActivity();
                break;
        }
    }

    private void addMealType() {
        EditText aTv = (EditText) view.findViewById(R.id.et_frag_food_diary_main);
        MealTypeDbMethods aMealDbHandler = new MealTypeDbMethods(getActivity().getBaseContext());
        String st = aTv.getText().toString().trim();
        if (st.length() > 1) {
            aMealDbHandler.insertMealType(st);
            aTv.setText(null);
        } else {
            Toast.makeText(getActivity().getBaseContext(), "Enter a Valid Name", Toast.LENGTH_SHORT).show();
        }

    }



    public void startAddMealActivity() {
        Intent i = new Intent(getActivity().getBaseContext(), AddMealActivity.class);
        startActivity(i);
    }
}
