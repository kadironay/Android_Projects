package com.example.kadir.tahminator;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends ActionBarActivity {

    public FlyOutContainer root;
    IMainActivityListener activityCommander;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        this.root = (FlyOutContainer) this.getLayoutInflater().inflate(R.layout.flyout_screen, null);
        this.setContentView(root);


        //activityCommander = (MainActivityListener) getCallingActivity();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void toggleMenu(View v){
        this.root.toggleMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if (id == R.id.signOutIcon){
            activityCommander = new LoginScreenActivity();
            activityCommander.signOut(this);

            // Start the login screen activity
            Intent intent = new Intent(this, LoginScreenActivity.class);
            // finishing activity not to go back to this activity by back button
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public interface IMainActivityListener {
        public void signOut(Context c);
    }
}
