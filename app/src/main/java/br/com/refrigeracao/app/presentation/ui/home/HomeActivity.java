package br.com.refrigeracao.app.presentation.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.refrigeracao.R;
import com.bumptech.glide.Glide;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;


import javax.inject.Inject;

import br.com.refrigeracao.app.model.Order;
import br.com.refrigeracao.app.model.User;
import br.com.refrigeracao.app.presentation.base.BaseActivity;
import br.com.refrigeracao.app.presentation.helper.CropCircleTransform;
import br.com.refrigeracao.app.presentation.ui.createorder.CreateOrderActivity;
import br.com.refrigeracao.app.presentation.ui.home.viewholder.OrderViewHolder;
import br.com.refrigeracao.app.presentation.ui.orderdetails.OrderDetailsActivity;
import br.com.refrigeracao.app.storage.firebase.FirebaseService;
import br.com.refrigeracao.app.storage.firebase.FirebaseHelper;
import br.com.refrigeracao.app.storage.firebase.FirebaseInterface;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity implements HomeContract.View {

    @BindView(R.id.recycler_view)
    RecyclerView recycler;
    @BindView(R.id.loadingPanel)
    RelativeLayout loadingPanel;
    @BindView(R.id.lbl_name)
    TextView txtUserName;
    @BindView(R.id.profile_img)
    ImageView imgUserPicture;
    @Inject HomeContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);
        getMyAppliation().getDaggerUiComponent().inject(this);
        initFirebaseRecyclerView();

        presenter.setView(this);
    }

    private void initFirebaseRecyclerView() {

        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        DatabaseReference mRef = FirebaseHelper.getDatabaseReference("/orders");

        recycler.setAdapter(new FirebaseRecyclerAdapter<Order, OrderViewHolder>(Order.class, R.layout.home_orders_simple_item, OrderViewHolder.class, mRef) {

            @Override
            protected void populateViewHolder(OrderViewHolder viewHolder, final Order model, int position) {
                viewHolder.setBrand(model.getBrand());
                viewHolder.setModel(model.getModel());
                viewHolder.setDescription(model.getDescription());

                viewHolder.setOnClickListenner(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // show order details
                        Intent intent = new Intent(HomeActivity.this, OrderDetailsActivity.class);
                        intent.putExtra("ORDER",model);
                        startActivity(intent);
                    }
                });
            }

        });

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

    @Override
    public void signOut() {
        finish();
    }


    // test

    @OnClick(R.id.test)
    public void testClicked(View view){

        Intent intent = new Intent(HomeActivity.this, CreateOrderActivity.class);
        startActivity(intent);

    }

    // Menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.logout:
                presenter.sigOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
