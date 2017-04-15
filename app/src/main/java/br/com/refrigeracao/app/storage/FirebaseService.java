package br.com.refrigeracao.app.storage;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.com.refrigeracao.app.model.Order;
import br.com.refrigeracao.app.presentation.ui.login.LoginActivity;
import br.com.refrigeracao.app.storage.firebaseinteface.FirebaseInterface;

/**
 * Created by elder on 2017-04-15.
 */

public class FirebaseService {

    private final static String BASE_URL = "https://refrigeracao-5eb36.firebaseio.com";
    private  static DatabaseReference mRef;

    public static void getOrders(final FirebaseInterface.Orders inteface){

        final ArrayList<Order> orders = new ArrayList<>();
        mRef = FirebaseDatabase.getInstance().getReference("/Order");

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot orderSnapshot : dataSnapshot.getChildren()){
                    Order order = orderSnapshot.getValue(Order.class);
                    if(order.getKey() == null)
                        order.setKey(orderSnapshot.getKey());
                    orders.add(order);
                }
                inteface.onSuccess(orders);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
               inteface.onFail(databaseError.toString());
            }
        });

    }

    public static void getSingleOrder(){

    }

}
