package com.example.ex2.recyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ex2.R;
import com.example.ex2.allObjects.User;
import com.example.ex2.databinding.UserItemBinding;

import java.util.ArrayList;
import java.util.List;

public class UserRecyclerViewAdapter extends RecyclerView.Adapter<UserViewHolder> {

    List<User> users = new ArrayList<>();
    UserItemBinding binding;

    public UserRecyclerViewAdapter(List<User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = UserItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new UserViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = users.get(position);
        Glide.with(binding.getRoot())
                .load(user.picture)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.error))
                .into(binding.userImg);


        binding.usernameTxt.setText(user.firstname);
        binding.lastNameTxt.setText(user.lastname);
        binding.countryUserTxt.setText(user.country);
        binding.cityUserTxt.setText(user.city);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
