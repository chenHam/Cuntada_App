package com.example.chen.cuntada_app.app.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.chen.cuntada_app.app.MyApplication;
import com.example.chen.cuntada_app.app.R;
import com.example.chen.cuntada_app.app.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

public class AllActivity extends AppCompatActivity {


    Button recipesButton;
    Button logOutButton;
    Button myDetailsButton;
    Button myRecipesButton;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all);

        firebaseAuth = FirebaseAuth.getInstance();


        Log.d("Tokyo", "AllActivity");

        Log.d("Tokyo", "before myApplication");
        MyApplication myApplication = new MyApplication();
        if(MyApplication.context == null){
            Log.d("Tokyo", "context is null");
        }
        MyApplication.context = getApplicationContext();
        if(MyApplication.context == null){
            Log.d("Tokyo", "context is **still** null");
        }
        Log.d("Tokyo", "after myApplication");

        myDetailsButton = (Button) findViewById(R.id.myDetailsButton);
        myDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Tokyo", "myDetailsButton clicked");
                startActivity(new Intent(getApplicationContext(), MyDetails.class));
            }
        });

        final Intent intent = new Intent(getApplicationContext(), RecipesActivity.class);

        recipesButton = (Button) findViewById(R.id.recipesButton);
        recipesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(getApplicationContext(), RecipesActivity.class));
                intent.putExtra("showMyRecipes", false);
                startActivity(intent);
            }
        });

        myRecipesButton = (Button) findViewById(R.id.myRecipesButton);
        myRecipesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("showMyRecipes", true);
                //startActivity(new Intent(getApplicationContext(), RecipesActivity.class));
                startActivity(intent);
            }
        });

        logOutButton = (Button) findViewById(R.id.logOutButton);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Tokyo", "Logout button has been clicked");
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));

//                MyApplication.sharedPref.edit().putBoolean("logout", true).apply();
            }
        });

    }

}
