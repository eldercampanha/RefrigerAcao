package br.com.refrigeracao.app.presentation.ui.home.viewholder;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.app.refrigeracao.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by elder-dell on 2017-03-12.
 */

public class OrderViewHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.brand)
    TextView txtBrand;
    @BindView(R.id.model)
    TextView txtModel;
    @BindView(R.id.description)
    TextView txtDescription;

    private String orderId;
    private View.OnClickListener listenter;

    public OrderViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void setBrand(String brand){
        txtBrand.setText(brand);
    }

    public void setModel(String model){
        if(model != null && !model.isEmpty())
            txtModel.setText("("+model+")");
    }

    public void setDescription(String description){
        txtDescription.setText(description);
    }

    public void setOnClickListenner(View.OnClickListener listenner){
        this.listenter = listenner;
    }

    @OnClick(R.id.card_view)
    public void setCardViewClicked(View cardview){
        listenter.onClick(cardview);
    }

}
