package br.com.refrigeracao.app.presentation.ui.login;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.app.refrigeracao.R;

import javax.inject.Inject;

import br.com.refrigeracao.app.MyApplication;
import br.com.refrigeracao.app.presentation.base.BaseActivity;
import br.com.refrigeracao.app.presentation.helper.TextViewHelper;
import br.com.refrigeracao.app.presentation.ui.home.HomeActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginContract.View {

    // used to lock user action
    @Inject LoginContract.Presenter presenter;
    protected boolean enabled = true;
    private TextViewHelper textViewHelper;

    @BindView(R.id.input_email)
    TextInputLayout txtEmail;
    @BindView(R.id.input_password)
    TextInputLayout txtPassword;
    @BindView(R.id.loadingPanel)
    RelativeLayout loadingPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //TODO: USER DAGGER
        // binding
        ButterKnife.bind(this);
        getMyAppliation().getDaggerUiComponent().inject(this);

        presenter.setView(this);

        textViewHelper = new TextViewHelper(this);

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
        setEnableTouchEvent(false);
        loadingPanel.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        loadingPanel.setVisibility(View.GONE);
        setEnableTouchEvent(true);
    }

    /**
     * This method blocks the activity interaction
     * in order to avoid the user to click repeatedly on Login button or Text fields
     * @param b
     */
    public void setEnableTouchEvent(boolean b) {
        enabled = b;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return enabled ?
                super.dispatchTouchEvent(ev) :
                true;
    }

    // UI EVENTS
    @OnClick(R.id.btn_login)
    void loginClicked(View loginButton){

        if(textViewHelper.validateRequiredFields(txtEmail, txtPassword)
                && textViewHelper.validateEmail(txtEmail)) {

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
