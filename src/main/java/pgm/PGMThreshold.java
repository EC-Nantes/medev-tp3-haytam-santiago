/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt 
 * to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java 
 * to edit this template
 */
package pgm;

/**
 * Classe utilitaire permettant d'appliquer une opération de seuillage
 * (binarisation) à une image PGM.
 *
 * <p>
 * Le seuillage transforme une image en niveaux de gris en une image binaire 
 * (noir & blanc) en comparant chaque pixel à un seuil donné :
 * </p>
 * 
 * <pre>
 *    pixel < seuil  →  0 (noir)
 *    pixel ≥ seuil  →  255 (blanc)
 * </pre>
 *
 * <p>
 * Cette opération permet de segmenter une image en séparant ses zones sombres
 * et claires. Elle est très utilisée en prétraitement d'image.
 * </p>
 *
 * Exemple d'utilisation :
 * <pre>
 *     PGMImage img = PGMReader.read("coins.pgm");
 *     PGMImage out = PGMThreshold.threshold(img, 120);
 *     PGMWriter.write(out, "coins_seuil_120.pgm");
 * </pre>
 *
 * @author hayta
 */
public class PGMThreshold {

    /**
     * Applique un seuillage binaire sur une image PGM.
     *
     * @param img   L'image d'entrée en niveaux de gris.
     *              Elle doit contenir :
     *              <ul>
     *                  <li>une matrice {@code pixels[y][x]}</li>
     *                  <li>une largeur {@code width}</li>
     *                  <li>une hauteur {@code height}</li>
     *              </ul>
     *
     * @param seuil La valeur de seuil (entre 0 et 255).  
     *              <ul>
     *                 <li>Si {@code pixel < seuil} → 0</li>
     *                 <li>Sinon → 255</li>
     *              </ul>
     *
     * @return Une nouvelle image PGM binaire contenant le résultat du seuillage.
     *         Jamais {@code null}.  
     *         L'image originale n'est pas modifiée.
     */
    public static PGMImage threshold(PGMImage img, int seuil) {
        PGMImage out = new PGMImage(img.width, img.height);

        for (int i = 0; i < img.height; i++) {
            for (int j = 0; j < img.width; j++) {
                out.pixels[i][j] = (img.pixels[i][j] < seuil ? 0 : 255);
            }
        }
        return out;
    }
}
