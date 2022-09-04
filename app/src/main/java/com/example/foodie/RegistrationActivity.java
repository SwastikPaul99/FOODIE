package com.example.foodie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity{
    EditText username,email,password,confpassword;
    Button registerbtn;
    TextView haveanaccnt,registerlogo ;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        registerlogo = findViewById(R.id.logo);
        username = findViewById(R.id.username);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        confpassword=findViewById(R.id.confirmpassword);
        registerbtn=findViewById(R.id.registerbutton);
        haveanaccnt=findViewById(R.id.haveanaccount);

        firebaseAuth=FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null)
        {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
        haveanaccnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });
        registerbtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 String mail=email.getText().toString().trim();
                 String pwd=password.getText().toString().trim();

                 if(TextUtils.isEmpty(mail))
                 {
                     email.setError("Email is required");
                     return;
                 }
                 if(TextUtils.isEmpty(pwd))
                 {
                     password.setError("Password is Required");
                     return;
                 }
                 if(password.length()<6)
                 {
                     password.setError("Minimum 6 letters are Required");
                     return;
                 }
                 firebaseAuth.createUserWithEmailAndPassword(mail,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                     @Override
                     public void onComplete(@NonNull Task<AuthResult> task) {
                         if(task.isSuccessful())
                         {
                             Toast.makeText(RegistrationActivity.this,"User Created Successfully",Toast.LENGTH_SHORT).show();
                             startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                         }
                         else
                         {
                             Toast.makeText(RegistrationActivity.this,"Some Error Occured"+task.getException(),Toast.LENGTH_SHORT).show();
                         }
                     }
                 });
             }
         });

    }
}