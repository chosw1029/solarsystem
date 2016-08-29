package nextus.solarsystem.model;

import android.databinding.BaseObservable;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chosw on 2016-08-27.
 */

public class UserData extends BaseObservable implements Parcelable{

    public String user_id;
    public String user_nickname;
    public String user_thumnail;
    public String user_token;
    public String user_point;
    public String user_phone;
    public String user_birthday;
    public String user_usedPoint;

    public UserData(Parcel in)
    {
        this.user_id = in.readString();
        this.user_nickname = in.readString();
        this.user_thumnail = in.readString();
        this.user_token = in.readString();
        this.user_point = in.readString();
        this.user_phone = in.readString();
        this.user_birthday = in.readString();
        this.user_usedPoint = in.readString();
    }


    public static final Creator<UserData> CREATOR = new Creator<UserData>() {
        public UserData createFromParcel(Parcel source) {
            return new UserData(source);
        }

        public UserData[] newArray(int size) {
            return new UserData[size];
        }
    };

    public String getUser_id() {
        return user_id;
    }

    public String getUser_nickname() {
        return user_nickname;
    }

    public String getUser_thumnail() {
        return user_thumnail;
    }

    public String getUser_token() {
        return user_token;
    }

    public String getUser_point() {
        return user_point;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public String getUser_birthday() {
        return user_birthday;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.user_id);
        parcel.writeString(this.user_nickname);
        parcel.writeString(this.user_thumnail);
        parcel.writeString(this.user_token);
        parcel.writeString(this.user_point);
        parcel.writeString(this.user_phone);
        parcel.writeString(this.user_birthday);
        parcel.writeString(this.user_usedPoint);
    }
}
