package br.com.refrigeracao.app.presentation.ui.home;

import android.content.Context;
import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import br.com.refrigeracao.app.model.User;

/**
 * Created by eldercampbaltazar@gmail.com on 2017-03-10.
 */

public class HomePresenter implements HomeContract.Presenter{

    private HomeContract.View view;
    private User mUser;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;

    public HomePresenter(){
        // TODO: USE DAGGER
        // TODO: SETUP AUTH LISTENER
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void setView(HomeContract.View view) {
        this.view = view;
    }

    @Override
    public void loadUser() {

        view.showLoading();

        // TODO: SET UP A USER TO DAGGER
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            if(this.mUser == null) mUser = new User(user);

            //TODO: REMVOVE THE BELOW CODE USED FOR TESTING
            String name ="Mr.Bean";
            Uri photoUrl = Uri.parse("https://files.incrivel.club/files/news/part_17/174910/3144810-58c75097b408eb88104a66dbb58eda09-1480946976-650-367728d9ce-1481678356.jpg");
            if(mUser.getName() == null){

                // Updating user display name and photo
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(name)
                            .setPhotoUri(photoUrl)
                            .build();
                    user.updateProfile(profileUpdates);
            }

            view.hideLoading();
            view.updateUserInfo(mUser.getName(), mUser.getPhotoUrl());
        }

    }

    @Override
    public void sigOut() {
        mAuth.signOut();
        view.signOut();
    }
}
