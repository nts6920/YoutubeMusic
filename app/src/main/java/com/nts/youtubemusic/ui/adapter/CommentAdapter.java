package com.nts.youtubemusic.ui.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nts.youtubemusic.data.model.video.Items;
import com.nts.youtubemusic.databinding.ItemCommentBinding;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private List<Items> commentList = new ArrayList<>();

    @SuppressLint("NotifyDataSetChanged")
    public void setCommentList(List<Items> commentList) {
        this.commentList = commentList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCommentBinding binding = ItemCommentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(commentList.get(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return commentList != null ? commentList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemCommentBinding binding;

        public ViewHolder(@NonNull ItemCommentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Items comment) {
            Glide.with(binding.imgCmt).load(comment.getSnippet().getTopLevelComment().getSnippet().getAuthorProfileImageUrl()).into(binding.imgCmt);
            binding.txtCmt.setText(comment.getSnippet().getTopLevelComment().getSnippet().getTextOriginal());
        }
    }
}

