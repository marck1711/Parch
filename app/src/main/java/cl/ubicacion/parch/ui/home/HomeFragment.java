package cl.ubicacion.parch.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;

import cl.ubicacion.parch.R;
import cl.ubicacion.parch.Servicio.Login;
import cl.ubicacion.parch.Utilitario.XML;
import cl.ubicacion.parch.model.Credenciales;
import cl.ubicacion.parch.ui.gallery.GalleryFragment;


public class HomeFragment extends Fragment {
private Button btnIngresar;
    private HomeViewModel homeViewModel;
    private TextView txtNombre;
    private TextView txtPass;

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        btnIngresar = (Button)root.findViewById(R.id.btnIngresar);
        txtNombre = (TextView)root.findViewById(R.id.txtNombre);
        txtPass = (TextView)root.findViewById(R.id.txtPass);

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if(!Ingresar()) {
                        Toast.makeText(getActivity().getApplicationContext(),"Las credenciales ingresadas no son validas", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Bundle bundle = new Bundle();
                        bundle.putString("rut", txtNombre.getText().toString());
                        Navigation.findNavController(view).navigate(R.id.action_nav_home_to_nav_gallery, bundle);
                    }
            }
        });
        Credenciales respuesta = ObtenerCredenciales();
        if(respuesta!=null){
            txtNombre.setText(respuesta.getUsuario());
            txtPass.setText(respuesta.getPass());
        }
        return root;
    }

    private boolean Ingresar(){
        try {
            String nombre = txtNombre.getText().toString();
            String password = txtPass.getText().toString();
            if(nombre.equals("")||password.equals("")|| nombre == null||password == null)
            {
                return false;
            }
            Credenciales credenciales = new Credenciales();
            credenciales.setUsuario(nombre);
            credenciales.setPass(password);
            Login login = new Login(credenciales);
            login.execute();
            Thread.sleep(1000);
            if (login.resultado) {
                return true;
            }
        }
        catch (InterruptedException ex)
        {
            return false;
        }
        return false;
    }

    private Credenciales ObtenerCredenciales(){
        XML xml = new XML();
        Credenciales credenciales =  xml.RecuperarCredenciales();
        if(credenciales == null){
            return null;
        }else {
            return credenciales;
        }
    }


}