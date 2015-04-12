package com.example.kadir.tahminator;


import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUPActivity extends ActionBarActivity
{
    EditText editTextEmail,editTextPassword,editTextConfirmPassword;
    Button btnCreateAccount;

    LoginDataBaseAdapter loginDataBaseAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_screen);

        //To allow Up navigation with the app icon in the action bar
        // If your minSdkVersion is below 11 use:
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // If your minSdkVersion is 11 or higher use:
        //getActionBar().setDisplayHomeAsUpEnabled(true);

        // get Instance  of Database Adapter
        loginDataBaseAdapter=new LoginDataBaseAdapter(this);
        loginDataBaseAdapter=loginDataBaseAdapter.open();

        // Get Refferences of Views
        editTextEmail =(EditText)findViewById(R.id.editTextSUUsername);
        editTextPassword=(EditText)findViewById(R.id.editTextSUPassword);
        editTextConfirmPassword=(EditText)findViewById(R.id.editTextSUConfirmPassword);

        btnCreateAccount=(Button)findViewById(R.id.buttonCreateAccount);
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

                String userName= editTextEmail.getText().toString();
                String password=editTextPassword.getText().toString();
                String confirmPassword=editTextConfirmPassword.getText().toString();

                // check if any of the fields are vaccant
                if(userName.equals("")||password.equals("")||confirmPassword.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Field Vaccant", Toast.LENGTH_LONG).show();
                    return;
                }
                // check if both password matches
                if(!password.equals(confirmPassword))
                {
                    Toast.makeText(getApplicationContext(), "Password does not match", Toast.LENGTH_LONG).show();
                    return;
                }
                else
                {
                    // Save the Data in Database
                    loginDataBaseAdapter.insertEntry(userName, password);
                    Toast.makeText(getApplicationContext(), "Account Successfully Created ", Toast.LENGTH_LONG).show();
                }

                /// Create Intent for LoginScreenActivity and Start The Activity
                Intent i = new Intent(getApplicationContext(), LoginScreenActivity.class);
                startActivity(i);
            }
        });
    }
    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

        loginDataBaseAdapter.close();
    }


}
