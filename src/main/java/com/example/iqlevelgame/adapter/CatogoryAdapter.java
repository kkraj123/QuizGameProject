package com.example.iqlevelgame.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.iqlevelgame.R;
import com.example.iqlevelgame.activity.QuizeActivity;
import com.example.iqlevelgame.model.CatogoryModel;

import java.util.ArrayList;

public class CatogoryAdapter extends RecyclerView.Adapter<CatogoryAdapter.CatogoryViewHolder>{

    Context context;
    ArrayList<CatogoryModel> catogories;

    public CatogoryAdapter(Context context, ArrayList<CatogoryModel> catogories){
      this.context=context;
      this.catogories=catogories;
    }

    @NonNull
    @Override
    public CatogoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.sample_layout,parent,false);
        return new CatogoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CatogoryViewHolder holder, int position) {
      CatogoryModel model=catogories.get(position);
      holder.textView.setText(model.getCatogoryName());

        Glide.with(context)
                .load(model.getCatogoryImage())
                .into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, QuizeActivity.class);
                intent.putExtra("catId",model.getCatogoryId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return catogories.size();
    }

    public class CatogoryViewHolder extends RecyclerView.ViewHolder{

    ImageView imageView;
    TextView textView;
    public CatogoryViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView=itemView.findViewById(R.id.imageUser);
        textView=itemView.findViewById(R.id.catogoryName);
    }
}


}
