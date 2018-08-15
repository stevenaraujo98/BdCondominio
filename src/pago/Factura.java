/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pago;

/**
 *
 * 
 */
public enum Factura {
   
    ALICUOTA(1, "alícuota"),
    ELECTRICIDAD(2, "electricidad"),
    AGUA(3, "agua"),
    TELEFONO(4, "teléfono"),
    MULTA(5, "multa");
    
    
    private final int id;
    private final String tipo;
    private Factura(int id, String tipo) {
        this.id = id;
        this.tipo = tipo;
    }
    
    public static Factura getById(int id) {
        if(id > values().length || id < 0)
            return null;
        return values()[id - 1];
    }

    public int getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }
    
}
