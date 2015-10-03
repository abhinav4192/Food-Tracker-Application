package fightingpit.foodtracker.CustomLists;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.List;

import fightingpit.foodtracker.AddMealActivity;
import fightingpit.foodtracker.R;

/**
 * Created by AG on 29-Sep-15.
 */

public class ListAdapterSymptomsInAddMeal extends BaseAdapter {

    Context context;
    protected List<ListSymptomsInAddMeal> ListData;
    private LayoutInflater inflater;
    //ViewHolder holder;
    View atempView;
    int sympVal;

    public ListAdapterSymptomsInAddMeal(Context iContext, List<ListSymptomsInAddMeal> iListData ){
        this.ListData = iListData;
        this.inflater = LayoutInflater.from(iContext);
        this.context = iContext;
    }

    private static class ViewHolder {
        TextView SymptomNameDisplay;
        TextView SymptomValueDisplay;
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
            //Log.d("ABG", "in convertview if");
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_symptoms_in_add_meal, null);
            holder.SymptomNameDisplay = (TextView) convertView.findViewById(R.id.tv_lv_symptom_name);
            holder.SymptomValueDisplay = (TextView) convertView.findViewById(R.id.tv_lv_symp_current_value);
            holder.SeekBarDisplay = (SeekBar)  convertView.findViewById(R.id.sb_symptom_in_add_meal);
            convertView.setTag(holder);
        }
        else {
            //Log.d("ABG", "in convertview else");
            holder = (ViewHolder) convertView.getTag();
        }
        holder.SymptomNameDisplay.setText(ListData.get(position).getName());
        holder.SymptomValueDisplay.setText(ListData.get(position).getDisplayValueInTextbar());
        holder.SeekBarDisplay.setProgress(ListData.get(position).getValueFromSeekbar());

        holder.SeekBarDisplay.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
                ((AddMealActivity) context).updateSymptomValue(position, sympVal);
            }


            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }


            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //Log.d("ABG","Progress Changed:"+String.valueOf(position));
                // TODO Auto-generated method stub
                // To convert it as discrete value
                holder.SymptomValueDisplay.setText(String.valueOf(progress));
                sympVal = progress;
            }
        });
        return convertView;
    }
}
