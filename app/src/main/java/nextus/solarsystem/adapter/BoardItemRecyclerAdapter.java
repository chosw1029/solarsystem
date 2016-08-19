package nextus.solarsystem.adapter;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.Collections;
import java.util.List;

import nextus.solarsystem.R;
import nextus.solarsystem.databinding.AddImageLayoutBinding;
import nextus.solarsystem.databinding.BoardImageViewBinding;
import nextus.solarsystem.viewmodel.BoardItemViewModel;
import nextus.solarsystem.viewmodel.BoardRecycleItemViewModel;
import nextus.solarsystem.viewmodel.CreateContentsViewModel;

/**
 * Created by chosw on 2016-08-11.
 */

public class BoardItemRecyclerAdapter extends RecyclerView.Adapter<BoardItemRecyclerAdapter.AddedImageViewHolder> {

    private List<String> imageList;
    private Context context;

    public BoardItemRecyclerAdapter(Context context, List<String> data) {
        this.context = context;
        this.imageList = data;
    }

    public BoardItemRecyclerAdapter(List<String> imageList) {
        this.imageList = imageList;
    }


    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
        notifyDataSetChanged();
    }

    public List<String> getImageList() { return imageList; }

    @Override
    public AddedImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BoardImageViewBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.board_image_view, parent, false);
        return new AddedImageViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(AddedImageViewHolder holder, int position) {
        holder.bindItem(imageList, this);
        //if( (int) holder.binding.boardImage.getTag(holder.binding.boardImage.getId()) == position )
        Glide.with(context).load(imageList.get(position)).thumbnail(0.1f).centerCrop().into(holder.binding.boardImage);
        //setImageList(holder.binding.getViewModel().getData());
    }

    @Override
    public int getItemCount() {
        if(imageList == null ) return 0;
        else
            return imageList.size();
    }

    public static class AddedImageViewHolder extends RecyclerView.ViewHolder {
        final BoardImageViewBinding binding;

        public AddedImageViewHolder(BoardImageViewBinding binding) {
            super(binding.placeCardImage);
            this.binding = binding;
        }

        void bindItem(List<String> data, BoardItemRecyclerAdapter adapter) {
            if( binding.getViewModel() == null )
            {
                binding.setViewModel(new BoardItemViewModel(itemView.getContext(), null));
                binding.getViewModel().setData(data);
                binding.getViewModel().setAdapter(adapter);
            }
            //binding.boardImage.setTag(binding.boardImage.getId(), position);
        }
    }
}
