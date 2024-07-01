package gr.aueb.cf.finalproject01.models;

public class Entry {

    private String id;
    private String entryDate; // TODO this is the date from Timestamp
    private String entryQuestion;

    private String entryAnswer;
    private String datestamp;

    public Entry() {}

    public Entry(String id, String entryDate, String entryQuestion, String entryAnswer, String datestamp) {
        this.id = id;
        this.entryDate = entryDate;
        this.entryQuestion = entryQuestion;
        this.entryAnswer = entryAnswer;
        this.datestamp = datestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {    ////////////////////////////
        this.entryDate = entryDate;
    }

    public String getEntryAnswer() {
        return entryAnswer;
    }

    public void setEntryAnswer(String entryAnswer) {
        this.entryAnswer = entryAnswer;
    }

    public String getDatestamp() {
        return datestamp;
    }

    public void setDatestamp(String datestamp) {
        this.datestamp = datestamp;
    }

    public String getEntryQuestion() {
        return entryQuestion;
    }

    public void setEntryQuestion(String entryQuestion) {
        this.entryQuestion = entryQuestion;
    }
}

