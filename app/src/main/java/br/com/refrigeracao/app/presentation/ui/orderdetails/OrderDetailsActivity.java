package br.com.refrigeracao.app.presentation.ui.orderdetails;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.Date;

import br.com.refrigeracao.app.model.Order;
import br.com.refrigeracao.app.presentation.helper.ImageHelper;
import br.com.refrigeracao.app.storage.firebase.FirebaseService;
import br.com.refrigeracao.app.storage.firebase.FirebaseInterface;
import br.com.refrigeracao.app.util.PermissionUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderDetailsActivity extends AppCompatActivity {

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.brand)
    TextView txtBrand;
    @BindView(R.id.model)
    TextView txtModel;
    @BindView(R.id.description)
    TextView txtDescription;
    @BindView(R.id.productImage)
    ImageView imageview;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private String mCurrentPhotoPath;
    private Uri imageUri;
    public Order mOrder;
    private String fileName;
    private boolean isRunning;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        ButterKnife.bind(this);

        isRunning = true;

        mOrder = (Order) getIntent().getSerializableExtra("ORDER");
        if(mOrder != null){
            txtBrand.setText(mOrder.getBrand());
            txtModel.setText(mOrder.getModel());
            txtDescription.setText(mOrder.getDescription());
            setUpFirebaseRuntime(mOrder);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRunning = false;
    }

    public void setUpFirebaseRuntime(Order order) {

        FirebaseService.getSingleOrder(order.getKey(), new FirebaseInterface.SingleOrder() {

            @Override
            public void sucess(Order order) {
                if (order != null) {
                    mOrder = order;
                    txtBrand.setText(order.getBrand());
                    txtModel.setText(order.getModel());
                    txtDescription.setText(order.getDescription());

                    if (order.getImageName() != null) {
                        loadImage(order);
                    }
                }
            }

            @Override
            public void fail(String error) {

            }
        });
    }

    private void loadImage(Order order) {

        FirebaseService.downloadImage(order.getImageName(), new FirebaseInterface.DownloadImage() {
            @Override
            public void success(Uri uri) {
                Glide.with(OrderDetailsActivity.this).load(uri)
                        .listener(new RequestListener<Uri, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e, Uri model, Target<GlideDrawable> target, boolean isFirstResource) {
                                progressBar.setVisibility(View.GONE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(GlideDrawable resource, Uri model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                progressBar.setVisibility(View.GONE);
                                return false;
                            }
                        })
                        .into(imageview);
            }

            @Override
            public void fail(String error) {

            }
        });
    }

    @OnClick(R.id.productImage)
    public void imageClicked(ImageView image) {

        PermissionUtil.verifyStoragePermissions(this);

        Intent chooserIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // create a path to save the image
        fileName = mOrder.getKey() +".jpg";
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
                uploadImageToFirebase();
                imageview.setAlpha(0.5f);
                progressBar.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(this, "Error while capturing Image", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void uploadImageToFirebase() {
        Bitmap bitmap = ImageHelper.uriToBitmap(imageUri, OrderDetailsActivity.this);

        if (bitmap != null) {

            // update order Image on Firebase
            mOrder.setImageName(fileName);
            FirebaseService.updateOrder(mOrder);
            FirebaseService.uploadImage(bitmap, fileName, new FirebaseInterface.UploadImage() {
                @Override
                public void success(Uri uri) {

                    if(!isRunning)
                        return;

                    imageview.setAlpha(1f);
                    // display taken picture
                    Glide.with(OrderDetailsActivity.this)
                            .load(uri)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .listener(new RequestListener<Uri, GlideDrawable>() {
                                @Override
                                public boolean onException(Exception e, Uri model, Target<GlideDrawable> target, boolean isFirstResource) {
                                    progressBar.setVisibility(View.GONE);
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(GlideDrawable resource, Uri model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                    progressBar.setVisibility(View.GONE);
                                    return false;
                                }
                            })
                            .into(imageview);

                }

                @Override
                public void fail(String error) {

                }
            });

        }
    }
}
