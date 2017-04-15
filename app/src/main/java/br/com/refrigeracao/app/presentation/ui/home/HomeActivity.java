package br.com.refrigeracao.app.presentation.ui.home;

import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.refrigeracao.R;
import com.bumptech.glide.Glide;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;

import javax.inject.Inject;

import br.com.refrigeracao.app.model.Order;
import br.com.refrigeracao.app.model.User;
import br.com.refrigeracao.app.presentation.base.BaseActivity;
import br.com.refrigeracao.app.presentation.helper.CropCircleTransform;
import br.com.refrigeracao.app.presentation.ui.home.viewholder.OrderViewHolder;
import br.com.refrigeracao.app.storage.FirebaseService;
import br.com.refrigeracao.app.storage.firebaseinteface.FirebaseInterface;
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
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("/Order");

        recycler.setAdapter(new FirebaseRecyclerAdapter<Order, OrderViewHolder>(Order.class, R.layout.home_orders_simple_item, OrderViewHolder.class, mRef) {
            @Override
            protected void populateViewHolder(OrderViewHolder viewHolder, Order model, int position) {
                viewHolder.setBrand(model.getBrand());
                viewHolder.setModel(model.getModel());
                viewHolder.setDescription(model.getDescription());
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


        User mUser = new User(FirebaseAuth.getInstance().getCurrentUser());

        // creating fake Order
        Order order = new Order();
        order.setBrand("Brastemp");
        order.setModel("Super");
        order.setDescription("Nao joga agua fora");

        String id = mUser.getId();


/*
        //OLD WAY
        // creating connection
         Firebase mRef = new Firebase(databaseUrl);

        // creating Hashmap
        HashMap<String,Object> hashMap = order.toHashMap();
        mRef.setValue(hashMap);
/* run value example
        // creating child
        final Firebase mRefChild = mRef.child("Name");
        mRefChild.setValue("Bruno");


        // adding listener
        mRefChild.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                txtUserName.setText(dataSnapshot.getValue(String.class));


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
*/



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
