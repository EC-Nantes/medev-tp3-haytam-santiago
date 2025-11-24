/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt 
 * to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java 
 * to edit this template
 */
package pgm;

/**
 * Classe utilitaire permettant de calculer l'histogramme d'une image PGM.
 * 
 * <p>
 * Un histogramme d'une image en niveaux de gris représente, pour chaque valeur 
 * de 0 à 255, le nombre de pixels ayant cette intensité. Cela permet d'analyser 
 * la répartition lumineuse de l'image (contraste, zones sombres/claires, etc.).
 * </p>
 * 
 * <p>
 * Cette classe ne modifie jamais l'image d'entrée. Elle se contente de lire les 
 * valeurs du tableau de pixels et de compter les occurrences.
 * </p>
 * 
 * Exemple d'utilisation :
 * <pre>
 *     PGMImage img = PGMReader.read("baboon.pgm");
 *     int[] histogram = PGMHistogram.computeHistogram(img);
 *     System.out.println("Nombre de pixels de valeur 128 = " + histogram[128]);
 * </pre>
 * 
 * @author hayta
 */
public class PGMHistogram {

    /**
     * Calcule l'histogramme d'une image PGM passée en paramètre.
     * 
     * <p>
     * Cette méthode parcourt l'ensemble des pixels de l'image et incrémente,
     * pour chaque intensité (0–255), le compteur correspondant.
     * </p>
     * 
     * @param img l'image PGM dont on souhaite calculer l'histogramme.
     *            Elle doit contenir :
     *            <ul>
     *              <li>une matrice de pixels {@code pixels[y][x]}</li>
     *              <li>une hauteur {@code height}</li>
     *              <li>une largeur {@code width}</li>
     *            </ul>
     * 
     * @return un tableau de 256 cases où la case {@code hist[v]} contient 
     *         le nombre de pixels dont la valeur est {@code v}.
     *         <br>Jamais {@code null}.
     */
    public static int[] computeHistogram(PGMImage img) {
        int[] hist = new int[256];

        for (int i = 0; i < img.height; i++) {
            for (int j = 0; j < img.width; j++) {
                hist[img.pixels[i][j]]++;
            }
        }
        return hist;
    }
}
