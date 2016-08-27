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
import nextus.solarsystem.databinding.UserdataLayoutItemBinding;
import nextus.solarsystem.model.Comment;
import nextus.solarsystem.model.UserData;
import nextus.solarsystem.viewmodel.CommentsViewModel;
import nextus.solarsystem.viewmodel.PointViewModel;

/**
 * Created by chosw on 2016-08-11.
 */

public class UserDataAdapter extends RecyclerView.Adapter<UserDataAdapter.UserDataViewHolder> {

    private List<UserData> userDataList;
    private Context context;

    public UserDataAdapter(Context context) {
        this.context = context;
        this.userDataList = Collections.emptyList();
    }

    public UserDataAdapter(List<UserData> userDataList) {
        this.userDataList = userDataList;
    }

    public void setUserDataList(List<UserData> userDataList) {
        this.userDataList = userDataList;
        notifyDataSetChanged();
    }

    public List<UserData> getUserDataList() { return userDataList; }

    @Override
    public UserDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        UserdataLayoutItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.userdata_layout_item, parent, false);
        return new UserDataViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(UserDataViewHolder holder, int position) {
        holder.bindItem(userDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return userDataList.size();
    }

    public static class UserDataViewHolder extends RecyclerView.ViewHolder {

        final UserdataLayoutItemBinding binding;

        public UserDataViewHolder(UserdataLayoutItemBinding binding) {
            super(binding.placeCard);
            this.binding = binding;
        }

        void bindItem(UserData userData) {
            if (binding.getViewModel() == null) {
                binding.setViewModel(new PointViewModel(itemView.getContext(), null));
                binding.getViewModel().setUserData(userData);
            } else {
                 binding.getViewModel().setUserData(userData);
            }
        }
    }
}
