package cl.ubicacion.parch.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import cl.ubicacion.parch.R;
import cl.ubicacion.parch.Servicio.Dispositivos;
import cl.ubicacion.parch.Utilitario.Adaptador;
import cl.ubicacion.parch.Utilitario.XML;
import cl.ubicacion.parch.model.Credenciales;
import cl.ubicacion.parch.model.Dispositivo;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    public ListView dispositivo;
    public Dispositivo[] datos;
    private Credenciales credenciales;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel = ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        return root;
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        String rut = ObtenerCredenciales();
        if (!rut.equals("")|| rut != null) {
            // rut = getArguments().getString("rut");
            this.datos =  ObtenerDispositivos(rut);
            if(datos!=null) {
                dispositivo = (ListView) getActivity().findViewById(R.id.lista);
                dispositivo.setAdapter(new Adaptador(getActivity(), datos));
            }
        }
        if(datos!=null) {
            dispositivo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Bundle bundle = new Bundle();
                    bundle.putString("dispositivo", datos[position].getNumero().toString());
                    Navigation.findNavController(view).navigate(R.id.action_nav_gallery_to_nav_slideshow, bundle);
                }
            });
        }
    }

    private Dispositivo[] ObtenerDispositivos(String rut)  {
        try {
        Dispositivos dispositivos = new Dispositivos(rut);
        dispositivos.execute();
        Thread.sleep(1000);
        return dispositivos.dispositivos;
        }
        catch (InterruptedException ex)
        {
            return null;
        }
    }

    private String ObtenerCredenciales()
    {
        XML xml = new XML();
       this.credenciales =  xml.RecuperarCredenciales();
       if(credenciales != null) {
           if(credenciales.getEstado() == true) {
               return credenciales.getUsuario();
           }else{return "";}
       }else{
           return "";
       }
    }
}