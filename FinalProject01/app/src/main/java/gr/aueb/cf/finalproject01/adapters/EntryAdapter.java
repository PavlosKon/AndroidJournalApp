package gr.aueb.cf.finalproject01.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

import gr.aueb.cf.finalproject01.R;
import gr.aueb.cf.finalproject01.models.Entry;

public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.EntryViewHolder> {

    private Context context;
    private ArrayList<Entry> entryArrayList;

    public EntryAdapter(Context context, ArrayList<Entry> entryArrayList) {
        this.context = context;
        this.entryArrayList = entryArrayList;
    }

    @NonNull
    @Override
    public EntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.entry_item, parent, false);
        return new EntryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EntryViewHolder holder, int position) {
        Entry entry = entryArrayList.get(position);
        holder.entryDateMTV.setText(entry.getEntryDate());
        holder.entryQuestionMTV.setText(entry.getEntryQuestion());
        holder.entryAnswerMTV.setText(entry.getEntryAnswer());
//        holder.cardViewConstraintLayout.setBackgroundColor(Color.parseColor("#" + note.getBackgroundColor()));
    }

    @Override
    public int getItemCount() {
        return entryArrayList.size();
    }

    public class EntryViewHolder extends RecyclerView.ViewHolder {

        private MaterialTextView entryDateMTV;
        private MaterialTextView entryQuestionMTV;

        private MaterialTextView entryAnswerMTV;
        private ConstraintLayout cardViewConstraintLayout;

        public EntryViewHolder(@NonNull View itemView) {
            super(itemView);
            entryDateMTV = itemView.findViewById(R.id.entryDateMTV);
            entryQuestionMTV = itemView.findViewById(R.id.entryQuestionMTV);
            entryAnswerMTV = itemView.findViewById(R.id.entryAnswerMTV);
            cardViewConstraintLayout = itemView.findViewById(R.id.cardViewConstraintLayout);
        }
    }
}