package br.com.refrigeracao.app.model;

import java.security.Timestamp;

/**
 * Created by elder-dell on 2017-03-11.
 */

public class TecnicalVisit {

    private Order order;
    private User user;
    private Timestamp resovledAt;
    private int status;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getResovledAt() {
        return resovledAt;
    }

    public void setResovledAt(Timestamp resovledAt) {
        this.resovledAt = resovledAt;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
