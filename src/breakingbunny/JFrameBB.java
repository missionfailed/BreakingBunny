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
        private Image high;     // Imagen de highscores
        private Image menu;    // Imagen del menu
        private Image fondo;     // Imagen de fondo
        private Graphics dbg;	// Objeto grafico
        private boolean off; // Checa si se quiere sonido o no
        private boolean start; // Checa si empezo el juego
        private boolean pausa;  // Checa si el juego esta pausado
        private boolean instrucciones;  // Checa si se oprimio el boton para ver las instrucciones
        private boolean highscores;     // Checa si se oprimio el boton de highscores
        private boolean first;  // Checa si es la primera vez que choca la pelota con el ponejito
        private int vidas;      // Vidas del personaje
        private int score;      // Score del juego
        private int direccion; // Direccion del Ponejito
        private int direccion_ant; //Direccion Anterior
        private Bloque bloque;
        private Bloque[][] bloques;    // Objeto Bloque
        private Ponejtio ponejito;  // Objeto Ponejtio
        private Pelotita pelotita;  // Objeto Pelotita
        private SoundClip col;    //Objeto AudioClip 
        private SoundClip destBloque;     //Objeto AudioClip
        private SoundClip musica;        //Objeto SoundClip
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
            setSize(950, 650);
            direccion = 0;
            instrucciones = false;
            pausa = false;
            first = false;
            start = true;
            vidas = 3;
            score = 0;
            menu = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/images/BreakingBunny_TitleScreen.png"));
            ins = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/images/BreakingBunny_Instructions.png"));
            high = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/images/BreakingBunny_HighScores.png"));
            fondo = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/images/BreakingBunny_Main.png"));
            creditos=Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/images/BreakingBunny_GameOver.png"));
            col = new SoundClip ("/sounds/bounce.wav");
            musica = new SoundClip ("/sounds/8-bit Internets Levels.wav");
            musica.setLooping(true);
            musica.play();
            //crear al ponejito en su posicion inicial
            ponejito = new Ponejtio(0, 0, 25);
            int posX = getWidth()/2 + ponejito.getAncho()/2;
            int posY = getHeight() - ponejito.getAlto()-8;
            ponejito.setPosX(posX);
            ponejito.setPosY(posY);
            //crear a la pelotita en la posicion inicial
            pelotita = new Pelotita(0, 0, 0, 0);
            posX = getWidth()/2 + pelotita.getAncho()/2;
            posY = getHeight()/2 + pelotita.getAlto()/2;
            pelotita.setPosX(posX);
            pelotita.setPosY(posY);
            pelotita.setVelX(15);
            pelotita.setVelY(-15);
            bloque = new Bloque(0, 0, 3);
            bloques = new Bloque [13][3];
            for(int i = 0; i < 13; i++) {
                for(int j = 0; j < 3; j++) {
                    int numLives = 3;
                    bloques[i][j] = new Bloque((i * bloque.getAncho())+20, ((j * bloque.getAlto()) + (bloque.getAlto() / 2)), numLives);
                }
            }
            
            setBackground(Color.blue);
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
            //Determina el tiempo que ha transcurrido desde que el Applet inicio su ejecución
            long tiempoTranscurrido = System.currentTimeMillis() - tiempoActual;
            
            //Guarda el tiempo actual
            tiempoActual += tiempoTranscurrido;
            
            if (!pausa && !start) {
                //dependiendo de la tecla que se este oprimiendo es hacia donde se mueve el personaje Ponejito
                switch (direccion) {
                    case 1:
                        ponejito.setPosX(ponejito.getPosX() + ponejito.getVelocidad()+5);
                        if (direccion_ant != direccion)
                        ponejito.actualiza(tiempoActual);
                        break; 
                    case 2:
                        ponejito.setPosX(ponejito.getPosX() - ponejito.getVelocidad()+5);
                        if (direccion_ant != direccion)
                        ponejito.actualiza(tiempoActual);
                        break;
                    case 0:
                        ponejito.setPosX(ponejito.getPosX());
                        break;
                }
                
                //movimiento de la pelotita
                pelotita.setPosX(pelotita.getPosX()+pelotita.getVelX());
                pelotita.setPosY(pelotita.getPosY()+pelotita.getVelY());
            }

        }
        
        /**
         * Metodo <I>checaColision</I>
         * Este metodo checa la colision entre los personajes,
         * la colision de los malos con la parte inferior del applet y
         * la  colision del bueno con los extremos del applet
        */
        public void checaColision() {
            int x = pelotita.getPosX();
            int y = pelotita.getPosY();
            checaPonejito(x, y);
            checaApplet(x, y);
            checaBloque(x, y);
        }
        
        /**
         * Metodo <I>checaBloque</I>
         * Este metodo checa las colisiones de la pelota con
         * el bloque en los diferentes lados
         * @param x es la <code>posicion en x</code> de la pelotita
         * @param y es la <code>posicion en y</code> de la pelotita
         */
        public void checaBloque(int x, int y) {
            for (int i = 0; i < 13; i++) {
                for (int j = 0; j < 3; j++) {
                    if (!bloques[i][j].destruido()) {
                        //System.out.println("Se supone que pega algo");
                        if (bloques[i][j].hitBottom(x, y)) {
                            pelotita.setVelY(Math.abs(pelotita.getVelY()));
                            System.out.println("PEGA ABAJO");
                            score += 50;
                            col.play();
                        }
                        if (bloques[i][j].hitLeft(x+pelotita.getAncho(), y)) {
                            pelotita.setVelX(-1*pelotita.getVelX());
                            score += 50;
                            col.play();
                        }
                        if (bloques[i][j].hitRight(x, y)) {
                            pelotita.setVelX(-1*pelotita.getVelX());
                            score += 50;
                            col.play();
                        }
                        if (bloques[i][j].hitTop(x, y+pelotita.getAlto())) {
                            pelotita.setVelY(-1*Math.abs(pelotita.getVelY()));
                            score += 50;
                            col.play();
                        }
                    }
                }
            }
        }
        
        /**
         * Metodo <I>checaApplet</I>
         * Metodo para checar las colisiones de la pelota con
         * el Applet
         * @param x es la <code>posicion en x</code> de la pelotita
         * @param y es la <code>posicion en y</code> de la pelotita
         */
        public void checaApplet(int x, int y) {
            if (x >= getWidth() - pelotita.getAncho()) {
                pelotita.setVelX(-1*Math.abs(pelotita.getVelX()));
            }
            if (x <= 0) {
                pelotita.setVelX(Math.abs(pelotita.getVelX()));
            }
            if (y <= 0) {
                pelotita.setVelY(Math.abs(pelotita.getVelY()));
            }
            if (y >= getHeight()) {
                //pelotita.setVelY(-1*Math.abs(pelotita.getVelY()));
                vidas--;
                score -= 100;
                int posX = getWidth()/2 + pelotita.getAncho()/2;
                int posY = getHeight()/2 + pelotita.getAlto()/2;
                pelotita.setPosX(posX);
                pelotita.setPosY(posY);
                pelotita.setVelX(15);
                pelotita.setVelY(-15);
            }
        }
        
        /**
         * Metodo <I>checaPonejito</I>
         * Metodo para checar la colision del ponejito con
         * la pelota y el Applet
         * @param x es la <code>posicion en x</code> de la pelotita
         * @param y es la <code>posicion en y</code> de la pelotita
         */
        public void checaPonejito (int x, int y) {
            //checa colision de la pelotita con el ponejito
            if (ponejito.pegaPonejito(x, y) && pelotita.getVelX() < 0) {
                pelotita.setVelY(-1*pelotita.getVelY());
            }
            if (ponejito.pegaPonejito(x, y) && pelotita.getVelX() > 0) {
                pelotita.setVelY(-1*pelotita.getVelY());
            }
            //checa colision del ponejito con los extremos del applet
            if (ponejito.getPosX()<0) {
                ponejito.setPosX(0);
            }
            if (ponejito.getPosX()+ponejito.getAncho() >= getWidth()) {
                ponejito.setPosX(getWidth()-ponejito.getAncho());
            }
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
            //presiono flecha izquierda
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                direccion_ant = direccion;
                direccion = 2;
            //Presiono flecha derecha
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                direccion_ant = direccion;
                direccion = 1;
            }
            
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (start) {
                    start = false;
                }
            }
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
                if (start && !highscores) {
                    instrucciones = !instrucciones;
                }
            }
            //Presiono la letra H
            if (e.getKeyCode() == KeyEvent.VK_H) {
                if (start && !instrucciones) {
                    highscores = !highscores;
                }
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
                if (start) {
                    g.drawImage(menu, 0, 0, this);
                    if (instrucciones) {
                        g.drawImage(ins, 0, 0, this);
                    }
                    if (highscores) {
                        g.drawImage(high, 0, 0, this);
                    }
                } else if (ponejito!=null && pelotita!=null) {
                    g.drawImage(fondo, 0, 0, this);
                    g.drawImage(ponejito.getImagenI(), ponejito.getPosX(), ponejito.getPosY(), this);
                    g.drawImage(pelotita.getImagenI(), pelotita.getPosX(), pelotita.getPosY(), this);
                    for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 3; j++) {
                            if (!bloques[i][j].destruido()) {
                                g.drawImage(bloques[i][j].getImagenI(), bloques[i][j].getPosX(), bloques[i][j].getPosY(), this);
                            }
                        }
                    }
                }
            } else {
                //imprime creditos
                start = true;
                g.drawImage(creditos, 0, 0, this);
                if (!start) {
                    init();
                }
                
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

