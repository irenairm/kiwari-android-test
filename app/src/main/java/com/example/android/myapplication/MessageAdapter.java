package com.example.android.myapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private Context mContext;
    private List<Message> mMessage;
    public static final int MSG_RECEIVER = 0;
    public static final int MSG_SENDER = 1;
    private FirebaseAuth auth;

    public MessageAdapter(Context mContext, List<Message> mMessage) {
        this.mContext = mContext;
        this.mMessage = mMessage;
        notifyItemChanged(0,mMessage.size());
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        if (viewType == MSG_SENDER){
            View view = LayoutInflater.from(mContext).inflate(R.layout.message_sender,parent,false);
            return new MessageAdapter.ViewHolder(view);
        }
        else{
            View view = LayoutInflater.from(mContext).inflate(R.layout.message_receiver,parent,false);
            return new MessageAdapter.ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position){
        Message msg = mMessage.get(position);
        Log.d("sender",msg.getSender());
        Log.d("receiver",msg.getReceiver());
        Log.d("message",msg.getMessage());
        Log.d("date",msg.getDate());
        Log.d("time",msg.getTime());
        holder.text.setText(msg.getMessage());

        holder.date.setText(msg.getDate());

        holder.time.setText(msg.getTime());
    }

    @Override
    public int getItemCount() {
        return mMessage.size();
    }

    @Override
    public int getItemViewType(int position){
     String user = auth.getInstance().getCurrentUser().getUid();
     if (mMessage.get(position).getSender().equals(user)){
         return MSG_SENDER;
     }
     else{
         return MSG_RECEIVER;
     }

    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView text;
        private TextView date;
        private TextView time;



        public ViewHolder(View itemView) {
            super(itemView);
            text =  itemView.findViewById(R.id.message);
            date = itemView.findViewById(R.id.text_message_date);
            time = itemView.findViewById(R.id.text_message_time);


        }
    }
}
