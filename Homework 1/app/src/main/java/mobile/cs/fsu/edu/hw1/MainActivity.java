package mobile.cs.fsu.edu.hw1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button updateButton;
    TextView name;
    EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        updateButton = (Button)
                findViewById(R.id.button_update);
        name = (TextView)
                findViewById(R.id.text_view);
        input = (EditText)
                findViewById(R.id.editText_name);
    }

    public void onClick(View view) {
        Toast.makeText(this, "Updating...", Toast.LENGTH_SHORT).show();

        name.setText(input.getText());
    }
}
