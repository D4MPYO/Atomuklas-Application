// TriviaAdapter.java
package com.reviling.filamentandroid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TriviaAdapter extends RecyclerView.Adapter<TriviaAdapter.TriviaViewHolder> {

    private final List<TriviaItem> triviaItems;

    public TriviaAdapter(List<TriviaItem> triviaItems) {
        this.triviaItems = triviaItems;
    }

    @NonNull
    @Override
    public TriviaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trivia_item, parent, false);
        return new TriviaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TriviaViewHolder holder, int position) {
        TriviaItem item = triviaItems.get(position);
        holder.triviaText.setText(item.getText());

        if (item.hasImage()) {
            holder.triviaImage.setVisibility(View.VISIBLE);
            holder.triviaImage.setImageResource(item.getImageResId());
        } else {
            holder.triviaImage.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return triviaItems.size();
    }

    static class TriviaViewHolder extends RecyclerView.ViewHolder {
        TextView triviaText;
        ImageView triviaImage;

        TriviaViewHolder(View itemView) {
            super(itemView);
            triviaText = itemView.findViewById(R.id.triviaText);
            triviaImage = itemView.findViewById(R.id.triviaImage);
        }
    }
}
