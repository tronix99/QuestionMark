package com.arx_era.rentmyvehicle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.arx_era.rentmyvehicle.Adapters.profile_list;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class Profile extends Fragment {

    FirebaseAuth mAuth;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    //view objects
    private TextView textViewUserEmail,textViewUserName,textViewUserPhone;
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
        textViewUserName = (TextView) view.findViewById(R.id.textViewUserName);
        textViewUserPhone = (TextView) view.findViewById(R.id.textViewUserPhone);
        buttonLogout = (Button) view.findViewById(R.id.buttonLogout);

        //displaying logged in user name
        textViewUserEmail.setText(user.getEmail());
        textViewUserName.setText("?"+user.getDisplayName());
        textViewUserPhone.setText(user.getPhoneNumber());

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

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        List<String> list_profile = new ArrayList<>();
        list_profile.add("Test");
        list_profile.add("Test");
        list_profile.add("Test");
        mAdapter = new profile_list(list_profile);
        recyclerView.setAdapter(mAdapter);

        return view;
    }
}
