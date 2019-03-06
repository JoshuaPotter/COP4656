//TriviaItem
package edu.fsu.cs.mobile.hw4;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

public class TriviaItem implements Parcelable {
    private String question;
    private String category;
    private String type;
    private String difficulty;
    private ArrayList<String> incorrectAnswers;
    private String correctAnswer;

    public TriviaItem() {

    }

    public TriviaItem(String category, String type, String difficulty, String question,
                      String correctAnswer, ArrayList<String> incorrectAnswers) {
        this.category = category;
        this.type = type;
        this.difficulty = difficulty;
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.incorrectAnswers = incorrectAnswers;
    }

    protected TriviaItem(Parcel in) {
        category = in.readString();
        type = in.readString();
        difficulty = in.readString();
        question = in.readString();
        correctAnswer = in.readString();
        incorrectAnswers = in.readArrayList(String.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(category);
        dest.writeString(type);
        dest.writeString(difficulty);
        dest.writeString(question);
        dest.writeString(correctAnswer);
        dest.writeList(incorrectAnswers);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TriviaItem> CREATOR = new Creator<TriviaItem>() {
        @Override
        public TriviaItem createFromParcel(Parcel in) {
            return new TriviaItem(in);
        }

        @Override
        public TriviaItem[] newArray(int size) {
            return new TriviaItem[size];
        }
    };

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public ArrayList<String> getIncorrectAnswers() {
        return incorrectAnswers;
    }

    public void setIncorrectAnswers(ArrayList<String> incorrectAnswers) {
        this.incorrectAnswers = incorrectAnswers;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }


}
