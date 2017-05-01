package ie.ul.collegetimekeeper.Objects;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.io.Serializable;
import java.util.ArrayList;

import ie.ul.collegetimekeeper.Activities.MenuActivity;
import ie.ul.collegetimekeeper.Activities.ModulesActivity;
import ie.ul.collegetimekeeper.Activities.TimerActivity;
import ie.ul.collegetimekeeper.Activities.WorkSelectionActivity;
import ie.ul.collegetimekeeper.R;

import static android.R.attr.switchMinWidth;
import static android.R.attr.tag;

/**
 * Created by Patryk on 29/04/2017.
 */

public class DrawerNav {
    Context context;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    User user;
    DrawerListAdapter adapter;
    Intent i;
    String tag = "ie.ul.collegetimekeeper";

    ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();

    public DrawerNav(Context context, User user){
        this.context = context;
        this.user = user;
        addItems();
        adapter = new DrawerListAdapter(context, mNavItems);
    }

    public void addItems(){
        mNavItems.add(new NavItem("Home", "Meetup destination", R.drawable.ic_action_home));
        mNavItems.add(new NavItem("Time", "Change your preferences", R.drawable.ic_action_time));
        mNavItems.add(new NavItem("About", "Get to know about us", R.drawable.ic_action_about));
    }

    public DrawerListAdapter getAdapter(){
        return adapter;
    }

    public void selectItemFromDrawer(int position) {
        switch(position) {
            case 0:
                Log.i(tag, "Home selected---------------");
                Intent i = new Intent(context, MenuActivity.class);
                i.putExtra("user", (Serializable) user);
                context.startActivity(i);
                break;
            case 1:
                Log.i(tag, "Timer selected---------------");
                i = new Intent(context, WorkSelectionActivity.class);
                i.putExtra("user", (Serializable) user);
                context.startActivity(i);
                break;

            case 2:

                break;
        }
    }


}
