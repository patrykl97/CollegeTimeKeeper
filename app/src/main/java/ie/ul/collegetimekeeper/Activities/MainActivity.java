package ie.ul.collegetimekeeper.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ie.ul.collegetimekeeper.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickSignIn(View v) {
        Button button = (Button) v;
        Intent i = new Intent(this, LogInActivity.class);
        startActivity(i);
    }

    public void onClickSignUp(View v) {
        Button button = (Button) v;
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }
}
