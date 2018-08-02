package ar.com.corpico.appcorpico.notes.domain.entity;

public class Note {
    private int id;
    private String fecha;
    private String descripcion;
    private String operario;
    private int operario_id;
    private int editable;

    public Note(int id, String fecha, String descripcion, String operario,int operario_id,int editable) {
        this.id = id;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.operario = operario;
        this.operario_id = operario_id;
        this.editable = editable;
    }

    public Note() {
        // empty constructor
    }

    public int getEditable() {
        return editable;
    }

    public void setEditable(int editable) {
        this.editable = editable;
    }

    public int getOperario_id() {
        return operario_id;
    }

    public void setOperario_id(int operario_id) {
        this.operario_id = operario_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getOperario() {
        return operario;
    }

    public void setOperario(String operario) {
        this.operario = operario;
    }
}
