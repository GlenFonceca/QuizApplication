public class QuizOption {
    private int optionId;
    private int questionId;
    private String optionText;

    public QuizOption(int optionId, int questionId, String optionText) {
        this.optionId = optionId;
        this.questionId = questionId;
        this.optionText = optionText;
    }

    // Getters and Setters
    public int getOptionId() {
        return optionId;
    }

    public void setOptionId(int optionId) {
        this.optionId = optionId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }
}
