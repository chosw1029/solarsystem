package nextus.solarsystem.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * Created by chosw on 2016-08-12.
 */

public class CreateContents extends BaseObservable {

    @Bindable public String user_name;
    @Bindable public String edit_text;

    public CreateContents(){}

    public String getUser_name() {
        return user_name;
    }


    public String getEdit_text() {
        return edit_text;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }


    public void setEdit_text(String edit_text) {
        this.edit_text = edit_text;
    }
}
