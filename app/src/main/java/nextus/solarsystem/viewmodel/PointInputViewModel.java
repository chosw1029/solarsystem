package nextus.solarsystem.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.support.v4.app.DialogFragment;
import android.view.View;

import java.util.List;

import nextus.solarsystem.R;
import nextus.solarsystem.view.DatePickerFragment;
import nextus.solarsystem.view.PointInputActivity;

/**
 * Created by chosw on 2016-08-28.
 */

public class PointInputViewModel extends BaseObservable{

    private Context context;
    private DataChangedListener dataChangedListener;

    public PointInputViewModel(Context context, DataChangedListener dataChangedListener)
    {
        this.context = context;
        this.dataChangedListener = dataChangedListener;
    }

    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.date_picker:
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(((PointInputActivity)context).getSupportFragmentManager(), "datePicker");
                break;
            case R.id.attendant_add:
                break;
        }
    }

    public interface DataChangedListener
    {
        void attendantChanged(List<String> attendant_list);
    }
}
