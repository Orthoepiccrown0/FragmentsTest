package com.epiccrown.fragmentstest;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Epiccrown on 04.03.2018.
 */

public class Firstfragment extends Fragment implements RecyclerTouchHelper.RecyclerItemTouchHelperListener{

    private RecyclerView mRecyclerView;
    private FloatingActionButton mFloatingActionButton;
    private CrimeListAdapter mAdapter;
    private static boolean showSubtitle = false;
    private CoordinatorLayout frameLayout;
    private ArrayList<Crime> crime_list;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.clime_list_recycler,container,false);
        mRecyclerView = v.findViewById(R.id.recyclerview_crimes);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        frameLayout = v.findViewById(R.id.recycler_layout_frame);
        mFloatingActionButton = v.findViewById(R.id.fab);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                try {
                    fm.beginTransaction().replace(R.id.main_container, new AddCrime())
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .addToBackStack(null)
                            .commit();
                }catch (Exception ex){ex.printStackTrace();}
            }
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && mFloatingActionButton.getVisibility() == View.VISIBLE) {
                    mFloatingActionButton.hide();
                } else if (dy < 0 && mFloatingActionButton.getVisibility() != View.VISIBLE) {
                    mFloatingActionButton.show();
                }
            }
        });

        updateUI();
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.list_menu,menu);
        MenuItem showSub = menu.findItem(R.id.details_menu);
        showSub.setChecked(showSubtitle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.refresh_item_menu:
                updateUI();
                //Toast.makeText(getActivity(), "Refresh cliccked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.details_menu:
                showSubtitle = !showSubtitle;
                item.setChecked(showSubtitle);
                updateSubtitle(showSubtitle);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public void updateSubtitle(boolean show){
        if(show) {
            int itemcount = CrimeLab.get(getActivity()).getCrimes().size();
            if(itemcount>0) {
                AppCompatActivity activity = (AppCompatActivity) getActivity();
                activity.getSupportActionBar().setSubtitle(itemcount + " crime(s) found");
            }
        }else{
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            activity.getSupportActionBar().setSubtitle(null);
        }

    }

    public void updateUI(){
        mAdapter = new CrimeListAdapter(CrimeLab.get(getActivity()),getActivity());
        mAdapter.setCrimes(CrimeLab.get(getActivity()).getCrimes());
        mRecyclerView.setAdapter(mAdapter );
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mRecyclerView);
        notifyEmptylst();

    }

    @Override
    public void onResume() {
        super.onResume();
        if(CrimeLab.currentCrime!=null)
        CrimeLab.get(getActivity()).updateCrime(CrimeLab.currentCrime);
        updateUI();
        updateSubtitle(showSubtitle);
        getActivity().setTitle("Crimes list");
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof CrimeListAdapter.MyViewHolder) {
            // get the removed item name to display it in snack bar
            String name = "";
            crime_list = CrimeLab.get(getActivity()).getCrimes();
            if(crime_list!=null) {
                // backup of removed item for undo purpose

                final Crime deletedItem = crime_list.get(viewHolder.getAdapterPosition());
                final int deletedIndex = viewHolder.getAdapterPosition();

                // remove the item from recycler view
                mAdapter.removeItem(viewHolder.getAdapterPosition());

                // showing snack bar with Undo option
                Snackbar snackbar = Snackbar
                        .make(frameLayout, "Crime was removed from list!", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        // undo is selected, restore the deleted item
                        mAdapter.restoreItem(deletedItem, deletedIndex);
                        notifyEmptylst();
                    }
                });
                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();
            }
            notifyEmptylst();
        }
    }

    private void notifyEmptylst(){
        TextView emptylist = getActivity().findViewById(R.id.empty_list);
        if(emptylist==null) return;
        if(CrimeLab.get(getActivity()).getCrimes().size()>0){
            emptylist.setVisibility(View.GONE);
        }else{
            emptylist.setVisibility(View.VISIBLE);
        }
    }



}
