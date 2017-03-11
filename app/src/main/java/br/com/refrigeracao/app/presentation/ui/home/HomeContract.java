package br.com.refrigeracao.app.presentation.ui.home;

import android.net.Uri;

import br.com.refrigeracao.app.model.User;

/**
 * Created by elder-dell on 2017-03-10.
 */

public interface HomeContract {

    interface View {

        void updateUserInfo(String name, Uri photoUrl);

        void showError(int messageId);

        void showLoading();

        void hideLoading();

        void signOut();
    }

    interface Presenter {

        void setView(HomeContract.View view);

        void loadUser();

        void sigOut();
    }
}
