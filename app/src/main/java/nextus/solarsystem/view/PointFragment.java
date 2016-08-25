package nextus.solarsystem.view;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import nextus.solarsystem.R;

/**
 * Created by chosw on 2016-08-25.
 */

public class PointFragment extends Fragment {

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public PointFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public PointFragment newInstance(int sectionNumber) {
        PointFragment fragment = new PointFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_point, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
        videoSetting((VideoView)rootView.findViewById(R.id.videoView), rootView);
        videoSetting2((VideoView)rootView.findViewById(R.id.videoView2), rootView);
        return rootView;
    }

    public void videoSetting(final VideoView videoView, View rootView)
    {
        videoView.setVideoPath("http://restartallkill.nextus.co.kr/pokemongo/video/LinkinPark.mp4");
        final MediaController mediaController = new MediaController(getContext());
        videoView.setMediaController(mediaController);
        videoView.seekTo(1500);
        mediaController.setAnchorView(rootView.findViewById(R.id.video_anchor));
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaController.hide();
            }
        });
    }

    public void videoSetting2(VideoView videoView, View rootView)
    {
        videoView.setVideoPath("http://restartallkill.nextus.co.kr/pokemongo/video/yourvoice.mp4");
        final MediaController mediaController = new MediaController(getContext());
        videoView.setMediaController(mediaController);
        videoView.seekTo(1500);
        mediaController.setAnchorView(rootView.findViewById(R.id.video_anchor));
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                //mediaController.show(0);
            }
        });
    }

}
