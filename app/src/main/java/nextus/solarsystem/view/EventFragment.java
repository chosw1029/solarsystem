package nextus.solarsystem.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nextus.solarsystem.R;
import nextus.solarsystem.databinding.EventPageBinding;


/**
 * Created by chosw on 2016-08-31.
 */

public class EventFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private EventPageBinding binding;

    public EventFragment()
    {

    }

    public static EventFragment newInstance(int sectionNumber) {
        EventFragment fragment = new EventFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(container.getContext()), R.layout.event_page, null, false);


        return binding.getRoot();
    }
}
