package com.techouts.pcomplaints.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.techouts.pcomplaints.ApplicationListActivity;
import com.techouts.pcomplaints.ChatActivity;
import com.techouts.pcomplaints.CreateServiceActivity;
import com.techouts.pcomplaints.ExServiceManDetailsActivity;
import com.techouts.pcomplaints.ExServiceManListActivity;
import com.techouts.pcomplaints.HomeActivity;
import com.techouts.pcomplaints.MyCompliaintsActivity;
import com.techouts.pcomplaints.R;
import com.techouts.pcomplaints.ServicesActivity;
import com.techouts.pcomplaints.UserDetailsActivity;
import com.techouts.pcomplaints.UserListActivity;
import com.techouts.pcomplaints.utils.AppConstents;
import com.techouts.pcomplaints.utils.SharedPreferenceUtils;

import java.util.List;

/**
 * Created by TO-OW109 on 02-02-2018.
 */

public class MenuListAdaper extends RecyclerView.Adapter<MenuListAdaper.MenuViewHolder> {

    private List<String> menuList;

    public MenuListAdaper(List<String> menuList) {
        this.menuList = menuList;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_menu_item, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MenuViewHolder holder, int position) {
        holder.tvGroupTitle.setText(menuList.get(position));
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    class MenuViewHolder extends RecyclerView.ViewHolder {
        TextView tvGroupTitle;
        LinearLayout llChildItems;
        public MenuViewHolder(final View itemView) {
            super(itemView);
            tvGroupTitle = itemView.findViewById(R.id.tvGroupTitle);
            llChildItems = itemView.findViewById(R.id.llChildItems);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String title = tvGroupTitle.getText().toString();
                    switch (title) {
                        case AppConstents.CREATE_SERVICE: {
                            llChildItems.setVisibility(View.GONE);
                            ((HomeActivity)itemView.getContext()).closeDrawer();
                            Intent intent = new Intent(itemView.getContext(), CreateServiceActivity.class);
                            itemView.getContext().startActivity(intent);
                            break;
                        }
                        case AppConstents.SERVICES: {
                            llChildItems.setVisibility(View.GONE);
                            ((HomeActivity)itemView.getContext()).closeDrawer();
                            Intent intent = new Intent(itemView.getContext(), ServicesActivity.class);
                            itemView.getContext().startActivity(intent);
                            break;
                        }
                        case AppConstents.MY_ACCOUNT:{
                            llChildItems.setVisibility(View.GONE);
                            ((HomeActivity)itemView.getContext()).closeDrawer();
                            Intent intent = new Intent(itemView.getContext(), UserDetailsActivity.class);
                            intent.putExtra(AppConstents.EXTRA_EMAIL_ID, SharedPreferenceUtils.getStringValue(AppConstents.EMAIL_ID));
                            itemView.getContext().startActivity(intent);
                            break;
                        }
                        case AppConstents.MY_COMPLAINTS:{
                            llChildItems.setVisibility(View.GONE);
                            ((HomeActivity)itemView.getContext()).closeDrawer();
                            Intent intent = new Intent(itemView.getContext(), MyCompliaintsActivity.class);
                            itemView.getContext().startActivity(intent);
                            break;
                        }
                        case AppConstents.SERVICE_MAN_LIST:{
                            llChildItems.setVisibility(View.GONE);
                            ((HomeActivity)itemView.getContext()).closeDrawer();
                            Intent intent = new Intent(itemView.getContext(), ExServiceManListActivity.class);
                            intent.putExtra(AppConstents.EXTRA_LOGIN_TYPE,((HomeActivity)itemView.getContext()).loginType);
                            intent.putExtra(AppConstents.EXTRA_USER_LIST,AppConstents.SERVICE_MAN);
                            itemView.getContext().startActivity(intent);
                            break;
                        }
                        case AppConstents.CUSTOMER_LIST:{
                            llChildItems.setVisibility(View.GONE);
                            ((HomeActivity)itemView.getContext()).closeDrawer();
                            Intent intent = new Intent(itemView.getContext(), UserListActivity.class);
                            intent.putExtra(AppConstents.EXTRA_USER_LIST,AppConstents.CUSTOMER);
                            itemView.getContext().startActivity(intent);
                            break;
                        }
                        case AppConstents.CHAT:{
                            llChildItems.setVisibility(View.GONE);
                            ((HomeActivity)itemView.getContext()).closeDrawer();
                            Intent intent = new Intent(itemView.getContext(), ChatActivity.class);
                            itemView.getContext().startActivity(intent);
                            break;
                        }
                        case AppConstents.APPLICATION_LIST:{
                            if(llChildItems.getVisibility() == View.VISIBLE){
                                llChildItems.setVisibility(View.GONE);
                            }
                            else{
                                llChildItems.setVisibility(View.VISIBLE);
                                llChildItems.removeAllViews();
                                View view = LayoutInflater.from(itemView.getContext()).inflate(R.layout.list_menu_child_item,null);
                                TextView tvSearchByArea = view.findViewById(R.id.tvSearchByArea);
                                TextView tvSearchByApplicationType = view.findViewById(R.id.tvSearchByApplicationType);
                                llChildItems.addView(view);
                                tvSearchByArea.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        ((HomeActivity)itemView.getContext()).closeDrawer();
                                        Intent intent = new Intent(itemView.getContext(), ApplicationListActivity.class);
                                        intent.putExtra(AppConstents.EXTRA_SEARCH_BY,AppConstents.SEARCH_BY_AREA);
                                        itemView.getContext().startActivity(intent);
                                    }
                                });

                                tvSearchByApplicationType.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        ((HomeActivity)itemView.getContext()).closeDrawer();
                                        Intent intent = new Intent(itemView.getContext(), ApplicationListActivity.class);
                                        intent.putExtra(AppConstents.EXTRA_SEARCH_BY,AppConstents.SEARCH_BY_APPLICATION_TYPE);
                                        itemView.getContext().startActivity(intent);
                                    }
                                });
                            }
                            break;
                        }
                        case AppConstents.MY_PROFILE:{
                            ((HomeActivity)itemView.getContext()).closeDrawer();
                            Intent intent = new Intent(itemView.getContext(), ExServiceManDetailsActivity.class);
                            intent.putExtra(AppConstents.EXTRA_ISFROM_MYPROFILE,true);
                            itemView.getContext().startActivity(intent);
                            break;
                        }
                        case AppConstents.LOGIN :{
                            llChildItems.setVisibility(View.GONE);
//                            ((HomeActivity)itemView.getContext()).closeDrawer();
//                            Intent intent = new Intent(itemView.getContext(), LoginActivity.class);
//                            itemView.getContext().startActivity(intent);
                            ((HomeActivity) itemView.getContext()).finish();
                            break;
                        }
                        case AppConstents.LOGOUT: {
                            llChildItems.setVisibility(View.GONE);
                            ((HomeActivity)itemView.getContext()).closeDrawer();
//                            Intent intent = new Intent(itemView.getContext(), LoginActivity.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            itemView.getContext().startActivity(intent);

                            ((HomeActivity) itemView.getContext()).showLogoutPopup();
                            break;
                        }
                        default:
                            break;
                    }
                }
            });
        }
    }
}
