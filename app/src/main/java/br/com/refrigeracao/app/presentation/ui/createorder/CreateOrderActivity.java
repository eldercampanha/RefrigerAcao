package br.com.refrigeracao.app.presentation.ui.createorder;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.refrigeracao.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.io.File;

import br.com.refrigeracao.app.model.Order;
import br.com.refrigeracao.app.presentation.helper.ImageHelper;
import br.com.refrigeracao.app.presentation.helper.Regex;
import br.com.refrigeracao.app.presentation.ui.orderdetails.OrderDetailsActivity;
import br.com.refrigeracao.app.storage.firebase.FirebaseInterface;
import br.com.refrigeracao.app.storage.firebase.FirebaseService;
import br.com.refrigeracao.app.util.PermissionUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class CreateOrderActivity extends AppCompatActivity {

    @BindView(R.id.input_brand)
    TextInputLayout brand;
    @BindView(R.id.input_model)
    TextInputLayout model;
    @BindView(R.id.input_description)
    EditText description;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.productImage)
    ImageView imageview;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private String mCurrentPhotoPath;
    private Uri imageUri;
    public Order mOrder;
    private String fileName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);
        ButterKnife.bind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_create_order, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.save:
                save();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void save() {

        if(brand.getEditText().getText().toString().length() > 0 &&
                model.getEditText().getText().toString().length() > 0 &&
                description.getText().toString().length() > 0){

            mOrder = new Order();
            mOrder.setModel(model.getEditText().getText().toString());
            mOrder.setDescription(description.getText().toString());
            mOrder.setBrand(brand.getEditText().getText().toString());

            FirebaseService.createOrder(mOrder, new FirebaseInterface.CreateOrder() {
                @Override
                public void sucess(String orderId) {
                    Toast.makeText(CreateOrderActivity.this, orderId, Toast.LENGTH_LONG).show();
                    if(imageUri!= null)
                        uploadImageToFirebase(orderId);
                    else
                        finish();
                }

                @Override
                public void fail(String error) {
                    Toast.makeText(CreateOrderActivity.this, error, Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    @OnClick(R.id.productImage)
    public void imageClicked(ImageView image) {

        PermissionUtil.verifyStoragePermissions(this);

        Intent chooserIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // create a path to save the image
        fileName = "temp.jpg";
        File f = new File(Environment.getExternalStorageDirectory(), fileName);
        imageUri = Uri.fromFile(f);

        chooserIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        startActivityForResult(chooserIntent, REQUEST_IMAGE_CAPTURE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            if (imageUri != null) {
                Glide.with(this).load(imageUri).diskCacheStrategy(DiskCacheStrategy.NONE).into(imageview);
                progressBar.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(this, "Error while capturing Image", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void uploadImageToFirebase(String name) {
        Bitmap bitmap = ImageHelper.uriToBitmap(imageUri, CreateOrderActivity.this);

        if (bitmap != null) {

            // update order Image on Firebase
            mOrder.setImageName(name);
            FirebaseService.updateOrder(mOrder);
            FirebaseService.uploadImage(bitmap, name, new FirebaseInterface.UploadImage() {
                @Override
                public void success(Uri uri) {
                    finish();
                }

                @Override
                public void fail(String error) {

                }
            });

        }
    }

}
