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
        
        Image conejito1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/images/LadoIzq1.gif"));
        Image conejito2 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/images/LadoDer1.gif"));
        
        anim = new Animacion();
        anim.sumaCuadro(conejito1, 80);
        anim.sumaCuadro(conejito2, 80);
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
    
    /**
     * Metodo que checa si la pelota choco con el ponejito
     * @param ballX la posicion en x de la pelota
     * @param ballY la posicion en y de la pelota
     * @return <code>booleano</code>
     */
    public boolean pegaPonejito(int ballX, int ballY) {
        if ((ballX >= posX) && (ballX <= posX + getAncho()) && ((ballY >= posY) && (ballY <= posY + getAlto()))) {
                return true;
        }
        return false;
    }
}
