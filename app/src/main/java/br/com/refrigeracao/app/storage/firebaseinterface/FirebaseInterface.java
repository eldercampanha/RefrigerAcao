package br.com.refrigeracao.app.storage.firebaseinterface;

import java.util.ArrayList;

import br.com.refrigeracao.app.model.Order;

/**
 * Created by elder on 2017-04-15.
 */

public interface FirebaseInterface {

    interface Orders{
        void onSuccess(ArrayList<Order> orderList);
        void onFail(String error);
    }

    interface SingleOrder{
        void sucess(Order order);
        void fail(String error);
    }

    interface CreateOrder{
        void sucess(String orderId);
        void fail(String error);

    }
}
