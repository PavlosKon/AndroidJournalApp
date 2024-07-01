package gr.aueb.cf.finalproject01.models;

public class Question {

    private String id;
    private String userId;
    private String question;
    private String datestamp;
    private int upvotes;
    private int downvotes;


    private QuestionStatus questionStatus;
    public Question() {

    }

    public Question(String id, String userId, String question, String datestamp, int upvotes, int downvotes, QuestionStatus questionStatus) {
        this.id = id;
        this.userId = userId;
        this.question = question;
        this.datestamp = datestamp;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
        this.questionStatus = questionStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getDatestamp() {
        return datestamp;
    }

    public void setDatestamp(String datestamp) {
        this.datestamp = datestamp;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }

    public int getDownvotes() {
        return downvotes;
    }

    public void setDownvotes(int downvotes) {
        this.downvotes = downvotes;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public QuestionStatus getQuestionStatus() {
        return questionStatus;
    }

    public void setQuestionStatus(QuestionStatus questionStatus) {
        this.questionStatus = questionStatus;
    }

}
