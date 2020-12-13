package com.example.roomfloydentrega;

import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.roomfloydentrega.databinding.FragmentInsertarEventoBinding;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static androidx.core.content.ContextCompat.checkSelfPermission;

public class InsertarEventoFragment extends Fragment {

    private FragmentInsertarEventoBinding binding;

    Uri imagenSeleccionada;
    private EventosViewModel eventosViewModel;
    private NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentInsertarEventoBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        eventosViewModel = new ViewModelProvider(requireActivity()).get(EventosViewModel.class);

        binding.imagen.setOnClickListener(v -> {
            abrirGaleria();
        });

        binding.insertar.setOnClickListener(v -> {
            String evento = binding.evento.getText().toString();
            String fecha = binding.fecha.getText().toString();

            eventosViewModel.insertar(evento, fecha, imagenSeleccionada);

            navController.popBackStack();
        });
    }

    /* https://developer.android.com/training/permissions/requesting */

    private void abrirGaleria(){
        if (checkSelfPermission(requireContext(), READ_EXTERNAL_STORAGE) == PERMISSION_GRANTED) {
            lanzadorGaleria.launch("image/*");
        } else {
            lanzadorPermisos.launch(READ_EXTERNAL_STORAGE);
        }
    }

    private final ActivityResultLauncher<String> lanzadorGaleria =
            registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
                //eventosViewModel.establecerImagenSeleccionada(uri);
                imagenSeleccionada = uri;
                Glide.with(requireView()).load(uri).into(binding.imagen);
            });

    private final ActivityResultLauncher<String> lanzadorPermisos =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    lanzadorGaleria.launch("image/*");
                }
            });
}