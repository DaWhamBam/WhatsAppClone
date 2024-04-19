package com.example.chatapp.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.BR;
import com.example.chatapp.R;
import com.example.chatapp.databinding.RowChatBinding;
import com.example.chatapp.model.ChatMassage;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {

    private List<ChatMassage> chatMassageList;
    private Context context;

    public ChatAdapter(List<ChatMassage> chatMassageList, Context context) {
        this.chatMassageList = chatMassageList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.row_chat, parent, false);

        RowChatBinding binding = DataBindingUtil.bind(view);
        return new MyViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.getBinding().setVariable(BR.chatMessage, chatMassageList.get(position));
        holder.getBinding().executePendingBindings();

    }

    @Override
    public int getItemCount() {
        return chatMassageList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private RowChatBinding binding;

        public MyViewHolder(RowChatBinding binding) {
            super(binding.getRoot());
            setBinding(binding);
        }

        public RowChatBinding getBinding() {
            return binding;
        }

        public void setBinding(RowChatBinding binding) {
            this.binding = binding;
        }
    }


}
