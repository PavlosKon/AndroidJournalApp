package gr.aueb.cf.finalproject01.helpers;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class DBHelper {
    public static FirebaseDatabase databaseReference = FirebaseDatabase.getInstance();
    public static FirebaseAuth auth = FirebaseAuth.getInstance();

    public static DatabaseReference getEntriesReference() {
        return databaseReference.getReference(getCurrentUserId()).child("entries");
    }

    public static DatabaseReference getVotedQuestionsReference() {
        return databaseReference.getReference(getCurrentUserId()).child("voted_questions");
    }

    public static DatabaseReference getQuestionsReference() {
        return databaseReference.getReference("public_questions");           //child("questions");
    }

    public static DatabaseReference getApprovedQuestionsReference() {
        return databaseReference.getReference("approved_questions");           //child("questions");
    }


    public static DatabaseReference getSimpleEntriesReference() {
        return databaseReference.getReference("entries");
    }

    public static String getCurrentUserId() {
        return Objects.requireNonNull(auth.getCurrentUser()).getUid();
    }
}
