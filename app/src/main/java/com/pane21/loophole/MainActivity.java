package com.pane21.loophole;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final int RC_SIGN_IN = 0;
    private FirebaseAuth auth;
    RecyclerView mRecyclerView;

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mConditionRef = mRootRef.child("businesses");


    public List<BusinessObject> mBusinessObjects;
    private ChildEventListener mChildEventListener;
    public MyRvAdapter mRvAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        mBusinessObjects = new ArrayList<>();
        mRvAdapter = new MyRvAdapter((ArrayList<BusinessObject>) mBusinessObjects, R.layout.list_item, getApplicationContext());

        mChildEventListener = new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                       BusinessObject businessObject = dataSnapshot.getValue(BusinessObject.class);
                       mBusinessObjects.add(businessObject);
                       mRecyclerView.setAdapter(mRvAdapter);

                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                };
        mConditionRef.addChildEventListener(mChildEventListener);


        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser() != null){
            //user signed in
            Log.d("Auth", auth.getCurrentUser().getEmail());


        }else {
            startActivityForResult(AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setProviders(
                            AuthUI.FACEBOOK_PROVIDER,
                            AuthUI.EMAIL_PROVIDER,
                            AuthUI.GOOGLE_PROVIDER).build(), RC_SIGN_IN);
        }
        findViewById(R.id.log_out_button).setOnClickListener(this);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            if(resultCode == RESULT_OK){
                //user logged in
                Log.d("Auth", auth.getCurrentUser().getEmail());
            }else {
                //user not authenticated
                Log.d("Auth", "NOt Authenticated");
            }
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.log_out_button){
            AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d("auth", "onComplete: userlogged out");
                            finish();
                        }
                    });
        }
    }

}
