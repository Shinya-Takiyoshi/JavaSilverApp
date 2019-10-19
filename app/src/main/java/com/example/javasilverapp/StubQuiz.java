package com.example.javasilverapp;

public class StubQuiz {
    private String program;
    private String[] selection;
    private int maxButton;
    private int[] answer;

    public String getProgram() {
        return program;
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

    public void setProgram(String program) {
        this.program = program;
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
}
