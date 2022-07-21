package com.androidigniter.loginandregistration.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.androidigniter.loginandregistration.R;
import com.androidigniter.loginandregistration.controllers.RealmController;
import com.androidigniter.loginandregistration.models.Keywords;
import com.androidigniter.loginandregistration.models.Relatives;

import java.util.ArrayList;

public class KeywordsAdapter extends RecyclerView.Adapter<KeywordsAdapter.MyAdapter> {

    Context context;
    ArrayList<Keywords> data;
    RealmController helper;
    private LayoutInflater inflater;

    public KeywordsAdapter(Context context, ArrayList<Keywords> data, RealmController helper) {
        this.context = context;
        this.data = data;
        this.helper = helper;
    }

    @NonNull
    @Override
    public MyAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        View view = inflater.inflate(R.layout.keywords_cell, parent, false);
        return new MyAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter holder, int position) {
        holder.tvKeyword.setText(data.get(position).getKeyword());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyAdapter extends RecyclerView.ViewHolder {
        TextView tvKeyword;
        Button btnDel;

        public MyAdapter(@NonNull View itemView) {
            super(itemView);
            tvKeyword = itemView.findViewById(R.id.tvKeyword);
            btnDel = itemView.findViewById(R.id.btnDel);

            btnDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    confirmDialog(getAbsoluteAdapterPosition());
                }
            });
        }
    }

    public void confirmDialog(final int pos) {
        new AlertDialog.Builder(context)
                .setTitle("Title")
                .setMessage("Do you really want to delete?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        helper.deleteKeyword(data.get(pos).getKeyword());
                        data.remove(pos);
                        notifyDataSetChanged();
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }

}
