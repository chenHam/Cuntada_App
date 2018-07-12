package com.example.chen.cuntada_app.app.View;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.chen.cuntada_app.app.MainActivity;
import com.example.chen.cuntada_app.app.R;
import com.google.firebase.auth.FirebaseAuth;

//package com.example.chen.cuntada_app.app;
//
//import android.app.Activity;
//import android.app.Fragment;
//import android.app.FragmentTransaction;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.Spinner;
//import android.widget.Toast;
//
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ServerValue;
//import com.google.firebase.database.ValueEventListener;
//
//import org.w3c.dom.Text;
//
//import java.util.HashMap;
//
//
public class RecipesActivity extends AppCompatActivity{

    // auth check if logged in

    //private FirebaseAuth firebaseAuth;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    String userId = firebaseAuth.getCurrentUser().getUid();

    private BroadcastReceiver receiver;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        boolean showMyRecipes = getIntent().getBooleanExtra("showMyRecipes", false);

        if (savedInstanceState == null) {
            if(!showMyRecipes){
                RecipesListFragment fragment = new RecipesListFragment();
                FragmentTransaction tran = getSupportFragmentManager().beginTransaction();
                tran.add(R.id.main_container, fragment);
                //tran.addToBackStack("");
                tran.commit();
            } else {
                MyRecipesFragment fragment =  new MyRecipesFragment();
                FragmentTransaction tran = getSupportFragmentManager().beginTransaction();
                tran.add(R.id.main_container, fragment);
                //tran.addToBackStack("");
                tran.commit();
            }

        }

        IntentFilter filter = new IntentFilter();
        filter.addAction("EDIT_RECIPE");

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                String name = intent.getStringExtra("name");
                String category = intent.getStringExtra("cateogry");
                String ingredients = intent.getStringExtra("ingredients");
                String instructions = intent.getStringExtra("instructions");
                Bitmap avatar = (Bitmap) intent.getExtras().get("avatar");

                Log.d("Tokyo", name);
                Log.d("Tokyo", category);
                Log.d("Tokyo", ingredients);
                Log.d("Tokyo", instructions);

                Bundle bundle = new Bundle();
                bundle.putString("name", name);
                bundle.putString("category", category);
                bundle.putString("ingredients", ingredients);
                bundle.putString("instructions", instructions);
                bundle.putParcelable("avatar", avatar);

                EditRecipeFragment fragment = new EditRecipeFragment();
                fragment.setArguments(bundle);
                FragmentTransaction tran = getSupportFragmentManager().beginTransaction();
                tran.replace(R.id.main_container, fragment);
                tran.addToBackStack("tag");
                tran.commit();

                //getSupportFragmentManager().popBackStack();
            }
        };
        registerReceiver(receiver, filter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
//            case R.id.menu_add:
            case R.id.addRecipeItem:
                Log.d("TAG","menu add selected");
                NewRecipeFragment fragment = new NewRecipeFragment();
                FragmentTransaction tran = getSupportFragmentManager().beginTransaction();
                tran.replace(R.id.main_container, fragment);
                tran.addToBackStack("tag");
                tran.commit();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }
        super.onDestroy();
    }

}