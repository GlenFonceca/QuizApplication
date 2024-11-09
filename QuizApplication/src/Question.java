public class Question {
    private int questionId;
    private int quizId;
    private String questionText;
    private String correctAnswer;

    public Question(int questionId, int quizId, String questionText, String correctAnswer) {
        this.questionId = questionId;
        this.quizId = quizId;
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
    }

    // Getters and Setters
    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
