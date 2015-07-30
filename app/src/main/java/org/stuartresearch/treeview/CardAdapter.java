package org.stuartresearch.treeview;

import android.content.res.Resources;
import android.support.design.widget.Snackbar;
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
            final Snackbar snackBar = Snackbar.make(v, Integer.toString(getAdapterPosition()), Snackbar.LENGTH_SHORT);
                    snackBar.setAction("close", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            snackBar.dismiss();
                        }
                    });
            snackBar.show();

            data.remove(getAdapterPosition());
            notifyDataSetChanged();
        }
    }

}
