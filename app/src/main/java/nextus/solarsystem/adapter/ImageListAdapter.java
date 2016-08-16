package nextus.solarsystem.adapter;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nextus.solarsystem.R;
import nextus.solarsystem.databinding.AddImageLayoutBinding;
import nextus.solarsystem.viewmodel.CreateContentsViewModel;

/**
 * Created by chosw on 2016-08-11.
 */

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.AddedImageViewHolder> {

    private List<Bitmap> imageList;
    private Context context;

    public ImageListAdapter(Context context) {
        this.context = context;
        this.imageList = Collections.emptyList();
    }

    public ImageListAdapter(List<Bitmap> imageList) {
        this.imageList = imageList;
    }

    public void setImageList(List<Bitmap> imageList) {
        this.imageList = imageList;
    }

    public List<Bitmap> getImageList() { return imageList; }

    @Override
    public AddedImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        AddImageLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.add_image_layout, parent, false);

        return new AddedImageViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(AddedImageViewHolder holder, int position) {
        holder.bindItem(imageList);
        holder.binding.addedImage.setImageBitmap(imageList.get(position));
        holder.binding.cancelButton.setVisibility(View.VISIBLE);
        holder.binding.cancelButton.setImageResource(R.drawable.ic_close_black_24dp);
        holder.binding.cancelButton.setTag(position);


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
        final AddImageLayoutBinding binding;

        public AddedImageViewHolder(AddImageLayoutBinding binding) {
            super(binding.placeCard);
            this.binding = binding;
        }

        void bindItem(List<Bitmap> addedImage) {
            if (binding.getViewModel() == null) {
                binding.setViewModel(new CreateContentsViewModel(itemView.getContext(), null));
            } else {
                binding.getViewModel().setAddedImage(addedImage);
            }
        }
    }
}
