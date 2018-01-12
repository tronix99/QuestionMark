package com.arx_era.rentmyvehicle;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profile extends Fragment {

    FirebaseAuth mAuth;

    //view objects
    private TextView textViewUserEmail;
    private Button buttonLogout;

    public Profile() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        //initializing firebase authentication object
        mAuth = FirebaseAuth.getInstance();

        //getting current user
        FirebaseUser user = mAuth.getCurrentUser();

        //initializing views
        textViewUserEmail = (TextView) view.findViewById(R.id.textViewUserEmail);
        buttonLogout = (Button) view.findViewById(R.id.buttonLogout);

        //displaying logged in user name
        textViewUserEmail.setText("Welcome "+user.getEmail());

        //adding listener to button
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    //if logout is pressed
                    if(view == buttonLogout){
                        //logging out the user
                        mAuth.signOut();
                        //closing activity
                        getActivity().finish();
                        //starting login activity
                        startActivity(new Intent(getActivity(), LoginRegisterActivity.class));
                    }
            }
        });

        return view;
    }
}
