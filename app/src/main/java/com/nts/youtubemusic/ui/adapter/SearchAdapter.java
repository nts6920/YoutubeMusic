package com.nts.youtubemusic.ui.adapter;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nts.youtubemusic.data.model.table.VideoChannel;
import com.nts.youtubemusic.databinding.ItemVideoSearchBinding;
import com.nts.youtubemusic.interfaces.YoutubeListen;
import com.nts.youtubemusic.utils.ConvertTime;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private List<VideoChannel> items = new ArrayList<>();
    private YoutubeListen listen;

    public void setListen(YoutubeListen listen) {
        this.listen = listen;
    }


    public List<VideoChannel> getItems() {
        return items;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setItems(List<VideoChannel> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemVideoSearchBinding binding = ItemVideoSearchBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SearchViewHolder(binding);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.bind(items.get(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {
        private ItemVideoSearchBinding binding;

        public SearchViewHolder(@NonNull ItemVideoSearchBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @SuppressLint("SetTextI18n")
        public void bind(VideoChannel videoChannel) {
            Glide.with(binding.imgThumbnails).load(videoChannel.getItem().getSnippet().getThumbnails().getHigh().getUrl()).into(binding.imgThumbnails);
            binding.txtTitle.setText(videoChannel.getItem().getSnippet().getTitle());
            binding.txtChannel.setText(videoChannel.getItem().getSnippet().getChannelTitle());
            binding.txtView.setText(ConvertTime.convertViewCount(binding.getRoot().getContext(), videoChannel.getItem().getStatistics().getViewCount()) +
                    " - " + ConvertTime.convertTime(binding.getRoot().getContext(), videoChannel.getItem().getSnippet().getPublishedAt()));
            binding.textDuration.setText(ConvertTime.convertDuration(Duration.parse(videoChannel.getItem().getContentDetails().getDuration()).getSeconds()));


            binding.getRoot().setOnClickListener(v -> {
                if (videoChannel.getItem() != null) {
                    listen.onClickItem(videoChannel.getItem());
                }
            });

        }
    }
}

