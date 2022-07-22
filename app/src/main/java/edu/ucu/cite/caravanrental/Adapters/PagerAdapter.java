package edu.ucu.cite.caravanrental.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import edu.ucu.cite.caravanrental.Fragments.ApproveRentsFragment;
import edu.ucu.cite.caravanrental.Fragments.CancelledRentsFragment;
import edu.ucu.cite.caravanrental.Fragments.CompletedRentsFragment;
import edu.ucu.cite.caravanrental.Fragments.PendingRentsFragment;

public class PagerAdapter extends FragmentPagerAdapter {
    private int tabsRent;

    public PagerAdapter(@NonNull FragmentManager fm, int behavior, int tabsRent) {
        super(fm, behavior);
        this.tabsRent = tabsRent;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new PendingRentsFragment();
            case 1:
                return new ApproveRentsFragment();
            case 2:
                return new CancelledRentsFragment();
            case 3:
                return new CompletedRentsFragment();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return tabsRent;
    }
}
