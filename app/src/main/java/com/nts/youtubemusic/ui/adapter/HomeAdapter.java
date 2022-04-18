package com.nts.youtubemusic.ui.adapter;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nts.youtubemusic.common.Constant;
import com.nts.youtubemusic.data.model.table.VideoChannel;
import com.nts.youtubemusic.databinding.ItemVideoBinding;
import com.nts.youtubemusic.databinding.ItemVideoSearchBinding;
import com.nts.youtubemusic.interfaces.YoutubeListen;
import com.nts.youtubemusic.utils.ConvertTime;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<VideoChannel> items = new ArrayList<>();
    private YoutubeListen listen;
    private int type;

    public void setType(int type) {
        this.type = type;
    }

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
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (type == Constant.TYPE_HOME) {
            ItemVideoBinding binding = ItemVideoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new HomeViewHolder(binding);
        } else {
            ItemVideoSearchBinding binding = ItemVideoSearchBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new SearchViewHolder(binding);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (type == Constant.TYPE_HOME) {
            if (holder.getAdapterPosition() < items.size()) {
                ((HomeViewHolder) holder).bind(items.get(holder.getAdapterPosition()));
            }
        } else {
            if (holder.getAdapterPosition() < items.size()) {
                ((SearchViewHolder) holder).bind(items.get(holder.getAdapterPosition()));
            }
        }

    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void getInfoVideo(VideoChannel videoChannel, AppCompatImageView imgThumbnails, AppCompatTextView tvTitle, AppCompatTextView tvDuration, View root) {
        Glide.with(imgThumbnails).load(videoChannel.getItem().getSnippet().getThumbnails().getHigh().getUrl()).into(imgThumbnails);
        tvTitle.setText(videoChannel.getItem().getSnippet().getTitle());
        tvDuration.setText(ConvertTime.convertDuration(Duration.parse(videoChannel.getItem().getContentDetails().getDuration()).getSeconds()));
        root.setOnClickListener(v -> {
            if (videoChannel.getItem() != null) {
                listen.onClickItem(videoChannel.getItem());
            }
        });
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {
        private final ItemVideoBinding binding;

        public HomeViewHolder(@NonNull ItemVideoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @SuppressLint("SetTextI18n")
        public void bind(VideoChannel videoChannel) {
            binding.txtChannel.setText(videoChannel.getItem().getSnippet().getChannelTitle() +
                    " - " + ConvertTime.convertViewCount(binding.getRoot().getContext(), videoChannel.getItem().getStatistics().getViewCount()) +
                    " - " + ConvertTime.convertTime(binding.getRoot().getContext(), videoChannel.getItem().getSnippet().getPublishedAt()));
            Glide.with(binding.imgChannel).load(videoChannel.getChannel().getItems().get(0).getSnippet().getThumbnails().getHigh().getUrl()).into(binding.imgChannel);
            getInfoVideo(videoChannel, binding.imgThumbnails, binding.txtTitle, binding.textDuration, binding.getRoot());
            getInfoVideo(videoChannel, binding.imgThumbnails, binding.txtTitle, binding.textDuration, binding.getRoot());

        }

    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {
        private final ItemVideoSearchBinding binding;

        public SearchViewHolder(@NonNull ItemVideoSearchBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @SuppressLint("SetTextI18n")
        public void bind(VideoChannel videoChannel) {
            getInfoVideo(videoChannel, binding.imgThumbnails, binding.txtTitle, binding.textDuration, binding.getRoot());
            binding.txtChannel.setText(videoChannel.getItem().getSnippet().getChannelTitle());
            binding.txtView.setText(ConvertTime.convertViewCount(binding.getRoot().getContext(), videoChannel.getItem().getStatistics().getViewCount()) +
                    " - " + ConvertTime.convertTime(binding.getRoot().getContext(), videoChannel.getItem().getSnippet().getPublishedAt()));

        }


    }

}
