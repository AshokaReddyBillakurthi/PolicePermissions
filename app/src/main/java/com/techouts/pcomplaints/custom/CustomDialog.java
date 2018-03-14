package com.techouts.pcomplaints.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.techouts.pcomplaints.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TO-OW109 on 05-03-2018.
 */

public class CustomDialog extends Dialog{

    private EditText edtSearch;
    private ImageView ivCross;
    private Context mContext;
    private ListNamesAdapter listNamesAdapter;
    private List<String> listNames;
    private NameSelectedListener nameSelectedListener;


    public CustomDialog(@NonNull Context context,List<String> listNames,NameSelectedListener nameSelectedListener) {
        super(context);
        this.mContext = context;
        this.listNames = listNames;
        this.nameSelectedListener = nameSelectedListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_layout);
        edtSearch = findViewById(R.id.edtSearch);
        ivCross = findViewById(R.id.ivCross);
        RecyclerView rvList = findViewById(R.id.rvList);
        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        listNamesAdapter = new ListNamesAdapter();
        rvList.setAdapter(listNamesAdapter);
        listNamesAdapter.refresh(listNames);
        edtSearch.setHint("Search Area(Min. 3 Letters)");

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                /*some text*/
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null && s.length() > 0)
                    ivCross.setVisibility(View.VISIBLE);
                else
                    ivCross.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (null!=s && s.length() >= 3) {
                    searchText(s.toString());
                } else if (s==null||s.length()==0) {
                    ivCross.setVisibility(View.GONE);
                    searchText("");
                }
            }
        });

        ivCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtSearch.setText("");
                edtSearch.setHint("Search Area(Min. 3 Letters)");
                ivCross.setVisibility(View.GONE);
                searchText("");
            }
        });
    }


    private void searchText(String searchText){
        try {
            List<String> tempList = new ArrayList<>();
            if (!TextUtils.isEmpty(searchText)) {
                for (String string : listNames) {
                    if ((string.toLowerCase().contains(searchText.toLowerCase()))) {
                        tempList.add(string);
                    }
                }
                listNamesAdapter.refresh(tempList);
            } else {
                listNamesAdapter.refresh(listNames);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class ListNamesAdapter extends RecyclerView.Adapter<ListNamesAdapter.NamesHolder>{

        private List<String> listNames;

        public ListNamesAdapter(){
            this.listNames = new ArrayList<>();
        }

        @Override
        public NamesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_cell,parent,false);
            return new NamesHolder(view);
        }

        public void refresh(List<String> listNames){
            this.listNames = listNames;
            notifyDataSetChanged();
        }

        @Override
        public void onBindViewHolder(NamesHolder holder, int position) {
            holder.tvListName.setText(listNames.get(position).toString()+"");
        }

        @Override
        public int getItemCount() {
            return listNames.size();
        }

        class NamesHolder extends RecyclerView.ViewHolder {
            private TextView tvListName;

            public NamesHolder(View itemView) {
                super(itemView);
                tvListName = itemView.findViewById(R.id.tvListName);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = tvListName.getText().toString();
                        nameSelectedListener.onNameSelected(name);
                    }
                });
            }
        }
    }

    public interface NameSelectedListener{
        void onNameSelected(String listName);
    }
}
