package edu.fsu.cs.mobile.hw5;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    private LoginFragment loginFragment;
    private RegisterFragment registerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instantiate LoginFragment
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        loginFragment = new LoginFragment();
        transaction.add(R.id.frameLayout_login, loginFragment, LoginFragment.TAG);
        registerFragment = new RegisterFragment();
        transaction.add(R.id.frameLayout_register, registerFragment, RegisterFragment.TAG);
        transaction.commit();

    }

    @Override
    public void onBackPressed() {
        // Override back button to traverse through FragmentManager until no fragments in stack
        //    otherwise restore native functionality
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    public void registerUser(View view) {
        registerFragment.registerUser(view);
    }

    public void resetRegistrationForm(View view) {
        registerFragment.resetRegistrationForm(view);
    }

    public void loginUser(View view) {
        loginFragment.loginUser(view);
    }
}
