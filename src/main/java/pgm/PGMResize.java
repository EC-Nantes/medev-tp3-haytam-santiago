package pgm;

/**
 * Classe utilitaire pour le redimensionnement d'images PGM.
 * Permet de réduire ou agrandir une image par un facteur entier.
 * 
 * @author hayta
 */
public class PGMResize {

    /**
     * Réduit la taille d'une image par un facteur donné.
     * Conserve un pixel sur N (échantillonnage simple).
     * 
     * @param img l'image source à réduire
     * @param factor le facteur de réduction (2 = divise par 2)
     * @return une nouvelle image réduite
     */
    public static PGMImage reduce(PGMImage img, int factor) {
        int newW = img.width / factor;
        int newH = img.height / factor;

        PGMImage out = new PGMImage(newW, newH);

        for (int i = 0; i < newH; i++) {
            for (int j = 0; j < newW; j++) {
                out.pixels[i][j] = img.pixels[i * factor][j * factor];
            }
        }
        return out;
    }

    /**
     * Agrandit une image par un facteur donné.
     * Duplique chaque pixel N fois dans les deux dimensions.
     * 
     * @param img l'image source à agrandir
     * @param factor le facteur d'agrandissement (2 = double la taille)
     * @return une nouvelle image agrandie
     */
    public static PGMImage enlarge(PGMImage img, int factor) {
        int newW = img.width * factor;
        int newH = img.height * factor;

        PGMImage out = new PGMImage(newW, newH);

        for (int i = 0; i < img.height; i++) {
            for (int j = 0; j < img.width; j++) {
                for (int dy = 0; dy < factor; dy++) {
                    for (int dx = 0; dx < factor; dx++) {
                        out.pixels[i * factor + dy][j * factor + dx] = img.pixels[i][j];
                    }
                }
            }
        }
        return out;
    }
}