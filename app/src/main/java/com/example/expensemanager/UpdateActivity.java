package com.example.expensemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.expensemanager.databinding.ActivityUpdateBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UpdateActivity extends AppCompatActivity {

    ActivityUpdateBinding binding;
    String newType;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        String id = getIntent().getStringExtra("id");
        String amount = getIntent().getStringExtra("amount");
        String note = getIntent().getStringExtra("note");
        String type = getIntent().getStringExtra("type");

        binding.udtAmount.setText(amount);
        binding.udtNote.setText(note);

        switch(type) {
            case "Income":
                newType = "Income";
                binding.incomeCb.setChecked(true);
                break;

            case "Expense":
                newType = "Expense";
                binding.expenseCb.setChecked(true);
                break;
        }

        binding.incomeCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newType = "Income";
                binding.incomeCb.setChecked(true);
                binding.expenseCb.setChecked(false);
            }
        });

        binding.expenseCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newType = "Expense";
                binding.incomeCb.setChecked(false);
                binding.expenseCb.setChecked(true);
            }
        });

        binding.btnUpdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String amount = binding.udtAmount.getText().toString();
                String note = binding.udtNote.getText().toString();
//                Map<String, Object> updates = new HashMap<>();
//                updates.put("amount", amount);
//                updates.put("note", note);
//                updates.put("type", type);

                firebaseFirestore.collection("Expenses").document(firebaseAuth.getUid())
                        .collection("Notes").document(id)
                        .update("amount", amount,
                                "note", note,
                                "type", type).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                onBackPressed();
                                Toast.makeText(UpdateActivity.this, "Updated", Toast.LENGTH_LONG).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(UpdateActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                            }
                        });
            }
        });

        binding.btnDlt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseFirestore.collection("Expenses").document(firebaseAuth.getUid())
                        .collection("Notes").document(id).delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                onBackPressed();
                                Toast.makeText(UpdateActivity.this, "Deleted", Toast.LENGTH_LONG).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(UpdateActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });

    }
}
