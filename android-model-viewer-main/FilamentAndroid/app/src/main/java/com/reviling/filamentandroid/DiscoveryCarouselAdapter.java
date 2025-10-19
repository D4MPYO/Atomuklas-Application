package com.reviling.filamentandroid;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DiscoveryCarouselAdapter extends RecyclerView.Adapter<DiscoveryCarouselAdapter.ViewHolder> {
    private final Context context;
    private final List<Integer> imageList;

    public DiscoveryCarouselAdapter(Context context, List<Integer> imageList) {
        this.context = context;
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.discovery_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int startIndex = position * 3;

        if (startIndex + 2 < imageList.size()) {
            int res1 = imageList.get(startIndex);
            int res2 = imageList.get(startIndex + 1);
            int res3 = imageList.get(startIndex + 2);

            holder.imageView1.setImageResource(res1);
            holder.imageView2.setImageResource(res2);
            holder.imageView3.setImageResource(res3);

            holder.imageView1.setOnClickListener(v -> handleImageClick(startIndex));
            holder.imageView2.setOnClickListener(v -> handleImageClick(startIndex + 1));
            holder.imageView3.setOnClickListener(v -> handleImageClick(startIndex + 2));
        }
    }

    private void handleImageClick(int imageIndex) {
        Intent intent;

        // 👇 Update these with your actual activities
        switch (imageIndex) {
            case 0:
                intent = new Intent(context, LearningModules.class);
                break;
            case 1:
                intent = new Intent(context, ViewModels.class);
                break;
            case 2:
                intent = new Intent(context, Quizzes.class);
                break;
            case 3:
                intent = new Intent(context, Trivia_New.class);
                break;
            case 4:
                intent = new Intent(context, AutomaniaGames.class);
                break;
            case 5:
                intent = new Intent(context, Worksheets2.class);
                break;
            // Add more cases depending on how many images you have
            default:
                return;
        }

        // If starting activity from adapter, add FLAG_ACTIVITY_NEW_TASK
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return (int) Math.ceil(imageList.size() / 3.0);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView1, imageView2, imageView3;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView1 = itemView.findViewById(R.id.image1);
            imageView2 = itemView.findViewById(R.id.image2);
            imageView3 = itemView.findViewById(R.id.image3);
        }
    }
}
