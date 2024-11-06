package com.example.numad24fa_yunxinliu;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class ContactsCollectActivity extends AppCompatActivity {
    FloatingActionButton add_contact;
    RecyclerView recyclerView;
    ArrayList<Contact> contacts = new ArrayList<Contact>();
    ContactAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contacts_collect);

        add_contact = findViewById(R.id.add_contact);
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new ContactAdapter(this, contacts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        add_contact.setOnClickListener(view -> {
            showAddContactDialog();
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.contacts_collector), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void showAddContactDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.add_contact_popup); // 设置自定义布局
        dialog.setTitle("Add Contact");

        EditText nameInput = dialog.findViewById(R.id.contactNameInput);
        EditText phoneInput = dialog.findViewById(R.id.contactPhoneInput);
        Button addButton = dialog.findViewById(R.id.addButton);
        Button cancelButton = dialog.findViewById(R.id.cancelButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameInput.getText().toString();
                String phone = phoneInput.getText().toString();

                if (!name.isEmpty() && !phone.isEmpty()) {
                    Contact newContact = new Contact(name, phone);
                    contacts.add(newContact);
                    adapter.notifyDataSetChanged();
                    Snackbar.make(recyclerView, "Successfully added contact info", Snackbar.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    Snackbar.make(recyclerView, "Please fill all the required fields", Snackbar.LENGTH_SHORT).show();
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