package com.example.memoryscramble_swc;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etRows, etColumns, etTimeout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etRows    = findViewById(R.id.etRows);
        etColumns = findViewById(R.id.etColumns);
        etTimeout = findViewById(R.id.etTimeout);

        Button btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(v -> startGame());
    }

    private void startGame() {
        String rowsStr    = etRows.getText().toString().trim();
        String colsStr    = etColumns.getText().toString().trim();
        String timeoutStr = etTimeout.getText().toString().trim();

        if (rowsStr.isEmpty() || colsStr.isEmpty() || timeoutStr.isEmpty()) {
            toast("Please fill in all fields");
            return;
        }

        int rows    = Integer.parseInt(rowsStr);
        int cols    = Integer.parseInt(colsStr);
        int timeout = Integer.parseInt(timeoutStr);

        if (rows < 1 || cols < 1) {
            toast("Rows and columns must be at least 1");
            return;
        }

        if ((rows * cols) % 2 != 0) {
            toast("Rows × Columns must be an even number!");
            return;
        }

        int pairs = (rows * cols) / 2;
        if (pairs > CardModel.SYMBOLS.length) {
            toast("Board too large. Maximum " + (CardModel.SYMBOLS.length * 2) + " cells.");
            return;
        }

        if (timeout < 5) {
            toast("Timeout must be at least 5 seconds");
            return;
        }

        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("ROWS",    rows);
        intent.putExtra("COLS",    cols);
        intent.putExtra("TIMEOUT", timeout);
        startActivity(intent);
    }

    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}