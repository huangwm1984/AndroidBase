package com.hwm.test.http.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by diff on 2016/2/3.
 */
public class News implements Parcelable {

    private String date;

    private ArrayList<Stories> stories;

    private List<TopStories> top_stories;


    public void setDate(String date) {
        this.date = date;
    }

    public void setStories(ArrayList<Stories> stories) {
        this.stories = stories;
    }

    public void setTop_stories(List<TopStories> top_stories) {
        this.top_stories = top_stories;
    }

    public String getDate() {
        return date;
    }

    public ArrayList<Stories> getStories() {
        return stories;
    }

    public List<TopStories> getTop_stories() {
        return top_stories;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.date);
        dest.writeList(this.stories);
        dest.writeList(this.top_stories);
    }

    public News() {
    }

    protected News(Parcel in) {
        this.date = in.readString();
        this.stories = new ArrayList<Stories>();
        in.readList(this.stories, List.class.getClassLoader());
        this.top_stories = new ArrayList<TopStories>();
        in.readList(this.top_stories, List.class.getClassLoader());
    }

    public static final Creator<News> CREATOR = new Creator<News>() {
        public News createFromParcel(Parcel source) {
            return new News(source);
        }

        public News[] newArray(int size) {
            return new News[size];
        }
    };

    @Override
    public String toString() {
        return "NewsEntity{" +
                "date='" + date + '\'' +
                ", stories=" + stories +
                ", top_stories=" + top_stories +
                '}';
    }
}
