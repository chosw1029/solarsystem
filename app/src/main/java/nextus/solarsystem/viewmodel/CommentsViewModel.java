package nextus.solarsystem.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import nextus.solarsystem.GlobalApplication;
import nextus.solarsystem.model.BoardItem;
import nextus.solarsystem.model.Comment;
import nextus.solarsystem.utils.ContentService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by chosw on 2016-08-21.
 */

public class CommentsViewModel extends BaseObservable implements ViewModel {

    final String TAG = "CommentsViewModel";
    public ObservableField<String> text = new ObservableField<>();

    private Context context;
    private List<Comment> commentList;
    private Comment comment;
    private Subscription subscription;
    private DataListener dataListener;
    private int board_id = 0;

    public CommentsViewModel(Context context)
    {
        this.context = context;
    }

    public void setBoardID(int board_id)
    {
        this.board_id = board_id;
    }

    public int getBoardID() { return this.board_id; }

    public void setDataListener(DataListener dataListener)
    {
        this.dataListener = dataListener;
    }

    public void loadCommentData()
    {
        if (subscription != null && !subscription.isUnsubscribed()) subscription.unsubscribe();

        ContentService contentService = GlobalApplication.getGlobalApplicationContext().getContentService();
        subscription = contentService.getCommentData(board_id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(GlobalApplication.getGlobalApplicationContext().defaultSubscribeScheduler())
                .subscribe(new Subscriber<List<Comment>>(){
                    @Override
                    public void onCompleted(){
                        if (dataListener != null) dataListener.onCommentItemChanged(commentList);
                    }
                    @Override
                    public void onError(Throwable error)
                    {
                        Log.e(TAG, "Error loading Item ", error);
                    }
                    @Override
                    public void onNext(List<Comment> commentList)
                    {
                        Log.i(TAG, "Item loaded " + commentList.get(0).comment_info);
                        CommentsViewModel.this.commentList = commentList;
                    }
                });
    }

    public void setComment(Comment comment)
    {
        this.comment = comment;
        notifyChange();
    }

    public String getUserID()
    {
        return comment.user_id;
    }

    public String getUserName()
    {
        return comment.user_name;
    }

    public String getUserThumnail()
    {
        return comment.user_thumnail;
    }

    public String getDate()
    {
        String date_string = comment.date;
        String date = "";

        Log.e("DATE_STRING",date_string);
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.KOREA);

        Date current = new Date(System.currentTimeMillis());


        try{
            Date d = format.parse(date_string) ;
            long time_long = current.getTime() - d.getTime();
            int time = (int)time_long/1000;

            Log.e("Time", ""+time);
            if( time < 60 )
            {
                date = "방금";
                //boardData.get(position).date = "방금";
                //holder.binding.date.setText("방금");
            }
            else if( time >=60 && time < 3600 )
            {
                time = time/60;
                date = ""+time+"분 전";
                //boardData.get(position).date = ""+time+"분 전";
                //notifyDataSetChanged();
                //holder.binding.date.setText(""+time+"분 전");
            }
            else if( time >=3600 && time < 86400 )
            {
                time = time/3600;
                date = ""+time+"시간 전";
                //  holder.binding.date.setText(""+time+"시간 전");
            }
            else
            {
                date = date_string;
                //holder.binding.date.setText(date_string);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return date;
    }

    public String getCommentText()
    {
        return comment.comment_info;
    }

    public interface DataListener
    {
        void onCommentItemChanged(List<Comment> comment);
        void create(View view);
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
                //createContents.edit_text = editable.toString();
                Log.e("Changed","-------------------------------------"+text.get());

            }
        }
    };

    public String getImageUrl() {
        // The URL will usually come from a model (i.e Profile)
        return comment.user_thumnail;
    }

    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl)
                .into(view);
    }

    @Override
    public void destroy() {

    }
}
