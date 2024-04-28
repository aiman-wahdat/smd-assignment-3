package com.example.smd_assignment_3;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recycleBinAdapter extends RecyclerView.Adapter<recycleBinAdapter.ViewHolder>{

    //    ContactClicked parentActivity;
//    public interface ContactClicked{
//        public void deleteContactFromList(int index);
//    }

    ArrayList<Password> recycleBin;
    Context context;
    public recycleBinAdapter(Context c, ArrayList<Password> list)
    {
        context = c;
        recycleBin = list;
        //parentActivity = (ContactClicked) c;
    }

    @NonNull
    @Override
    public recycleBinAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bin_single_item_layout, parent, false);
        return new recycleBinAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull recycleBinAdapter.ViewHolder holder, int position) {
        MyDatabaseHelper database = new MyDatabaseHelper(context);
        holder.tvUsername.setText(recycleBin.get(position).getUsername());
        holder.tvPassword.setText(recycleBin.get(position).getPassword());
        holder.tvURL.setText(recycleBin.get(position).getWebsiteUrl());

        holder.ivRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder deleteDialog = new AlertDialog.Builder(context);
                deleteDialog.setTitle("Confirmation");
                deleteDialog.setMessage("Do you really want to Restore it?");
                deleteDialog.setPositiveButton("Restore", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Restore code
                                MyDatabaseHelper database = new MyDatabaseHelper(context);
                                database.restoreBinItem(recycleBin.get(holder.getAdapterPosition()).getId());
                                database.close();

                                recycleBin.remove(holder.getAdapterPosition());
                                notifyDataSetChanged();

                                Intent intent = new Intent(context,password_items.class);
                                startActivity(context,intent,null);
                                ((Activity)context).finish();

                            }
                });
                deleteDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                deleteDialog.show();

            }
        });

        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder deleteDialog = new AlertDialog.Builder(context);
                deleteDialog.setTitle("Confirmation");
                deleteDialog.setMessage("Do you really want to Delete it?");
                deleteDialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // delete code
                        MyDatabaseHelper database = new MyDatabaseHelper(context);
                        database.deleteBinItem(recycleBin.get(holder.getAdapterPosition()).getId());
                        database.close();

                        recycleBin.remove(holder.getAdapterPosition());
                        notifyDataSetChanged();

                        Intent intent = new Intent(context,password_items.class);
                        startActivity(context,intent,null);
                        ((Activity)context).finish();

                    }
                });
                deleteDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                deleteDialog.show();


            }
        });
    }

    @Override
    public int getItemCount() {
        return recycleBin.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvUsername, tvPassword, tvURL;
        ImageView ivRestore, ivDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvUsername = itemView.findViewById(R.id.tvusername);
            tvPassword = itemView.findViewById(R.id.tvpass);
            tvURL = itemView.findViewById(R.id.tvurl);
            ivRestore=itemView.findViewById(R.id.ivRestore);
            ivDelete=itemView.findViewById(R.id.ivDelete);
        }
    }
}
