package fightingpit.foodtracker;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Fragment;
import android.widget.Button;


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

        Button aButton2 = (Button) view.findViewById(R.id.bt_addMeal);
        aButton2.setOnClickListener(this);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        NavigationDrawerFragment mNavigationDrawerFragment = (NavigationDrawerFragment)
                getActivity().getFragmentManager().findFragmentById(R.id.navigation_drawer);
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
            case R.id.bt_addMeal:
                startAddMealActivity();
                break;
        }
    }

    public void startAddMealActivity() {
        Intent i = new Intent(getActivity().getBaseContext(), AddMealActivity.class);
        startActivity(i);
    }
}
