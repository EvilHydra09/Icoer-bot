package com.example.ash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Message> messages;
    private RecyclerView recyclerView;
    private recyclerAdapter adapter;
    private ImageButton sendButton;
    private EditText msgInput;
    private getRequest request;
    private ImageButton helpbutton;
    private TextToSpeech t1;
    Voice voice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        request = new getRequest(this);
        helpbutton = findViewById(R.id.helpicon);
        recyclerView = findViewById(R.id.recyclerView);
        // Set RecyclerView layout manager.
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        // Set an animation
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        messages = new ArrayList<>();
        adapter = new recyclerAdapter(messages);
        recyclerView.setAdapter(adapter);



        sendButton = (ImageButton) findViewById(R.id.msgButton);
        msgInput = (EditText) findViewById(R.id.msgInput);

        String str = getIntent().getStringExtra("name");
        msgInput.setText(str);
        t1 = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i != TextToSpeech.ERROR){
                    t1.setLanguage(Locale.US);

                }
            }
        });
        
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = msgInput.getText().toString();
                if(message.length() != 0){
                    messages.add(new Message(true, message));
                    int newPosition = messages.size() - 1;
                    adapter.notifyItemInserted(newPosition);
                    recyclerView.scrollToPosition(newPosition);
                    msgInput.setText("");
                    getReply(message);

                }
            }
        });
        helpbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,MainActivity2.class));
            }
        });


    }

    private void getReply(String message) {
        request.getResponse(message, new getRequest.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Log.d("REQUEST ERROR", message);
            }

            @Override
            public void onResponse(String reply) {
                messages.add(new Message(false, reply));
                int newPosition = messages.size() - 1;
                adapter.notifyItemInserted(newPosition);
                recyclerView.scrollToPosition(newPosition);
                t1.speak(reply,TextToSpeech.QUEUE_FLUSH,null);
            }
        });

    }

}