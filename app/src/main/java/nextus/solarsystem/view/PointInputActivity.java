package nextus.solarsystem.view;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import nextus.solarsystem.R;
import nextus.solarsystem.databinding.ActivityPointInputBinding;
import nextus.solarsystem.viewmodel.PointInputViewModel;


public class PointInputActivity extends AppCompatActivity implements PointInputViewModel.DataChangedListener{

    private ActivityPointInputBinding binding;
    private PointInputViewModel pointInputViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_point_input);
        pointInputViewModel = new PointInputViewModel(this, this);
        binding.setViewModel(pointInputViewModel);
        setRecyclerView(binding.attendantRecycler);
    }

    public void setRecyclerView(RecyclerView recyclerView)
    {
        recyclerView.setAdapter(null);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }


    @Override
    public void attendantChanged(List<String> attendant_list) {
        //binding.attendantRecycler.getAdapter()
    }
}
