package org.stuartresearch.treeview;

import android.content.res.Resources;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jake on 7/28/15.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    ArrayList<TreeEntry> data;

    public CardAdapter(ArrayList<TreeEntry> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TreeEntry entry = data.get(position);
        String item = entry.content;
        int indent = entry.indent;

        holder.text.setText(item);

        ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) holder.card.getLayoutParams();
        p.setMargins((int) (10 * indent * Resources.getSystem().getDisplayMetrics().density), 5, 5, 5);
        holder.card.requestLayout();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void expandAll() {
        for (int i = 0; i < data.size(); i++) {
            TreeEntry entry = data.get(i);
            expandEntry(entry, i);
        }
        notifyDataSetChanged();
    }

    public void collapseAll() {
        for (int i = 0; i < data.size(); i++) {
            TreeEntry entry = data.get(i);
            collapseEntry(entry, i);
        }
        notifyDataSetChanged();
    }

    public void expandEntry(TreeEntry entry) {
        int index = data.indexOf(entry);
        expandEntry(entry, index);
        notifyDataSetChanged();
    }

    public void collapseEntry(TreeEntry entry) {
        int index = data.indexOf(entry);
        collapseEntry(entry, index);
        notifyDataSetChanged();
    }

    private void expandEntry(TreeEntry entry, int index) {
        int size = entry.children.size();
        for (int i = 0; i < size; i++) {
            TreeEntry child = entry.children.get(i);
            if (child.indent == entry.indent + 1) {
                data.add(index + 1 + i, child);

                if (child.isExpanded) {
                    expandEntry(child, index + i);
                }
            }
        }

        entry.isExpanded = true;
    }

    private void collapseEntry(TreeEntry entry, int index) {
        int size = entry.children.size();
        for (int i = 0; i < size; i++)
            data.remove(index + 1);

        entry.isExpanded = false;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView text;
        public CardView card;

        public ViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.card_view_text);
            card = (CardView) itemView.findViewById(R.id.card_view);

            card.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            TreeEntry entry = data.get(getAdapterPosition());

            if (entry.isExpanded) {
                collapseEntry(entry);
            } else {
                expandEntry(entry);
            }

            entry.isExpanded = !entry.isExpanded;
            return;
        }

    }

}
