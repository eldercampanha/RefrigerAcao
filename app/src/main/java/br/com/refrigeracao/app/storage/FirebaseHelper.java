package br.com.refrigeracao.app.storage;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.refrigeracao.app.model.User;

/**
 * Created by elder on 2017-04-15.
 */

public class FirebaseHelper {

    private final static String BASE_URL = "https://refrigeracao-5eb36.firebaseio.com";
    private  static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DatabaseReference mRef;
    private static User mUser = new User(FirebaseAuth.getInstance().getCurrentUser());


    public static DatabaseReference getDatabaseReference(String path) {
        return database.getReference(mUser.getId()+path);
    }

}
