package com.example.appfilmes;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appfilmes.adapters.FilmeAdapter;
import com.example.appfilmes.models.Filme;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private List<Filme> filmes;
    private FilmeAdapter adapter;
    private Handler handler = new Handler();
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = FirebaseFirestore.getInstance();
        filmes = new ArrayList<>();
        adapter = new FilmeAdapter(filmes, this);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);

        fetchFilmesFromFirebase();

        // Adiciona um delay para a mudança de itens
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (currentIndex < filmes.size()) {
                    recyclerView.smoothScrollToPosition(currentIndex++);
                    handler.postDelayed(this, 3000); // 3 segundos de delay
                }
            }
        }, 3000);
    }

    private void fetchFilmesFromFirebase() {
        db.collection("filmes")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot document : queryDocumentSnapshots) {
                        Filme filme = document.toObject(Filme.class);
                        filmes.add(filme);
                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(MainActivity.this, "Erro ao carregar dados", Toast.LENGTH_SHORT).show());
    }
}