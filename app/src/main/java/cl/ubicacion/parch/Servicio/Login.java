package cl.ubicacion.parch.Servicio;
import android.os.AsyncTask;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;

import cl.ubicacion.parch.Utilitario.XML;
import cl.ubicacion.parch.model.Credenciales;
import cl.ubicacion.parch.model.Persona;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;


public class Login extends AsyncTask<String,Integer,Boolean> {

    private String url = "http://192.168.0.206:3000/api/login/";
    private Credenciales credenciales;
    public Persona persona;
    public boolean resultado;

    public Login(Credenciales _credenciales){
        this.credenciales = _credenciales;
    }

    protected Boolean doInBackground(String... params) {
            boolean resul = true;
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost post = new HttpPost(url);
            post.setHeader("content-type", "application/json");
            try
            {
                JSONObject dato = new JSONObject();
                dato.put("rut", credenciales.getUsuario());
                dato.put("pass", credenciales.getPass());
                StringEntity entity = new StringEntity(dato.toString());
                post.setEntity(entity);
                HttpResponse resp = httpClient.execute(post);
                String respStr = EntityUtils.toString(resp.getEntity());
                Gson gson = new Gson();
                this.persona = gson.fromJson(respStr, Persona.class);
                XML xml = new XML();
                xml.CrearOrUpdateFile(this.persona);
                resul = resultado = true;
            }
            catch(Exception ex)
            {
                resul = resultado = false;
            }
            return resul;
        }

        protected void onPostExecute(Boolean result) {
        }
    }



