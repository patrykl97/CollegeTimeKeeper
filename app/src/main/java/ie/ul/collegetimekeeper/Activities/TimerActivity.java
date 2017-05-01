package ie.ul.collegetimekeeper.Activities;

import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;

import java.io.Serializable;

import ie.ul.collegetimekeeper.Objects.DrawerListAdapter;
import ie.ul.collegetimekeeper.Objects.DrawerNav;
import ie.ul.collegetimekeeper.Objects.User;
import ie.ul.collegetimekeeper.R;

import static android.support.v7.appcompat.R.id.list_item;

public class TimerActivity extends AppCompatActivity implements ListView.OnItemClickListener {

    ListView mDrawerList;
    RelativeLayout mDrawerPane;
    DrawerNav drawerNav;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    static User user;
    private ImageButton startButton;
    private ImageButton pauseButton;
    private Button resetButton;
    private Button saveButton;
    private TextView timerValue;
    TextView moduleCode;
    TextView workType;
    private long startTime = 0L;
    private Handler customHandler = new Handler();
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    static String tag = "ie.ul.collegetimekeeper";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        user = (User)getIntent().getSerializableExtra("user");

        drawerNav = new DrawerNav(this, user);
        mDrawerPane = (RelativeLayout) findViewById(R.id.drawerPane);
        mDrawerList = (ListView) findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);
        DrawerListAdapter adapter = drawerNav.getAdapter();
        mDrawerList.setAdapter(adapter);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                Log.d(tag, "onDrawerCOpened: " + getTitle());
                invalidateOptionsMenu();
                mDrawerList.bringToFront();
                mDrawerLayout.requestLayout();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                Log.d(tag, "onDrawerClosed: " + getTitle());

                invalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

       // mDrawerList.setOnItemClickListener(this);

        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        // Drawer Item click listeners
       /* mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(tag, "Clicked on the screen");
                drawerNav.selectItemFromDrawer(position);
            }
        });*/


        timerValue = (TextView) findViewById(R.id.timerValue);
        moduleCode = (TextView) findViewById(R.id.moduleCodeTextview);
        workType = (TextView) findViewById(R.id.workTypeTextview);
        startButton = (ImageButton) findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startTime = SystemClock.uptimeMillis();
                customHandler.postDelayed(updateTimerThread, 0);
            }
        });
        pauseButton = (ImageButton) findViewById(R.id.pauseButton);
        pauseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                timeSwapBuff += timeInMilliseconds;
                customHandler.removeCallbacks(updateTimerThread);
            }
        });
        resetButton = (Button)findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    public class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3){
            Log.i(tag, "Works in the DrawerItemClick-------------------------------");
        }
    }



    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeSwapBuff + timeInMilliseconds;
            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            timerValue.setText("" + mins + ":"
                            + String.format("%02d", secs) + ":"
                            + String.format("%03d", milliseconds));
            customHandler.postDelayed(this, 0);
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
