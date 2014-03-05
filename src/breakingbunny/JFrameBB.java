/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package breakingbunny;

/**
 *
 * @author Ovidio Villarreal, Graciela Garcia
 */

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import java.net.URL;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

public class JFrameBB extends JFrame implements Runnable, KeyListener, MouseListener{
        private static final long serialVersionUID = 1L;
        //declaracion de variables

        private Image dbImage;	// Imagen a proyectar
        private Image ins;      // Imagen de instrucciones
        private Image creditos; // Imagen de creditos
        private Graphics dbg;	// Objeto grafico
        private boolean off; // Checa si se quiere sonido o no
        private boolean pausa;  // Checa si el juego esta pausado
        private boolean instrucciones;  // Checa si se oprimio el boton para ver las instrucciones
        private int vidas;      // Vidas del personaje
        private int direccion; // Direccion del Ponejito
        private Bloque []bloque;    // Objeto Bloque
        private Ponejtio ponejito;  // Objeto Ponejtio
        private Pelotita pelotita;  // Objeto Pelotita
        private SoundClip colPared;    //Objeto AudioClip 
        private SoundClip destrBloque;     //Objeto AudioClip
        private String []arr;   // Objeto de lo leeido del archivo
        //Variables control de tiempo de animacion
        private long tiempoActual;
	private long tiempoInicial;
        
         //Constructor
        public JFrameBB() {
            init();
            start();
        }
        
        public void init() {
            setSize(800, 500);
            
            
            
            setBackground(Color.green);
            addKeyListener(this);
            addMouseListener(this);
        }
        
        /** 
	 * Metodo <I>start</I> sobrescrito de la clase <code>Applet</code>.<P>
        * En este metodo se crea e inicializa el hilo
        * para la animacion este metodo es llamado despues del init o 
        * cuando el usuario visita otra pagina y luego regresa a la pagina
        * en donde esta este <code>Applet</code>
        * 
        */
	public void start () {
		// Declaras un hilo
		Thread th = new Thread (this);
		// Empieza el hilo
		th.start ();
	}
        
        /**
	 * Metodo <I>stop</I> sobrescrito de la clase <code>Applet</code>.<P>
	 * En este metodo se pueden tomar acciones para cuando se termina
	 * de usar el <code>Applet</code>. Usualmente cuando el usuario sale de la pagina
	 * en donde esta este <code>Applet</code>.
	 */
	public void stop() {
       
	}
        
        /**
	 * Metodo <I>destroy</I> sobrescrito de la clase <code>Applet</code>.<P>
	 * En este metodo se toman las acciones necesarias para cuando
	 * el <code>Applet</code> ya no va a ser usado. Usualmente cuando el usuario
	 * cierra el navegador.
	 */
	public void destroy() {
	    
	}
        
        /** 
	 * Metodo <I>run</I> sobrescrito de la clase <code>Thread</code>.<P>
        * En este metodo se ejecuta el hilo, es un ciclo indefinido donde se incrementa
        * la posicion en x o y dependiendo de la direccion, finalmente 
        * se repinta el <code>Applet</code> y luego manda a dormir el hilo.
        * 
        */
	public void run () {
            //Guarda el tiempo actual del sistema
            //tiempoActual = System.currentTimeMillis();
                while (true) {
                    actualiza();
                    checaColision();
                    repaint();    // Se actualiza el <code>Applet</code> repintando el contenido.
                    try	{
                            // El thread se duerme.
                            Thread.sleep (200);
                    }
                    catch (InterruptedException ex)	{
                            System.out.println("Error en " + ex.toString());
                    }
                }
	}
        
        /**
         * Metodo <I>actualiza</I>
         * Este metodo actualiza a los personajes en el applet en sus movimientos
        */
        public void actualiza() {
            

        }
        
        /**
         * Metodo <I>checaColision</I>
         * Este metodo checa la colision entre los personajes,
         * la colision de los malos con la parte inferior del applet y
         * la  colision del bueno con los extremos del applet
        */
        public void checaColision() {

        }
        
        /**
	 * Metodo <I>keyTyped</I> sobrescrito de la interface <code>KeyListener</code>.<P>
	 * En este metodo maneja el evento que se genera al presionar una tecla que no es de accion.
	 * @param e es el <code>evento</code> que se genera en al presionar las teclas.
	 */
        public void keyTyped(KeyEvent e) {
            
        }
        
        /**
	 * Metodo <I>keyPressed</I> sobrescrito de la interface <code>KeyListener</code>.<P>
	 * En este metodo maneja el evento que se genera al presionar cualquier la tecla.
	 * @param e es el <code>evento</code> generado al presionar las teclas.
	 */
        public void keyPressed(KeyEvent e) {

            
            //Presiono la letra P
            if (e.getKeyCode() == KeyEvent.VK_P) {
                pausa = !pausa;
            }
            //Presiono la letra G
            if (e.getKeyCode() == KeyEvent.VK_G) {
                if (!pausa && !instrucciones) {
                    try {
                        grabaArchivo();
                    } catch (IOException ex) {
                        System.out.println("Error en " + ex.toString());
                    }
                }
            }
            //Presiono la letra C
            if (e.getKeyCode() == KeyEvent.VK_C) {
                try {
                    leeArchivo();
                } catch (IOException ex) {
                    System.out.println("Error en " + ex.toString());
                }
            }
            //Presiono la letra I
            if (e.getKeyCode() == KeyEvent.VK_I) {
                instrucciones = !instrucciones;
            }
            //Presiono la letra S
            if (e.getKeyCode() == KeyEvent.VK_S) {
                off = !off;
            }
        }
        
        /**
	 * Metodo <I>keyReleased</I> sobrescrito de la interface <code>KeyListener</code>.<P>
	 * En este metodo maneja el evento que se genera al soltar la tecla presionada.
	 * @param e es el <code>evento</code> que se genera en al soltar las teclas.
	 */
        public void keyReleased(KeyEvent e) {
            //libero flecha izquierda o libero flecha derecha
            if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
                direccion = 0;
            }
        }
        
        /**
	 * Metodo <I>paint</I> sobrescrito de la clase <code>Applet</code>,
	 * heredado de la clase Container.<P>
	 * En este metodo se dibuja la imagen con la posicion actualizada,
	 * ademas que cuando la imagen es cargada te despliega una advertencia.
	 * @param g es el <code>objeto grafico</code> usado para dibujar.
	 */
        
        public void paint(Graphics g) {
               //Inicializa el DoubleBuffer
                if (dbImage == null){
                    dbImage = createImage(this.getSize().width, this.getSize().height);
                    dbg = dbImage.getGraphics();
                }
                //Actualiza la imagen de fondo
                dbg.setColor(getBackground());
                dbg.fillRect(0, 0, this.getSize().width, this.getSize().height);
                //Actualiza el Foreground
                dbg.setColor(getForeground());
                paint1(dbg);
                //Dibuja la imagen actualizada
                g.drawImage(dbImage, 0, 0, this); 
        }
        
        public void paint1 (Graphics g){
            //Se pinta siempre y cuando tengas vidas
            if (vidas > 0) {

            } else {
                //imprime creditos
                g.drawImage(creditos, 0, 0, this);
            }
	}

    
        /**
	 * Metodo mouseClicked sobrescrito de la interface MouseListener.
	 * En este metodo maneja el evento que se genera al hacer click con el mouse
	 * sobre algun componente.
	 * e es el evento generado al hacer click con el mouse.
	 */
        public void mouseClicked(MouseEvent e) {

        }

        /**
	 * Metodo mousePressed sobrescrito de la interface MouseListener.
	 * En este metodo maneja el evento que se genera al presionar un botÃ³n
	 * del mouse sobre algun componente.
	 * e es el evento generado al presionar un botÃ³n del mouse sobre algun componente.
	 */
        public void mousePressed(MouseEvent e) {
            //Se lanza la pelota

        }

        /**
	 * Metodo mouseReleased sobrescrito de la interface MouseListener.
	 * En este metodo maneja el evento que se genera al soltar un botÃ³n
	 * del mouse sobre algun componente.
	 * e es el evento generado al soltar un botÃ³n del mouse sobre algun componente.
	 */
        public void mouseReleased(MouseEvent e) {

        }

        /**
	 * Metodo mouseEntered sobrescrito de la interface MouseListener.
	 * En este metodo maneja el evento que se genera cuando el mouse
	 * entra en algun componente.
	 * e es el evento generado cuando el mouse entra en algun componente.
	 */
        public void mouseEntered(MouseEvent e) {

        }

        /**
	 * Metodo mouseExited sobrescrito de la interface MouseListener.
	 * En este metodo maneja el evento que se genera cuando el mouse
	 * sale de algun componente.
	 * e es el evento generado cuando el mouse sale de algun componente.
	 */
        public void mouseExited(MouseEvent e) {

        }
        /**
        * Metodo que lee a informacion de un archivo y lo agrega a un vector.
        *
        * @throws IOException
        */
        public void leeArchivo() throws IOException {
                                                          
                BufferedReader fileIn;
                try {
                        fileIn = new BufferedReader(new FileReader("gamedata.txt"));
                } catch (FileNotFoundException e){
                        File data = new File("/savedata/datos.txt");
                        PrintWriter fileOut = new PrintWriter(data);
                        //fileOut.println(""+bueno.getPosX()+";"+""+bueno.getPosY()+";"+""+malo.getPosX()+";"+""+malo.getPosY()+";"+""+velocidadX+";"+""+velocidadY+";"+""+clickPelota+";"+""+llegaMaxAltura+";"+""+vidas+";"+""+perdida+";"+""+alturaMax+";"+""+off);
                        fileOut.close();
                        fileIn = new BufferedReader(new FileReader("gamedata.txt"));
                }
                String dato = fileIn.readLine();
                while(dato != null) {  

                }
                fileIn.close();
        }
        /**
        * Metodo que agrega la informacion del vector al archivo.
        *
        * @throws IOException
        */
        public void grabaArchivo() throws IOException {
                                                          
                PrintWriter fileOut = new PrintWriter(new FileWriter("gamedata.txt"));
                //fileOut.println(""+bueno.getPosX()+";"+""+bueno.getPosY()+";"+""+malo.getPosX()+";"+""+malo.getPosY()+";"+""+velocidadX+";"+""+velocidadY+";"+""+clickPelota+";"+""+llegaMaxAltura+";"+""+vidas+";"+""+perdida+";"+""+alturaMax+";"+""+off);
                fileOut.close();
        }
}

