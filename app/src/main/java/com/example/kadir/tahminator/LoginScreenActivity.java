package com.example.kadir.tahminator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.Toast;


public class LoginScreenActivity extends ActionBarActivity {

    private EditText editTextUsername, editTextPassword;
    LoginDataBaseAdapter loginDataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        // create a instance of SQLite Database
        loginDataBaseAdapter=new LoginDataBaseAdapter(this);
        loginDataBaseAdapter=loginDataBaseAdapter.open();

        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextSUPassword);

        // Load User Input
        SharedPreferences sp = getSharedPreferences("userinfo", Context.MODE_PRIVATE);


        String username = sp.getString("usernameInput", "");
        String password = sp.getString("passwordInput", "");

        if(username != "" && password != ""){

            //startActivity(new Intent(this, MainActivity.class));
        }


        // set listeners
        editTextUsername.addTextChangedListener(mTextWatcher);
        editTextPassword.addTextChangedListener(mTextWatcher);

        // run once to disable if empty
        checkFieldsForEmptyValues();
    }

    //  create a textWatcher member
    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // check Fields For Empty Values
            checkFieldsForEmptyValues();
        }
    };

    void checkFieldsForEmptyValues(){
        Button b = (Button) findViewById(R.id.buttonLogin);

        String s1 = editTextUsername.getText().toString();
        String s2 = editTextPassword.getText().toString();

        if(s1.equals("")|| s2.equals("")){
            b.setEnabled(false);
        } else {
            b.setEnabled(true);
        }
    }

    public void OnClickLogin(View view){

        // checking if login information is true
        String usernameInput = editTextUsername.getText().toString();
        String passwordInput = editTextPassword.getText().toString();

        // fetch the Password form database for respective user name
        String storedPassword = loginDataBaseAdapter.getSingleEntry(usernameInput);

        boolean isLoginSuccessful = passwordInput.equals(storedPassword);

        // check if the Stored password matches with  Password entered by user
        if(isLoginSuccessful)
        {
            Toast.makeText(LoginScreenActivity.this, "Congrats: Login Successfull", Toast.LENGTH_LONG).show();
            //dialog.dismiss();
        }
        else
        {
            Toast.makeText(LoginScreenActivity.this, "User Name or Password does not match", Toast.LENGTH_LONG).show();
        }

        // if so, check if checkBox is checked and store info
        boolean isChecked = ((CheckBox) findViewById(R.id.checkBox)).isChecked();

        if(isChecked && isLoginSuccessful){

            // saveUserInput
            SharedPreferences sp = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();

            editor.putString("usernameInput", usernameInput);
            editor.putString("passwordInput", passwordInput);
            editor.apply();

            // Start MainActivity
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }

    }

    public void checkboxClicked(View view){}

    public void signUpClicked(View view){
        /// Create Intent for SignUpActivity and Start The Activity
        Intent intentSignUP = new Intent(this,SignUPActivity.class);
        startActivity(intentSignUP);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close The Database
        loginDataBaseAdapter.close();
    }


}
