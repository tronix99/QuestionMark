package com.arx_era.rentmyvehicle;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.arx_era.rentmyvehicle.Model.User;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

public class LoginRegisterActivity extends AppCompatActivity {

    SignInButton gbtn;
    FirebaseAuth mAuth;
    FirebaseDatabase db;
    DatabaseReference users;
    Button sbtn,rbtn;
    RelativeLayout rootLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        sbtn = (Button) findViewById(R.id.sbtn);
        sbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoginDialog();
            }
        });
        rbtn = (Button) findViewById(R.id.rbtn);
        rbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRegisterDialog();
            }
        });
        rootLayout = (RelativeLayout) findViewById(R.id.rootLayout);

        //init firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users");
    }

    private void showLoginDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("SIGN IN");
        dialog.setMessage("Please Use Email To Sign In");
        final ProgressDialog progress = new ProgressDialog(this);

        LayoutInflater inflater = LayoutInflater.from(this);
        View signin_layout = inflater.inflate(R.layout.layout_sign_in,null);

        final MaterialEditText email = signin_layout.findViewById(R.id.editEmail);
        final MaterialEditText password = signin_layout.findViewById(R.id.editPass);

        dialog.setView(signin_layout);

        //Set Buttons
        dialog.setPositiveButton("SIGIN IN", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();

                        progress.setMessage("Loading :) ");
                        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        progress.setIndeterminate(true);
                        progress.show();
                        //Check Validation
                        if (TextUtils.isEmpty(email.getText().toString())) {
                            Snackbar.make(rootLayout, "Enter EmailId", Snackbar.LENGTH_SHORT)
                                    .show();
                            return;
                        }
                        if (TextUtils.isEmpty(password.getText().toString())) {
                            Snackbar.make(rootLayout, "Enter Passeord", Snackbar.LENGTH_SHORT)
                                    .show();
                            return;
                        }
                        if (password.getText().toString().length() < 8) {
                            Snackbar.make(rootLayout, "Password to short", Snackbar.LENGTH_SHORT)
                                    .show();
                            return;
                        }

                        //Login
                        mAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                             startActivity(new Intent(LoginRegisterActivity.this, MainActivity.class));
                                             finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Snackbar.make(rootLayout, "Error" + e.getMessage(), Snackbar.LENGTH_SHORT)
                                        .show();
                            }
                        });
                    }
                });

        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        dialog.show();
    }

    private void showRegisterDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Register");
        dialog.setMessage("Please Use Email To Register");

        LayoutInflater inflater = LayoutInflater.from(this);
        View register_layout = inflater.inflate(R.layout.layout_register,null);

        final MaterialEditText email = register_layout.findViewById(R.id.editEmail);
        final MaterialEditText password = register_layout.findViewById(R.id.editPass);
        final MaterialEditText name = register_layout.findViewById(R.id.editName);
        final MaterialEditText phone = register_layout.findViewById(R.id.editPhone);

        dialog.setView(register_layout);

        //Set Buttons
        dialog.setPositiveButton("Register", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

                //Check Validation
                if(TextUtils.isEmpty(email.getText().toString())){
                    Snackbar.make(rootLayout,"Enter EmailId",Snackbar.LENGTH_SHORT)
                            .show();
                    return;
                }
                if(TextUtils.isEmpty(phone.getText().toString())){
                    Snackbar.make(rootLayout,"Enter Phone",Snackbar.LENGTH_SHORT)
                            .show();
                    return;
                }
                if(TextUtils.isEmpty(password.getText().toString())){
                    Snackbar.make(rootLayout,"Enter Password",Snackbar.LENGTH_SHORT)
                            .show();
                    return;
                }
                if(password.getText().toString().length() < 8){
                    Snackbar.make(rootLayout,"Password to short",Snackbar.LENGTH_SHORT)
                            .show();
                    return;
                }

                //Register User user
                mAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                //save user to db
                                User user = new User();
                                user.setEmail(email.getText().toString());
                                user.setPassword(password.getText().toString());
                                user.setName(name.getText().toString());
                                user.setPhone(phone.getText().toString());

                                users.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Snackbar.make(rootLayout,"Registered",Snackbar.LENGTH_SHORT)
                                                        .show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Snackbar.make(rootLayout,"Registration failed",Snackbar.LENGTH_SHORT)
                                                        .show();
                                            }
                                        });
                            }
                        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(rootLayout,"Registration failed",Snackbar.LENGTH_SHORT)
                                .show();
                    }
                });
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
