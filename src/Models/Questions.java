package Models;


/**
 * Created by User on 10.01.2016.
 */
public class Questions {

    private String id;
    private String question;
    private String answerId;
    private String firstAnswer;
    private String secondAnswer;
    private String thirdAnswer;
    private String fourthAnswer;
    private String rightAnswer;

    public Questions() {
        this(null, null, null, null, null, null, null, null);
    }

    public Questions(String id, String question, String answerId, String firstAnswer, String secondAnswer, String thirdAnswer, String fourthAnswer, String rightAnswer) {
        this.id = id;
        this.question = question;
        this.answerId = answerId;
        this.firstAnswer = firstAnswer;
        this.secondAnswer = secondAnswer;
        this.thirdAnswer = thirdAnswer;
        this.fourthAnswer = fourthAnswer;
        this.rightAnswer = rightAnswer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return  question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswerId() { return answerId; }

    public void setAnswerId(String answerId) { this.answerId = answerId; }

    public String getFirstAnswer() {
        return firstAnswer;
    }

    public void setFirstAnswer(String firstAnswer) {
        this.firstAnswer = firstAnswer;
    }

    public String getSecondAnswer() {
        return secondAnswer;
    }

    public void setSecondAnswer(String secondAnswer) {
        this.secondAnswer = secondAnswer;
    }

    public String getThirdAnswer() {
        return thirdAnswer;
    }

    public void setThirdAnswer(String thirdAnswer) {
        this.thirdAnswer = thirdAnswer;
    }

    public String getFourthAnswer() {
        return fourthAnswer;
    }

    public void setFourthAnswer(String fourthAnswer) {
        this.fourthAnswer = fourthAnswer;
    }

    public String getRightAnswer() { return rightAnswer; }

    public void setRightAnswer(String rightAnswer) { this.firstAnswer = rightAnswer; }
}
