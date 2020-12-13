package com.example.roomfloydentrega;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.roomfloydentrega.databinding.FragmentListaEventosBinding;
import com.example.roomfloydentrega.databinding.ViewholderEventoBinding;

import java.util.List;


public class ListaEventosFragment extends Fragment {

    private FragmentListaEventosBinding binding;
    private NavController navController;
    private EventosViewModel eventosViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentListaEventosBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        eventosViewModel = new ViewModelProvider(requireActivity()).get(EventosViewModel.class);

        binding.navegarAInsertarEvento.setOnClickListener(v -> {
            navController.navigate(R.id.action_listaEventosFragment_to_insertarEventoFragment);
        });

        EventosAdapter eventosAdapter = new EventosAdapter();
        binding.listaEventos.setAdapter(eventosAdapter);

        eventosViewModel.obtener().observe(getViewLifecycleOwner(), eventoList -> {
            eventosAdapter.establecerEventoList(eventoList);
        });
    }

    class EventosAdapter extends RecyclerView.Adapter<EventoViewHolder> {

        List<Evento> eventoList;

        @NonNull
        @Override
        public EventoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new EventoViewHolder(ViewholderEventoBinding.inflate(getLayoutInflater(), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull EventoViewHolder holder, int position) {
            Evento evento = eventoList.get(position);
            holder.binding.evento.setText(evento.evento);
            holder.binding.fecha.setText(evento.fecha);

            Glide.with(holder.itemView).load(evento.imagen).into(holder.binding.imagen);
        }

        @Override
        public int getItemCount() {
            return eventoList == null ? 0 : eventoList.size();
        }

        void establecerEventoList(List<Evento> eventoList) {
            this.eventoList = eventoList;
            notifyDataSetChanged();
        }
    }

    class EventoViewHolder extends RecyclerView.ViewHolder {
        ViewholderEventoBinding binding;

        public EventoViewHolder(@NonNull ViewholderEventoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}