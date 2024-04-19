package com.example.chatapp.repository;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.chatapp.model.ChatGroup;
import com.example.chatapp.model.ChatMassage;
import com.example.chatapp.views.GroupsActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Repository {

    MutableLiveData<List<ChatGroup>> chatGroupMutableLiveData;

    FirebaseDatabase database;
    DatabaseReference reference;
    DatabaseReference groupReference;

    MutableLiveData<List<ChatMassage>> messagesLiveDate;



    public Repository() {
        this.chatGroupMutableLiveData = new MutableLiveData<>();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        messagesLiveDate = new MutableLiveData<>();
    }

    public void firebaseAnonymousAuth(Context context) {
        FirebaseAuth.getInstance().signInAnonymously()
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent i = new Intent(context, GroupsActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(i);
                        }
                    }
                });
    }

    // Getting current user id
    public String getCurrentUserId() {
        return FirebaseAuth.getInstance().getUid();
    }

    // SignOut
    public void signOut() {
        FirebaseAuth.getInstance().signOut();
    }


    //Getting Chat Groups from Firebase DB


    public MutableLiveData<List<ChatGroup>> getChatGroupMutableLiveData() {

        List<ChatGroup> groupsList = new ArrayList<>();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                groupsList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ChatGroup group = new ChatGroup(dataSnapshot.getKey());
                    groupsList.add(group);
                }

                chatGroupMutableLiveData.postValue(groupsList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return chatGroupMutableLiveData;
    }

    public void createNewChatGroup(String groupName) {
        reference.child(groupName).setValue(groupName);
    }


    public MutableLiveData<List<ChatMassage>> getMessagesLiveDate(String groupName) {

        groupReference = database.getReference().child(groupName);
        List<ChatMassage> messagesList = new ArrayList<>();

        groupReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messagesList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ChatMassage message = dataSnapshot.getValue(ChatMassage.class);
                    messagesList.add(message);
                }

                messagesLiveDate.postValue(messagesList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return messagesLiveDate;
    }

    public void sendMessage(String messageText, String chatGroup) {

        DatabaseReference ref = database
                .getReference(chatGroup);

        if (!messageText.trim().equals("")) {
            ChatMassage msg = new ChatMassage(
                    FirebaseAuth.getInstance().getCurrentUser().getUid(),
                    messageText,
                    System.currentTimeMillis()
            );

            String randomKey = ref.push().getKey();
            ref.child(randomKey).setValue(msg);

        }


    }



}
