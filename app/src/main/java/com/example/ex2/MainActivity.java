package com.example.ex2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ex2.allObjects.Root;
import com.example.ex2.allObjects.User;
import com.example.ex2.databinding.ActivityMainBinding;
import com.example.ex2.http.UserApiClient;
import com.example.ex2.http.UserService;
import com.example.ex2.room.UsersDatabase;
import com.example.ex2.room.UsersTables;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    User myUser;
    UsersDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.nextUserBtn.setOnClickListener(clickListener);
        binding.addUserBtn.setOnClickListener(clickListener1);
        binding.viewCollBtn.setOnClickListener(clickListener2);


    }
        View.OnClickListener clickListener2 = v -> {
            Intent intent = new Intent(MainActivity.this, UserActivity.class);
            startActivity(intent);
        };
    View.OnClickListener clickListener1 = v -> {
        if(myUser.id.equals("Error")){
            Toast.makeText(this, "Can't add error to collection", Toast.LENGTH_SHORT).show();
            return;
        }
        db = UsersDatabase.getInstance(this);

        UsersTables userInDB = db.userDao().findUserById(myUser.id);
        if(userInDB != null){
            Toast.makeText(this, "Can't Add Same Person(Person in DB)", Toast.LENGTH_SHORT).show();
            return;

        }

        UsersTables user = new UsersTables();
        user.uid = myUser.id;
        user.firstname = myUser.firstname;
        user.lastname = myUser.lastname;
        user.email = myUser.email;
        user.age = myUser.age;
        user.city = myUser.city;
        user.country = myUser.country;
        user.picture = myUser.picture;
        db.userDao().insertUser(user);
        Toast.makeText(this, "User added", Toast.LENGTH_SHORT).show();


    };
    View.OnClickListener clickListener = v -> {
        binding.nextUserBtn.setEnabled(false);
        binding.addUserBtn.setEnabled(false);
        binding.viewCollBtn.setEnabled(false);
        Retrofit retrofit = UserApiClient.getClient();
        UserService service = retrofit.create(UserService.class);
        Call<Root> callAsync = service.getUser(null, null, null);
        callAsync.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(@NonNull Call<Root> call, @NonNull Response<Root> response) {
                Root root = response.body();
                assert root != null;
                binding.fNameTxt.setText(root.results.get(0).name.first);
                binding.lNameTxt.setText(root.results.get(0).name.last);
                binding.ageValTxt.setText(String.valueOf(root.results.get(0).dob.age));
                binding.emailTxt.setText(root.results.get(0).email);
                binding.cityTxt.setText(root.results.get(0).location.city);
                binding.countryTxt.setText(root.results.get(0).location.country);
                Glide.with(MainActivity.this).load(root.results.get(0).picture.large).apply(new RequestOptions().placeholder(R.drawable.placeholder).error(R.drawable.error)).into(binding.picture);
                myUser = new User(root.results.get(0).id.value,root.results.get(0).name.first,root.results.get(0).name.last,root.results.get(0).email,root.results.get(0).dob.age,root.results.get(0).location.city,root.results.get(0).location.country, root.results.get(0).picture.large);
                binding.nextUserBtn.setEnabled(true);
                binding.addUserBtn.setEnabled(true);
                binding.viewCollBtn.setEnabled(true);
            }

            @Override
            public void onFailure(@NonNull Call<Root> call, @NonNull Throwable throwable) {
                binding.fNameTxt.setText("Error");
                binding.lNameTxt.setText("Error");
                binding.ageValTxt.setText("Error");
                binding.emailTxt.setText("Error");
                binding.cityTxt.setText("Error");
                binding.countryTxt.setText("Error");
                Glide.with(MainActivity.this).load(R.drawable.error).apply(new RequestOptions().placeholder(R.drawable.placeholder).error(R.drawable.error)).into(binding.picture);
                myUser = new User("Error","Error","Error","Error",0,"Error","Error", "Error");
                binding.nextUserBtn.setEnabled(true);
                binding.addUserBtn.setEnabled(true);
                binding.viewCollBtn.setEnabled(true);
            }
        });
    };

    @Override
    protected void onResume() {
        super.onResume();
        binding.nextUserBtn.performClick();
    }
}