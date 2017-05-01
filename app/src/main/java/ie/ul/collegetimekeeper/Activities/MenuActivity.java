package ie.ul.collegetimekeeper.Activities;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DrawableUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import ie.ul.collegetimekeeper.Objects.CustomObject;
import ie.ul.collegetimekeeper.Objects.DrawerListAdapter;
import ie.ul.collegetimekeeper.Objects.DrawerNav;
import ie.ul.collegetimekeeper.Objects.MenuCustomAdapter;
import ie.ul.collegetimekeeper.Objects.NavItem;
import ie.ul.collegetimekeeper.Objects.User;
import ie.ul.collegetimekeeper.Objects.Work;
import ie.ul.collegetimekeeper.R;

import static ie.ul.collegetimekeeper.Activities.ModulesActivity.user;

public class MenuActivity extends AppCompatActivity {

    ListView mDrawerList;
    RelativeLayout mDrawerPane;
    DrawerNav drawerNav;
    static User user;
    //static ArrayList<Work> workList;
    ListView listView;

    ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        user = (User)getIntent().getSerializableExtra("user");
       // workList = (ArrayList<Work>)getIntent().getSerializableExtra("work");
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

        //listView = (ListView)findViewById(R.id.listviewMenu);
       // MenuCustomAdapter customAdapter = new MenuCustomAdapter(getApplicationContext(), workList);
        //listView.setAdapter(customAdapter);

    }
}
