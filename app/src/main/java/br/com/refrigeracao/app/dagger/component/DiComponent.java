package br.com.refrigeracao.app.dagger.component;

import javax.inject.Singleton;

import br.com.refrigeracao.app.dagger.module.ApplicationModule;
import br.com.refrigeracao.app.dagger.module.presentation.PresenterModule;
import br.com.refrigeracao.app.dagger.scope.PerActivity;
import br.com.refrigeracao.app.presentation.ui.home.HomeActivity;
import br.com.refrigeracao.app.presentation.ui.login.LoginActivity;
import dagger.Component;
import dagger.Provides;

/**
 * Created by elder-dell on 2017-03-11.
 */

@Singleton
@PerActivity
@Component(modules = {PresenterModule.class, ApplicationModule.class})
public interface DiComponent {

    void inject(LoginActivity activity);
    void inject(HomeActivity activity);
}
