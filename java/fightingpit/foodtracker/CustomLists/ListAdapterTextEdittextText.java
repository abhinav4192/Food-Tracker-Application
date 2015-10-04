package fightingpit.foodtracker.CustomLists;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import fightingpit.foodtracker.R;

/**
 * Created by AG on 04-Oct-15.
 */
public class ListAdapterTextEdittextText extends BaseAdapter {

    Context context;
    protected List<GenericContainer> ListData;
    private LayoutInflater inflater;

    public ListAdapterTextEdittextText(Context iContext, List<GenericContainer> iListData){
        this.ListData = iListData;
        this.inflater = LayoutInflater.from(iContext);
        this.context = iContext;
    }

    private class ViewHolder {
        TextView text1;
        EditText editText1;
        TextView text2;
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
            convertView = inflater.inflate(R.layout.list_text_editext_text, null);
            holder.text1 = (TextView) convertView.findViewById(R.id.tv_latet_1);
            holder.editText1 = (EditText) convertView.findViewById(R.id.et_latet_1);
            holder.text2 = (TextView) convertView.findViewById(R.id.tv_latet_2);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.text1.setText(ListData.get(position).getStringParam1());
        holder.text2.setText(ListData.get(position).getStringParam2());
        return convertView;
    }
}
