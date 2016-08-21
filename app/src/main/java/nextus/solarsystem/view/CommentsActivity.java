package nextus.solarsystem.view;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.WindowManager;

import java.util.List;

import nextus.solarsystem.R;
import nextus.solarsystem.adapter.CommentAdapter;
import nextus.solarsystem.databinding.ActivityCommentsBinding;
import nextus.solarsystem.model.Comment;
import nextus.solarsystem.viewmodel.CommentsViewModel;

public class CommentsActivity extends Activity implements CommentsViewModel.DataListener{

    private ActivityCommentsBinding binding;
    private CommentsViewModel commentsViewModel;
    private CommentAdapter adapter;

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
    }

    public void setUpRecyclerView(RecyclerView recyclerView)
    {
        adapter = new CommentAdapter(this);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    public void setUpDisplay()
    {
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

        int width =  (int)(display.getWidth() * 0.9); //Display 사이즈의 70%
        int height = (int)(display.getHeight() * 0.7);  //Display 사이즈의 90%

        getWindow().getAttributes().width = width;
        getWindow().getAttributes().height = height;
    }

    @Override
    public void onCommentItemChanged(List<Comment> comment) {
        adapter = (CommentAdapter) binding.commentsRecycler.getAdapter();
        adapter.setCommentList(comment);
        adapter.notifyDataSetChanged();
    }
}
