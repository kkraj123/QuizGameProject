package com.example.iqlevelgame.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.iqlevelgame.R;
import com.example.iqlevelgame.databinding.FragmentWalletBinding;
import com.example.iqlevelgame.model.Users;
import com.example.iqlevelgame.model.WithdrawRequest;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class WalletFragment extends Fragment {

    public WalletFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    FragmentWalletBinding binding;
    FirebaseFirestore database;
    Users users;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWalletBinding.inflate(inflater,container,false);
        database=FirebaseFirestore.getInstance();

        database.collection("users")
                .document(FirebaseAuth.getInstance().getUid())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                users=documentSnapshot.toObject(Users.class);
                binding.currentCoin.setText(String.valueOf(users.getCoins()));
            }
        });

        binding.sentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (users.getCoins() > 60000) {
                    String uid=FirebaseAuth.getInstance().getUid();
                    String payPal=binding.paypalEmail.getText().toString();

                    WithdrawRequest request=new WithdrawRequest(uid,payPal,users.getName());

                    database.collection("withdraws")
                            .document(uid)
                            .set(request).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getContext(), "Request sent successfully", Toast.LENGTH_SHORT).show();
                        }
                    });

                }else {
                    Toast.makeText(getContext(), "You need to more coins to get withdraw", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        MenuInflater menuInflater=new MenuInflater(getContext());
        menuInflater.inflate(R.menu.wallet_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.wallet){
            Toast.makeText(getContext(), "Wallet is selected", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}