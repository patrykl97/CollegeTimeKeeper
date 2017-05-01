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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import ie.ul.collegetimekeeper.Functions.AddWorkRequest;
import ie.ul.collegetimekeeper.Objects.DrawerListAdapter;
import ie.ul.collegetimekeeper.Objects.DrawerNav;
import ie.ul.collegetimekeeper.Objects.User;
import ie.ul.collegetimekeeper.R;

import static android.R.attr.format;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static android.support.v7.appcompat.R.id.list_item;

public class TimerActivity extends AppCompatActivity {

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
    static int position;
    static boolean reset;
    int mins;
    int secs;
    int milliseconds;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        user = (User)getIntent().getSerializableExtra("user");
        position = getIntent().getIntExtra("position", 0);

        timerValue = (TextView) findViewById(R.id.timerValue);
        moduleCode = (TextView) findViewById(R.id.moduleCodeTextview);
        workType = (TextView) findViewById(R.id.workTypeTextview);
        moduleCode.setText(user.getModulesList().get(position).getModuleID());
        workType.setText(user.getModulesList().get(position).getWork().getTypeOfWork());
        startButton = (ImageButton) findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                reset = false;
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
                mins = 0;
                secs = 0;
                milliseconds = 0;
                customHandler.removeCallbacks(updateTimerThread);
                timerValue.setText(String.format("%02d", mins) + ":"
                        + String.format("%02d", secs) + ":"
                        + String.format("%03d", milliseconds));
            }
        });
        saveButton = (Button)findViewById(R.id.saveTimeButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customHandler.removeCallbacks(updateTimerThread);
                String minutes = timerValue.getText().toString();
                user.getModulesList().get(position).getWork().setTimeSpent(Integer.parseInt(minutes.substring(0,2)));
                Log.i(tag, "-------------------------------------------------: mins " + Integer.parseInt(minutes.substring(0,2)));
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                Intent i = new Intent(TimerActivity.this, MenuActivity.class);
                                i.putExtra("user", (Serializable) user);
                                startActivity(i);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                AddWorkRequest addWorkRequest = new AddWorkRequest(user, position, listener);
                RequestQueue queue = Volley.newRequestQueue(TimerActivity.this);
                queue.add(addWorkRequest);
            }
        });
    }




    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeSwapBuff + timeInMilliseconds;
            int secs = (int) (updatedTime / 1000);
            mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            timerValue.setText(String.format("%02d", mins) + ":"
                            + String.format("%02d", secs) + ":"
                            + String.format("%03d", milliseconds));
            customHandler.postDelayed(this, 0);
        }
    };


}
