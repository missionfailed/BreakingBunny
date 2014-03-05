/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package breakingbunny;

/**
 *
 * @author Tate && Ovi
 */
import java.awt.Image;
import java.awt.Toolkit;
public class Ponejtio extends Base{
    int velocidad;
    
    public Ponejtio(int posX, int posY, int velocidad) {
        super(posX, posY);
        this.velocidad = velocidad;
    }
    
     /**
     * Metodo modificador usado para cambiar la velocidad del objeto 
     * @param conteo es la <code>velocidad</code> del objeto.
     */
    public void setVelocidad(int velocidad) {
            this.velocidad = velocidad;
    }

    /**
     * Metodo de acceso que regresa la velocidad del objeto 
     * @return conteo es la <code>velocidad</code> del objeto.
     */
    public int getVelocidad() {
            return velocidad;
    }
}
