package com.example.expensemanager;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.viewHolder> {

    Context context;
    ArrayList<TransactionRetreival> transactionRetreivalArrayList;

    public TransactionAdapter(Context context, ArrayList<TransactionRetreival> transactionRetreivalArrayList) {
        this.context = context;
        this.transactionRetreivalArrayList = transactionRetreivalArrayList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item1, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        TransactionRetreival model = transactionRetreivalArrayList.get(position);
        String priority = model.getType();
        if(priority.equals("Expense")){
            holder.priority.setBackgroundResource(R.drawable.red_shape);
        }else {
            holder.priority.setBackgroundResource(R.drawable.green_shape);
        }
        holder.amount.setText(model.getAmount());
        holder.date.setText(model.getDate());
//        holder.note.setText(model.getNote());
        if (model.getNote() != null) {
            holder.note.setText(model.getNote());
        } else {
            holder.note.setText("No Note Available");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, UpdateActivity.class);
                i.putExtra("id", transactionRetreivalArrayList.get(position).getId());
                i.putExtra("amount", transactionRetreivalArrayList.get(position).getAmount());
                i.putExtra("note", transactionRetreivalArrayList.get(position).getNote());
                i.putExtra("type", transactionRetreivalArrayList.get(position).getType());
                context.startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return transactionRetreivalArrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        TextView note, amount, date;
        View priority;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            note = itemView.findViewById(R.id.note1);
            amount = itemView.findViewById(R.id.amount1);
            date = itemView.findViewById(R.id.date1);
            priority = itemView.findViewById(R.id.priority1);
        }
    }
}
