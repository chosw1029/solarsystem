package nextus.solarsystem.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.Collections;
import java.util.List;

import nextus.solarsystem.GlobalApplication;
import nextus.solarsystem.R;
import nextus.solarsystem.databinding.BoardItemRecyclerImgBinding;
import nextus.solarsystem.model.BoardItem;
import nextus.solarsystem.viewmodel.BoardItemViewModel;
import nextus.solarsystem.viewmodel.MainViewModel;

/**
 * Created by chosw on 2016-08-11.
 */

public class BoardItemAdapter extends RecyclerView.Adapter<BoardItemAdapter.BoardItemViewHolder> {

    private List<BoardItem> boardData;
    private Context context;

    public BoardItemAdapter(Context context) {
        this.context = context;
        this.boardData = Collections.emptyList();
    }

    public BoardItemAdapter(List<BoardItem> boardData) {
        this.boardData = boardData;
    }

    public void setBoardData(List<BoardItem> boardData) {
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
        holder.bindItem(boardData.get(position));
        Glide.with(this.context).load(boardData.get(position).board_img).thumbnail(0.1f).centerCrop().into(holder.binding.boardImg);

        //holder.binding.userId.setText(GlobalApplication.getGlobalApplicationContext().getUserProfile().getNickname());
       // holder.binding.userIcon
    }

    @Override
    public int getItemCount() {
        return boardData.size();
    }

    public static class BoardItemViewHolder extends RecyclerView.ViewHolder {
        final BoardItemRecyclerImgBinding binding;

        public BoardItemViewHolder(BoardItemRecyclerImgBinding binding) {
            super(binding.placeCard);
            this.binding = binding;
        }

        void bindItem(BoardItem boardItem) {
            if (binding.getViewModel() == null) {
                binding.setViewModel(new BoardItemViewModel(itemView.getContext(), boardItem));
            } else {
                binding.getViewModel().setBoardItem(boardItem);
            }
        }
    }
}
