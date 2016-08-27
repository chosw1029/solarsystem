package nextus.solarsystem.model;

import android.databinding.BaseObservable;

/**
 * Created by chosw on 2016-08-27.
 */

public class UserData extends BaseObservable {

    public String user_id;
    public String user_nickname;
    public String user_thumnail;
    public String user_token;
    public String user_point;
    public String user_phone;
    public String user_birthday;

    public UserData()
    {

    }

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
}
