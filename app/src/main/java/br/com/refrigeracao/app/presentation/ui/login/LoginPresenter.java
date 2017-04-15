package br.com.refrigeracao.app.presentation.ui.login;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.app.refrigeracao.R;
import com.firebase.client.FirebaseApp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;

import javax.inject.Inject;

import br.com.refrigeracao.app.model.User;

/**
 * Created by elder-dell on 2017-03-09.
 */

public class LoginPresenter implements LoginContract.Presenter {

    private final String TAG = LoginPresenter.class.getSimpleName();
    private LoginContract.View view;
    private User mUser;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    private Context mContext;
    FirebaseUser user;
    public  LoginPresenter(Context context){
        mAuth = FirebaseAuth.getInstance();
        mContext = context;
    }

    @Override
    public void setView(LoginContract.View view) {
        this.view = view;
        addAuthStateListener();
    }

    @Override
    public void loadStatus() {

        // TODO: SET UP A USER TO DAGGER
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
                mUser = new User(user);
                // load home screen
                view.onAuthSuccess();
        }
    }

    @Override
    public void loginUserWithEmailAndPassword(String email, String password) {
        view.showLoading();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity)view, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        if(isLoggedIn())
                            view.onAuthSuccess();

                        if (!task.isSuccessful()) {
                            view.showError(R.string.loging_error);
                        }
                        view.hideLoading();
                    }
                });
    }


    public void addAuthStateListener(){

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (isLoggedIn()) {
                    // User is signed in
                    view.onAuthSuccess();
                } else {
                    // User is signed out
                }
            }
        };
    }

    public void removeAuthStateListener(){
        if (mAuthListener != null)
            mAuth.removeAuthStateListener(mAuthListener);
    }

    public boolean isLoggedIn(){
        return FirebaseAuth.getInstance().getCurrentUser() != null;
    }

}
