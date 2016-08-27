package nextus.solarsystem.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import nextus.solarsystem.R;


public class PointInputActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_input);

        ImageView imageView = (ImageView) findViewById(R.id.date_picker);

    }
}
