package gr.aueb.cf.finalproject01.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import gr.aueb.cf.finalproject01.R;
import gr.aueb.cf.finalproject01.adapters.EntryAdapter;
import gr.aueb.cf.finalproject01.adapters.QuestionAdapter;
import gr.aueb.cf.finalproject01.helpers.DBHelper;
import gr.aueb.cf.finalproject01.helpers.DateStampHelper;
import gr.aueb.cf.finalproject01.models.Entry;
import gr.aueb.cf.finalproject01.models.Question;
import gr.aueb.cf.finalproject01.models.QuestionStatus;


public class HomeFragment extends Fragment {

    public RecyclerView recyclerView;
    private ArrayList<Entry> entryArrayList;

    public final ArrayList<String> approvedQuestionsList = new ArrayList<>();

    private EntryAdapter entryAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(linearLayoutManager);
        entryArrayList = new ArrayList<>();

        FloatingActionButton addBtn = view.findViewById(R.id.addBtn);

        DBHelper.getApprovedQuestionsReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot data : snapshot.getChildren()) {
//                        Log.i("Approved id check 1", "data:  " + data.getValue());
                        approvedQuestionsList.add(data.getValue().toString());
//                        Log.i("Approved id check  1", "approvedQuestionsList.toString(): " + approvedQuestionsList.toString());
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DBHelper.getEntriesReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                entryArrayList.clear();

                if (snapshot.exists()) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        Entry entry = data.getValue(Entry.class);
                        if (entry != null) {
                            entryArrayList.add(entry);
                        }
                    }
                    entryAdapter = new EntryAdapter(getContext(), entryArrayList);    /////////////
                    recyclerView.setAdapter(entryAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog();
            }
        });

        return view;
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog, null);
        builder.setView(dialogView);
        builder.setCancelable(false);

        TextView entryQuestionTV = dialogView.findViewById(R.id.entryQuestionTV);
        EditText entryAnswerET = dialogView.findViewById(R.id.entryAnswerET);

        String entryQuestion = approvedQuestionsList.get(new Random().nextInt(approvedQuestionsList.size()));
        entryQuestionTV.setText(entryQuestion);

        builder.setTitle(entryQuestion)
                .setPositiveButton("ΥΠΟΒΟΛΗ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String entryDate = DateStampHelper.getDateStamp();
                        String entryAnswer = entryAnswerET.getText().toString().trim();

                        if (entryAnswer.isEmpty()) {
                            entryAnswerET.setError("Γράψε την απάντησή σου εδώ");
                            entryAnswerET.requestFocus();
                            return;
                        }

                        String entryId = DBHelper.getEntriesReference().push().getKey();
                        Entry entry = new Entry(entryId, entryDate, entryQuestion, entryAnswer, DateStampHelper.getDateStamp());

                        DBHelper.getEntriesReference().child(entryId).setValue(entry).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getActivity(), "Entry added!", Toast.LENGTH_SHORT).show();      ///////////
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();       ////////////
                            }
                        });

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
