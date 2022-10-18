package com.bcis.chamena.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bcis.chamena.R;
import com.bcis.chamena.adapter.FoodItemAdapter;
import com.bcis.chamena.common.RecyclerViewMargin;
import com.bcis.chamena.common.UserPref;
import com.bcis.chamena.databinding.FoodItemLayoutBinding;
import com.bcis.chamena.databinding.GreetLayoutBinding;
import com.bcis.chamena.databinding.UserHomeLayoutBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class UserHomeFragment extends Fragment {
    UserHomeLayoutBinding binding;
    ArrayList<Dummy> data = new ArrayList<>();
    GreetLayoutBinding greetLayoutBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = UserHomeLayoutBinding.inflate(getLayoutInflater());
        greetLayoutBinding = GreetLayoutBinding.inflate(getLayoutInflater());
        loadData();
        bindView();
        return binding.getRoot();
    }

    void bindView(){
        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.swipeRefresh.setRefreshing(false);
            }
        });
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            greetLayoutBinding.name.setText(new UserPref(null,getContext()).getUserPref().fullName);
            binding.root.addView(greetLayoutBinding.getRoot());
        }


        for (Dummy item:data) {
            FoodItemLayoutBinding foodItemBinding = FoodItemLayoutBinding.inflate(getLayoutInflater());
            foodItemBinding.category.setText(item.category);
            foodItemBinding.image.setImageDrawable(getContext().getDrawable(item.icon));
            FoodItemAdapter adapter = new FoodItemAdapter(item.items);
            RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
            RecyclerView foodRecyclerView = foodItemBinding.foodItemList;
            foodRecyclerView.setLayoutManager(manager);
            foodRecyclerView.setHasFixedSize(true);
            foodRecyclerView.addItemDecoration(new RecyclerViewMargin(25,item.items.size()));
            foodRecyclerView.setAdapter(adapter);
            binding.root.addView(foodItemBinding.getRoot());
        }


    }

    void loadData(){
        ArrayList<String> items = new ArrayList<String>();
        for(int i=0;i<10;i++){
            items.add("Item "+i);
        }
        data.add(new Dummy("Foods",items, R.drawable.burger));
        data.add(new Dummy("Drinks",items,R.drawable.cup));
    }


}

class Dummy{
    String category;
    ArrayList<String> items;
    int icon;
    Dummy(String category,ArrayList<String> items,int icon){
        this.category=category;
        this.items=items;
        this.icon=icon;
    }
}