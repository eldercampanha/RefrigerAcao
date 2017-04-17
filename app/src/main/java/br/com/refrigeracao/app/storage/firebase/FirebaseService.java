package br.com.refrigeracao.app.storage.firebase;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import br.com.refrigeracao.app.model.Order;

/**
 * Created by elder on 2017-04-15.
 */

public class FirebaseService {

    public static final String TAG = FirebaseService.class.getSimpleName();

    public static void getOrders(final FirebaseInterface.Orders ordersInterface){

        final ArrayList<Order> orders = new ArrayList<>();
        DatabaseReference mRef = FirebaseHelper.getDatabaseReference("/orders");

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot orderSnapshot : dataSnapshot.getChildren()){
                    Order order = orderSnapshot.getValue(Order.class);
                    if(order.getKey() == null)
                        order.setKey(orderSnapshot.getKey());
                    orders.add(order);
                }
                ordersInterface.onSuccess(orders);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
               ordersInterface.onFail(databaseError.toString());
            }
        });

    }


    public static void getSingleOrder(final String orderKey, final FirebaseInterface.SingleOrder singleOrderInterface){
        DatabaseReference mRef = FirebaseHelper.getDatabaseReference("/orders");
        mRef.child(orderKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Order order = (Order)dataSnapshot.getValue(Order.class);
                if(order!=null)
                    singleOrderInterface.sucess(order);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i(TAG, databaseError.getMessage());
                singleOrderInterface.fail(databaseError.toString());
            }
        });

    }

    public static void createOrder(final Order order, final FirebaseInterface.CreateOrder createOrderInterface){

        DatabaseReference mRef = FirebaseHelper.getDatabaseReference("/orders");
        order.setKey(mRef.push().getKey());
        mRef.child(order.getKey()).setValue(order).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                    createOrderInterface.sucess(order.getKey());
                else
                    createOrderInterface.fail(task.getException().getMessage());
            }
        });

    }

    public static void updateOrder(final Order order){
        DatabaseReference mRef = FirebaseHelper.getDatabaseReference("/orders");
        mRef.child(order.getKey()).setValue(order);
    }

    public static void uploadImage(Bitmap bitmap, String imageName, final FirebaseInterface.UploadImage uploadImageInterface) {

        StorageReference mRef = FirebaseHelper.getStorageReference(imageName);
        mRef.child(imageName);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Log.i(TAG, exception.getMessage());
                uploadImageInterface.fail(exception.getMessage());

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                Log.i(TAG, downloadUrl.toString());
                uploadImageInterface.success(downloadUrl);
            }
        });
    }

    public static void downloadImage(String imageName, final FirebaseInterface.DownloadImage downloadImageInterface){

        StorageReference mRef = FirebaseHelper.getStorageReference(imageName);

        mRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                downloadImageInterface.success(uri);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                downloadImageInterface.fail(exception.getMessage());
            }
        });
    }

}
