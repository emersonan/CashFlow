package com.araragi.cashflow.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.araragi.cashflow.R;
import com.araragi.cashflow.entity.CashTransaction;
import com.araragi.cashflow.entity.CustomDate;
import com.araragi.cashflow.fragments.TransactionDetails;

import java.util.ArrayList;

/**
 * Created by Araragi on 2017-09-20.
 */

public class AdapterCashRecyclerView extends RecyclerView.Adapter<AdapterCashRecyclerView.ViewHolder> {

    private static final String TAG = "CustomAdapter";
    protected ArrayList<CashTransaction> mDataSet;

    FragmentManager fragmentManager;

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView textDescription;
        private final TextView textCategory;
        private final TextView textAmount;
        private final ImageView image;



        public ViewHolder(View v) {

            super(v);


            textCategory = (TextView) v.findViewById(R.id.txt_item_category);
            textDescription = (TextView)v.findViewById(R.id.txt_item_description);
            textAmount = (TextView)v.findViewById(R.id.txt_item_amount);
            image = (ImageView)v.findViewById(R.id.item_image_plus);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();
                    CashTransaction cashTransaction = mDataSet.get(position);
                    Log.v("adapter","--- Transaction clicked: " + cashTransaction.customToString());
                    Toast.makeText(v.getContext(), cashTransaction.customToString(), Toast.LENGTH_SHORT).show();



                }
            });



        }





        public TextView getTextViewDiscription() {
            return textDescription;
        }
        public TextView getTextCategory(){
            return textCategory;
        }
        public TextView getTextAmount(){
            return textAmount;
        }
        public ImageView getImage(){
            return image;
        }
    }


    public AdapterCashRecyclerView(ArrayList<CashTransaction> cashTransactions) {
        mDataSet = cashTransactions;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_recycler_view, viewGroup, false);

        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
       // Log.d(TAG, "Element " + position + " set.");
        CashTransaction cashTransaction = mDataSet.get(position);
        viewHolder.getTextCategory().setText(cashTransaction.getCategory());
        long millis = cashTransaction.getDate();

        viewHolder.getTextViewDiscription().setText(CustomDate.toCustomDateFromMillis(millis));
        viewHolder.getTextAmount().setText(cashTransaction.getAmount());
        viewHolder.getImage()
                .setImageResource(cashTransaction.getType()==CashTransaction.TYPE_INCOME?
                R.mipmap.plus_sign:R.mipmap.minus_sign);

    }


    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}

