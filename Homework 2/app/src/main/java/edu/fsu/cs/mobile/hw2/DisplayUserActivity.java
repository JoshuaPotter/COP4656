package edu.fsu.cs.mobile.hw2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DisplayUserActivity extends AppCompatActivity {
    private TextView email;
    private TextView password;
    private TextView name;
    private TextView classes;
    private TextView role;
    private Button passwordButton;
    private String passwordVal;
    private boolean showPassword;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_user);

        email = (TextView) findViewById(R.id.textView_email);
        password = (TextView) findViewById(R.id.textView_password);
        name = (TextView) findViewById(R.id.textView_name);
        classes = (TextView) findViewById(R.id.textView_class);
        role = (TextView) findViewById(R.id.textView_role);
        passwordButton = (Button) findViewById(R.id.button_showPassword);
        showPassword = false;

        Intent intent = getIntent();
        bundle = intent.getExtras();

        if(bundle != null) {
            email.setText(bundle.getString("email"));
            password.setText("********");
            classes.setText(bundle.getString("classes"));
            role.setText(bundle.getString("role"));

            if(bundle.getString("name").toString().equals("")) {
                name.setText("n/a");
            } else {
                name.setText(bundle.getString("name"));
            }
        }
    }

    public void showPassword(View view) {
        if(showPassword) {
            password.setText("********");
            passwordButton.setText("Show Password");
            showPassword = false;
        } else {
            password.setText(bundle.getString("password"));
            passwordButton.setText("Hide Password");
            showPassword = true;
        }
    }
}
