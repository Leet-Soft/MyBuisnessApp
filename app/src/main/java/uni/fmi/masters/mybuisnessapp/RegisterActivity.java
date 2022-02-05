package uni.fmi.masters.mybuisnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import uni.fmi.masters.mybuisnessapp.db.BuissnessDBHelper;
import uni.fmi.masters.mybuisnessapp.entity.UserEntity;

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
    BuissnessDBHelper db;
    Dialog dialog;
    String selectedAvatar;

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

        db = new BuissnessDBHelper(this);

        registerB.setOnClickListener(onClickListener);
        cancelB.setOnClickListener(onClickListener);

        genderRG.setOnCheckedChangeListener(checkedChangeListener);
        avatarIV.setOnClickListener(onAvatarClick);
    }

    private View.OnClickListener onAvatarClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dialog = new Dialog(RegisterActivity.this);

            dialog.setContentView(R.layout.avatar_selection);

            dialog.setCanceledOnTouchOutside(true);

            ImageView avatar1 = dialog.findViewById(R.id.avata1ImageView);
            ImageView avatar2 = dialog.findViewById(R.id.avata2ImageView);
            ImageView avatar3 = dialog.findViewById(R.id.avata3ImageView);
            ImageView avatar4 = dialog.findViewById(R.id.avata4ImageView);

            avatar1.setTag("avatar1");
            avatar2.setTag("avatar2");
            avatar3.setTag("avatar3");
            avatar4.setTag("avatar4");

            avatar1.setOnClickListener(dialogAvatarClickListener);
            avatar2.setOnClickListener(dialogAvatarClickListener);
            avatar3.setOnClickListener(dialogAvatarClickListener);
            avatar4.setOnClickListener(dialogAvatarClickListener);

            dialog.show();
        }
    };

    private View.OnClickListener dialogAvatarClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ImageView selectedImage = (ImageView) view;

            avatarIV.setImageDrawable(selectedImage.getDrawable());
            selectedAvatar = selectedImage.getTag().toString();

            dialog.hide();
        }
    };

    private RadioGroup.OnCheckedChangeListener checkedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int selectedId) {

            if(selectedId == R.id.otherRadioButton){
                otherGenderET.setVisibility(View.VISIBLE);
            }else{
                otherGenderET.setVisibility(View.GONE);
            }

        }
    };

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.cancelButton){
                finish();
            }else{
                String password = passwordET.getText().toString();
                String password2 = repeatPasswordET.getText().toString();

                if(password.length() > 0 && password.equals(password2)){

                    String email = emailET.getText().toString();
                    String firstName = firstNameET.getText().toString();
                    String lastName = lastNameET.getText().toString();

                    RadioButton rb = findViewById(genderRG.getCheckedRadioButtonId());
                    String gender = rb.getText().toString();

                    if(gender.equals("other")){
                        gender = otherGenderET.getText().toString();
                    }

                    String avatar = selectedAvatar;

                    UserEntity user = new UserEntity();
                    user.setAvatar(avatar);
                    user.setEmail(email);
                    user.setFirstName(firstName);
                    user.setLastName(lastName);
                    user.setPassword(password);
                    user.setGender(gender);

                    if(db.insertUser(user)){
                        finish();
                    }else
                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText(getApplicationContext(), "Password Missmatch", Toast.LENGTH_LONG).show();
                }


            }
        }
    };
}