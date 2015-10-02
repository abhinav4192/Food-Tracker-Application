package fightingpit.foodtracker.CustomLists;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

import fightingpit.foodtracker.AddMealActivity;
import fightingpit.foodtracker.R;


/**
 * Created by AG on 11-Sep-15.
 */
public class ListAdapterNameAndDeleteIcon extends BaseAdapter {

    Context context;
    protected List<ListSingleElement> ListData;
    private LayoutInflater inflater;

    public ListAdapterNameAndDeleteIcon(Context iContext, List<ListSingleElement> iListData){
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
            //Log.d("ABG", "in convertview if");
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_name_and_delete_icon, null);
            holder.FoodDisplay = (TextView) convertView.findViewById(R.id.tv_displayFood);
            holder.DeleteButtonView = (ImageView) convertView.findViewById(R.id.iv_foodDeleteView);
            convertView.setTag(holder);
        }
        else {
            //Log.d("ABG", "in convertview else");
            holder = (ViewHolder) convertView.getTag();
        }
        holder.FoodDisplay.setText(ListData.get(position).getText());

        ImageView aImg = (ImageView) convertView.findViewById(R.id.iv_foodDeleteView);
        aImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Delete button clicked. Remove the Food Item.
                ((AddMealActivity) context).removeFoodFromMeal(position);
            }
        });
        return convertView;
    }
}
