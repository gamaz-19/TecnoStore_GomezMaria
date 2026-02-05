package Model;

public class Celular {

    private int id, stock;
    private String modelo;

    private enum sistemaOperativo {
        IOS, ANDROID
    }

    private enum gama {
        ALTA, MEDIA, BAJA
    }
    private Marca marca;

}
