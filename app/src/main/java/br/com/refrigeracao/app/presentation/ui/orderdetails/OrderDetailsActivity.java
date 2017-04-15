package br.com.refrigeracao.app.presentation.ui.orderdetails;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.app.refrigeracao.R;

import br.com.refrigeracao.app.model.Order;
import br.com.refrigeracao.app.storage.FirebaseService;
import br.com.refrigeracao.app.storage.firebaseinterface.FirebaseInterface;
import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderDetailsActivity extends AppCompatActivity {

    @BindView(R.id.brand)
    TextView txtBrand;
    @BindView(R.id.model)
    TextView txtModel;
    @BindView(R.id.description)
    TextView txtDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        ButterKnife.bind(this);

        Order order = (Order)getIntent().getSerializableExtra("ORDER");

        setUpFirebaseRuntime(order);

    }

    public void setUpFirebaseRuntime(Order order) {
        FirebaseService.getSingleOrder(order.getKey(), new FirebaseInterface.SingleOrder() {
            @Override
            public void sucess(Order order) {
                if(order!=null) {
                    txtBrand.setText(order.getBrand());
                    txtModel.setText(order.getModel());
                    txtDescription.setText(order.getDescription());
                }
            }

            @Override
            public void fail(String error) {

            }
        });
    }
}
