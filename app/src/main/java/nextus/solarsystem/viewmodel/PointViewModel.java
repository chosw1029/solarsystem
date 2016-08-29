package nextus.solarsystem.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import nextus.solarsystem.GlobalApplication;
import nextus.solarsystem.model.UserData;
import nextus.solarsystem.utils.ContentService;
import nextus.solarsystem.view.PointActivity;
import nextus.solarsystem.view.PointInputActivity;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by chosw on 2016-08-27.
 */

public class PointViewModel extends BaseObservable{

    private Context context;
    private Subscription subscription;
    private DataListener dataListener;
    private List<UserData> userDataList;
    private UserData userData;
    private ArrayList<UserData> temp;


    public PointViewModel(Context context, DataListener dataListener)
    {
        this.context = context;
        this.dataListener = dataListener;
        loadUserData();
    }

    public void setUserData(UserData userData)
    {
        this.userData = userData;
        notifyChange();
    }

    public void loadUserData()
    {
        if (subscription != null && !subscription.isUnsubscribed()) subscription.unsubscribe();

        ContentService contentService = GlobalApplication.getGlobalApplicationContext().getContentService();
        subscription = contentService.getUserData()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(GlobalApplication.getGlobalApplicationContext().defaultSubscribeScheduler())
                .subscribe(new Subscriber<List<UserData>>(){
                    @Override
                    public void onCompleted(){
                        if (dataListener != null) dataListener.userDataChanged(userDataList);
                    }
                    @Override
                    public void onError(Throwable error)
                    {
                        Log.e(TAG, "Error loading Item ", error);
                    }
                    @Override
                    public void onNext(List<UserData> userDataList)
                    {
                       // Log.i(TAG, "Item loaded " + boardItems);
                        PointViewModel.this.userDataList = userDataList;
                        temp = new ArrayList<UserData>(userDataList);
                    }
                });

    }

    public void onClick(View view)
    {
        Intent intent = new Intent(context, PointInputActivity.class);
        intent.putParcelableArrayListExtra("userData", temp);
        context.startActivity(intent);
    }


    public String getUserID() { return userData.getUser_id(); }
    public String getUserName() { return userData.getUser_nickname(); }
    public String getUserThumnail() { return  userData.getUser_thumnail(); }
    public String getUserToken() { return  userData.getUser_token(); }
    public String getUserPoint() { return "포인트 : "+userData.getUser_point(); }
    public String getUserPhone() { return "번호 : "+userData.getUser_phone(); }
    public String getUserBirthday() { return "생일 : "+userData.getUser_birthday(); }


    public String getImageUrl() {
        // The URL will usually come from a model (i.e Profile)
        return userData.user_thumnail;
    }

    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl)
                .into(view);
    }

    public interface DataListener
    {
        void userDataChanged(List<UserData> userDataList);
    }
}
