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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.techouts.pcomplaints.R;
import com.techouts.pcomplaints.adapters.AreaAdapter;
import com.techouts.pcomplaints.adapters.CityAdapter;
import com.techouts.pcomplaints.adapters.DistrictAdapter;
import com.techouts.pcomplaints.adapters.DivisionPoliceStationAdapter;
import com.techouts.pcomplaints.adapters.StateAdapter;
import com.techouts.pcomplaints.adapters.SubDivisionAdapter;
import com.techouts.pcomplaints.model.Area;
import com.techouts.pcomplaints.model.City;
import com.techouts.pcomplaints.model.District;
import com.techouts.pcomplaints.model.DivisionPoliceStation;
import com.techouts.pcomplaints.model.State;
import com.techouts.pcomplaints.model.SubDivision;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TO-OW109 on 05-03-2018.
 */

public class CustomDialog extends Dialog {

    private EditText edtSearch;
    private ImageView ivCross;
    private Context mContext;
    private LinearLayout llSearch;
    private LinearLayout llBtns;

    private List<String> listNames;
    private List<Area> areaList;
    private List<City> cityList;
    private List<State> stateList;
    private List<District> districtList;
    private List<SubDivision> subDivisionList;
    private List<DivisionPoliceStation> divisionPoliceStationsList;
    private NameSelectedListener nameSelectedListener;
    private OnStateSelected onStateSelected;
    private OnDistrictSelected onDistrictSelected;
    private OnSubDivisionSelected onSubDivisionSelected;
    private OnDivisionPoliceStation onDivisionPoliceStation;
    private boolean isSearchReq;
    private boolean isCheckboxNeed = false;
    private TextView tvTitle;
    private Button btnOk;
    private String title;
    private boolean isArea = false;
    private boolean isCity = false;
    private boolean isState = false;
    private boolean isDistrict = false;
    private boolean isSubDivision = false;
    private boolean isDivisionPoliceStation = false;
    private ListNamesAdapter listNamesAdapter;
    private AreaAdapter areaAdapter;
    private CityAdapter cityAdapter;
    private DistrictAdapter districtAdapter;
    private StateAdapter stateAdapter;
    private SubDivisionAdapter subDivisionAdapter;
    private DivisionPoliceStationAdapter divisionPoliceStationAdapter;


    public CustomDialog(@NonNull Context context, List<String> listNames, String title,
                        boolean isSearchReq, boolean isCheckboxNeed, NameSelectedListener nameSelectedListener) {
        super(context);
        this.mContext = context;
        this.listNames = listNames;
        this.isSearchReq = isSearchReq;
        this.isCheckboxNeed = isCheckboxNeed;
        this.title = title;
        this.nameSelectedListener = nameSelectedListener;
    }

//    public CustomDialog(@NonNull Context context, List<Area> areaList, String title,boolean isArea,
//                        boolean isSearchReq, boolean isCheckboxNeed, NameSelectedListener nameSelectedListener) {
//        super(context);
//        this.mContext = context;
//        this.areaList = areaList;
//        this.isSearchReq = isSearchReq;
//        this.isCheckboxNeed = isCheckboxNeed;
//        this.title = title;
//        this.nameSelectedListener = nameSelectedListener;
//        this.isArea = isArea;
//    }
//
//    public CustomDialog(@NonNull Context context, List<City> cityList, boolean isCity, String title,
//                        boolean isSearchReq, boolean isCheckboxNeed, NameSelectedListener nameSelectedListener) {
//        super(context);
//        this.mContext = context;
//        this.cityList = cityList;
//        this.isSearchReq = isSearchReq;
//        this.isCheckboxNeed = isCheckboxNeed;
//        this.title = title;
//        this.nameSelectedListener = nameSelectedListener;
//        this.isCity = isCity;
//    }

    public CustomDialog(@NonNull Context context, boolean isState, List<State> stateList, String title,
                        boolean isSearchReq, boolean isCheckboxNeed, NameSelectedListener nameSelectedListener) {
        super(context);
        this.mContext = context;
        this.stateList = stateList;
        this.isSearchReq = isSearchReq;
        this.isCheckboxNeed = isCheckboxNeed;
        this.title = title;
        this.nameSelectedListener = nameSelectedListener;
        this.isState = isState;
    }

    public CustomDialog(@NonNull Context context, boolean isState, List<State> stateList, String title,
                        boolean isSearchReq, boolean isCheckboxNeed, OnStateSelected onStateSelected) {
        super(context);
        this.mContext = context;
        this.stateList = stateList;
        this.isSearchReq = isSearchReq;
        this.isCheckboxNeed = isCheckboxNeed;
        this.title = title;
        this.onStateSelected = onStateSelected;
        this.isState = isState;
    }

    public CustomDialog(@NonNull Context context, List<District> districtList, String title,
                        boolean isSearchReq, boolean isCheckboxNeed,
                        boolean isDistrict, OnDistrictSelected onDistrictSelected) {
        super(context);
        this.mContext = context;
        this.districtList = districtList;
        this.isSearchReq = isSearchReq;
        this.isCheckboxNeed = isCheckboxNeed;
        this.title = title;
        this.onDistrictSelected = onDistrictSelected;
        this.isDistrict = isDistrict;
    }

    public CustomDialog(@NonNull Context context, List<SubDivision> subDivisionList,
                        boolean isSubDivision, String title, boolean isSearchReq, boolean isCheckboxNeed,
                        OnSubDivisionSelected onSubDivisionSelected) {
        super(context);
        this.mContext = context;
        this.subDivisionList = subDivisionList;
        this.isSearchReq = isSearchReq;
        this.isCheckboxNeed = isCheckboxNeed;
        this.title = title;
        this.onSubDivisionSelected = onSubDivisionSelected;
        this.isSubDivision = isSubDivision;
    }

    public CustomDialog(@NonNull Context context, List<DivisionPoliceStation> divisionPoliceStationsList,
                        String title, boolean isSearchReq, boolean isCheckboxNeed,
                        boolean isDivisionPoliceStation, OnDivisionPoliceStation onDivisionPoliceStation) {
        super(context);
        this.mContext = context;
        this.divisionPoliceStationsList = divisionPoliceStationsList;
        this.isSearchReq = isSearchReq;
        this.isCheckboxNeed = isCheckboxNeed;
        this.title = title;
        this.onDivisionPoliceStation = onDivisionPoliceStation;
        this.isDivisionPoliceStation = isDivisionPoliceStation;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_layout);
        edtSearch = findViewById(R.id.edtSearch);
        ivCross = findViewById(R.id.ivCross);
        llSearch = findViewById(R.id.llSearch);
        llBtns = findViewById(R.id.llBtns);
        btnOk = findViewById(R.id.btnOk);
        tvTitle = findViewById(R.id.tvTitle);
        CustomRecyclerView rvList = findViewById(R.id.rvList);
        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        rvList.setHasFixedSize(true);

        if (isArea) {
            areaAdapter = new AreaAdapter(areaList, isCheckboxNeed, nameSelectedListener);
            rvList.setAdapter(areaAdapter);
        } else if (isCity) {
            cityAdapter = new CityAdapter(cityList, nameSelectedListener);
            rvList.setAdapter(cityAdapter);
        } else if (isState) {
            stateAdapter = new StateAdapter(stateList, onStateSelected);
            rvList.setAdapter(stateAdapter);
        } else if (isDistrict) {
            districtAdapter = new DistrictAdapter(districtList, onDistrictSelected);
            rvList.setAdapter(districtAdapter);
        } else if (isSubDivision) {
            subDivisionAdapter = new SubDivisionAdapter(subDivisionList, onSubDivisionSelected);
            rvList.setAdapter(subDivisionAdapter);
        } else if (isDivisionPoliceStation) {
            divisionPoliceStationAdapter = new DivisionPoliceStationAdapter(divisionPoliceStationsList, onDivisionPoliceStation);
            rvList.setAdapter(divisionPoliceStationAdapter);
        } else {
            listNamesAdapter = new ListNamesAdapter();
            rvList.setAdapter(listNamesAdapter);
            listNamesAdapter.refresh(listNames);
        }

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder stringBuilder = new StringBuilder();
                List<Area> areas = areaAdapter.getSelectedAreas();
                for (Area area : areas) {
                    stringBuilder.append(area.areaName).append(",");
                }
                int index = -1;
                String areaStr = "";
                index = stringBuilder.toString().lastIndexOf(",");
                if (index >= 0) {
                    areaStr = stringBuilder.toString().substring(0, index);
                    nameSelectedListener.onNameSelected(areaStr);
                }
            }
        });

        edtSearch.setHint("Search (Min. 3 Letters)");

        tvTitle.setText(title);

        if (isCheckboxNeed)
            llBtns.setVisibility(View.VISIBLE);
        else
            llBtns.setVisibility(View.GONE);

        if (isSearchReq)
            llSearch.setVisibility(View.VISIBLE);
        else
            llSearch.setVisibility(View.GONE);

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
                if (null != s && s.length() >= 3) {
                    searchText(s.toString());
                } else if (s == null || s.length() == 0) {
                    ivCross.setVisibility(View.GONE);
                    searchText("");
                }
            }
        });

        ivCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtSearch.setText("");
                edtSearch.setHint("Search (Min. 3 Letters)");
                ivCross.setVisibility(View.GONE);
                searchText("");
            }
        });

    }


    private void searchText(String searchText) {
        try {
            if (isState) {
                List<State> tempList = new ArrayList<>();
                if (!TextUtils.isEmpty(searchText)) {
                    for (State state : stateList) {
                        if ((state.stateName.toLowerCase().contains(searchText.toLowerCase()))) {
                            tempList.add(state);
                        }
                    }
                    stateAdapter.refresh(tempList);
                } else {
                    stateAdapter.refresh(stateList);
                }
            } else if (isCity) {
                List<City> tempList = new ArrayList<>();
                if (!TextUtils.isEmpty(searchText)) {
                    for (City city : cityList) {
                        if ((city.cityName.toLowerCase().contains(searchText.toLowerCase()))) {
                            tempList.add(city);
                        }
                    }
                    cityAdapter.refresh(tempList);
                } else {
                    cityAdapter.refresh(cityList);
                }
            } else if (isArea) {
                List<Area> tempList = new ArrayList<>();
                if (!TextUtils.isEmpty(searchText)) {
                    for (Area area : areaList) {
                        if ((area.areaName.toLowerCase().contains(searchText.toLowerCase()))) {
                            tempList.add(area);
                        }
                    }
                    areaAdapter.refresh(tempList);
                } else {
                    areaAdapter.refresh(areaList);
                }
            } else {
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
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class ListNamesAdapter extends RecyclerView.Adapter<ListNamesAdapter.NamesHolder> {

        private List<String> listNames;
        private List<String> selectedList;

        public ListNamesAdapter() {
            this.listNames = new ArrayList<>();
            this.selectedList = new ArrayList<>();
        }

        @Override
        public NamesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_cell, parent, false);
            return new NamesHolder(view);
        }

        public void refresh(List<String> listNames) {
            this.listNames = listNames;
            notifyDataSetChanged();
        }

        @Override
        public void onBindViewHolder(NamesHolder holder, int position) {
            holder.tvListName.setText(listNames.get(position).toString() + "");
        }

        @Override
        public int getItemCount() {
            return listNames.size();
        }

        class NamesHolder extends RecyclerView.ViewHolder {
            private TextView tvListName;
            private CheckBox cbxChecked;

            public NamesHolder(View itemView) {
                super(itemView);
                tvListName = itemView.findViewById(R.id.tvListName);
                cbxChecked = itemView.findViewById(R.id.cbxChecked);

                if (isCheckboxNeed)
                    cbxChecked.setVisibility(View.VISIBLE);

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

    public interface NameSelectedListener {
        void onNameSelected(String listName);
    }

    public interface OnStateSelected {
        void onStateSelected(State state);
    }

    public interface OnDistrictSelected {
        void onDistrictSelected(District district);
    }

    public interface OnSubDivisionSelected {
        void OnSubDivisionSelected(SubDivision subDivision);
    }

    public interface OnDivisionPoliceStation {
        void onDivisionPoliceStation(DivisionPoliceStation divisionPoliceStation);
    }


}
