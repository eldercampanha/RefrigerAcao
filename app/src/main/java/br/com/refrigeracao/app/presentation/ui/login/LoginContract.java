package br.com.refrigeracao.app.presentation.ui.login;

/**
 * Created by elder-dell on 2017-03-09.
 */

public interface LoginContract {

    interface View {

        void onAuthSuccess();

        void showError(int messageId);

        void showLoading();

        void hideLoading();
    }

    interface Presenter {

        void setView(LoginContract.View view);

        void loadStatus();

        void loginUserWithEmailAndPassword(String email,String password);

    }

}
