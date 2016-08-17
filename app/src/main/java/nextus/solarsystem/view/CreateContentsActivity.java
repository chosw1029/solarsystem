package nextus.solarsystem.view;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import org.apache.commons.io.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import nextus.solarsystem.R;
import nextus.solarsystem.adapter.ImageListAdapter;
import nextus.solarsystem.databinding.ActivityCreateContentsBinding;
import nextus.solarsystem.utils.BitmapOrientation;
import nextus.solarsystem.utils.ContentService;
import nextus.solarsystem.utils.FilePath;
import nextus.solarsystem.viewmodel.CreateContentsViewModel;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kakao.auth.StringSet.file;

public class CreateContentsActivity extends Activity implements CreateContentsViewModel.EventListener {

    private ActivityCreateContentsBinding binding;
    private CreateContentsViewModel createContentsViewModel;

    private int PICK_IMAGE_REQUEST = 1;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    private ArrayList<Bitmap> addedImg = new ArrayList<>();
    private ArrayList<String> fileName = new ArrayList<>();

    ProgressDialog mProgressDialog;
    Uri filePath;
    File file;

    public String selectedFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_contents);
        createContentsViewModel = new CreateContentsViewModel(this, this);
        binding.setViewModel(createContentsViewModel);
        binding.getViewModel().setImage(binding.userThumnail);
        setUpDisplay();
        setUpRecyclerView(binding.imageList);

    }

    public void setUpRecyclerView(RecyclerView recyclerView)
    {
        ImageListAdapter adapter = new ImageListAdapter(this);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
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

        if(Build.VERSION.SDK_INT > 22 )
        {
            requestPermissions((new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}), REQUEST_CODE_ASK_PERMISSIONS);
        }
        else
        {
            Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickIntent.setType("image/*");
            startActivityForResult(pickIntent, PICK_IMAGE_REQUEST);
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.cancel_button:
                int position = (int) view.getTag();
                ImageListAdapter adapter = (ImageListAdapter) binding.imageList.getAdapter();
                adapter.getImageList().remove(position);
                adapter.notifyDataSetChanged();
                break;
            case R.id.upload_button:
                showProgressDialog();
                uploadFile(view);
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            selectedFilePath = FilePath.getPath(this, filePath);

            String test = data.getData().toString();

            file = new File(filePath.getPath());

            Log.e("AbsolutePath",""+test);
            fileName.add(file.getName());
            try {
                AssetFileDescriptor afd = getContentResolver().openAssetFileDescriptor(filePath, "r");
                BitmapFactory.Options opt = new BitmapFactory.Options();
                opt.inSampleSize = 4;
                Bitmap bitmap2 = BitmapFactory.decodeFileDescriptor(afd.getFileDescriptor(), null, opt);
                Bitmap bitmap = BitmapOrientation.rotateBitmap(getApplicationContext(), filePath, test, bitmap2);
                //bitmap2.recycle();

                //Getting the Bitmap from Gallery
                //bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Bitmap temp = resizeBitmapImage(bitmap, 300);
                //Setting the Bitmap to ImageView

                addedImg.add(bitmap);
                binding.getViewModel().setImageList(addedImg);
                //adapter.notifyDataSetChanged();

                afd.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }



    @Override
    public void onImageListChanged(List<Bitmap> imageList) {
        ImageListAdapter adapter = (ImageListAdapter) binding.imageList.getAdapter();
        adapter.setImageList(imageList);
        adapter.notifyDataSetChanged();
    }

    private void uploadFile(final View view) {
        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri
        //File test = FileUtils.getFile(file, filePath);
/*
        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), getByteImage(addedImg.get(0)));

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("picture", file.getName(), requestFile);

        // add another part within the multipart request
        String userName = binding.getViewModel().createContents.user_name;

        RequestBody description =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), userName);
*/
        RequestBody text = createPartFromString(binding.getViewModel().createContents.edit_text);
        RequestBody date = createPartFromString(new SimpleDateFormat("yyyy/MM/dd HH:mm").format(new Date(System.currentTimeMillis())));
        RequestBody userName = createPartFromString(binding.getViewModel().createContents.user_name);
        RequestBody count = createPartFromString(""+addedImg.size());


        MultipartBody.Part body;
        if( addedImg.size() > 0)
        {
            body = prepareFilePart("photo");
        }
        else
            body = MultipartBody.Part.createFormData("","");

        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("text", text);
        map.put("date", date);
        map.put("userName", userName);
        map.put("count", count);


        // finally, execute the request
        Call<ResponseBody> call = ContentService.Factory.create().uploadFileWithPartMap(map, body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   Response<ResponseBody> response) {
                //Snackbar.make(view, "업로드가 완료되었습니다.", Snackbar.LENGTH_SHORT).show();
                hideProgressDialog();
                finish();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
            }
        });
    }

    public byte[] getByteImage(Bitmap bmp)
    {
        try
        {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            baos.close();
            bmp.recycle();
            bmp = null;
            return imageBytes;
        } catch (Exception e)
        {
            return null;
        }
    }

    public static final String MULTIPART_FORM_DATA = "multipart/form-data";

    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                MediaType.parse(MULTIPART_FORM_DATA), descriptionString);
    }

    @NonNull
    private MultipartBody.Part prepareFilePart(String partName) {
        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri
        //File file = FileUtils.getFile(this, fileUri);

        // create RequestBody instance from file
        //RequestBody requestFile = RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), getByteImage(addedImg.get(0)));

        RequestBody requestFile = RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), new File(selectedFilePath));
        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, ""+file.getName()+".png", requestFile);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    pickIntent.setType("image/*");
                    startActivityForResult(pickIntent, PICK_IMAGE_REQUEST);

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Loading");
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        binding.getViewModel().destroy();
        addedImg.clear();
        addedImg = null;
        binding = null;
    }
}
