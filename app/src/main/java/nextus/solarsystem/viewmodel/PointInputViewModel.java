package nextus.solarsystem.viewmodel;

import android.content.Context;
import android.content.DialogInterface;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.databinding.library.baseAdapters.BR;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import nextus.solarsystem.R;
import nextus.solarsystem.model.UserData;
import nextus.solarsystem.view.DatePickerFragment;
import nextus.solarsystem.view.PointInputActivity;

/**
 * Created by chosw on 2016-08-28.
 */

public class PointInputViewModel extends BaseObservable {

    private Context context;
    private DataChangedListener dataChangedListener;
    private ArrayList<UserData> userDataArrayList;
    private ArrayList<UserData> attendant_list = new ArrayList<>();
    private ObservableField<Boolean> addedAttandant = new ObservableField<>();
    private ObservableField<Integer> totalMoney = new ObservableField<>();
    private ObservableField<Integer> individual_money = new ObservableField<>();
    public ObservableField<Integer> total_attendant = new ObservableField<>();
    public ObservableField<String> text = new ObservableField<>();

    public PointInputViewModel(Context context, DataChangedListener dataChangedListener, ArrayList<UserData> userDataArrayList)
    {
        this.context = context;
        this.dataChangedListener = dataChangedListener;
        this.userDataArrayList = userDataArrayList;
        total_attendant.set(0);
    }

    public void setAttendant_list(List<UserData> attendant_list)
    {
        this.attendant_list = new ArrayList<>(attendant_list);
        updateData();
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
                showMultiChoiceDialog();
                Log.e("userData",""+userDataArrayList.get(0).user_nickname);
                break;
            case R.id.apply_button:
                Log.e("AttandantListSize",""+attendant_list.size());
                break;
        }
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
               if( !editable.toString().contentEquals("") & addedAttandant.get() != null ) updateData();
            }
        }
    };

    public void updateData()
    {
        total_attendant.set(attendant_list.size());
        totalMoney.set(Integer.parseInt(text.get()));
        individual_money.set(totalMoney.get()/total_attendant.get());

        for(int k=0; k<attendant_list.size(); k++)
        {
            attendant_list.get(k).user_usedPoint = ""+individual_money.get();
        }

        if( dataChangedListener != null )
            dataChangedListener.attendantChanged(attendant_list);

    }

    public void showMultiChoiceDialog()
    {
        String[] colors = new String[]{
                userDataArrayList.get(0).user_nickname,
                userDataArrayList.get(1).user_nickname,
                userDataArrayList.get(2).user_nickname,
                userDataArrayList.get(3).user_nickname,
                userDataArrayList.get(4).user_nickname,
        };

        final boolean[] checkedColors = new boolean[]{
                false, // 정상문
                false, // 신현수
                false, // 조상욱
                false, // 박진태
                false // 이해성
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMultiChoiceItems(colors, checkedColors, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                // Update the current focused item's checked status
                checkedColors[which] = isChecked;

            }
        });

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                for (int i = 0; i<checkedColors.length; i++){
                    boolean checked = checkedColors[i];
                    if (checked) {
                        attendant_list.add(userDataArrayList.get(i));
                    }
                }
                addedAttandant.set(true);
                updateData();

            }
        }).create().show();
    }

    public interface DataChangedListener
    {
        void attendantChanged(List<UserData> attendant_list);
        void remove(View view);
    }
}
