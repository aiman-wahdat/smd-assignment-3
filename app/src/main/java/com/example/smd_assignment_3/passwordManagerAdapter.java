package com.example.smd_assignment_3;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

public class passwordManagerAdapter extends RecyclerView.Adapter<passwordManagerAdapter.ViewHolder> {
//    ContactClicked parentActivity;
//    public interface ContactClicked{
//        public void deleteContactFromList(int index);
//    }

    ArrayList<Password> passwordManager;
    Context context;

    public passwordManagerAdapter(Context c, ArrayList<Password> list)
    {
        context = c;
        passwordManager = list;
        //parentActivity = (ContactClicked) c;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.password_manager_single_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvUsername.setText(passwordManager.get(position).getUsername());
        holder.tvPassword.setText(passwordManager.get(position).getPassword());
        holder.tvURL.setText(passwordManager.get(position).getWebsiteUrl());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                AlertDialog.Builder deleteDialog = new AlertDialog.Builder(context);
                deleteDialog.setTitle("Confirmation");
                deleteDialog.setMessage("Do you really want to delete it?");
                deleteDialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // delete code
                        MyDatabaseHelper database = new MyDatabaseHelper(context);
                        database.deleteUser(passwordManager.get(holder.getAdapterPosition()).getId());
                        database.close();

                        passwordManager.remove(holder.getAdapterPosition());
                        notifyDataSetChanged();
                        //parentActivity.deleteContactFromList(holder.getAdapterPosition());
                    }
                });
                deleteDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                deleteDialog.show();

                return false;
            }
        });

        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog editDialog = new AlertDialog.Builder(context).create();
                View view = LayoutInflater.from(context).inflate(R.layout.edit_password_layout, null, false);
                editDialog.setView(view);

                EditText etName = view.findViewById(R.id.etEditName);
                EditText etPassword = view.findViewById(R.id.etEditPassword);
                EditText etURL = view.findViewById(R.id.etEditUrl);
                Button btnEdit = view.findViewById(R.id.btnedit);
                Button btnCancel = view.findViewById(R.id.btnCancel);


                etName.setText(passwordManager.get(holder.getAdapterPosition()).getUsername());
                etPassword.setText(passwordManager.get(holder.getAdapterPosition()).getPassword());
                etURL.setText(passwordManager.get(holder.getAdapterPosition()).getWebsiteUrl());

                editDialog.show();

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editDialog.dismiss();
                    }
                });

                btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = etName.getText().toString().trim();
                        String password = etPassword.getText().toString().trim();
                        String url = etURL.getText().toString().trim();
                        MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(context);
                    myDatabaseHelper.updateUser(passwordManager.get(holder.getAdapterPosition()).getId(),name, password,url);
                        myDatabaseHelper.close();

                        editDialog.dismiss();

                        passwordManager.get(holder.getAdapterPosition()).setUsername(name);
                        passwordManager.get(holder.getAdapterPosition()).setPassword(password);
                        passwordManager.get(holder.getAdapterPosition()).setWebsiteUrl(url);
                        notifyDataSetChanged();

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return passwordManager.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvUsername, tvPassword, tvURL;
        ImageView ivEdit, ivDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvUsername = itemView.findViewById(R.id.tvusername);
            tvPassword = itemView.findViewById(R.id.tvpass);
            tvURL = itemView.findViewById(R.id.tvurl);
            ivEdit=itemView.findViewById(R.id.ivEdit);
            ivDelete=itemView.findViewById(R.id.ivDelete);
        }
    }
}