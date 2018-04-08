package com.techouts.pcomplaints;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.techouts.pcomplaints.entities.ChatMessage;

public class ChatActivity extends BaseActivity {

    private static final int SIGN_IN_REQUEST_CODE = 101;
    private FirebaseListAdapter<ChatMessage> adapter;
    private TextView tvTitle;
    private ImageView ivBack;
    private int count = 0;

    @Override
    public int getRootLayout() {
        return R.layout.activity_chat;
    }

    @Override
    public void initGUI() {

        tvTitle = findViewById(R.id.tvTitle);
        ivBack = findViewById(R.id.ivBack);

        tvTitle.setText("Chat");

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

    }

    @Override
    public void initData() {

        if(FirebaseAuth.getInstance().getCurrentUser() == null) {
            // Start sign in/sign up activity
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .build(),
                    SIGN_IN_REQUEST_CODE
            );
        } else {
            // User is already signed in. Therefore, display
            // a welcome Toast
            showToast( "Welcome " + FirebaseAuth.getInstance()
                    .getCurrentUser()
                    .getDisplayName());

            // Load chat room contents
            displayChatMessages();
        }

        displayChatMessages();

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = (EditText)findViewById(R.id.input);

                // Read the input field and push a new instance
                // of ChatMessage to the Firebase database
                FirebaseDatabase.getInstance()
                        .getReference()
                        .push()
                        .setValue(new ChatMessage(input.getText().toString(),
                                FirebaseAuth.getInstance()
                                        .getCurrentUser()
                                        .getDisplayName())
                        );

                // Clear the input
                input.setText("");
            }
        });

    }


    private void displayChatMessages(){
        ListView listOfMessages =  findViewById(R.id.list_of_messages);

       adapter = new FirebaseListAdapter<ChatMessage>(this, ChatMessage.class,
                R.layout.message, FirebaseDatabase.getInstance().getReference()) {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {

                LinearLayout llLayout = v.findViewById(R.id.llLayout);
                // Get references to the views of message.xml
                TextView messageText = v.findViewById(R.id.message_text);
                TextView messageUser = v.findViewById(R.id.message_user);
                if(model.getMessageUser().equalsIgnoreCase("Ashoka")){
                    llLayout.setGravity(Gravity.LEFT);
                    messageText.setGravity(Gravity.LEFT);
                    messageUser.setGravity(Gravity.LEFT);
                }
                else{
                    llLayout.setGravity(Gravity.RIGHT);
                    messageText.setGravity(Gravity.RIGHT);
                    messageUser.setGravity(Gravity.RIGHT);
                }

                count++;


                // Set their text
                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());

//                // Format the date before showing it
//                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
//                        model.getMessageTime()));
            }
        };

        listOfMessages.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SIGN_IN_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                showToast("Successfully signed in. Welcome!");
                displayChatMessages();
            } else {
                showToast("We couldn't sign you in. Please try again later.");
                // Close the app
                finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        signOut();
    }

    private void signOut(){
        try{
            AuthUI.getInstance().signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            showToast("You have been signed out from chat.");
                            // Close activity
                            finish();
                        }
                    });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
