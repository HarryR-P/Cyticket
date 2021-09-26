package com.kk08.robbieexperiments;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.kk08.robbieexperiments.Fragments.BuyTab;
import com.kk08.robbieexperiments.Fragments.DataTab;
import com.kk08.robbieexperiments.Fragments.SellTab;

public class PageAdapter extends FragmentPagerAdapter {

    private int numTabs;

    public PageAdapter(@NonNull FragmentManager fm, int behavior, int numTabs) {
        super(fm, behavior);
        this.numTabs = numTabs;
    }


    // Define each of the tabs
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return new BuyTab();
            case 1:
                return new SellTab();
            case 2:
                return new DataTab();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return numTabs;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
