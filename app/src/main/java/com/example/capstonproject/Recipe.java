/* Assignment: 3
Campus: Ashdod
Authors:
Eliran Naduyev 312089105
Maria Garber
*/

package com.example.capstonproject;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Recipe implements Parcelable {

    private String seq;
    private String name;
    private String Title;
    private String Thumbnail;
    private Float calories;
    private String kind;

    public Recipe(String seq, String name, String title, String thumbnail, Float calories, String kind) {
        this.seq = seq;
        this.name = name;
        this.Title = title;
        this.Thumbnail = thumbnail;
        this.calories = calories;
        this.kind = kind;
    }

    protected Recipe(Parcel in) {
        seq = in.readString();
        name = in.readString();
        Title = in.readString();
        Thumbnail = in.readString();
        if (in.readByte() == 0) {
            calories = null;
        } else {
            calories = in.readFloat();
        }
        kind = in.readString();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public String getSeq(){
        return seq;
    }

    public String getname() {
        return name;
    }

    public String getTitle() {
        return Title;
    }

    public String getThumbnail() {
        return Thumbnail;
    }

    public Float getcalories() {
        return calories;
    }

    public String getkind() {
        return kind;
    }

    @NonNull
    @Override
    public String toString() {
        return getTitle();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(seq);
        parcel.writeString(name);
        parcel.writeString(Title);
        parcel.writeString(Thumbnail);
        if (calories == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeFloat(calories);
        }
        parcel.writeString(kind);
    }
}
