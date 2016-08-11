package nextus.solarsystem.viewmodel;

import android.content.Context;
import android.util.Log;

import java.util.List;

import nextus.solarsystem.GlobalApplication;
import nextus.solarsystem.model.BoardItem;
import nextus.solarsystem.utils.ContentService;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by chosw on 2016-08-11.
 */

public class MainViewModel implements ViewModel {

    private static final String TAG = "BestLocationViewModel";

    private Context context;
    private Subscription subscription;
    private DataListener dataListener;
    private List<BoardItem> boardItems;

    public MainViewModel(Context context, DataListener dataListener)
    {
        this.context = context;
        this.dataListener = dataListener;
        loadBoardData();
    }
/*
    public interface OnClickListener{
        void onClick(View view);
    }
*/
    private void loadBoardData()
    {
        if (subscription != null && !subscription.isUnsubscribed()) subscription.unsubscribe();

        ContentService contentService = GlobalApplication.getGlobalApplicationContext().getContentService();
        subscription = contentService.getData()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(GlobalApplication.getGlobalApplicationContext().defaultSubscribeScheduler())
                .subscribe(new Subscriber<List<BoardItem>>(){
                    @Override
                    public void onCompleted(){
                        if (dataListener != null) dataListener.onBoardItmeChanged(boardItems);
                    }
                    @Override
                    public void onError(Throwable error)
                    {
                        Log.e(TAG, "Error loading Item ", error);
                    }
                    @Override
                    public void onNext(List<BoardItem> boardItems)
                    {
                        Log.i(TAG, "Item loaded " + boardItems);
                        MainViewModel.this.boardItems = boardItems;
                    }
                });
    }

    @Override
    public void destroy() {
        if (subscription != null && !subscription.isUnsubscribed()) subscription.unsubscribe();
        subscription = null;
        context = null;
        dataListener = null;
    }

    public interface DataListener
    {
        void onBoardItmeChanged(List<BoardItem> boardItems);
    }
}
