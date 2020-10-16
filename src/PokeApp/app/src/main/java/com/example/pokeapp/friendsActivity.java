package com.example.pokeapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


public class friendsActivity extends AppCompatActivity {

    //for getting user data
    private FileMan fileManager;
    //for networking. needed in ANY activity that makes requests.

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        fileManager = new FileMan(this);

        //updates on open of friends list
        update();
    }

    //Called when poke is pressed with test UUID
    //Adds a poke request and sets up a listener.
    //The result for this should just be a confirmation message.
    public void poke(View v) {
        //You need a target UUID for this. Sending it to user "Friend" right now (check admin)
        final String args[] = {"poke", fileManager.getUUID(), "0e33e1c6-d0a3-4155-941e-fd1d357c458d", "message_here"};
        Thread wait; //calls a method once p has a result
        if(!fileManager.getUUID().equals("")) {
            //create pokey thread to register
            Thread t = RequestManager.requestThreadFactory.newThread(new RequestTask("https://poke.zachlef.in/poke/poke", args));
            t.start();
            //create thread to wait for result
            wait = new Thread(new Runnable(){
                @Override
                public void run() {
                    String result;
                    while(true) {
                        result = RequestManager.requestThreadFactory.getResult();
                        if(result != null) break;
                    }
                    //IMPORTANT: This is where behavior for requests should be implemented; call a function with "result" as argument.
                    placeholderResult(result);
                }
            });
            wait.start();
        }
    }

    //Called when update is pressed
    //Adds an update request and sets up a listener.
    //Similar result to poll; JSON array string with {"name": "Jake", "friends": ["uuid","uuid"]}
    public void update() {
        final String args[] = {"update", fileManager.getUUID()};
        Thread wait; //calls a method once p has a result
        if(!fileManager.getUUID().equals("")) {
            //create pokey thread to register
            Thread t = RequestManager.requestThreadFactory.newThread(new RequestTask("https://poke.zachlef.in/poke/update", args));
            t.start();
            //create thread to wait for result
            wait = new Thread(new Runnable(){
                @Override
                public void run() {
                    String result;
                    while(true) {
                        result = RequestManager.requestThreadFactory.getResult();
                        if(result != null) break;
                    }
                    //IMPORTANT: This is where behavior for requests should be implemented; call a function with "result" as argument.
                    placeholderResult(result);
                }
            });
            wait.start();
        }
    }

    //starts add friend activity
    public void addFriend(View v) {
        Intent intent = new Intent(this, addFriendActivity.class);
        startActivity(intent);
    }

    //Called when poke is pressed with test UUID
    public void removeFriend(View v) {
        final String args[] = {"friends/delete", fileManager.getUUID(), "0e33e1c6-d0a3-4155-941e-fd1d357c458d"};
        Thread wait; //calls a method once p has a result
        if(!fileManager.getUUID().equals("")) {
            //create pokey thread to register
            Thread t = RequestManager.requestThreadFactory.newThread(new RequestTask("https://poke.zachlef.in/poke/friends/delete", args));
            t.start();
            //create thread to wait for result
            wait = new Thread(new Runnable(){
                @Override
                public void run() {
                    String result;
                    while(true) {
                        result = RequestManager.requestThreadFactory.getResult();
                        if(result != null) break;
                    }
                    //IMPORTANT: This is where behavior for requests should be implemented; call a function with "result" as argument.
                    placeholderResult(result);
                }
            });
            wait.start();
        }
    }

    //placeholder function. replace with your endpoint's needs.
    public void placeholderResult(String r) {
        Log.d("RESULT", r);
    }

    public void leaveFriends(View v){
        this.finish();
    }
}