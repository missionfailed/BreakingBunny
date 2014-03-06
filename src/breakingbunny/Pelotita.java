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
public class Pelotita extends Base{
    int velX;
    int velY;
    
    /**
    * Metodo constructor que hereda los atributos de la clase <code>Base</code>.
    * @param posX es la <code>posiscion en x</code> del objeto Pelotita.
    * @param posY es el <code>posiscion en y</code> del objeto Pelotita.
    * @param image es la <code>imagen</code> del objeto Pelotita.
    * @param velocidad es la <code>velocidad</code> del objeto Pelotita
    */
    public Pelotita (int posX, int posY, int velX, int velY) {
        super(posX, posY);
        this.velX = velX;
        this.velY = velY;        
        
        
        Image pelota = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/images/fire.gif"));
        anim = new Animacion();
        anim.sumaCuadro(pelota, 80);
    }
    
     /**
     * Metodo modificador usado para cambiar la velocidad del objeto 
     * @param conteo es la <code>velocidad</code> del objeto.
     */
    public void setVelX(int velX) {
            this.velX = velX;
    }

    /**
     * Metodo de acceso que regresa la velocidad del objeto 
     * @return conteo es la <code>velocidad</code> del objeto.
     */
    public int getVelX() {
            return velX;
    }
    
    /**
     * Metodo modificador usado para cambiar la velocidad del objeto 
     * @param conteo es la <code>velocidad</code> del objeto.
     */
    public void setVelY(int velY) {
            this.velY = velY;
    }

    /**
     * Metodo de acceso que regresa la velocidad del objeto 
     * @return conteo es la <code>velocidad</code> del objeto.
     */
    public int getVelY() {
            return velY;
    }
}
