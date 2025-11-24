package pgm;

/**
 * Représente une image numérique au format PGM (Portable Gray Map).
 * Cette classe stocke les dimensions de l'image ainsi que la matrice 
 * contenant les valeurs de niveaux de gris pour chaque pixel.
 */
public class PGMImage {
    
    /** Largeur de l'image en pixels. */
    public int width;
    
    /** Hauteur de l'image en pixels. */
    public int height;
    
    /** * Matrice stockant les niveaux de gris des pixels.
     * Les valeurs sont généralement comprises entre 0 (noir) et 255 (blanc).
     * L'accès se fait sous la forme pixels[y][x] (ligne, colonne).
     */
    public int[][] pixels;

    /**
     * Constructeur pour initialiser une nouvelle image vide avec des dimensions données.
     *
     * @param width  La largeur souhaitée de l'image.
     * @param height La hauteur souhaitée de l'image.
     */
    public PGMImage(int width, int height) {
        this.width = width;
        this.height = height;
        // Alloue la mémoire pour le tableau à deux dimensions selon la hauteur et la largeur
        this.pixels = new int[height][width];
    }
}