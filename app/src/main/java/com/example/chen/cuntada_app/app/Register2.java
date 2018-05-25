package com.example.chen.cuntada_app.app;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Register2 extends Fragment {
    private static final String TAG = MainActivity.class.getSimpleName();

    private DatabaseReference UsersDB;
    DatabaseReference ref;
    EditText weight;
    EditText height;
    RadioGroup rg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register_2, container, false);
//        super.onCreate(savedInstanceState);

        UsersDB = FirebaseDatabase.getInstance().getReference("users");

        weight = (EditText) view.findViewById(R.id.weight);
        height = (EditText) view.findViewById(R.id.height);
        rg = (RadioGroup) view.findViewById(R.id.gender);

        return view;
    }

    public void rbclick(View view){
        // get selected radio button from radioGroup
//        int selectedId = radioSexGroup.getCheckedRadioButtonId();

        // get selected radio button from radioGroup
        int selectedId = rg.getCheckedRadioButtonId();
        RadioButton rb =  (RadioButton) view.findViewById(selectedId);
        Toast.makeText(getActivity().getApplicationContext(),rb.getText(), Toast.LENGTH_LONG).show();

    }


}
