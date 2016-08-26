package nextus.solarsystem.model;

import android.databinding.BaseObservable;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chosw on 2016-08-21.
 */

public class Comment implements Parcelable{

    public String user_id;
    public String user_name;
    public String user_thumnail;
    public String comment_info;
    public String date;
    public int comment_id;
    public int board_id;

    public Comment(Parcel in)
    {
        this.user_id = in.readString();
        this.user_name = in.readString();
        this.user_thumnail = in.readString();
        this.comment_info = in.readString();
        this.date = in.readString();
        this.comment_id = in.readInt();
        this.board_id = in.readInt();
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        public Comment createFromParcel(Parcel source) {
            return new Comment(source);
        }

        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.user_id);
        parcel.writeString(this.user_name);
        parcel.writeString(this.user_thumnail);
        parcel.writeString(this.comment_info);
        parcel.writeString(this.date);
        parcel.writeInt(this.comment_id);
        parcel.writeInt(this.board_id);
    }
}
