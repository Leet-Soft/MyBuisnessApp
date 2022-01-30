package uni.fmi.masters.mybuisnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import uni.fmi.masters.mybuisnessapp.db.BuissnessDBHelper;

public class LoginActivity extends AppCompatActivity {

    EditText usernameET;
    EditText passwordET;
    TextView registerTV;
    CheckBox rememberMeCB;
    Button loginB;

    BuissnessDBHelper db;
    int count = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameET = findViewById(R.id.usernameEditText);
        passwordET = findViewById(R.id.passwordEditText);
        registerTV = findViewById(R.id.registerTextView);
        rememberMeCB = findViewById(R.id.rememberCheckBox);
        loginB = findViewById(R.id.loginButton);

        loginB.setOnClickListener(onClickListener);

        db = new BuissnessDBHelper(this);

        registerTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String email = usernameET.getText().toString();
            String password = passwordET.getText().toString();

            if(db.checkCredentials(email, password)){
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_LONG).show();
                count--;

                if(count == 0){
                    finish();
                }
            }
        }
    };
}