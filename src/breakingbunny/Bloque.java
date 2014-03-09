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
public class Bloque extends Base{
    int hits;
    
    /**
    * Metodo constructor que hereda los atributos de la clase <code>Base</code>.
    * @param posX es la <code>posiscion en x</code> del objeto Bloque.
    * @param posY es el <code>posiscion en y</code> del objeto Bloque.
    * @param hits es la <code>cantidad de hits para destruir</code> el objeto Bloque
    * @param image es la <code>imagen</code> del objeto Bueno.
    */
    public Bloque (int posX, int posY, int hits) {
        super(posX, posY);
        this.hits = hits;
        Image bloque = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/images/Carrot1.gif"));
        anim = new Animacion();
        anim.sumaCuadro(bloque, 100);
    }
    
    public void setHits(int hits) {
        this.hits = hits;
    }
    
    public int getHits() {
        return hits;
    }
    
    public void menosHits() {
        hits--;
    }
    
    public boolean destruido() {
        return hits <= 0;
    }
    
    //Detect if the brick has been hit on its bottom, top, left, or right sides
    public boolean hitBottom(int ballX, int ballY) {
        if ((ballX >= posX) && (ballX <= posX + getAncho()) && (ballY <= posY + getAlto())) {
                menosHits();
                return true;
        }
        return false;
    }

    public boolean hitTop(int ballX, int ballY) {
        if ((ballX >= posX) && (ballX <= posX + getAncho()) && (ballY == posY)) {
                menosHits();
                return true;
        }
        return false;
    }

    public boolean hitLeft(int ballX, int ballY) {
        if ((ballY >= posY) && (ballY <= posY + getAlto()) && (ballX == posX)) {
                menosHits();
                return true;
        }
        return false;
    }

    public boolean hitRight(int ballX, int ballY) {
        if ((ballY >= posY) && (ballY <= posY + getAlto()) && (ballX == posX + getAncho())) {
                menosHits();
                return true;
        }
        return false;
    }

    
}
