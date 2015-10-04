package fightingpit.foodtracker.CustomLists;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

import fightingpit.foodtracker.AddFoodInAddMealActivity;
import fightingpit.foodtracker.AddMealActivity;
import fightingpit.foodtracker.R;


/**
 * Created by AG on 11-Sep-15.
 */
public class ListAdapterNameAndDeleteIcon extends BaseAdapter {

    Context context;
    protected List<GenericContainer> ListData;
    private LayoutInflater inflater;

    public ListAdapterNameAndDeleteIcon(Context iContext, List<GenericContainer> iListData){
        this.ListData = iListData;
        this.inflater = LayoutInflater.from(iContext);
        this.context = iContext;
    }

    private class ViewHolder {
        TextView FoodDisplay;
        ImageView DeleteButtonView;
    }


    @Override
    public int getCount() {
        return ListData.size();
    }

    @Override
    public Object getItem(int position) {
        return ListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public boolean isEnabled (int position) {
        return true;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_name_and_delete_icon, null);
            holder.FoodDisplay = (TextView) convertView.findViewById(R.id.tv_displayFood);
            holder.DeleteButtonView = (ImageView) convertView.findViewById(R.id.iv_list_delete_button);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.FoodDisplay.setText(ListData.get(position).getStringParam1());

        ImageView aImg = (ImageView) convertView.findViewById(R.id.iv_list_delete_button);
        aImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(context.getClass().getSimpleName().equals("AddMealActivity")){
                    // Delete button clicked. Remove the Food Item.
                    ((AddMealActivity) context).removeFoodFromMeal(position);
                } else if(context.getClass().getSimpleName().equals("AddFoodInAddMealActivity")){
                    // Delete Button Clicked. Remove the Ingredient.
                    ((AddFoodInAddMealActivity) context).removeIngredientFromFood(position);
                }


            }
        });
        return convertView;
    }
}
