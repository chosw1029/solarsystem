package nextus.solarsystem.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import nextus.solarsystem.GlobalApplication;
import nextus.solarsystem.R;
import nextus.solarsystem.utils.ContentService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PasswordActivity extends AppCompatActivity {

    int password = 1029;
    String input;
    Context context;
    EditText editText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        context = this;

        editText = (EditText) findViewById(R.id.editPassword);

        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if( editText.getText().toString().length() == 0 )
                {
                    Snackbar.make(view, "Enter the password",Snackbar.LENGTH_SHORT).show();
                }
                else
                {
                    input = editText.getText().toString();
                    if(Integer.parseInt(input) != password)
                    {
                        Snackbar.make(view, "The password is incorrect.",Snackbar.LENGTH_SHORT).show();
                    }
                    else
                    {
                        nextStep();
                    }
                }

            }
        });

    }

    public void nextStep()
    {
        SharedPreferences prefs = getSharedPreferences("Access", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("Access", true);
        editor.commit();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


}
