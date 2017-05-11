package ie.ul.collegetimekeeper.Objects;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ie.ul.collegetimekeeper.R;

/**
 * Created by Patryk on 01/05/2017.
 */

public class MenuCustomAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private User user;
    Work work;
    ArrayList<Work> workList = new ArrayList<>();

    private class ViewHolder {
        TextView textView1;
        TextView textView2;
        TextView textView3;
    }

    public MenuCustomAdapter(Context context, ArrayList<Work> workList) {
        inflater = LayoutInflater.from(context);
        this.user = user;
       // int counter = getCount();
        this.workList = new ArrayList<>();
        this.workList = workList;

    }

    public int getCount() {
        return workList.size();
    }

    public Work getItem(int position) {
        return workList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.time_listview, null);
            holder.textView1 = (TextView) convertView.findViewById(R.id.module);
            holder.textView2 = (TextView) convertView.findViewById(R.id.work);
            holder.textView3 = (TextView) convertView.findViewById(R.id.time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView1.setText(workList.get(position).getModule());
        holder.textView2.setText(workList.get(position).getTypeOfWork());
        holder.textView3.setText(workList.get(position).getTimeSpent()+ "");
        return convertView;
    }
}