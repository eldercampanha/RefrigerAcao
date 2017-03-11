package br.com.refrigeracao.app.model;

import android.graphics.Bitmap;
import android.net.Uri;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by elder-dell on 2017-03-10.
 */

public class User {

    private String id;
    private String name;
    private String email;
    private Uri photoUrl;

    public User(FirebaseUser user) {
        this.id = user.getUid();
        this.name = user.getDisplayName();
        this.email = user.getEmail();
        this.photoUrl = user.getPhotoUrl();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Uri getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(Uri photoUrl) {
        this.photoUrl = photoUrl;
    }

}
