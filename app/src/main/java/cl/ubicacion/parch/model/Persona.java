package cl.ubicacion.parch.model;

public class Persona {
    private String _id;
    private String rut;
    private String nombre;
    private String apellidoPaterno;
    private String apellitoMaterno;
    private Boolean estado;
    private String pass;

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellitoMaterno() {
        return apellitoMaterno;
    }

    public void setApellitoMaterno(String apellitoMaterno) {
        this.apellitoMaterno = apellitoMaterno;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }


}
