package nextus.solarsystem.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import nextus.solarsystem.R;
import nextus.solarsystem.adapter.UserDataAdapter;
import nextus.solarsystem.databinding.FragmentPointBinding;
import nextus.solarsystem.model.UserData;
import nextus.solarsystem.utils.ContentService;
import nextus.solarsystem.utils.DividerItemDecoration;
import nextus.solarsystem.viewmodel.PointViewModel;

/**
 * Created by chosw on 2016-08-25.
 */

public class PointFragment extends Fragment implements PointViewModel.DataListener{

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private FragmentPointBinding binding;

    public PointFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PointFragment newInstance(int sectionNumber) {
        PointFragment fragment = new PointFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(container.getContext()), R.layout.fragment_point, null, false);
        PointViewModel pointViewModel = new PointViewModel(container.getContext(), this);
        binding.setViewModel(pointViewModel);

        setRecyclerView(binding.userRecycler);

        return binding.getRoot();
    }

    public void setRecyclerView(RecyclerView recyclerView)
    {
        UserDataAdapter adapter = new UserDataAdapter(getContext());
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), null));
    }

    @Override
    public void userDataChanged(List<UserData> userDataList) {
        ((UserDataAdapter)binding.userRecycler.getAdapter()).setUserDataList(userDataList);
    }
}
