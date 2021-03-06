package com.araragi.cashflow.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.araragi.cashflow.CashFlowApp;
import com.araragi.cashflow.entity.CashTransact;
import com.araragi.cashflow.entity.CustomDate;

import io.objectbox.Box;
import io.objectbox.BoxStore;

/**
 * Created by Araragi on 2017-11-06.
 */

public class TransactionDetailsDialogFragment extends DialogFragment{

    public static final String TAG = "TransactionDetailsDialogFragment";

    private Box<CashTransact> cashBox;

    private long transactionId;
    private CashTransact transaction;
    private CashTransact cashTransactDeleted;

    private void setTransactionId(long transactionId){
        this.transactionId = transactionId;
    }
    private long getTransactionId(){
        return this.transactionId;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        Bundle bundle = this.getArguments();


        if (bundle != null) {
            setTransactionId(bundle.getLong("transactionId"));
        }

        BoxStore boxStore =((CashFlowApp)getActivity().getApplication()).getBoxStore();
        cashBox = boxStore.boxFor(CashTransact.class);
        Log.i("dialog", "-----transaction id: " + getTransactionId() + "----");
        transaction = cashBox.get(getTransactionId());




        String type  = "";
        if(transaction.getType() == 1){
            type = "Expense";
        }
        if(transaction.getType() == 2){
            type = "Income";
        }

        String message = "Date: " + CustomDate.toCustomDateFromMillis(transaction.getDate()) + "\n" +
                "Amount: " + transaction.getAmount() + "\n" +
                "Category: " + transaction.getCategory() + "\n" +
                "Type: " + type + "\n" +
                "Description: " + transaction.getDescription();



        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(message)
                .setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, getActivity().getIntent());
                    }
                })
                .setNegativeButton("DELETE",  new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_CANCELED, getActivity().getIntent());
//                        cashTransactDeleted =transaction;
//                        cashBox.remove(transactionId);

                    }
                })
        ;


        return builder.create();
    }

        public class SnackBarUndoListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {


            if(cashTransactDeleted != null){
                cashTransactDeleted.setId(0);
                long idAdded = cashBox.put(cashTransactDeleted);

                Toast.makeText(getActivity(), "Deletion undone", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getActivity(), "Transaction object null", Toast.LENGTH_SHORT).show();}


        }
    }




}
