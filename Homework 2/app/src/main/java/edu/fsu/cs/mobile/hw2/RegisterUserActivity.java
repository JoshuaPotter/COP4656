package edu.fsu.cs.mobile.hw2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class RegisterUserActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private EditText confirm_password;
    private EditText username;
    private TextView classes_label;
    private Spinner classes;
    private RadioGroup role;
    private CheckBox terms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        // setup variables
        email = (EditText) findViewById(R.id.editText_email);
        password = (EditText) findViewById(R.id.editText_password);
        confirm_password = (EditText) findViewById(R.id.editText_confirm_password);
        username = (EditText) findViewById(R.id.editText_name);
        classes_label = (TextView) findViewById(R.id.textView_class);
        classes = (Spinner) findViewById(R.id.spinner_classes);
        role = (RadioGroup) findViewById(R.id.radioGroup_role);
        terms = (CheckBox) findViewById(R.id.checkBox_terms);

        // setup classes dropdown
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.classes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classes.setAdapter(adapter);
    }

    public void removeErrors(View view) {
        email.setError(null);
        password.setError(null);
        confirm_password.setError(null);
        username.setError(null);
        classes_label.setError(null);
        ((RadioButton) findViewById(R.id.radioButton_instructor)).setError(null);
        terms.setError(null);
    }

    public void resetForm(View view) {
        email.setText("");
        password.setText("");
        confirm_password.setText("");
        username.setText("");
        classes.setSelection(0);
        role.clearCheck();
        terms.setChecked(false);
        removeErrors(view);
    }

    public void registerUser(View view) {
        boolean error = false;

        String email_val = email.getText().toString();
        String password_val = password.getText().toString();
        String confirm_password_val = confirm_password.getText().toString();
        String username_val = username.getText().toString();
        String classes_val = classes.getSelectedItem().toString();
        String role_val = "Joshu";
        boolean terms_val = terms.isChecked();

        if(email_val.equals("")) {
            // did not enter username
            error = true;
            email.setError("Required");
        } else {
            email.setError(null);
        }

        if(password_val.equals("")) {
            // did not enter password
            error = true;
            password.setError("Required");
        } else {
            password.setError(null);
        }

        if(confirm_password_val.equals("")) {
            // did not enter confirm password
            error = true;
            confirm_password.setError("Required");
        } else {
            confirm_password.setError(null);
        }

        if(classes_val.equals("")) {
            // no class selected
            error = true;

            classes_label.setError("Required");
        } else {
            classes_label.setError(null);
        }

        if(role.getCheckedRadioButtonId() == -1) {
            // did not select role
            error = true;
            ((RadioButton) findViewById(R.id.radioButton_instructor)).setError("Required");


            if(username_val.equals("")) {
                // did not enter name
                error = true;
                username.setError("Required");
            } else {
                username.setError(null);
            }
        } else {
            ((RadioButton) findViewById(R.id.radioButton_instructor)).setError(null);
            role_val = ((RadioButton) findViewById(role.getCheckedRadioButtonId())).getText().toString();

            if(role_val.equals("Student")) {
                // student

                // only check name if student
                if(username_val.equals("")) {
                    // did not enter name
                    error = true;
                    username.setError("Required");
                } else {
                    username.setError(null);
                }
            } else {
                username.setError(null);
                // instructor
            }
        }

        if(terms_val == false) {
            // did not agree to terms
            error = true;
            terms.setError("Required");
        } else {
            terms.setError(null);
        }

        if(error == false) {
            // process form if no errors found

            // check if email exists
            ArrayAdapter<String> adapter;
            String[] adminEmails = getResources().getStringArray(R.array.admins);
            for(int i = 0; i < adminEmails.length; i++) {
                if(email_val.equals(adminEmails[i])) {
                    error = true;
                }
            }

            if(error == false) {
                email.setError(null);

                if(!password_val.equals(confirm_password_val)) {
                    // passwords do not match
                    error = true;
                    password.setError("Passwords do not match");
                    confirm_password.setError("Passwords do not match");
                } else {
                    password.setError(null);
                    confirm_password.setError(null);

                    // send intent
                    Intent displayUser = new Intent(this, DisplayUserActivity.class);

                    Bundle bundle = new Bundle();
                    bundle.putString("email", email_val);
                    bundle.putString("password", password_val);
                    bundle.putString("name", username_val);
                    bundle.putString("classes", classes_val);
                    bundle.putString("role", role_val);

                    displayUser.putExtras(bundle);
                    startActivity(displayUser);
                }
            } else {
                // email exists
                email.setError("Email already exists.");
            }
        }

//        System.out.println(email_val);
//        System.out.println(password_val);
//        System.out.println(confirm_password_val);
//        System.out.println(username_val);
//        System.out.println(classes_val);
//        System.out.println(role_val);
//        System.out.println(terms_val);
    }
}
