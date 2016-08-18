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

    public BoardItemRecyclerAdapter(Context context) {
        this.context = context;
        this.imageList = Collections.emptyList();
    }

    public BoardItemRecyclerAdapter(List<String> imageList) {
        this.imageList = imageList;
    }

    public void setImageList(List<String> imageList) {
        if( this.imageList != imageList )
        {
            this.imageList = imageList;
            notifyDataSetChanged();
        }
    }

    public List<String> getImageList() { return imageList; }

    @Override
    public AddedImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BoardImageViewBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.board_image_view, parent, false);
        return new AddedImageViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(AddedImageViewHolder holder, int position) {
        holder.bindItem(imageList);

        /*
        holder.bindItem(imageList);
        holder.binding.addedImage.setImageBitmap(imageList.get(position));
        holder.binding.cancelButton.setVisibility(View.VISIBLE);
        holder.binding.cancelButton.setImageResource(R.drawable.ic_close_black_24dp);
        holder.binding.cancelButton.setTag(position);
*/

        //holder.bindItem(boardData.get(position));
        //Glide.with(this.context).load(boardData.get(position).board_img).thumbnail(0.1f).centerCrop().into(holder.binding.boardImg);

        //holder.binding.userId.setText(GlobalApplication.getGlobalApplicationContext().getUserProfile().getNickname());
       // holder.binding.userIcon
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public static class AddedImageViewHolder extends RecyclerView.ViewHolder {
        final BoardImageViewBinding binding;

        public AddedImageViewHolder(BoardImageViewBinding binding) {
            super(binding.placeCard);
            this.binding = binding;
        }

        void bindItem(List<String> data) {
            if (binding.getViewModel() == null) {
                binding.setViewModel(new BoardItemViewModel(itemView.getContext(), null));
            }
            else
            {
                binding.getViewModel().setData(data);
            }

        }
    }
}
