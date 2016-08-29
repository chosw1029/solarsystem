package nextus.solarsystem.adapter;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import nextus.solarsystem.R;
import nextus.solarsystem.databinding.PointUserItemBinding;
import nextus.solarsystem.databinding.UserdataLayoutItemBinding;
import nextus.solarsystem.model.UserData;
import nextus.solarsystem.viewmodel.PointInputViewModel;
import nextus.solarsystem.viewmodel.PointViewModel;

/**
 * Created by chosw on 2016-08-11.
 */

public class AttendantAdapter extends RecyclerView.Adapter<AttendantAdapter.UserDataViewHolder> {

    private List<UserData> userDataList;
    private Context context;

    public AttendantAdapter(Context context) {
        this.context = context;
        this.userDataList = Collections.emptyList();
    }

    public AttendantAdapter(List<UserData> userDataList) {
        this.userDataList = userDataList;
    }

    public void setUserDataList(List<UserData> userDataList) {
        this.userDataList = userDataList;
        notifyDataSetChanged();
    }

    public List<UserData> getUserDataList() { return userDataList; }

    @Override
    public UserDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PointUserItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.point_user_item, parent, false);
        return new UserDataViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(UserDataViewHolder holder, int position) {
        holder.bindItem(userDataList.get(position));
//        holder.binding.individualMoney.setText(holder.binding.getViewModel2().getIndividual_money());
    }

    @Override
    public int getItemCount() {
        return userDataList.size();
    }

    public static class UserDataViewHolder extends RecyclerView.ViewHolder {

        final PointUserItemBinding binding;

        public UserDataViewHolder(PointUserItemBinding binding) {
            super(binding.placeCard);
            this.binding = binding;
        }

        void bindItem(UserData userData) {
            if (binding.getViewModel() == null) {
                binding.setViewModel(new PointViewModel(itemView.getContext(), null));
                binding.setViewModel2(new PointInputViewModel(itemView.getContext(), null, null));
                binding.getViewModel().setUserData(userData);
                Log.e("SET_VIEWMODEL","");
            } else {
                 binding.getViewModel().setUserData(userData);
                binding.getViewModel2().setUserData(userData);
            }
        }
    }
}
