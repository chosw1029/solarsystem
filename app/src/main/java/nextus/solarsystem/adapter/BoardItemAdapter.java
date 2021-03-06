package nextus.solarsystem.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import nextus.solarsystem.R;
import nextus.solarsystem.databinding.BoardItemRecyclerImgBinding;
import nextus.solarsystem.model.BoardItem;
import nextus.solarsystem.viewmodel.BoardItemViewModel;

/**
 * Created by chosw on 2016-08-11.
 */

public class BoardItemAdapter extends RecyclerView.Adapter<BoardItemAdapter.BoardItemViewHolder> {

    private BoardItem boardData;
    private Context context;


    public BoardItemAdapter(Context context) {
        this.context = context;
        this.boardData = new BoardItem();
    }

    public BoardItemAdapter(BoardItem boardData) {
        this.boardData = boardData;
    }

    public void setBoardData(BoardItem boardData) {
        this.boardData = boardData;
    }

    @Override
    public BoardItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BoardItemRecyclerImgBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.board_item_recycler_img,
                parent,
                false);
        return new BoardItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(BoardItemViewHolder holder, int position) {
        holder.bindItem(boardData.getBoardData().get(position));
        holder.binding.placeCard.setTag(position);

        if(boardData.getBoardData().get(position).image_count > 0)
        {
            holder.binding.getViewModel().getAdapter().setImageList(boardData.getBoard_img().get(position));
        }
        else
        {
            holder.binding.getViewModel().getAdapter().setImageList(Collections.<String>emptyList());
        }
        if(boardData.getBoardData().get(position).user_thumnail != null)
            Glide.with(this.context).load(boardData.getBoardData().get(position).user_thumnail).thumbnail(0.1f).centerCrop().into(holder.binding.userIcon);
    }

    @Override
    public int getItemCount() {
        if(boardData.getBoardData() == null ) return 0;
        else return boardData.getBoardData().size();
    }

    public static class BoardItemViewHolder extends RecyclerView.ViewHolder {
        final BoardItemRecyclerImgBinding binding;

        public BoardItemViewHolder(BoardItemRecyclerImgBinding binding) {
            super(binding.placeCard);
            this.binding = binding;
        }

        void bindItem(BoardItem.BoardData boardItem) {
            if (binding.getViewModel() == null) {
                binding.setViewModel(new BoardItemViewModel(itemView.getContext(), boardItem));
                binding.getViewModel().setRecyclerView(binding.boardRecycler);
            } else {
                binding.getViewModel().setBoardItem(boardItem);
            }
        }
    }

}
