package com.example.numad24fa_yunxinliu;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
    private ArrayList<Contact> contacts;
    private Context context;

    public ContactAdapter(Context context, ArrayList<Contact> contacts) {
        this.context = context;
        this.contacts = contacts;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact contact = contacts.get(holder.getAdapterPosition());
        holder.nameTextView.setText(contact.name);
        holder.phoneTextView.setText(contact.phone);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + contact.getPhone().trim()));
                view.getContext().startActivity(intent);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int removedPosition = holder.getAdapterPosition();
                Contact removedContact = contacts.get(removedPosition);
                contacts.remove(removedPosition);
                notifyItemRemoved(removedPosition);

                Snackbar.make(holder.itemView, "Contact deleted", Snackbar.LENGTH_LONG)
                        .setAction("Undo", v -> {
                            contacts.add(removedPosition, removedContact);
                            notifyItemInserted(removedPosition);
                        }).show();
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditContactDialog(contact, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView phoneTextView;
        Button delete;
        Button edit;

        public ContactViewHolder(@NonNull View item) {
            super(item);
            nameTextView = item.findViewById(R.id.nameTextView);
            phoneTextView = item.findViewById(R.id.phoneTextView);
            delete = item.findViewById(R.id.delete);
            edit = item.findViewById(R.id.edit);
        }
    }

    private void showEditContactDialog(Contact contact, int position) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.add_contact_popup);
        dialog.setTitle("Edit Contact");

        EditText nameInput = dialog.findViewById(R.id.contactNameInput);
        EditText phoneInput = dialog.findViewById(R.id.contactPhoneInput);
        Button addButton = dialog.findViewById(R.id.addButton);
        Button cancelButton = dialog.findViewById(R.id.cancelButton);

        nameInput.setText(contact.getName());
        phoneInput.setText(contact.getPhone());

        addButton.setText("Save");

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameInput.getText().toString();
                String phone = phoneInput.getText().toString();

                if (!name.isEmpty() && !phone.isEmpty()) {
                    contact.setName(name);
                    contact.setPhoneNumber(phone);
                    notifyItemChanged(position);
                    Snackbar.make(((ContactsCollectActivity) context).findViewById(R.id.recyclerView),
                            "Contact updated", Snackbar.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                Snackbar.make(((ContactsCollectActivity) context).findViewById(R.id.recyclerView),
                        "Contact updated", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
