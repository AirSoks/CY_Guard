package engine.utilitaire;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/*
 * Cette classe charge les images nécessaires à la simulation.
*/
public class SimulationUtility {

	
    /**
     * Cette classe retourne le fichier image appartir du chemin fourni dans le parametre
     * 
     * @param filePath Le chemin du fichier image
     * @return Image le fichier image trouvé sur le chemin fourni
     */
    public static Image readImage(String filePath) {
        try {
            return ImageIO.read(new File(filePath));
        } catch (IOException e) {
            System.err.println("-- Can not read the image file ! --");
            return null;
        }
    }
}