package com.example.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.ktx.Firebase;


public class MainActivity extends AppCompatActivity {
    EditText txtNick,txtPassword,txtEmail;
    Button btngiris,btnkayıt;
    String nick,email,password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         mAuth= FirebaseAuth.getInstance();

        txtEmail=findViewById(R.id.txtEmail);
        txtNick=findViewById(R.id.txtNick);
        txtPassword=findViewById(R.id.txtPassword);


        btnkayıt=findViewById(R.id.btnkayıt);
        btnkayıt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nick=txtNick.getText().toString();
                email=txtEmail.getText().toString();
                password=txtPassword.getText().toString();

               if(!TextUtils.isEmpty(nick)&&!TextUtils.isEmpty(email)&&!TextUtils.isEmpty(password)){
                    mAuth.createUserWithEmailAndPassword(email,password)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                   // Toast.makeText(MainActivity.this, "kayıt yapıldı", Toast.LENGTH_SHORT).show();
                                    if (task.isSuccessful()) {
                                        Toast.makeText(MainActivity.this, "Kayıt yapıldı", Toast.LENGTH_SHORT).show();
                                    }else {
                                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                            // Kullanıcı zaten kayıtlı, dolayısıyla düzenleme yapılacak
                                            mAuth.signInWithEmailAndPassword(email, password)
                                                    .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                                            if (task.isSuccessful()) {
                                                                Toast.makeText(MainActivity.this, "Giriş yapıldı", Toast.LENGTH_SHORT).show();
                                                                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                                                                startActivity(intent);
                                                            } else {
                                                                Toast.makeText(MainActivity.this, "Giriş yapılamadı", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                        } else {
                                            Toast.makeText(MainActivity.this, "Kayıt yapılamadı", Toast.LENGTH_SHORT).show();
                                        }
                                    }




                                }
                            });
                }


            }
        });

        btngiris=findViewById(R.id.btngiris);
        btngiris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                nick=txtNick.getText().toString();
                email=txtEmail.getText().toString();
                password=txtPassword.getText().toString();


                if(!TextUtils.isEmpty(nick)&&!TextUtils.isEmpty(email)&&!TextUtils.isEmpty(password)){
                    mAuth.signInWithEmailAndPassword(email,password)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    /*Toast.makeText(MainActivity.this, "giriş yapıldı", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(MainActivity.this,MainActivity2.class);
                                    startActivity(intent);*/

                                    if (task.isSuccessful()) {
                                        Toast.makeText(MainActivity.this, "Giriş yapıldı", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                                        startActivity(intent);
                                    } else {
                                        if (task.getException() instanceof FirebaseAuthInvalidUserException) {
                                            // Kullanıcı kayıtlı değil, hata mesajı göster
                                            Toast.makeText(MainActivity.this, "Kayıtlı kullanıcı bulunamadı", Toast.LENGTH_SHORT).show();
                                        } else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                            // Geçersiz kimlik bilgileri, hata mesajı göster
                                            Toast.makeText(MainActivity.this, "Geçersiz kimlik bilgileri", Toast.LENGTH_SHORT).show();
                                        } else {
                                            // Diğer hatalar, genel hata mesajı göster
                                            Toast.makeText(MainActivity.this, "Giriş yapılamadı", Toast.LENGTH_SHORT).show();
                                        }
                                    }




                                }
                            });

                }
            }
        });



    }
}