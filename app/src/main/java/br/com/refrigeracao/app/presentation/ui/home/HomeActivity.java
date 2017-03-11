package br.com.refrigeracao.app.presentation.ui.home;

import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.refrigeracao.R;
import com.bumptech.glide.Glide;

import br.com.refrigeracao.app.presentation.base.BaseActivity;
import br.com.refrigeracao.app.presentation.helper.CropCircleTransform;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity implements HomeContract.View {

    @BindView(R.id.loadingPanel)
    RelativeLayout loadingPanel;
    @BindView(R.id.lbl_name)
    TextView txtUserName;
    @BindView(R.id.profile_img)
    ImageView imgUserPicture;
    private HomePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        presenter = new HomePresenter();
        presenter.setView(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadUser();
    }

    @Override
    public void updateUserInfo(String name, Uri photoUrl) {

        txtUserName.setText(name);
        Glide.with(HomeActivity.this)
                .load(photoUrl)
                .bitmapTransform(new CropCircleTransform(HomeActivity.this))
                .into(imgUserPicture);
    }

    @Override
    public void showError(int messageId) {
        Snackbar.make(txtUserName,getString(messageId),Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showLoading() {
        loadingPanel.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        loadingPanel.setVisibility(View.GONE);
    }

}
