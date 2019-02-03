package com.learner.fifawc2018wallpaper.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.learner.fifawc2018wallpaper.Model.Common;
import com.learner.fifawc2018wallpaper.R;
import com.learner.fifawc2018wallpaper.Adapter.CategoryListAdapter;
import com.learner.fifawc2018wallpaper.Model.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {

    private DatabaseReference countryInformationRef;
    private DatabaseReference rootRef;
    private RecyclerView countryCategoryRecyclerView;
    private List<Category> categoryList = new ArrayList<>();


    private static CategoryFragment INSTANCE = null;

    public static CategoryFragment getInstance(){
        if (INSTANCE ==null){
            return new CategoryFragment();
        }else {
            return INSTANCE;
        }
    }

    public CategoryFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_category, container, false);
        countryCategoryRecyclerView =  view.findViewById(R.id.country_category_rv);
        countryCategoryRecyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(container.getContext(), 2);
        countryCategoryRecyclerView.setLayoutManager(gridLayoutManager);
        Log.e("size of country", "onCreateView: "+categoryList.size() );
        Log.e("database ref", "onCreateView: "+countryInformationRef );

        rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef.keepSynced(true);
        countryInformationRef = rootRef.child(Common.STR_COUNTRY_CATEGORY_REF);
        countryInformationRef.keepSynced(true);
        countryInformationRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postDataSnapshot: dataSnapshot.getChildren()){
                    Category category = postDataSnapshot.getValue(Category.class);
                    categoryList.add(category);
                }

                if (categoryList.size()>0){
                    CategoryListAdapter adapter = new CategoryListAdapter(getContext(),categoryList);
                    countryCategoryRecyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("ERROR_FETCH_DATABASE", "onCancelled: "+databaseError.getMessage() );
            }
        });

        return view;
    }

}
