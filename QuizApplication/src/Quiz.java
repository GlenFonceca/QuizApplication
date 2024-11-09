public class Quiz {
    private int quizId;
    private String title;

    public Quiz(int quizId, String title) {
        this.quizId = quizId;
        this.title = title;
    }

    // Getters and Setters
    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
