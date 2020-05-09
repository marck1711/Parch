package cl.ubicacion.parch.ui.slideshow;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.Marker;

import java.text.SimpleDateFormat;
import java.util.Date;

import cl.ubicacion.parch.R;
import cl.ubicacion.parch.Servicio.GetSolicitud;
import cl.ubicacion.parch.Servicio.SetSolicitud;
import cl.ubicacion.parch.model.Solicitud;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    MapView mapView;
    LatLng position;
    String markerText = "Chile";
    Solicitud solicitud;
    String dispositivo="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.map, container, false);
        dispositivo = getArguments().getString("dispositivo");
        solicitud = ObtenerSolicitudes();
        position = new LatLng(Double.valueOf(solicitud.getX()), Double.valueOf(solicitud.getY()));
        mapView = (MapView) rootView.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                Marker marker  = googleMap.addMarker(new MarkerOptions().position(position).title(markerText));
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(position, 16);
                googleMap.animateCamera(cameraUpdate);
            }
        });

        Button btnGetUbicacion = (Button) rootView.findViewById(R.id.btnGetUbicacion);
        btnGetUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObtenerUbicacion();
            }
        });
        return rootView;
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    public void ObtenerUbicacion()  {
        GenerarSolicitud();
        Solicitud solicitud = ObtenerSolicitudes();
        if(solicitud == null){
            return;
        }
        position = new LatLng(Double.valueOf(solicitud.getX()), Double.valueOf(solicitud.getY()));
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                Marker marker  = googleMap.addMarker(new MarkerOptions().position(position).title(markerText));
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(position, 16);
                googleMap.animateCamera(cameraUpdate);
            }
        });
    }

    private void GenerarSolicitud() {
        try {
            SetSolicitud solicitud = new SetSolicitud(CrearNuevaSolicitud());
            solicitud.execute();
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
        }
    }

    private Solicitud ObtenerSolicitudes() {
        try {
            GetSolicitud solicitud = new GetSolicitud("FINALIZADO", dispositivo);
            solicitud.execute();
            Thread.sleep(1000);
            return solicitud.solicitudes[solicitud.solicitudes.length-1];
        } catch (InterruptedException ex) {
            return null;
        }
    }

    private Solicitud CrearNuevaSolicitud(){
        Solicitud solicitud = new Solicitud();
        solicitud.setRut(this.solicitud.getRut());
        solicitud.setNumero(this.solicitud.getNumero());
        solicitud.setFecha(ObtenerFecha());
        solicitud.setEstado("ABIERTA");
        solicitud.setX("0");
        solicitud.setY("0");
        return solicitud;
    }

    private String ObtenerFecha(){
        return  new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }
}