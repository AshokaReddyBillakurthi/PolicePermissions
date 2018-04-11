package com.techouts.pcomplaints;

import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techouts.pcomplaints.adapters.FriendsListAdapter;
import com.techouts.pcomplaints.data.ParseFirebaseData;
import com.techouts.pcomplaints.data.Tools;
import com.techouts.pcomplaints.entities.Friend;
import com.techouts.pcomplaints.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import static com.techouts.pcomplaints.adapters.FriendsListAdapter.OnItemClickListener;

public class ActivitySelectFriend extends BaseActivity {

    private ActionBar actionBar;
    private RecyclerView recyclerView;
    private FriendsListAdapter mAdapter;
    private List<Friend> friendList;

    public static final String USERS_CHILD = "users";
    private ParseFirebaseData pfbd;

    private ImageView ivBack;
    private TextView tvTitle;

    @Override
    public int getRootLayout() {
        return R.layout.activity_new_chat;
    }

    @Override
    public void initGUI() {
//        initToolbar();
        initComponent();
        friendList=new ArrayList<>();
        pfbd = new ParseFirebaseData(this);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(USERS_CHILD);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String totalData = dataSnapshot.getValue().toString();
                // TODO: 09-04-2018 if number of items is 0 then show something else
                mAdapter = new FriendsListAdapter(ActivitySelectFriend.this, pfbd.getUserList(totalData));
                recyclerView.setAdapter(mAdapter);

                mAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, Friend obj, int position) {
                        ActivityChatDetails.navigate((ActivitySelectFriend) ActivitySelectFriend.this,
                                findViewById(R.id.lyt_parent), obj);
                    }
                });

                bindView();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Snackbar.make(getWindow().getDecorView(), "Could not connect", Snackbar.LENGTH_LONG).show();
            }
        });

        // for system bar in lollipop
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Tools.systemBarLolipop(this);
        }

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvTitle.setText("Select Friend");
    }

    @Override
    public void initData() {

    }

    private void initComponent() {
        ivBack = findViewById(R.id.ivBack);
        tvTitle = findViewById(R.id.tvTitle);
        recyclerView = findViewById(R.id.recyclerView);
        
        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
		recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
    }

    public void initToolbar(){
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
//        actionBar.setSubtitle(Constant.getFriendsData(this).size()+" friends");
    }

    public void bindView() {
        try {
            mAdapter.notifyDataSetChanged();
        } catch (Exception e) {
        }
    }
}
