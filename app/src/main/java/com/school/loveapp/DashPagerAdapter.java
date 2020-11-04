package com.school.loveapp;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class DashPagerAdapter extends FragmentStatePagerAdapter {
    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    int mNoOfTabs;
    public DashPagerAdapter(FragmentManager fm){
        super(fm);
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                Dash_Enter tab1 =new Dash_Enter();
                return tab1;
            case 1:
                Dash_Search tab2 = new Dash_Search();
                return tab2;
            case 2:
                Dash_Details tab3 = new Dash_Details();
                return tab3;
            default:
                return  null;
        }
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return 3;
    }

}
