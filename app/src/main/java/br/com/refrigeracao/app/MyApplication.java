package br.com.refrigeracao.app;

import android.app.Application;

import com.firebase.client.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

import br.com.refrigeracao.app.dagger.component.DaggerDiComponent;
import br.com.refrigeracao.app.dagger.component.DiComponent;
import br.com.refrigeracao.app.dagger.module.ApplicationModule;

/**
 * Created by elder-dell on 2017-03-11.
 */

public class MyApplication extends Application {

    private DiComponent mDiComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        // Dagger
        mDiComponent = DaggerDiComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

        Firebase.setAndroidContext(this);

        if(!FirebaseApp.getApps(this).isEmpty()){
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }
    }

    public DiComponent getDaggerUiComponent() {
        return mDiComponent;
    }
}