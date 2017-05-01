package ie.ul.collegetimekeeper.Objects;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ie.ul.collegetimekeeper.R;

import static android.R.id.list;

/**
 * Created by Patryk on 30/04/2017.
 */

public class MyCustomAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<String> modules = new ArrayList<String>();
    private Context context;
    String tag = "ie.ul.collegetimekeeper";



    public MyCustomAdapter(ArrayList<String> modules, Context context) {
        this.modules = modules;
        this.context = context;
    }

    @Override
    public int getCount() {
        return modules.size();
    }

    @Override
    public Object getItem(int pos) {
        return modules.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
        //just return 0 if your list items do not have an Id variable.
    }

    //@Override
    public String getModuleId(int pos) {
        return modules.get(pos);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
       // if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.module_activity_listview, null);
       // }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.label);
        listItemText.setText(modules.get(position));

        //Handle buttons and add onClickListeners
        ImageButton deleteBtn = (ImageButton)view.findViewById(R.id.btnRemoveModule);

        //View.setTag(listItemText, deleteBtn);

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                //list.remove(position); //or some other task
                //notifyDataSetChanged();
                Log.i(tag, "Delete clicked---------------------------------------");

            }
        });

        return view;
    }
}