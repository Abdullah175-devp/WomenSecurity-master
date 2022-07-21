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
import com.androidigniter.loginandregistration.models.Relatives;

import java.util.ArrayList;

public class RelativesAdapter extends RecyclerView.Adapter<RelativesAdapter.MyAdapter> {

    Context context;
    ArrayList<Relatives> data;
    RealmController helper;
    private LayoutInflater inflater;

    public RelativesAdapter(Context context, ArrayList<Relatives> data, RealmController helper) {
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
        View view = inflater.inflate(R.layout.relatives_cell, parent, false);
        return new MyAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter holder, int position) {
        holder.tvName.setText(data.get(position).getName());
        holder.tvEmail.setText(data.get(position).getEmail());
        holder.tvPhone.setText(data.get(position).getPhone());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyAdapter extends RecyclerView.ViewHolder {
        TextView tvName, tvEmail, tvPhone;
        Button btnDel;

        public MyAdapter(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvPhone = itemView.findViewById(R.id.tvPhone);
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
                        helper.deleteRelative(data.get(pos).getPhone(),
                                data.get(pos).getEmail());
                        data.remove(pos);
                        notifyDataSetChanged();
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }

}
