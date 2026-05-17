package com.example.memoryscramble_swc;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameActivity extends AppCompatActivity
        implements CardAdapter.OnCardClickListener {

    private TextView    tvTimer, tvPairs;
    private List<CardModel> cards;
    private CardAdapter     adapter;

    private int  nRows, nCols, timeoutSeconds, totalPairs;
    private int  firstSelected  = -1;
    private int  secondSelected = -1;
    private int  matchedPairs   = 0;
    private boolean isChecking  = false;
    private boolean gameOver    = false;

    private CountDownTimer countDownTimer;
    private final Handler  handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        nRows          = getIntent().getIntExtra("ROWS",    4);
        nCols          = getIntent().getIntExtra("COLS",    4);
        timeoutSeconds = getIntent().getIntExtra("TIMEOUT", 60);
        totalPairs     = (nRows * nCols) / 2;

        tvTimer = findViewById(R.id.tvTimer);
        tvPairs = findViewById(R.id.tvPairs);

        setupCards();
        setupRecyclerView();
        startTimer();
    }

    private void setupCards() {
        List<String> pool = new ArrayList<>();
        for (int i = 0; i < totalPairs; i++) {
            pool.add(CardModel.SYMBOLS[i]);
            pool.add(CardModel.SYMBOLS[i]);
        }
        Collections.shuffle(pool);

        cards = new ArrayList<>();
        for (String sym : pool) {
            cards.add(new CardModel(sym));
        }
    }

    private void setupRecyclerView() {
        adapter = new CardAdapter(cards, this);
        RecyclerView rv = findViewById(R.id.rvCards);
        rv.setLayoutManager(new GridLayoutManager(this, nCols));
        rv.setAdapter(adapter);
        updatePairsText();
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeoutSeconds * 1000L, 1000) {

            @Override public void onTick(long ms) {
                long secs = ms / 1000;
                tvTimer.setText(String.format("⏱ %02d:%02d", secs / 60, secs % 60));
                tvTimer.setTextColor(secs <= 10 ? 0xFFE53935 : 0xFFFFFFFF);
            }

            @Override public void onFinish() {
                tvTimer.setText("⏱ 00:00");
                if (!gameOver) showGameOver();
            }
        }.start();
    }

    @Override
    public void onCardClicked(int position) {
        if (gameOver || isChecking) return;

        CardModel card = cards.get(position);
        if (card.isFaceUp() || card.isMatched()) return;

        card.setFaceUp(true);
        adapter.notifyItemChanged(position);

        if (firstSelected == -1) {
            firstSelected = position;
        } else {
            secondSelected = position;
            isChecking = true;
            checkMatch();
        }
    }

    private void checkMatch() {
        CardModel first  = cards.get(firstSelected);
        CardModel second = cards.get(secondSelected);
        final int p1 = firstSelected, p2 = secondSelected;

        if (first.getSymbol().equals(second.getSymbol())) {
            // ✓ Match
            first.setMatched(true);
            second.setMatched(true);
            matchedPairs++;
            adapter.notifyItemChanged(p1);
            adapter.notifyItemChanged(p2);
            resetSelection();
            updatePairsText();
            if (matchedPairs == totalPairs) showWin();

        } else {
            // ✗ No match — flip back after 1 second
            handler.postDelayed(() -> {
                first.setFaceUp(false);
                second.setFaceUp(false);
                adapter.notifyItemChanged(p1);
                adapter.notifyItemChanged(p2);
                resetSelection();
            }, 1000);
        }
    }

    private void resetSelection() {
        firstSelected  = -1;
        secondSelected = -1;
        isChecking     = false;
    }

    private void updatePairsText() {
        tvPairs.setText("Pairs: " + matchedPairs + " / " + totalPairs);
    }

    private void showWin() {
        gameOver = true;
        countDownTimer.cancel();
        new AlertDialog.Builder(this)
                .setTitle("You Win!")
                .setMessage("Great job! All " + totalPairs + " pairs matched.")
                .setPositiveButton("Play again", (d, w) -> recreate())
                .setNegativeButton("Exit",       (d, w) -> finish())
                .setCancelable(false)
                .show();
    }

    private void showGameOver() {
        gameOver = true;
        new AlertDialog.Builder(this)
                .setTitle("Game Over")
                .setMessage("Time's up! You matched " + matchedPairs
                        + " of " + totalPairs + " pairs.")
                .setPositiveButton("Try again", (d, w) -> recreate())
                .setNegativeButton("Exit",      (d, w) -> finish())
                .setCancelable(false)
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        if (countDownTimer != null) countDownTimer.cancel();
    }
}