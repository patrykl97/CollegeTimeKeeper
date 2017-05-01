package ie.ul.collegetimekeeper.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.Serializable;

import ie.ul.collegetimekeeper.Objects.DrawerListAdapter;
import ie.ul.collegetimekeeper.Objects.DrawerNav;
import ie.ul.collegetimekeeper.Objects.Module;
import ie.ul.collegetimekeeper.Objects.User;
import ie.ul.collegetimekeeper.R;


import static ie.ul.collegetimekeeper.Activities.TimerActivity.tag;

public class WorkSelectionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner moduleSpinner;
    Spinner workSpinner;
    TextView moduleTextview;
    TextView workTextview;
    Button startWork;
    ListView mDrawerList;
    RelativeLayout mDrawerPane;
    DrawerNav drawerNav;
    static User user;
    static String [] modulesArray;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_selection);

        user = (User)getIntent().getSerializableExtra("user");
        modulesArray = new String[user.getModulesList().size()];
        Log.i(tag, "---------------------------------------------------------------: modules size: " + user.getModulesList().size());
        for(int i = 0; i < user.getModulesList().size(); i++){
            modulesArray[i] = user.getModulesList().get(i).getModuleID();
        }
        drawerNav = new DrawerNav(this, user);

        mDrawerPane = (RelativeLayout) findViewById(R.id.drawerPane);
        mDrawerList = (ListView) findViewById(R.id.navList);
        DrawerListAdapter adapter = drawerNav.getAdapter();
        mDrawerList.setAdapter(adapter);

        // Drawer Item click listeners
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                drawerNav.selectItemFromDrawer(position);
            }
        });

        moduleTextview = (TextView)findViewById(R.id.moduleCodeTextview);
        workTextview = (TextView)findViewById(R.id.workTypeTextview);
        moduleSpinner = (Spinner)findViewById(R.id.moduleSpinner);
        workSpinner = (Spinner)findViewById(R.id.workSpinner);
        startWork = (Button)findViewById(R.id.startWorkButton);

        ArrayAdapter<String> adapterModules = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, modulesArray);
        ArrayAdapter<CharSequence> adapterWork = ArrayAdapter.createFromResource(this, R.array.work_types, android.R.layout.simple_spinner_dropdown_item);
        moduleSpinner.setAdapter(adapterModules);
        workSpinner.setAdapter(adapterWork);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Log.i(tag, moduleSpinner.getSelectedItem().toString() +" -----------------------------");
            user.getModulesList().get(position).addToWorkList(workSpinner.getSelectedItem().toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    protected void onClickStartWork(View v){
        Intent i = new Intent(this, TimerActivity.class);
        position = moduleSpinner.getSelectedItemPosition();
        user.getModulesList().get(position).setWork(workSpinner.getSelectedItem().toString());
        i.putExtra("user", (Serializable) user);
        i.putExtra("position", position);
        this.startActivity(i);
    }
}
