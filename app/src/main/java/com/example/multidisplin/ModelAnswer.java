package com.example.multidisplin;

public class ModelAnswer {
    int id;
    String nim;
    String name;
    String title;
    String answer;

    public ModelAnswer(int id, String nim, String name, String title, String answer) {
        this.id = id;
        this.nim = nim;
        this.name = name;
        this.title = title;
        this.answer = answer;
    }

    public ModelAnswer(String nim, String name, String title, String answer) {
        this.nim = nim;
        this.name = name;
        this.title = title;
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "AnswerModel{" +
                "nim=" + nim +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
