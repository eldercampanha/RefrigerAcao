package br.com.refrigeracao.app.presentation.ui.home;

import android.content.Context;
import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
    private Context mContext;

    public HomePresenter(){

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


            if(this.mUser == null) mUser = new User();
            this.mUser.setName(user.getDisplayName());
            this.mUser.setEmail(user.getEmail());
            this.mUser.setPhotoUrl(user.getPhotoUrl());
            this.mUser.setId(user.getUid());

            view.updateUserInfo(user.getDisplayName(), user.getPhotoUrl());
        }

    }

    @Override
    public void loadUserPicture() {

    }
}
