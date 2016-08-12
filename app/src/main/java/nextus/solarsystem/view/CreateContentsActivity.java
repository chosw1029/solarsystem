package nextus.solarsystem.view;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import nextus.solarsystem.R;
import nextus.solarsystem.databinding.ActivityCreateContentsBinding;
import nextus.solarsystem.viewmodel.CreateContentsViewModel;

public class CreateContentsActivity extends Activity implements CreateContentsViewModel.openGalleryListener{

    private ActivityCreateContentsBinding binding;
    private CreateContentsViewModel createContentsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_contents);
        createContentsViewModel = new CreateContentsViewModel(this);
        binding.setViewModel(createContentsViewModel);
        setUpDisplay();



    }

    public void setUpDisplay()
    {
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

        int width = (int) (display.getWidth() * 0.95); //Display 사이즈의 70%
        int height = (int) (display.getHeight() * 0.95);  //Display 사이즈의 90%

        getWindow().getAttributes().width = width;
        getWindow().getAttributes().height = height;
    }

    @Override
    public void openGallery(View view) {
        Snackbar.make(view, "TEST", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
    }
}
