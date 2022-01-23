package uni.fmi.masters.mybuisnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    EditText usernameET;
    EditText passwordET;
    TextView registerTV;
    CheckBox rememberMeCB;
    Button loginB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameET = findViewById(R.id.usernameEditText);
        passwordET = findViewById(R.id.passwordEditText);
        registerTV = findViewById(R.id.registerTextView);
        rememberMeCB = findViewById(R.id.rememberCheckBox);
        loginB = findViewById(R.id.loginButton);

    }
}