package com.nts.youtubemusic.ui.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nts.youtubemusic.R;
import com.nts.youtubemusic.common.Constant;
import com.nts.youtubemusic.data.model.recent.Item;
import com.nts.youtubemusic.data.model.table.Recent;
import com.nts.youtubemusic.databinding.ItemRecentBinding;
import com.nts.youtubemusic.interfaces.RecentListen;
import com.nts.youtubemusic.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class RecentAdapter extends RecyclerView.Adapter<RecentAdapter.ViewHolder> {
    private List<Recent> recentList = new ArrayList<>();
    private List<Item> items = new ArrayList<>();
    private RecentListen listen;
    private int type;


    @SuppressLint("NotifyDataSetChanged")
    public void setRecentList(List<Recent> recentList) {
        this.recentList = recentList;
        notifyDataSetChanged();
    }


    public void setListen(RecentListen listen) {
        this.listen = listen;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Item> getItems() {
        return items;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setItems(List<Item> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRecentBinding binding = ItemRecentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (type == Constant.TYPE_HOME) {

            holder.bind(recentList.get(holder.getAdapterPosition()).getName(), R.drawable.ic_recent);
        } else if (type == Constant.TYPE_TRENDING) {
            holder.bind(items.get(holder.getAdapterPosition()).getSnippet().getTitle(), R.drawable.ic_search);
        }

    }

    @Override
    public int getItemCount() {
        if (type == Constant.TYPE_HOME) {
            if (recentList != null)
                return recentList.size();
        } else if (type == Constant.TYPE_TRENDING) {
            if (items != null)
                return items.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemRecentBinding binding;

        public ViewHolder(@NonNull ItemRecentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(String txtRecent, int icon) {
            binding.txtRecent.setText(txtRecent);
            binding.txtRecent.setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0);
            binding.getRoot().setOnClickListener(v -> listen.onClickRecent(txtRecent));
            binding.txtRecent.setCompoundDrawablePadding(Utils.dpToPx(8));
        }
    }
}
