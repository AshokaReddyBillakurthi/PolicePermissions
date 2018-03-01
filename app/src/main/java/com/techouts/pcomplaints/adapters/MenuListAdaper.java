package com.techouts.pcomplaints.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.techouts.pcomplaints.CreateServiceActivity;
import com.techouts.pcomplaints.ExServiceManListActivity;
import com.techouts.pcomplaints.utils.AppConstents;
import com.techouts.pcomplaints.ApplicationListActivity;
import com.techouts.pcomplaints.HomeActivity;
import com.techouts.pcomplaints.R;
import com.techouts.pcomplaints.ServicesActivity;
import com.techouts.pcomplaints.UserListActivity;

import java.util.ArrayList;

/**
 * Created by TO-OW109 on 02-02-2018.
 */

public class MenuListAdaper extends RecyclerView.Adapter<MenuListAdaper.MenuViewHolder> {

    private ArrayList<String> menuList;

    public MenuListAdaper(ArrayList<String> menuList) {
        this.menuList = menuList;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_group_view, parent, false);
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

        public MenuViewHolder(final View itemView) {
            super(itemView);
            tvGroupTitle = itemView.findViewById(R.id.tvGroupTitle);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String title = tvGroupTitle.getText().toString();
                    switch (title) {
                        case AppConstents.CREATE_SERVICE: {
                            ((HomeActivity)itemView.getContext()).closeDrawer();
                            Intent intent = new Intent(itemView.getContext(), CreateServiceActivity.class);
                            itemView.getContext().startActivity(intent);
                            break;
                        }
                        case AppConstents.SERVICES: {
                            ((HomeActivity)itemView.getContext()).closeDrawer();
                            Intent intent = new Intent(itemView.getContext(), ServicesActivity.class);
                            itemView.getContext().startActivity(intent);
                            break;
                        }
                        case AppConstents.SERVICE_MAN_LIST:{
                            ((HomeActivity)itemView.getContext()).closeDrawer();
                            Intent intent = new Intent(itemView.getContext(), ExServiceManListActivity.class);
                            intent.putExtra(AppConstents.EXTRA_LOGIN_TYPE,((HomeActivity)itemView.getContext()).loginType);
                            intent.putExtra(AppConstents.EXTRA_USER_LIST,AppConstents.SERVICE_MAN);
                            itemView.getContext().startActivity(intent);
                            break;
                        }
                        case AppConstents.CUSTOMER_LIST:{
                            Intent intent = new Intent(itemView.getContext(), UserListActivity.class);
                            intent.putExtra(AppConstents.EXTRA_USER_LIST,AppConstents.CUSTOMER);
                            itemView.getContext().startActivity(intent);
                            break;
                        }
                        case AppConstents.APPLICATION_LIST:{
                            ((HomeActivity)itemView.getContext()).closeDrawer();
                            Intent intent = new Intent(itemView.getContext(), ApplicationListActivity.class);
                            itemView.getContext().startActivity(intent);
                            break;
                        }
                        case AppConstents.LOGIN :{
//                            ((HomeActivity)itemView.getContext()).closeDrawer();
//                            Intent intent = new Intent(itemView.getContext(), LoginActivity.class);
//                            itemView.getContext().startActivity(intent);
                            ((HomeActivity) itemView.getContext()).finish();
                            break;
                        }
                        case AppConstents.LOGOUT: {
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
