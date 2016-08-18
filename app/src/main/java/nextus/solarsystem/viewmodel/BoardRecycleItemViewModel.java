package nextus.solarsystem.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chosw on 2016-08-18.
 */

public class BoardRecycleItemViewModel extends BaseObservable implements ViewModel {

    private List<String> data;

    public BoardRecycleItemViewModel( List<String> data) {
        this.data = data;
    }

    public void setData(List<String> data)
    {
        this.data = data;
        notifyChange();
    }




    @Override
    public void destroy() {

    }
}
