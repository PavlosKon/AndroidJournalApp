package gr.aueb.cf.finalproject01.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import gr.aueb.cf.finalproject01.R;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText emailET;
    private TextInputEditText passwordET;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // NIGHT MODE DISABLE
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        firebaseAuth = FirebaseAuth.getInstance();

        // Check whether user has logged in
        if (firebaseAuth.getCurrentUser() != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);
        MaterialButton loginBtn = findViewById(R.id.loginBtn);
        MaterialTextView registerTV = findViewById(R.id.registerTV);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = Objects.requireNonNull(emailET.getText(), "Email field is empty").toString().trim();
                String password = Objects.requireNonNull(passwordET.getText(), "Password field is empty").toString().trim();

                if (email.isEmpty()) {
                    emailET.setError("Email field is empty");
                    emailET.requestFocus();
                    return;
                }
                if (password.isEmpty()) {
                    passwordET.setError("Password field is empty");
                    passwordET.requestFocus();
                    return;
                }

                firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(LoginActivity.this, "Login success!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
        registerTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}