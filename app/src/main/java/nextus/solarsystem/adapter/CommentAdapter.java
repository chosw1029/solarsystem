package nextus.solarsystem.adapter;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import nextus.solarsystem.GlobalApplication;
import nextus.solarsystem.R;
import nextus.solarsystem.databinding.CommentListLayoutBinding;
import nextus.solarsystem.model.Comment;
import nextus.solarsystem.viewmodel.CommentsViewModel;

/**
 * Created by chosw on 2016-08-11.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.AddedImageViewHolder> {

    private List<Comment> commentList;
    private Context context;

    public CommentAdapter(Context context) {
        this.context = context;
        this.commentList = Collections.emptyList();
    }

    public CommentAdapter(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public List<Comment> getCommentList() { return commentList; }

    @Override
    public AddedImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CommentListLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.comment_list_layout, parent, false);

        return new AddedImageViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(AddedImageViewHolder holder, int position) {
        holder.bindItem(commentList.get(position));

        if( Integer.parseInt(commentList.get(position).user_id) == GlobalApplication.getGlobalApplicationContext().getUserProfile().getId() )
            holder.binding.delete.setVisibility(View.VISIBLE);

        Log.e("Adapter", ""+position);
        //holder.binding.textView2.setText("SETSEsefsefesfsEFSEFEF");
        //holder.bindItem(boardData.get(position));
        //Glide.with(this.context).load(boardData.get(position).board_img).thumbnail(0.1f).centerCrop().into(holder.binding.boardImg);
        //holder.binding.userId.setText(GlobalApplication.getGlobalApplicationContext().getUserProfile().getNickname());
        // holder.binding.userIcon
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public static class AddedImageViewHolder extends RecyclerView.ViewHolder {
        final CommentListLayoutBinding binding;

        public AddedImageViewHolder(CommentListLayoutBinding binding) {
            super(binding.placeCard);
            this.binding = binding;
        }

        void bindItem(Comment comment) {
            if (binding.getViewModel() == null) {
                binding.setViewModel(new CommentsViewModel(itemView.getContext()));
                binding.getViewModel().setComment(comment);
            } else {
                 binding.getViewModel().setComment(comment);
            }
        }
    }
}
