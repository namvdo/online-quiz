package bean;

public class StudentWithAnswer <K, V, T>{
    private K quiz;
    private V quizCorrectAnswer;
    private T studentAnswer;

    public StudentWithAnswer(K quiz, V quizCorrectAnswer, T studentAnswer) {
        this.quiz = quiz;
        this.quizCorrectAnswer = quizCorrectAnswer;
        this.studentAnswer = studentAnswer;
    }

    public K getQuiz() {
        return quiz;
    }

    public void setQuiz(K quiz) {
        this.quiz = quiz;
    }

    public V getQuizCorrectAnswer() {
        return quizCorrectAnswer;
    }

    public void setQuizCorrectAnswer(V quizCorrectAnswer) {
        this.quizCorrectAnswer = quizCorrectAnswer;
    }

    public T getStudentAnswer() {
        return studentAnswer;
    }

    public void setStudentAnswer(T studentAnswer) {
        this.studentAnswer = studentAnswer;
    }
}
