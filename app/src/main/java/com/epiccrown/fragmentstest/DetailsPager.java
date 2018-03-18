package com.epiccrown.fragmentstest;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Epiccrown on 07.03.2018.
 */

public class DetailsPager extends Fragment {

    private ViewPager mViewPager;
    private ArrayList<Crime> mCrimes;
    private Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.detail_pager,container,false);

        mViewPager = v.findViewById(R.id.details_pager);
        mCrimes = CrimeLab.get(getActivity()).getCrimes();
        FragmentManager fragmentManager = ((FragmentActivity)mContext).getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimes.get(position);
                return CrimeDetailsFragment.newInstance(crime.getUuid());
            }
            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });

        UUID uuid = (UUID)getArguments().get("key");
        for(int i=0;i<mCrimes.size();i++){
            Crime crime = mCrimes.get(i);
            if(crime.getUuid().equals(uuid)) {mViewPager.setCurrentItem(i); break;}
        }

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }
}
