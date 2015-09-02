package fightingpit.foodtracker;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class FoodDiaryMainFragment extends Fragment {


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
        return inflater.inflate(R.layout.fragment_food_diary_main, container, false);
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

}
