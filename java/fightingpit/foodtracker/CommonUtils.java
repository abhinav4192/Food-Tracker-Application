package fightingpit.foodtracker;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by AG on 01-Oct-15.
 */
public class CommonUtils {

    public CommonUtils() {
        // Required empty public constructor
    }

    public static CommonUtils newInstance() {
        CommonUtils utils = new CommonUtils();
        return utils;
    }

    public static boolean islengthValid(String iString){
        iString = iString.trim();
        boolean result = false;
        if(iString.length()>0){
            result = true;
        }
        return result;
    }

    public static String makeProperFormat(String iString){
        iString= iString.trim();
        iString = iString.toLowerCase();
        iString = iString.substring(0,1).toUpperCase() + iString.substring(1);
        return iString;
    }

    // Hack from stack over flow to Dynamically Change Ingredient List size.
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
