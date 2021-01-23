package com.minesweeper.v2.animation;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minesweeper.R;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {

    private Cursor cData;
    private LayoutInflater mInflater;

    public RecycleViewAdapter(Context context, Cursor cData) {
        this.mInflater = LayoutInflater.from(context);
        this.cData = cData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (cData.moveToPosition(position)) {
            holder.textViewRank.setText(String.valueOf(position + 1));
            holder.textViewDate.setText(String.valueOf(
                    cData.getString(cData.getColumnIndex("date"))
            ));
            holder.textViewTime.setText(String.valueOf(
                    cData.getInt(cData.getColumnIndex("time"))
            ));
        }
    }

    @Override
    public int getItemCount() {
        return cData.getCount();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewRank, textViewDate, textViewTime;

        ViewHolder(View itemView) {
            super(itemView);
            textViewRank = itemView.findViewById(R.id.textViewRank);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewTime = itemView.findViewById(R.id.textViewTime);
        }
    }

    public void swapCursor(Cursor newCursor) {
        if (cData != null)
            cData.close();
        cData = newCursor;
        if (cData != null)
            notifyDataSetChanged();
    }
}
