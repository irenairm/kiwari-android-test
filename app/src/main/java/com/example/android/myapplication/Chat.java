package com.example.android.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Chat extends AppCompatActivity  {

    TextInputEditText msg;
    ImageView recvpic;
    TextView recvname;
    private FirebaseAuth auth;
    private DatabaseReference ref;
    private FirebaseDatabase database;
    RecyclerView recyclerView;
    User user;

    MessageAdapter messageAdapter;
    List<Message> mMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatroom_main);


        // Setup view

        msg = findViewById(R.id.messageInput) ;
        Button send  = findViewById(R.id.sendButton);
        TextView logout =  findViewById(R.id.logout);
        recvname =  findViewById(R.id.username);
        recvpic = findViewById(R.id.userpic);

        // Set up view for chats
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        // Set up database
        database = FirebaseDatabase.getInstance();


        if(auth.getInstance().getCurrentUser()!=null){
            Log.d("1","tesuto");
            Log.d("uid",auth.getInstance().getCurrentUser().getUid());

            if (auth.getInstance().getCurrentUser().getUid().equals("9OuOabrsMYfbl7aWT5jrcEZ4YQw2")) {
                Log.d("oncreate","masuk if");
                getData("s1426zkdOTdpiFVBf1GcMeVXPY32");
            }
            else if (auth.getInstance().getCurrentUser().getUid().equals("s1426zkdOTdpiFVBf1GcMeVXPY32")){
                Log.d("oncreate","masuk if");
                getData("9OuOabrsMYfbl7aWT5jrcEZ4YQw2");
            }

        }
        else{

        }

        // Send message handling
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Log.d("sendbutton","masuk");
                if (auth.getInstance().getCurrentUser().getUid().equals("9OuOabrsMYfbl7aWT5jrcEZ4YQw2")){
                    sendMessage("9OuOabrsMYfbl7aWT5jrcEZ4YQw2","s1426zkdOTdpiFVBf1GcMeVXPY32",msg.getText().toString());
                }
                else if ((auth.getInstance().getCurrentUser().getUid().equals("s1426zkdOTdpiFVBf1GcMeVXPY32"))){
                    sendMessage("s1426zkdOTdpiFVBf1GcMeVXPY32","9OuOabrsMYfbl7aWT5jrcEZ4YQw2", msg.getText().toString());

                }
            }

        });

        // Logout handling
        logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                auth.getInstance().signOut();
                Intent intent = new Intent(Chat.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void getData(final String uid){
        ref = database.getReference("users/"+uid);
        Log.d("masukgetdata","masuk get data");

        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("get data","tessssss");

                User user = dataSnapshot.getValue(User.class);
                Log.d("get data","tessssss");
                recvname.setText(user.getName());
                new DownloadImageTasks(recvpic).execute(user.getAvatar());
                readMessages(auth.getInstance().getCurrentUser().getUid(),uid);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void sendMessage(String sender, String receiver, String message){

        Log.d("masuk send message","masuk");
//         Get user data
        HashMap<String,Object> hashMap = new HashMap<>();
        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("dd MMMM yyyy");

        String dt = ft.format(date);

        SimpleDateFormat time = new SimpleDateFormat("kk:mm");
        String times = time.format(date);

        Message sentMsg = new Message(sender,receiver,message,dt,times);

        hashMap.put("sender",sentMsg.getSender());
        hashMap.put("receiver",sentMsg.getReceiver());
        hashMap.put("message",sentMsg.getMessage());
        hashMap.put("date",sentMsg.getDate());
        hashMap.put("time",sentMsg.getTime());

        // Put message in database
        FirebaseDatabase.getInstance()
                .getReference()
                .child("Chats")
                .push()
                .setValue(hashMap);

        // Clear input
        msg.setText("");
    }

    private void readMessages(final String myid, final String userid){
        mMessage = new ArrayList<>();
        ref = FirebaseDatabase.getInstance().getReference("Chats");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mMessage.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Message msgs = snapshot.getValue(Message.class);
                    if ((msgs.getSender().equals(myid) && msgs.getReceiver().equals(userid)) ||
                            ((msgs.getSender().equals(userid)) && (msgs.getReceiver().equals(myid)))
                    ){
                        mMessage.add(msgs);
                    }

                    messageAdapter = new MessageAdapter(Chat.this,mMessage);
                    recyclerView.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    // From stackoverflow

    private class DownloadImageTasks extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTasks(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
