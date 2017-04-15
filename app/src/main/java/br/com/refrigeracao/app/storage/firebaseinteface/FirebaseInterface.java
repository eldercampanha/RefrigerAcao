package br.com.refrigeracao.app.storage.firebaseinteface;

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
}
