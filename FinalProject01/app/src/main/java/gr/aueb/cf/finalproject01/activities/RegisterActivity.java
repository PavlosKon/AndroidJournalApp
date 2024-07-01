package gr.aueb.cf.finalproject01.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import gr.aueb.cf.finalproject01.R;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText emailET;
    private TextInputEditText passwordET;
    private TextInputEditText confirmPasswordET;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // NIGHT MODE DISABLE
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();
        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);
        confirmPasswordET = findViewById(R.id.confirmPasswordET);
        MaterialButton registerBtn = findViewById(R.id.registerBtn);
        MaterialTextView loginTV = findViewById(R.id.loginTV);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = Objects.requireNonNull(emailET.getText()).toString().trim();
                String password = Objects.requireNonNull(passwordET.getText()).toString().trim();
                String confirmPassword = Objects.requireNonNull(confirmPasswordET.getText()).toString().trim();

                // Simple validation
                if (email.isEmpty()) {
                    emailET.setError("Email is empty");
                    emailET.requestFocus();
                    return;
                }
                if (password.isEmpty()) {
                    passwordET.setError("Password is empty");
                    passwordET.requestFocus();
                    return;
                }
                if (confirmPassword.isEmpty()) {
                    confirmPasswordET.setError("Please insert a valid email");
                    confirmPasswordET.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailET.setError("Please enter a valid email");
                    emailET.requestFocus();
                    return;
                }
                if (password.length() < 6) {
                    passwordET.setError("Password must be atleast 6 chars");
                    passwordET.requestFocus();
                    return;
                }
                if (!password.equals(confirmPassword)) {
                    confirmPasswordET.setError("Passwords don't match");
                    confirmPasswordET.requestFocus();
                    return;
                } else {
                    // Create user
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(RegisterActivity.this, "User created", Toast.LENGTH_SHORT).show();
                            firebaseAuth.signOut();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });

        loginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}