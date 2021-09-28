package com.example.iqlevelgame.activity;

import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.iqlevelgame.R;
import com.example.iqlevelgame.databinding.ActivityMainBinding;
import com.example.iqlevelgame.fragments.Home;
import com.example.iqlevelgame.fragments.LeaderBoardFragment;
import com.example.iqlevelgame.fragments.ProfileFragment;
import com.example.iqlevelgame.fragments.WalletFragment;
import com.iammert.library.readablebottombar.ReadableBottomBar;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Quiz Game");

        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, new Home());
        transaction.commit();

        binding.bottomBar.setOnItemSelectListener(new ReadableBottomBar.ItemSelectListener() {
           @Override
           public void onItemSelected(int i) {
               FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
             switch (i){
                 case 0:
                     transaction.replace(R.id.content, new Home());
                     transaction.commit();
                     break;
                 case 1:
                     Toast.makeText(MainActivity.this, "Rank clicked", Toast.LENGTH_SHORT).show();
                     break;
                case 2:
                     transaction.replace(R.id.content, new WalletFragment());
                     transaction.commit();

                     break;
                 case 3:
                     transaction.replace(R.id.content, new ProfileFragment());
                     transaction.commit();
                     break;
             }
           }
       });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.wallet_menu,menu);
        return true;
    }
}