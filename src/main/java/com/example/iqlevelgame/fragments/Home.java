package com.example.iqlevelgame.fragments;

import android.app.FragmentBreadCrumbs;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.iqlevelgame.R;
import com.example.iqlevelgame.adapter.CatogoryAdapter;
import com.example.iqlevelgame.databinding.FragmentHomeBinding;
import com.example.iqlevelgame.model.CatogoryModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.ArrayList;


public class Home extends Fragment {



    public Home() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    FragmentHomeBinding binding;
    FirebaseFirestore databse;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater,container,false);

        databse=FirebaseFirestore.getInstance();

        ArrayList<CatogoryModel> catogories=new ArrayList<>();
        CatogoryAdapter adapter=new CatogoryAdapter(getContext(),catogories);

       databse.collection("catogries")
               .addSnapshotListener(new EventListener<QuerySnapshot>() {
                   @Override
                   public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                       catogories.clear();
                      for (DocumentSnapshot snapshot : value.getDocuments()){
                          CatogoryModel model= snapshot.toObject(CatogoryModel.class);
                          model.setCatogoryId(snapshot.getId());
                          catogories.add(model);
                      }
                      adapter.notifyDataSetChanged();
                   }
               });



        GridLayoutManager layoutManager=new GridLayoutManager(getContext(),2);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setAdapter(adapter);

        return binding.getRoot();
    }
}