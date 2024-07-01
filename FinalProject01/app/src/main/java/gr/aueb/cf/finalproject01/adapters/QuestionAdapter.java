package gr.aueb.cf.finalproject01.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

import gr.aueb.cf.finalproject01.R;
import gr.aueb.cf.finalproject01.models.Question;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {

    private Context context;
    private ArrayList<Question> questionArrayList;

    public QuestionAdapter(Context context, ArrayList<Question> questionArrayList) {
        this.context = context;
        this.questionArrayList = questionArrayList;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.question_item, parent, false);
        return new QuestionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionAdapter.QuestionViewHolder holder, int position) {
        Question question = questionArrayList.get(position);
        String questionId = question.getId();
        holder.questionMTV.setText(question.getQuestion());

    }

    public void changeColor(MaterialTextView mtvToUse) {
        mtvToUse.setBackgroundColor(Color.RED);
    }


    @Override
    public int getItemCount() { return questionArrayList.size(); }

    public class QuestionViewHolder extends RecyclerView.ViewHolder {
        private MaterialTextView questionMTV;
        private ConstraintLayout cardViewConstraintLayout;
        private Color questionColor;


        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            questionMTV = itemView.findViewById(R.id.questionMTV);
            cardViewConstraintLayout = itemView.findViewById(R.id.cardViewConstraintLayout);
        }
    }
}
