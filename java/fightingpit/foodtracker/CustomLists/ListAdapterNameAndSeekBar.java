package fightingpit.foodtracker.CustomLists;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.List;

import fightingpit.foodtracker.R;

/**
 * Created by AG on 29-Sep-15.
 */

public class ListAdapterNameAndSeekBar extends BaseAdapter {

    Context context;
    protected List<GenericContainer> ListData;
    private LayoutInflater inflater;
    //ViewHolder holder;
    View atempView;
    int sympVal;

    public ListAdapterNameAndSeekBar(Context iContext, List<GenericContainer> iListData){
        this.ListData = iListData;
        this.inflater = LayoutInflater.from(iContext);
        this.context = iContext;
    }

    private static class ViewHolder {
        TextView NameDisplay;
        TextView ValueDisplay;
        SeekBar SeekBarDisplay;
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
        atempView = convertView;
        final ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_name_and_seekbar, null);
            holder.NameDisplay = (TextView) convertView.findViewById(R.id.tv_lv_symptom_name);
            holder.ValueDisplay = (TextView) convertView.findViewById(R.id.tv_lv_symp_current_value);
            holder.SeekBarDisplay = (SeekBar)  convertView.findViewById(R.id.sb_symptom_in_add_meal);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.NameDisplay.setText(ListData.get(position).getStringParam1());
        holder.ValueDisplay.setText(ListData.get(position).getStringParam2());
        holder.SeekBarDisplay.setProgress(ListData.get(position).getIntParam1());

        holder.SeekBarDisplay.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
                //((AddMealActivity) context).updateSymptomValue(position, sympVal);
            }


            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }


            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // TODO Auto-generated method stub
                // To convert it as discrete value
                holder.ValueDisplay.setText(String.valueOf(progress));
                sympVal = progress;
            }
        });
        return convertView;
    }
}
