package pgm;

import java.awt.image.BufferedImage;

public class PGMUtils {

    public static BufferedImage toBufferedImage(PGMImage img) {
        BufferedImage bi = new BufferedImage(img.width, img.height, BufferedImage.TYPE_BYTE_GRAY);

        for (int y = 0; y < img.height; y++) {
            for (int x = 0; x < img.width; x++) {
                int v = img.pixels[y][x];
                int rgb = (v << 16) | (v << 8) | v;
                bi.setRGB(x, y, rgb);
            }
        }
        return bi;
    }
}
