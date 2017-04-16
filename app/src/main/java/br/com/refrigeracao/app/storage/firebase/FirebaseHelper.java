package br.com.refrigeracao.app.storage.firebase;

import android.renderscript.Short4;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import br.com.refrigeracao.app.model.User;

/**
 * Created by elder on 2017-04-15.
 */

public class FirebaseHelper {

    private final static String STORAGE_URL = "gs://refrigeracao-5eb36.appspot.com";
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DatabaseReference mRef;
    private static User mUser = new User(FirebaseAuth.getInstance().getCurrentUser());


    public static DatabaseReference getDatabaseReference(String path) {
        return database.getReference(mUser.getId() + path);
    }

    public static StorageReference getStorageReference(String imageName) {

        FirebaseStorage storage = FirebaseStorage.getInstance();

        // Create a storage reference from our app
        StorageReference storageRef = storage.getReferenceFromUrl(STORAGE_URL);

        // Create a reference to "mountains.jpg"
        StorageReference mRef = storageRef.child(imageName + ".jpg");

        // Create a reference to 'images/mountains.jpg'
        StorageReference mImagesRef = storageRef.child("images/" + imageName + ".jpg");

        // While the file names are the same, the references point to different files
        mRef.getName().equals(mImagesRef.getName());    // true
        mRef.getPath().equals(mImagesRef.getPath());    // false

        return mRef;
    }

}
