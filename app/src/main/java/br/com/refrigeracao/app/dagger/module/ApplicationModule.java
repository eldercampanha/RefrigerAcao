package br.com.refrigeracao.app.dagger.module;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by elder-dell on 2017-03-11.
 */

@Module
public class ApplicationModule {

    private Application mApplication;

    public ApplicationModule(Application app) { mApplication = app; }

    @Provides
    @Singleton
    Application providesApplication() { return mApplication; }

    @Provides
    @Singleton
    Context providesContext(Application app) { return app.getBaseContext(); }
}