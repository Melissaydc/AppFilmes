package com.example.appfilmes.adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.appfilmes.R;
import com.example.appfilmes.models.Filme;
import com.squareup.picasso.Picasso;
import java.util.List;

public class FilmeAdapter extends RecyclerView.Adapter<FilmeAdapter.FilmeViewHolder> {
    private List<Filme> filmes;
    private Context context;

    public FilmeAdapter(List<Filme> filmes, Context context) {
        this.filmes = filmes;
        this.context = context;
    }

    @NonNull
    @Override
    public FilmeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filme, parent, false);
        return new FilmeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmeViewHolder holder, int position) {
        Filme filme = filmes.get(position);
        holder.textTitulo.setText(filme.getTitulo());
        Picasso.get().load(filme.getImagem()).into(holder.imagemFilme);
        holder.textDiretor.setText(filme.getDiretor());
        holder.textAno.setText(String.valueOf(filme.getAno()));
        holder.btnSinopse.setOnClickListener(v ->
                Toast.makeText(context, filme.getSinopse(), Toast.LENGTH_SHORT).show());
    }

    @Override
    public int getItemCount() {
        return filmes.size();
    }

    public static class FilmeViewHolder extends RecyclerView.ViewHolder {
        TextView textTitulo, textDiretor, textAno;
        ImageView imagemFilme;
        Button btnSinopse;

        public FilmeViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitulo = itemView.findViewById(R.id.textTitulo);
            imagemFilme = itemView.findViewById(R.id.imagemFilme);
            textDiretor = itemView.findViewById(R.id.textDiretor);
            textAno = itemView.findViewById(R.id.textAno);
            btnSinopse = itemView.findViewById(R.id.btnSinopse);
        }
    }
}


