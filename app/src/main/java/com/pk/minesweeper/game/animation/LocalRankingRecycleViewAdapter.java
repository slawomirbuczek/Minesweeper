package com.pk.minesweeper.game.animation;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pk.minesweeper.R;

import java.util.Locale;

public class LocalRankingRecycleViewAdapter extends RecyclerView.Adapter<LocalRankingRecycleViewAdapter.ViewHolder> {

    private Cursor cData;
    private final LayoutInflater mInflater;

    public LocalRankingRecycleViewAdapter(Context context, Cursor cData) {
        this.mInflater = LayoutInflater.from(context);
        this.cData = cData;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewRank, textViewDate, textViewTime;

        ViewHolder(View itemView) {
            super(itemView);
            textViewRank = itemView.findViewById(R.id.localTextViewRank);
            textViewDate = itemView.findViewById(R.id.localTextViewDate);
            textViewTime = itemView.findViewById(R.id.localTextViewTime);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.local_ranking_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (cData.moveToPosition(position)) {
            holder.textViewRank.setText(String.valueOf(position + 1));
            holder.textViewDate.setText(String.valueOf(
                    cData.getString(cData.getColumnIndex("date"))
            ));
            holder.textViewTime.setText(
                    String.format(Locale.ENGLISH,"%.2f", cData.getFloat(cData.getColumnIndex("time"))
            ));
        }
    }

    @Override
    public int getItemCount() {
        return cData.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (cData != null)
            cData.close();
        cData = newCursor;
        if (cData != null)
            notifyDataSetChanged();
    }
}
