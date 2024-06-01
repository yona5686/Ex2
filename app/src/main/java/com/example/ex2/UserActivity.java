package com.example.ex2;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ex2.allObjects.User;
import com.example.ex2.databinding.ActivityUserBinding;
import com.example.ex2.recyclerView.UserRecyclerViewAdapter;
import com.example.ex2.room.UsersDatabase;
import com.example.ex2.room.UsersTables;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity {

    ActivityUserBinding binding;
    UsersDatabase db;
    List<User> allUsers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        db = UsersDatabase.getInstance(this);
        allUsers.clear();
        List<UsersTables> allEntitys= db.userDao().getAll();
        for (UsersTables user : allEntitys) {
            User user1 = new User();
            user1.id=user.uid;
            user1.firstname=user.firstname;
            user1.lastname=user.lastname;
            user1.city=user.city;
            user1.country=user.country;
            user1.picture=user.picture;
            allUsers.add(user1);
        }




        UserRecyclerViewAdapter adapter = new UserRecyclerViewAdapter(allUsers);

        binding.recyclerView.setAdapter(adapter);
    }
}