package nextus.solarsystem.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import java.util.Objects;

import nextus.solarsystem.GlobalApplication;
import nextus.solarsystem.model.CreateContents;

/**
 * Created by chosw on 2016-08-12.
 */

public class CreateContentsViewModel extends BaseObservable implements ViewModel{

    public Context context;
    public CreateContents createContents;
    public ObservableField<String> text = new ObservableField<>();

    public CreateContentsViewModel(Context context)
    {
        this.context = context;
        setData();
    }

    public void setData()
    {
        createContents = new CreateContents();
        createContents.setUser_name(GlobalApplication.getGlobalApplicationContext().getUserProfile().getNickname());
        createContents.setUser_thumnail(GlobalApplication.getGlobalApplicationContext().getUserProfile().getThumbnailImagePath());
    }

    public TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (!Objects.equals(text.get(), editable.toString())) {
                text.set(editable.toString());
                createContents.edit_text = editable.toString();
                Log.e("Changed","-------------------------------------");
            }
        }
    };

    public interface openGalleryListener
    {
        void openGallery(View view);
    }

    @Override
    public void destroy() {

    }
}
