package pgm;

import java.io.*;
import java.util.*;

/**
 * Classe utilitaire dédiée à la lecture de fichiers images au format PGM (P2).
 * Elle gère l'ouverture du fichier, le parsing de l'en-tête et le chargement 
 * des pixels dans un objet PGMImage.
 */
public class PGMReader {

    /**
     * Lit un fichier PGM donné et retourne une instance de PGMImage.
     *
     * @param filepath Le chemin d'accès au fichier .pgm à lire.
     * @return Un objet PGMImage contenant les dimensions et la matrice de pixels.
     * @throws Exception Si le format est incorrect, si le fichier est introuvable 
     * ou si la valeur max n'est pas 255.
     */
    public static PGMImage read(String filepath) throws Exception {
        // Initialisation du lecteur de fichier (BufferedReader pour une lecture ligne par ligne)
        BufferedReader br = new BufferedReader(new FileReader(filepath));

        // Lecture de la première ligne : le "Magic Number" doit être P2
        String line = br.readLine();
        if (!line.equals("P2")) {
            throw new Exception("Format incorrect : pas P2");
        }

        // Lecture de l'en-tête en ignorant les lignes de commentaires
        // Les commentaires PGM commencent par '#'
        do {
            line = br.readLine();
        } while (line.startsWith("#"));

        // À ce stade, la variable 'line' contient la ligne des dimensions
        // On sépare la ligne par les espaces pour obtenir largeur et hauteur
        String[] dims = line.split("\\s+");
        int width = Integer.parseInt(dims[0]);
        int height = Integer.parseInt(dims[1]);

        // Lecture de la valeur maximale de gris (la ligne suivante)
        // Le TP impose une valeur max de 255
        int max = Integer.parseInt(br.readLine());
        if (max != 255) throw new Exception("Max value != 255");

        // Création de l'objet image vide avec les dimensions lues
        PGMImage img = new PGMImage(width, height);

        // Lecture des pixels (le reste du fichier)
        int row = 0, col = 0;
        
        // Boucle de lecture jusqu'à la fin du fichier
        while ((line = br.readLine()) != null) {
            // Si la ligne est vide, on passe à la suivante
            if (line.trim().isEmpty()) continue;

            // Découpage de la ligne en "tokens" (nombres) séparés par des espaces
            String[] parts = line.trim().split("\\s+");
            
            for (String p : parts) {
                // Conversion de la chaîne en entier et affectation à la matrice
                img.pixels[row][col] = Integer.parseInt(p);
                col++;
                
                // Gestion du retour à la ligne dans la matrice (fin de colonne)
                if (col == width) {
                    col = 0;
                    row++;
                }
                
                // Sécurité : arrêt si on a rempli toute la hauteur prévue
                if (row == height) break;
            }
        }
        
        // Fermeture du fichier pour libérer les ressources
        br.close();
        return img;
    }
}