package br.com.refrigeracao.app.presentation.ui.login;

/**
 * Created by elder-dell on 2017-03-09.
 */

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View view;

    public  LoginPresenter(){

    }

    @Override
    public void setView(LoginContract.View view) { this.view = view; }

    @Override
    public void loadStatus() {
        //TODO: VERIFY IF USER IS LOGGED IN
        view.showError("Test");
    }

    @Override
    public void loginUserWithEmailAndPassword(String email, String password) {
        // TODO: LOGIN USER USING FIREBASE API
        view.onAuthSuccess();
    }
}
