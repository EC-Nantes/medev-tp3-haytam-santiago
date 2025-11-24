package pgm;

/**
 * Classe utilitaire pour calculer la différence entre deux images PGM.
 * Cette opération est utile pour détecter les changements ou le mouvement entre deux images.
 */
public class PGMDifference {

    /**
     * Calcule une nouvelle image représentant la différence absolue entre deux images.
     * <p>
     * La formule appliquée pour chaque pixel est : |pixelA - pixelB|.
     * </p>
     *
     * @param a La première image PGM.
     * @param b La seconde image PGM (doit avoir les mêmes dimensions que 'a').
     * @return Une nouvelle instance de PGMImage contenant la différence.
     * @throws IllegalArgumentException Si les dimensions (largeur ou hauteur) des deux images ne correspondent pas.
     */
    public static PGMImage diff(PGMImage a, PGMImage b) {
        // 1. Vérification de la compatibilité des dimensions
        if (a.width != b.width || a.height != b.height) {
            throw new IllegalArgumentException("Erreur : Les dimensions des images sont différentes (" 
                + a.width + "x" + a.height + " vs " + b.width + "x" + b.height + ")");
        }

        // 2. Création de l'image de sortie avec les mêmes dimensions
        PGMImage out = new PGMImage(a.width, a.height);

        // 3. Parcours de tous les pixels pour calculer la différence
        for (int i = 0; i < a.height; i++) {
            for (int j = 0; j < a.width; j++) {
                // Calcul de la valeur absolue de la différence pour éviter les nombres négatifs
                out.pixels[i][j] = Math.abs(a.pixels[i][j] - b.pixels[i][j]);
            }
        }
        
        return out;
    }
}