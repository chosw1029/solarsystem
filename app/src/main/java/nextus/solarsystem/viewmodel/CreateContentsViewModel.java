package nextus.solarsystem.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.graphics.Bitmap;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import nextus.solarsystem.GlobalApplication;
import nextus.solarsystem.model.CreateContents;

/**
 * Created by chosw on 2016-08-12.
 */

public class CreateContentsViewModel extends BaseObservable implements ViewModel{

    public Context context;
    public CreateContents createContents;
    public ObservableField<String> text = new ObservableField<>();
    public List<Bitmap> addedImage;
    public EventListener listener;

    public CreateContentsViewModel(Context context, EventListener listener)
    {
        this.context = context;
        this.listener = listener;
        setData();
    }

    public void setAddedImage(List<Bitmap> addedImage)
    {
        this.addedImage = addedImage;
    }

    public void setImageList(ArrayList<Bitmap> imageList)
    {
        listener.onImageListChanged(imageList);
    }


    public void setData()
    {
        createContents = new CreateContents();
        createContents.setUser_name(GlobalApplication.getGlobalApplicationContext().getUserProfile().getNickname());
        createContents.setUser_thumnail(GlobalApplication.getGlobalApplicationContext().getUserProfile().getThumbnailImagePath());
    }

    public void setImage(CircleImageView view)
    {
        Glide.with(this.context).load(createContents.getUser_thumnail()).diskCacheStrategy(DiskCacheStrategy.RESULT).into(view);
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
                Log.e("Changed","-------------------------------------"+editable.toString());
            }
        }
    };


    public interface EventListener
    {
        void onImageListChanged(List<Bitmap> imageList);
        void openGallery(View view);
        void onClick(View view);
    }

    @Override
    public void destroy() {
        this.context = null;
        this.listener = null;
        this.createContents = null;
        if(addedImage != null)
            this.addedImage.clear();
        this.addedImage = null;
    }
}
