package cl.ubicacion.parch.Utilitario;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import cl.ubicacion.parch.R;
import cl.ubicacion.parch.model.Dispositivo;

public class Adaptador extends BaseAdapter {

    private static LayoutInflater inflater = null;
    public Context ctx;
    public Dispositivo[] datos;

    public Adaptador(Context ctx, Dispositivo[] datos){
        this.ctx = ctx;
        this.datos = datos;
        inflater = (LayoutInflater)ctx.getSystemService(ctx.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final View vista = inflater.inflate(R.layout.device, null);

        TextView txtNombre = (TextView) vista.findViewById(R.id.txtNombre);
        TextView txtNumero = (TextView) vista.findViewById(R.id.txtNumero);

        txtNumero.setText(datos[position].getNumero());
        txtNombre.setText(datos[position].getNombreCorto());

        return vista;
    }

    @Override
    public int getCount() {
        return datos.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


}
