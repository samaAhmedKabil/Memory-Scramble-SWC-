package com.example.memoryscramble_swc;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    public interface OnCardClickListener {
        void onCardClicked(int position);
    }

    private final List<CardModel>      cards;
    private final OnCardClickListener  listener;

    public CardAdapter(List<CardModel> cards, OnCardClickListener listener) {
        this.cards    = cards;
        this.listener = listener;
    }

    @NonNull @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card, parent, false);
        return new CardViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        holder.bind(cards.get(position), position, listener);
    }

    @Override public int getItemCount() { return cards.size(); }

    static class CardViewHolder extends RecyclerView.ViewHolder {
        private final CardView cardView;
        private final TextView tvSymbol;

        CardViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            tvSymbol = itemView.findViewById(R.id.tvSymbol);
        }

        void bind(CardModel card, int position, OnCardClickListener listener) {
            if (card.isMatched()) {
                tvSymbol.setText(card.getSymbol());
                cardView.setCardBackgroundColor(0xFF43A047);
                tvSymbol.setTextColor(0xFFFFFFFF);
                itemView.setOnClickListener(null);
                itemView.setClickable(false);

            } else if (card.isFaceUp()) {
                tvSymbol.setText(card.getSymbol());
                cardView.setCardBackgroundColor(0xFFFFFFFF);
                tvSymbol.setTextColor(0xFF212121);
                itemView.setOnClickListener(null);
                itemView.setClickable(false);

            } else {
                tvSymbol.setText("?");
                cardView.setCardBackgroundColor(0xFF3F51B5);
                tvSymbol.setTextColor(0xFFFFFFFF);
                itemView.setClickable(true);
                itemView.setOnClickListener(v -> listener.onCardClicked(position));
            }
        }
    }
}