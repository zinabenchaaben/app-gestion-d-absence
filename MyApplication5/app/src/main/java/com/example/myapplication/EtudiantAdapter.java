package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Etudiant;
import com.example.myapplication.R;

import java.util.List;

public class EtudiantAdapter extends RecyclerView.Adapter<EtudiantAdapter.EtudiantViewHolder> {

    private List<Etudiant> etudiants;

    public EtudiantAdapter(List<Etudiant> etudiants) {
        this.etudiants = etudiants;
    }

    @NonNull
    @Override
    public EtudiantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.etudiant_item, parent, false);

        return new EtudiantViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EtudiantViewHolder holder, int position) {
        Etudiant etudiant = etudiants.get(position);

        // Bind data to the ViewHolder
        holder.idTextView.setText(etudiant.getId());
        holder.nomTextView.setText(etudiant.getNom());
        holder.prenomTextView.setText(etudiant.getPrenom());
        holder.classeTextView.setText(etudiant.getClasse());

        // Add logic to display presence information if needed
    }

    @Override
    public int getItemCount() {
        return etudiants.size();
    }

    static class EtudiantViewHolder extends RecyclerView.ViewHolder {
        TextView idTextView;
        TextView nomTextView;
        TextView prenomTextView;
        TextView classeTextView;

        EtudiantViewHolder(@NonNull View itemView) {
            super(itemView);
            idTextView = itemView.findViewById(R.id.idTextView);
            nomTextView = itemView.findViewById(R.id.nomTextView);
            prenomTextView = itemView.findViewById(R.id.prenomTextView);
            classeTextView = itemView.findViewById(R.id.classeTextView);
        }
    }
}
