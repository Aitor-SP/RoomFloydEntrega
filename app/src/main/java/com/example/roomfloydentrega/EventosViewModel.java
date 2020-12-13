package com.example.roomfloydentrega;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class EventosViewModel extends AndroidViewModel {
    private final EventosRepositorio eventosRepositorio;

    public EventosViewModel(@NonNull Application application) {
        super(application);

        eventosRepositorio = new EventosRepositorio(application);
    }

    public void insertar(String evento, String fecha, Uri imagenSeleccionada) {
        eventosRepositorio.insertar(evento, fecha, imagenSeleccionada);
    }

    public LiveData<List<Evento>> obtener() {
        return eventosRepositorio.obtener();
    }
}
