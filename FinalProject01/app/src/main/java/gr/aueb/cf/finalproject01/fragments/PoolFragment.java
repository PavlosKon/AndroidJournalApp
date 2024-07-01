package gr.aueb.cf.finalproject01.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import gr.aueb.cf.finalproject01.R;
import gr.aueb.cf.finalproject01.adapters.QuestionAdapter;
import gr.aueb.cf.finalproject01.helpers.DBHelper;
import gr.aueb.cf.finalproject01.helpers.DateStampHelper;
import gr.aueb.cf.finalproject01.models.QuestionStatus;
import gr.aueb.cf.finalproject01.models.Question;


public class PoolFragment extends Fragment {

    public RecyclerView recyclerView;
    private ArrayList<Question> questionArrayList;
    private QuestionAdapter questionAdapter;

    public final ArrayList<String> votedList = new ArrayList<>();

    public PoolFragment() {

    }

    /////////////////////////// PM

    @Override
    public void onStart() {
        super.onStart();
        DBHelper.getQuestionsReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                questionArrayList.clear();

                if (snapshot.exists()) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        Question question = data.getValue(Question.class);
                        if (question != null) {
                            if (question.getQuestionStatus() == QuestionStatus.PENDING && !votedList.contains(question.getId())) {
                                Log.i("idChecker 2", "question.getId() " + question.getId());
                                Log.i("idChecker 2", "votedList.toString(): " + votedList.toString());
                                questionArrayList.add(question);
                            }
                        }
                    }
                    if (questionAdapter == null) {
                        questionAdapter = new QuestionAdapter(getContext(), questionArrayList);
                        recyclerView.setAdapter(questionAdapter);
                    } else {
                        questionAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", "Failed to load questions", error.toException());
            }
        });

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DBHelper.getVotedQuestionsReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                votedList.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        Log.i("idChecker 1", "ds:  " + data.getValue());
                        votedList.add(data.getValue().toString());
                        Log.i("idChecker 1", "votedList.toString(): " + votedList.toString());
                    }
                }
                loadQuestions();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", "Failed to load voted questions", error.toException());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pool, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        Button submitQuestionBtn = view.findViewById(R.id.submitQuestionBtn);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        questionArrayList = new ArrayList<>();

//         ItemTouchHelper
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        DBHelper.getVotedQuestionsReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        Log.i("idChecker 1", "ds:  " + data.getValue());
                        votedList.add(data.getValue().toString());
                        Log.i("idChecker 1", "votedList.toString(): " + votedList.toString());
                    }
                    if (questionAdapter == null) {
                        questionAdapter = new QuestionAdapter(getContext(), questionArrayList);
                        recyclerView.setAdapter(questionAdapter);
                    } else {
                        questionAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", "Failed to load questions", error.toException());
            }
        });

        submitQuestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog();
            }
        });

        return view;
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int questionPosition = viewHolder.getAdapterPosition();
            QuestionStatus questionStatus = questionArrayList.get(questionPosition).getQuestionStatus();
            String questionId = questionArrayList.get(questionPosition).getId();
            String questionText = questionArrayList.get(questionPosition).getQuestion();
            int upvotes = questionArrayList.get(questionPosition).getUpvotes();
            int downvotes = questionArrayList.get(questionPosition).getDownvotes();

            switch (direction) {
                case ItemTouchHelper.LEFT:
//                    questionAdapter.changeColor(questionArrayList.get(questionPosition).getQuestion().);
                    DBHelper.getQuestionsReference().child(questionId).child("downvotes").setValue(downvotes + 1);
                    if (downvotes > 2) {
                        DBHelper.getQuestionsReference().child(questionId).child("questionStatus").setValue(QuestionStatus.REJECTED);
                    }
                    break;

                case ItemTouchHelper.RIGHT:
                    DBHelper.getQuestionsReference().child(questionId).child("upvotes").setValue(upvotes + 1);
                    if (upvotes > 2) {
                        DBHelper.getQuestionsReference().child(questionId).child("questionStatus").setValue(QuestionStatus.APPROVED);
                        DBHelper.getApprovedQuestionsReference().child(questionId).setValue(questionText);
                    }
                    break;
            }

            DBHelper.getVotedQuestionsReference().push().setValue(questionId);
            questionArrayList.remove(questionPosition);

            if (questionAdapter == null) {
                questionAdapter = new QuestionAdapter(getContext(), questionArrayList);
                recyclerView.setAdapter(questionAdapter);
            } else {
                questionAdapter.notifyDataSetChanged();
            }
            Toast.makeText(getActivity(), "Thanks for voting!", Toast.LENGTH_SHORT).show();      ///////////
        }
    };

    private void loadQuestions() {
        DBHelper.getQuestionsReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                questionArrayList.clear();

                if (snapshot.exists()) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        Question question = data.getValue(Question.class);
                        if (question != null && (!Objects.equals(question.getUserId(), DBHelper.getCurrentUserId()))) {
                            if (question.getQuestionStatus() == QuestionStatus.PENDING && !votedList.contains(question.getId())) {
                                Log.i("idChecker 2", "question.getId() " + question.getId());
                                Log.i("idChecker 2", "votedList.toString(): " + votedList.toString());
                                questionArrayList.add(question);
                            }
                        }
                    }
                    if (questionAdapter == null) {
                        questionAdapter = new QuestionAdapter(getContext(), questionArrayList);
                        recyclerView.setAdapter(questionAdapter);
                    } else {

                        questionAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", "Failed to load questions", error.toException());
            }
        });
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_submit_question_dialog, null);
        builder.setView(dialogView);
        builder.setCancelable(false);

        EditText writeQuestionET = dialogView.findViewById(R.id.writeQuestionET);

        builder.setTitle("Νέα ερώτηση")
                .setPositiveButton("ΥΠΟΒΟΛΗ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String questionToSubmit = writeQuestionET.getText().toString().trim();

                        if (questionToSubmit.isEmpty()) {
                            writeQuestionET.setError("Γράψε την ερώτησή σου εδώ");
                            writeQuestionET.requestFocus();
                            return;
                        }

                        String questionId = DBHelper.getQuestionsReference().push().getKey();
                        String userId = DBHelper.getCurrentUserId();
                        Question question = new Question(questionId, userId, questionToSubmit, DateStampHelper.getDateStamp(), 0, 0, QuestionStatus.PENDING);

                        DBHelper.getQuestionsReference().child(questionId).setValue(question).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getActivity(), "Question submitted!", Toast.LENGTH_SHORT).show();      ///////////

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();      ///////////

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