package nextus.solarsystem.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;

import nextus.solarsystem.GlobalApplication;
import nextus.solarsystem.model.BoardItem;
import nextus.solarsystem.model.Comment;
import nextus.solarsystem.utils.ContentService;
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
    private int board_id;

    public CommentsViewModel(Context context)
    {
        this.context = context;
    }

    public void setBoardID(int board_id)
    {
        this.board_id = board_id;
    }

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
        return comment.date;
    }

    public String getCommentText()
    {
        return comment.comment_info;
    }

    public interface DataListener
    {
        void onCommentItemChanged(List<Comment> comment);
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
                Log.e("Changed","-------------------------------------"+editable.toString());
            }
        }
    };

    @Override
    public void destroy() {

    }
}
