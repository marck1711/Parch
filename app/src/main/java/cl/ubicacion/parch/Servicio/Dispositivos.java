package cl.ubicacion.parch.Servicio;

import android.os.AsyncTask;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;
import cl.ubicacion.parch.model.Dispositivo;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;

public class Dispositivos extends AsyncTask<String,Integer,Boolean> {
    private String url = "http://192.168.0.206:3000/api/device/";
    public Dispositivo[] dispositivos;
    private String rut;

    public Dispositivos(String rut){
        this.rut = rut;
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        boolean resul = true;
        try
        {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost post = new HttpPost(url);
            post.setHeader("content-type", "application/json");
            JSONObject dato = new JSONObject();
            dato.put("rut", this.rut);
            StringEntity entity = new StringEntity(dato.toString());
            post.setEntity(entity);
            HttpResponse resp = httpClient.execute(post);
            String respStr = EntityUtils.toString(resp.getEntity(), "UTF-8");
            //JSONArray jsonArr = new JSONArray(respStr);
            Gson gson = new Gson();
            this.dispositivos = gson.fromJson(respStr,Dispositivo[].class);
            resul = true;
        }
        catch(Exception ex)
        {
            resul = false;
        }
        return resul;
    }

    protected void onPostExecute(Boolean result) {
    }
}
