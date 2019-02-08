package edu.fsu.cs.mobile.hw3;

import android.os.Parcel;
import android.os.Parcelable;

public class MyNote implements Parcelable {
    private String title;
    private String note;
    private String time;

    public MyNote() {

    }

    public MyNote(String title, String note, String time) {
        this.title = title;
        this.time = time;
        this.note = note;
    }

    protected MyNote(Parcel in) {
        title = in.readString();
        time = in.readString();
        note = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(time);
        dest.writeString(note);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MyNote> CREATOR = new Creator<MyNote>() {
        @Override
        public MyNote createFromParcel(Parcel in) {
            return new MyNote(in);
        }

        @Override
        public MyNote[] newArray(int size) {
            return new MyNote[size];
        }
    };

    public String getNote() {
        return note;
    }

    public String getTitle() {
        return title;
    }

    public String getTime() {
        return time;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
