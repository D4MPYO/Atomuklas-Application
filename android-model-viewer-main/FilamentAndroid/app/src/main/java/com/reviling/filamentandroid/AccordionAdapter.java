package com.reviling.filamentandroid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AccordionAdapter extends RecyclerView.Adapter<AccordionAdapter.ViewHolder> {

    private final List<AccordionItem> items;
    private final Set<Integer> expandedPositions = new HashSet<>();

    public AccordionAdapter(List<AccordionItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.accordion_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AccordionItem item = items.get(position);

        holder.headerText.setText(item.getHeader());
        holder.subHeaderText.setText(item.getSubheader());
        holder.bodyText.setText(item.getBody());

        boolean isExpanded = expandedPositions.contains(position);
        holder.subHeaderText.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.bodyText.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.arrowIcon.setImageResource(isExpanded ? R.drawable.ic_arrow_up : R.drawable.ic_arrow_down);

        holder.itemView.setOnClickListener(v -> {
            if (isExpanded) {
                expandedPositions.remove(position);
            } else {
                expandedPositions.add(position);
            }
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView headerText, subHeaderText, bodyText;
        ImageView arrowIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            headerText = itemView.findViewById(R.id.headerText);
            subHeaderText = itemView.findViewById(R.id.subHeaderText);
            bodyText = itemView.findViewById(R.id.bodyText);
            arrowIcon = itemView.findViewById(R.id.arrowIcon);
        }
    }
}
