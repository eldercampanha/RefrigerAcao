package br.com.refrigeracao.app.presentation.ui.login;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.app.refrigeracao.R;

import br.com.refrigeracao.app.presentation.helper.AppHelper;
import br.com.refrigeracao.app.presentation.ui.home.HomeActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    private LoginContract.Presenter presenter;

    @BindView(R.id.input_email)
    TextInputLayout txtEmail;
    @BindView(R.id.input_password)
    TextInputLayout txtPassword;
    @BindView(R.id.loadingPanel)
    RelativeLayout loadingPanel;
    private AppHelper appHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //TODO: USER DAGGER
        // binding
        ButterKnife.bind(this);
        presenter = new LoginPresenter(this);
        presenter.setView(this);
        appHelper = new AppHelper(this);

        // TODO: remove bellow code used for testing
        txtEmail.getEditText().setText("new@user.com");
        txtPassword.getEditText().setText("New12345");

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadStatus();
    }


    // PRESENTER METHODS
    @Override
    public void onAuthSuccess() {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void showError(int messageId) {
        Snackbar.make(txtEmail,getString(messageId),Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showLoading() {
        loadingPanel.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        loadingPanel.setVisibility(View.GONE);
    }

    // UI EVENTS
    @OnClick(R.id.btn_login)
    void loginClicked(View loginButton){

        if(appHelper.validateRequiredFields(txtEmail, txtPassword)
                && appHelper.validateEmail(txtEmail)) {

            String email = txtEmail.getEditText().getText().toString();
            String password = txtPassword.getEditText().getText().toString();
            presenter.loginUserWithEmailAndPassword(email, password);
        }
    }

    @OnClick(R.id.link_signup)
    void signUpClicked(View signupLabel){
        // TODO: OPEN SIGN UP SCREEN
        Snackbar.make(txtEmail,"Open Sign up Screen",Snackbar.LENGTH_LONG).show();
    }
}
