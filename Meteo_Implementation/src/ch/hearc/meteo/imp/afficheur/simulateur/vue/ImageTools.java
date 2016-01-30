package ch.hearc.meteo.imp.afficheur.simulateur.vue;

import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.ImageIcon;


/**
 * Pour images ne se trouvant pas dans un jar
 */
public class ImageTools
        {

        /*------------------------------------------------------------------*\
        	Methodes Public                                                 *|
        \*------------------------------------------------------------------*/

        public static ImageIcon loadASynchrone(String imagePath)
                {
                Image image = Toolkit.getDefaultToolkit().getImage(imagePath);
                ImageIcon imageIcon = new ImageIcon(image);
                //System.out.println(imageIcon.getIconWidth());
                return imageIcon;
                }

        public static ImageIcon loadSynchrone(String imagePath)
                {
                ImageIcon image = new ImageIcon(imagePath);
                //System.out.println(image.getIconWidth());
                return image;
                }

        public static ImageIcon loadSynchroneJar(String imagePath)
                {
                URL url = ClassLoader.getSystemResource(imagePath);
                ImageIcon image = new ImageIcon(url);
                //System.out.println(image.getIconWidth());
                return image;
                }

        public static ImageIcon loadASynchroneJar(String imagePath)
                {
                URL url = ClassLoader.getSystemResource(imagePath);
                Image image = Toolkit.getDefaultToolkit().getImage(url);
                ImageIcon imageIcon = new ImageIcon(image);
                //System.out.println(imageIcon.getIconWidth());
                return imageIcon;
                }

        /*------------------------------------------------------------------*\
        |*  Methodes Private                                                *|
        \*------------------------------------------------------------------*/


        }