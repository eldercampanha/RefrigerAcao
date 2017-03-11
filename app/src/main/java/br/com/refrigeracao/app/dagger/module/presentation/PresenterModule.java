package br.com.refrigeracao.app.dagger.module.presentation;

import android.content.Context;

import javax.inject.Singleton;

import br.com.refrigeracao.app.dagger.scope.PerActivity;
import br.com.refrigeracao.app.presentation.ui.login.LoginContract;
import br.com.refrigeracao.app.presentation.ui.login.LoginPresenter;
import dagger.Module;
import dagger.Provides;

/**
 * Created by elder-dell on 2017-03-11.
 */

@Singleton
@Module
public class PresenterModule {

    @PerActivity
    @Provides
    LoginContract.Presenter provideLoginPresenter(Context context){
        return  new LoginPresenter(context);
    }
}
