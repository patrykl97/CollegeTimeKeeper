package ie.ul.collegetimekeeper.Objects;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.Serializable;
import java.util.ArrayList;
import ie.ul.collegetimekeeper.Activities.MenuActivity;
import ie.ul.collegetimekeeper.Activities.WorkSelectionActivity;
import ie.ul.collegetimekeeper.Functions.WorkRequest;
import ie.ul.collegetimekeeper.R;

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
    ArrayList<Work> workList = new ArrayList<>();

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
        mNavItems.add(new NavItem("Exit", "Exit the app", R.drawable.ic_exit));
    }

    public DrawerListAdapter getAdapter(){
        return adapter;
    }

    public void selectItemFromDrawer(int position) {
        switch(position) {
            case 0:

                Response.Listener<String> getWorkListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject js = new JSONObject(response);

                            JSONArray array = js.getJSONArray("array");
                            for(int i = 0; i < array.length(); i++){
                                // for(int j = 0; j < array.getJSONArray(i).length(); j++){
                                String a = array.getJSONArray(i).getString(0);
                                String b = array.getJSONArray(i).getString(1);
                                int c = array.getJSONArray(i).getInt(2);

                                workList.add(new Work(a, b, c));
                                // }

                                Intent in = new Intent(context, MenuActivity.class);
                                in.putExtra("user", (Serializable) user);
                                in.putExtra("work", (Serializable) workList);
                                context.startActivity(in);

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                WorkRequest workRequest = new WorkRequest(user, getWorkListener);
                RequestQueue queue = Volley.newRequestQueue(context);
                queue.add(workRequest);
        break;
            case 1:
                Log.i(tag, "Timer selected---------------");
                i = new Intent(context, WorkSelectionActivity.class);
                i.putExtra("user", (Serializable) user);
                context.startActivity(i);
                break;

            case 2:
                System.exit(0);
                break;
        }
    }


}
