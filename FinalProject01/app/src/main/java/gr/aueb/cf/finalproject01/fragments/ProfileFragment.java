package gr.aueb.cf.finalproject01.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import gr.aueb.cf.finalproject01.R;
import gr.aueb.cf.finalproject01.activities.LoginActivity;
import gr.aueb.cf.finalproject01.helpers.DBHelper;


public class ProfileFragment extends Fragment {
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        TextView userDetailsTV = view.findViewById(R.id.userDetailsTV);
        Button changePassBtn = view.findViewById(R.id.changePassBtn);
        Button languageBtn = view.findViewById(R.id.languageBtn);
        Button logoutBtn = view.findViewById(R.id.logoutBtn);
        Button changeThemeBtn = view.findViewById(R.id.changeThemeBtn);
        String currentUser = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();
        userDetailsTV.setText(currentUser);

        changePassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog();
            }
        });

        languageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Feature not available yet", Toast.LENGTH_SHORT).show();      ///////////

            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                requireActivity().finish();
            }
        });

        changeThemeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

                }
                Toast.makeText(getActivity(), "Theme changed!", Toast.LENGTH_SHORT).show();

                getActivity().recreate();
            }
        });
        return view;
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_change_password_dialog, null);
        builder.setView(dialogView);
        builder.setCancelable(false);

        EditText enterNewPasswordET = dialogView.findViewById(R.id.enterNewPasswordET);
        EditText confirmNewPasswordET = dialogView.findViewById(R.id.confirmNewPasswordET);

        builder.setTitle("Εισαγωγή νέου κωδικού")
                .setPositiveButton("ΕΠΙΒΕΒΑΙΩΣΗ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newPassword = enterNewPasswordET.getText().toString().trim();
                        String confirmNewPassword = confirmNewPasswordET.getText().toString().trim();

                        if (newPassword.isEmpty()) {
                            enterNewPasswordET.setError("Please type a password");
                            enterNewPasswordET.requestFocus();
                            Toast.makeText(getActivity(), "Password not changed: No password provided", Toast.LENGTH_SHORT).show();      ///////////
                            return;
                        }

                        if (confirmNewPassword.isEmpty()) {
                            confirmNewPasswordET.setError("Please confirm your new password");
                            confirmNewPasswordET.requestFocus();
                            Toast.makeText(getActivity(), "Password not changed: Password not confirmed", Toast.LENGTH_SHORT).show();
                            return;
                        }
//
                        if (newPassword.length() < 6) {
                            enterNewPasswordET.setError("Password must be at least 6 chars");
                            enterNewPasswordET.requestFocus();
                            Toast.makeText(getActivity(), "Password not changed: Must be at least 6 chars", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (!newPassword.equals(confirmNewPassword)) {
                            confirmNewPasswordET.setError("Passwords don't match");
                            confirmNewPasswordET.requestFocus();
                            Toast.makeText(getActivity(), "Password not changed: Passwords didn't match", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else {
                            firebaseAuth.getCurrentUser().updatePassword(confirmNewPassword);
                            Toast.makeText(getActivity(), "Password updated!", Toast.LENGTH_SHORT).show();
                      }
                    }
                })
                .setNegativeButton("ΑΚΥΡΩΣΗ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}