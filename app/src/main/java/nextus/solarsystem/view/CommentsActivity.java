package nextus.solarsystem.view;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import nextus.solarsystem.GlobalApplication;
import nextus.solarsystem.R;
import nextus.solarsystem.adapter.CommentAdapter;
import nextus.solarsystem.databinding.ActivityCommentsBinding;
import nextus.solarsystem.model.Comment;
import nextus.solarsystem.utils.ContentService;
import nextus.solarsystem.viewmodel.CommentsViewModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentsActivity extends Activity implements CommentsViewModel.DataListener{

    private ActivityCommentsBinding binding;
    private CommentsViewModel commentsViewModel;
    private CommentAdapter adapter;

    private int board_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_comments);
        commentsViewModel = new CommentsViewModel(this);
        commentsViewModel.setDataListener(this);
        commentsViewModel.setBoardID(getIntent().getIntExtra("board_id", 0));
        commentsViewModel.loadCommentData();
        binding.setViewModel(commentsViewModel);
        setUpDisplay();
        setUpRecyclerView(binding.commentsRecycler);

        board_id = getIntent().getIntExtra("board_id", 0);
    }

    public void setUpRecyclerView(RecyclerView recyclerView)
    {
        adapter = new CommentAdapter(this);
        recyclerView.setAdapter(adapter);
        //recyclerView.addItemDecoration(new DividerItemDecoration());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    public void setUpDisplay()
    {
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

        int width =  (int)(display.getWidth() * 0.95); //Display 사이즈의 70%
        int height = (int)(display.getHeight() * 0.95);  //Display 사이즈의 90%

        getWindow().getAttributes().width = width;
        getWindow().getAttributes().height = height;
    }

    @Override
    public void onCommentItemChanged(List<Comment> comment) {
        adapter = (CommentAdapter) binding.commentsRecycler.getAdapter();
        adapter.setCommentList(comment);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void create(final View view) {
        //GlobalApplication.getGlobalApplicationContext().getUserProfile().getNickname()
        String user_id = ""+ GlobalApplication.getGlobalApplicationContext().getUserProfile().getId();
        String user_name = GlobalApplication.getGlobalApplicationContext().getUserProfile().getNickname();
        String date = new SimpleDateFormat("yyyy/MM/dd HH:mm").format(new Date(System.currentTimeMillis()));

        Call<ResponseBody> call = ContentService.Factory.create().createComment(""+board_id, user_id, user_name, commentsViewModel.text.get(), date);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   Response<ResponseBody> response) {
                Snackbar.make(view, "댓글을 작성하였습니다.", Snackbar.LENGTH_SHORT).show();
                commentsViewModel.loadCommentData();
                binding.commentEdit.setText("");
                //finish();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
            }
        });
    }

    @Override
    public void onResume()
    {
        super.onResume();
        binding.getViewModel().loadCommentData();
    }

    public void update()
    {
        binding.getViewModel().loadCommentData();
    }
}
