package cl.ubicacion.parch.Utilitario;

import android.os.Environment;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import cl.ubicacion.parch.model.Credenciales;
import cl.ubicacion.parch.model.Persona;

public class XML  {

    public void CrearOrUpdateFile(Persona persona){
        File file ;
        try {
            file = new File(ObtenerRuta());
            if(file.exists())
            {
                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                Document document = documentBuilder.parse(file);

                document.getElementsByTagName("_id").item(0).setTextContent(persona.get_id());
                document.getElementsByTagName("rut").item(0).setTextContent(persona.getRut());
                document.getElementsByTagName("nombre").item(0).setTextContent(persona.getNombre());
                document.getElementsByTagName("apellidoPaterno").item(0).setTextContent(persona.getApellidoPaterno());
                document.getElementsByTagName("apellitoMaterno").item(0).setTextContent(persona.getApellitoMaterno());
                document.getElementsByTagName("estado").item(0).setTextContent(persona.getEstado().toString());
                document.getElementsByTagName("pass").item(0).setTextContent(persona.getPass());

                DOMSource source = new DOMSource(document);
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                file.mkdirs();
                StreamResult outputStream = new StreamResult(new FileOutputStream (new File(file.getAbsolutePath().toString()), false));
                transformer.transform(source, outputStream);
            }else{
                file.createNewFile();
                String xmlRecords = "<?xml version='1.0' encoding='UTF-8' ?>" +
                        "<configuracion>" +
                        "<id>"+persona.get_id()+"</id>" +
                        "<rut>"+persona.getRut()+"</rut>" +
                        "<nombre>"+persona.getNombre()+"</nombre>" +
                        "<apellidoPaterno>"+persona.getApellidoPaterno()+"</apellidoPaterno>" +
                        "<apellidoMaterno>"+persona.getApellitoMaterno()+"</apellidoMaterno>" +
                        "<estado>"+persona.getEstado()+"</estado>" +
                        "<pass>"+persona.getPass()+"</pass>" +
                        "</configuracion>";

                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document d1 = builder.parse(new InputSource(new StringReader(xmlRecords)));

                DOMSource source = new DOMSource(d1);
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                StreamResult  outputStream = new StreamResult(new FileOutputStream (new File(file.getAbsolutePath().toString()), true));
                transformer.transform(source, outputStream);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String ObtenerRuta(){
        File file;
        String ruta = "";
        try {
            File nuevaCarpeta = new File(Environment.getExternalStorageDirectory(), "DatosParch");
            if (!nuevaCarpeta.exists()) {
                nuevaCarpeta.mkdir();
            }
            ruta = nuevaCarpeta +"/usuario.xml";
        } catch (Exception e) {
        }
        return ruta;
    }

    public Credenciales RecuperarCredenciales(){
        File file;
        Credenciales credenciales = new Credenciales();
        try {
            file = new File(ObtenerRuta());
            if(file.exists()) {
                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                Document document = documentBuilder.parse(file);
                credenciales.setUsuario(document.getElementsByTagName("rut").item(0).getTextContent());
                credenciales.setPass(document.getElementsByTagName("pass").item(0).getTextContent());
                credenciales.setEstado(Boolean.parseBoolean(document.getElementsByTagName("estado").item(0).getTextContent()));
            }
            else{return null;}
        } catch (SAXException e) {
            return null;
        } catch (IOException e) {
            return null;
        }catch (ParserConfigurationException e) {
            return null;
        }catch(Exception e){
            return null;
        }
        return credenciales;
    }


}
