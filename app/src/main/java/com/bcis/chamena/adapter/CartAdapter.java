package com.bcis.chamena.adapter;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static android.widget.ListPopupWindow.MATCH_PARENT;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bcis.chamena.R;
import com.bcis.chamena.cart.Cart;
import com.bcis.chamena.cart.CartModel;
import com.bcis.chamena.databinding.CartItemBinding;
import com.bcis.chamena.databinding.FoodItemBinding;
import com.bcis.chamena.model.Product;
import com.bumptech.glide.Glide;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartItemViewHolder> {
    List<Cart> items;
    Context context;
    public CartAdapter(List<Cart> items, Context context){
      this.items=items;
      this.context=context;
    }
    private OnCartManipulateListener listener;
    public interface  OnCartManipulateListener{
        void onChange(Cart cart,boolean status);
    }
    public  void onCartManipulationListener(OnCartManipulateListener listener){
        this.listener=listener;
    }
    @NonNull
    @Override
    public CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CartItemBinding binding= CartItemBinding.bind(View.inflate(parent.getContext(), R.layout.cart_item,null));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                MATCH_PARENT, WRAP_CONTENT);
        binding.getRoot().setLayoutParams(params);
        return new CartItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemViewHolder holder, int position) {
       holder.binding.price.setText("Rs "+items.get(position).productPrice.toString());
       holder.binding.productname.setText(items.get(position).productName);
       holder.binding.orderq.setText(String.valueOf(items.get(position).orderItems));
       holder.binding.plus.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               listener.onChange(items.get(position),true);
           }
       });
       holder.binding.less.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               listener.onChange(items.get(position),false);
           }
       });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class CartItemViewHolder extends RecyclerView.ViewHolder{
        CartItemBinding binding;
        public CartItemViewHolder(@NonNull CartItemBinding itemView) {
            super(itemView.getRoot());
           binding=itemView;
        }
    }
    @Override
    public void onAttachedToRecyclerView(
            RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
