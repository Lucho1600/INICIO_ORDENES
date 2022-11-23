package mx.com.encargalo.Model;

public class mio_mdlOrdenesRepartidor {

    private int idRepartidor;
    private String repNombreCompleto;

    public mio_mdlOrdenesRepartidor(){

    };
    public mio_mdlOrdenesRepartidor(int idRepartidor, String repNombreCompleto) {
        this.idRepartidor = idRepartidor;
        this.repNombreCompleto = repNombreCompleto;
    }

    public int getIdRepartidor() {
        return idRepartidor;
    }

    public void setIdRepartidor(int idRepartidor) {
        this.idRepartidor = idRepartidor;
    }

    public String getRepNombreCompleto() {
        return repNombreCompleto;
    }

    public void setRepNombreCompleto(String repNombreCompleto) {
        this.repNombreCompleto = repNombreCompleto;
    }
}

