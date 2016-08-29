package nextus.solarsystem.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import nextus.solarsystem.R;
import nextus.solarsystem.adapter.AttendantAdapter;
import nextus.solarsystem.adapter.UserDataAdapter;
import nextus.solarsystem.databinding.ActivityPointInputBinding;
import nextus.solarsystem.model.UserData;
import nextus.solarsystem.viewmodel.PointInputViewModel;


public class PointInputActivity extends AppCompatActivity implements PointInputViewModel.DataChangedListener{

    private ActivityPointInputBinding binding;
    private PointInputViewModel pointInputViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        ArrayList<UserData> user_list = intent.getParcelableArrayListExtra("userData");

        binding = DataBindingUtil.setContentView(this, R.layout.activity_point_input);
        pointInputViewModel = new PointInputViewModel(this, this, user_list);
        binding.setViewModel(pointInputViewModel);
        setRecyclerView(binding.attendantRecycler);
    }

    public void setRecyclerView(RecyclerView recyclerView)
    {
        AttendantAdapter adapter = new AttendantAdapter(this);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }


    @Override
    public void attendantChanged(List<UserData> attendant_list) {
        ((AttendantAdapter)binding.attendantRecycler.getAdapter()).setUserDataList(attendant_list);
    }
}
