/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package breakingbunny;

/**
 *
 * @author Tate
 */
public class Pelotita extends Base{
    int velocidad;
    
    /**
    * Metodo constructor que hereda los atributos de la clase <code>Base</code>.
    * @param posX es la <code>posiscion en x</code> del objeto Pelotita.
    * @param posY es el <code>posiscion en y</code> del objeto Pelotita.
    * @param image es la <code>imagen</code> del objeto Pelotita.
    * @param velocidad es la <code>velocidad</code> del objeto Pelotita
    */
    public Pelotita (int posX, int posY, int velocidad) {
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