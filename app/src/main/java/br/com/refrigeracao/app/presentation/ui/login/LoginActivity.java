package br.com.refrigeracao.app.presentation.ui.login;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.refrigeracao.R;

import br.com.refrigeracao.app.presentation.ui.helper.AppHelper;
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

        // binding
        ButterKnife.bind(this);
        presenter = new LoginPresenter();
        presenter.setView(this);
        appHelper = new AppHelper(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadStatus();
    }


    // PRESENTER METHODS
    @Overridet 
    public void onAuthSuccess() {
        // TODO: OPEN HOME SCREEN
        Snackbar.make(txtEmail,"Open Home Screen",Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showError(String message) {
        Snackbar.make(txtEmail,message,Snackbar.LENGTH_LONG).show();
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

        if(appHelper.validateRequiredFields(txtEmail, txtPassword)) {
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
