package uni.fmi.masters.mybuisnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

public class RegisterActivity extends AppCompatActivity {

    ImageView avatarIV;
    EditText emailET;
    EditText firstNameET;
    EditText lastNameET;
    EditText passwordET;
    EditText repeatPasswordET;
    EditText otherGenderET;
    RadioGroup genderRG;

    Button registerB;
    Button cancelB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        avatarIV = findViewById(R.id.avatarImageView);
        emailET = findViewById(R.id.emailEditText);
        firstNameET = findViewById(R.id.firstNameEditText);
        lastNameET = findViewById(R.id.lastNameEditText);
        passwordET = findViewById(R.id.registerPasswordEditText);
        repeatPasswordET = findViewById(R.id.registerRepeatPasswordEditText);
        otherGenderET = findViewById(R.id.otherGenderEditText);
        genderRG = findViewById(R.id.genderRadioGroup);

        registerB = findViewById(R.id.registerButton);
        cancelB = findViewById(R.id.cancelButton);


    }
}