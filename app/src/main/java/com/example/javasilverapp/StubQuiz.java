package com.example.javasilverapp;

public class StubQuiz {
    private String question;
    private int questionImage;
    private String[] selection;
    private int maxButton;
    private int[] answer;

    public String getQuestion() {
        return question;
    }

    public String[] getSelection() {
        return selection;
    }

    public int getMaxButton() {
        return maxButton;
    }

    public int[] getAnswer() {
        return answer;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setSelection(String[] selection) {
        this.selection = selection;
    }

    public void setMaxButton(int maxButton) {
        this.maxButton = maxButton;
    }

    public void setAnswer(int[] answer) {
        this.answer = answer;
    }

    public int getQuestionImage() {
        return questionImage;
    }

    public void setQuestionImage(int questionImage) {
        this.questionImage = questionImage;
    }
}
