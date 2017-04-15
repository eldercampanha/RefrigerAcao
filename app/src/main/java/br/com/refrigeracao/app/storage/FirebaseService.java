package br.com.refrigeracao.app.storage;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.com.refrigeracao.app.model.Order;
import br.com.refrigeracao.app.model.User;
import br.com.refrigeracao.app.storage.firebaseinterface.FirebaseInterface;

/**
 * Created by elder on 2017-04-15.
 */

public class FirebaseService {

    public static void getOrders(final FirebaseInterface.Orders ordersInterface){

        final ArrayList<Order> orders = new ArrayList<>();
        DatabaseReference mRef = FirebaseHelper.getDatabaseReference("/orders");

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot orderSnapshot : dataSnapshot.getChildren()){
                    Order order = orderSnapshot.getValue(Order.class);
                    if(order.getKey() == null)
                        order.setKey(orderSnapshot.getKey());
                    orders.add(order);
                }
                ordersInterface.onSuccess(orders);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
               ordersInterface.onFail(databaseError.toString());
            }
        });

    }


    public static void getSingleOrder(final String orderKey, final FirebaseInterface.SingleOrder singleOrderInterface){
        DatabaseReference mRef = FirebaseHelper.getDatabaseReference("/orders");
        mRef.child(orderKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Order order = (Order)dataSnapshot.getValue(Order.class);
                if(order!=null)
                    singleOrderInterface.sucess(order);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                singleOrderInterface.fail(databaseError.toString());
            }
        });

    }

    public static void createOrder(final Order order, final FirebaseInterface.CreateOrder createOrderInterface){

        DatabaseReference mRef = FirebaseHelper.getDatabaseReference("/orders");
        order.setKey(mRef.push().getKey());
        mRef.child(order.getKey()).setValue(order);

    }

}
