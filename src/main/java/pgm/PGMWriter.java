package pgm;

import java.io.*;

/**
 * Classe utilitaire pour l'écriture d'images au format PGM (Portable Gray Map).
 * Cette classe permet de sauvegarder des images en niveaux de gris au format ASCII (P2).
 * 
 * <p>Le format PGM ASCII (P2) structure les données comme suit :
 * <ul>
 *   <li>Ligne 1 : Magic number "P2"</li>
 *   <li>Ligne 2 : Commentaire optionnel commençant par '#'</li>
 *   <li>Ligne 3 : Largeur et hauteur de l'image</li>
 *   <li>Ligne 4 : Valeur maximale des niveaux de gris (255)</li>
 *   <li>Lignes suivantes : Valeurs des pixels (max 70 caractères par ligne)</li>
 * </ul>
 * 
 * @author Votre équipe
 * @version 1.0
 */
public class PGMWriter {

    /**
     * Écrit une image PGM dans un fichier au format ASCII (P2).
     * 
     * <p>Cette méthode sauvegarde l'image en respectant les contraintes du format PGM :
     * <ul>
     *   <li>Format ASCII avec magic number P2</li>
     *   <li>Ligne de commentaire automatique</li>
     *   <li>Valeur maximale fixée à 255</li>
     *   <li>Limitation à 70 caractères par ligne</li>
     * </ul>
     * 
     * @param img l'image PGM à sauvegarder, contenant les pixels, la largeur et la hauteur
     * @param filepath le chemin du fichier de destination (ex: "output.pgm")
     * @throws IOException si une erreur d'écriture survient
     * @throws NullPointerException si img ou filepath est null
     * @throws IllegalArgumentException si l'image contient des dimensions invalides
     * 
     * @see PGMImage
     */
    public static void write(PGMImage img, String filepath) throws Exception {
        BufferedWriter bw = new BufferedWriter(new FileWriter(filepath));

        // Écriture du header P2
        bw.write("P2\n");
        
        // Commentaire optionnel
        bw.write("# Fichier généré par TP Java\n");
        
        // Dimensions de l'image
        bw.write(img.width + " " + img.height + "\n");
        
        // Valeur maximale des niveaux de gris
        bw.write("255\n");

        // Écriture des pixels avec limitation à 70 caractères par ligne
        int count = 0;
        for (int i = 0; i < img.height; i++) {
            for (int j = 0; j < img.width; j++) {
                bw.write(img.pixels[i][j] + " ");
                count++;

                // Limiter à environ 70 caractères par ligne (17 valeurs max)
                if (count >= 17) {
                    bw.write("\n");
                    count = 0;
                }
            }
            bw.write("\n");
        }

        bw.close();
    }
}