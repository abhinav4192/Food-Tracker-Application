package fightingpit.foodtracker.CustomLists;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import fightingpit.foodtracker.R;


/**
 * Created by AG on 11-Sep-15.
 */
public class ListAdapterSingleElement extends BaseAdapter {

    Context context;
    protected List<ListSingleElement> ListData;
    private LayoutInflater inflater;

    public ListAdapterSingleElement(Context iContext, List<ListSingleElement> iListData){
        this.ListData = iListData;
        this.inflater = LayoutInflater.from(iContext);
        this.context = iContext;
    }

    private class ViewHolder {
        TextView FoodDisplay;
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
            convertView = inflater.inflate(R.layout.list_single_element, null);
            holder.FoodDisplay = (TextView) convertView.findViewById(R.id.tv_element_text);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.FoodDisplay.setText(ListData.get(position).getText());
        return convertView;
    }
}
