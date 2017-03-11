package br.com.refrigeracao.app;

import android.app.Application;

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

        mDiComponent = DaggerDiComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public DiComponent getDaggerUiComponent() {
        return mDiComponent;
    }
}