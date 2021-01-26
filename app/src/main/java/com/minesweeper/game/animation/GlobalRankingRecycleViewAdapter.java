package com.minesweeper.game.animation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minesweeper.R;
import com.minesweeper.client.model.RankingRecord;

public class GlobalRankingRecycleViewAdapter extends RecyclerView.Adapter<GlobalRankingRecycleViewAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private RankingRecord[] ranking;

    public GlobalRankingRecycleViewAdapter(Context context, RankingRecord[] ranking) {
        this.mInflater = LayoutInflater.from(context);
        this.ranking = ranking;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewRank, textViewPlayer, textViewDate, textViewTime;

        ViewHolder(View itemView) {
            super(itemView);
            textViewRank = itemView.findViewById(R.id.globalTextViewRank);
            textViewPlayer = itemView.findViewById(R.id.globalTextViewPlayer);
            textViewDate = itemView.findViewById(R.id.globalTextViewDate);
            textViewTime = itemView.findViewById(R.id.globalTextViewTime);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.global_ranking_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RankingRecord record = ranking[position];
        holder.textViewRank.setText(String.valueOf(position + 1));
        holder.textViewPlayer.setText(record.getUsername());
        holder.textViewDate.setText(record.getDateAsString());
        holder.textViewTime.setText(record.getTimeAsString());
    }

    @Override
    public int getItemCount() {
        return ranking.length;
    }

    public void swapRanking(RankingRecord[] ranking) {
        this.ranking = ranking;
        notifyDataSetChanged();
    }
}
