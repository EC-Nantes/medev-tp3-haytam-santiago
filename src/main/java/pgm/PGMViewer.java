package pgm;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Interface graphique principale du projet TP PGM.
 *
 * <p>
 * Cette fenêtre Swing permet de charger, visualiser, traiter et sauvegarder
 * des images au format PGM (P2). Elle s'appuie sur les classes du package 
 * {@code pgm} pour effectuer les différentes opérations de traitement 
 * d'image : lecture, écriture, seuillage, histogramme, agrandissement, 
 * réduction et différence.
 * </p>
 *
 * <p>
 * La fenêtre contient :
 * </p>
 *
 * <ul>
 *   <li>Un menu <b>Fichier</b> avec les actions « Ouvrir » et « Sauvegarder »</li>
 *   <li>Une zone centrale d'affichage de l'image</li>
 *   <li>Une barre de boutons pour appliquer les traitements</li>
 * </ul>
 *
 * <p>
 * Exemple d’utilisation :
 * </p>
 *
 * <pre>
 *     public static void main(String[] args) {
 *         new PGMViewer();
 *     }
 * </pre>
 *
 * @author hayta
 */
public class PGMViewer extends JFrame {

    /** Image actuellement chargée en mémoire. */
    private PGMImage currentImage;

    /** Label Swing permettant d'afficher l'image. */
    private JLabel imageLabel = new JLabel();

    /**
     * Constructeur principal créant et affichant la fenêtre graphique.
     *
     * <p>
     * Il initialise :
     * </p>
     * <ul>
     *   <li>le menu Fichier</li>
     *   <li>la zone d'affichage</li>
     *   <li>les boutons de traitement d'image</li>
     *   <li>les actions associées</li>
     * </ul>
     */
    public PGMViewer() {
        super("TP PGM - Viewer");
        setLayout(new BorderLayout());

        // Zone d'affichage
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(new JScrollPane(imageLabel), BorderLayout.CENTER);

        // ==== MENU FICHIER ====
        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("Fichier");
        JMenuItem openItem = new JMenuItem("Ouvrir");
        JMenuItem saveItem = new JMenuItem("Sauvegarder");
        menuFile.add(openItem);
        menuFile.add(saveItem);
        menuBar.add(menuFile);
        setJMenuBar(menuBar);

        // ==== PANEL DES BOUTONS ====
        JPanel btnPanel = new JPanel();
        JButton thresholdBtn = new JButton("Seuillage");
        JButton histogramBtn = new JButton("Histogramme");
        JButton enlargeBtn = new JButton("Agrandir x2");
        JButton reduceBtn = new JButton("Réduire x2");
        JButton diffBtn = new JButton("Différence");

        btnPanel.add(thresholdBtn);
        btnPanel.add(histogramBtn);
        btnPanel.add(enlargeBtn);
        btnPanel.add(reduceBtn);
        btnPanel.add(diffBtn);
        add(btnPanel, BorderLayout.SOUTH);

        // ==== ACTIONS ====

        openItem.addActionListener(e -> openImage());
        saveItem.addActionListener(e -> saveImage());
        thresholdBtn.addActionListener(e -> {
            if (currentImage == null) return;
            String s = JOptionPane.showInputDialog("Seuil ?");
            try {
                int seuil = Integer.parseInt(s);
                currentImage = PGMThreshold.threshold(currentImage, seuil);
                displayImage();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur seuil !");
            }
        });
        histogramBtn.addActionListener(e -> showHistogram());
        enlargeBtn.addActionListener(e -> {
            if (currentImage == null) return;
            currentImage = PGMResize.enlarge(currentImage, 2);
            displayImage();
        });
        reduceBtn.addActionListener(e -> {
            if (currentImage == null) return;
            currentImage = PGMResize.reduce(currentImage, 2);
            displayImage();
        });
        diffBtn.addActionListener(e -> applyDifference());

        // Fenêtre
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Ouvre une image PGM depuis un fichier choisi par l'utilisateur.
     *
     * <p>
     * Utilise {@link PGMReader} pour lire le fichier et afficher l’image
     * dans le panneau central.
     * </p>
     *
     * En cas d’erreur (format incorrect, fichier introuvable), un message
     * d’alerte est affiché à l’utilisateur.
     */
    private void openImage() {
        JFileChooser fc = new JFileChooser("C:\\Users\\hayta\\Desktop\\ImagesTestPGM");
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                currentImage = PGMReader.read(fc.getSelectedFile().getAbsolutePath());
                displayImage();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur de lecture PGM");
            }
        }
    }

    /**
     * Sauvegarde l'image courante dans un fichier choisi par l'utilisateur.
     *
     * <p>
     * Cette méthode utilise {@link PGMWriter} pour écrire l’image dans un
     * fichier PGM (P2). Si aucune image n’est chargée, l’action est ignorée.
     * </p>
     */
    private void saveImage() {
        if (currentImage == null) return;

        JFileChooser fc = new JFileChooser();
        if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                PGMWriter.write(currentImage, fc.getSelectedFile().getAbsolutePath());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur de sauvegarde !");
            }
        }
    }

    /**
     * Affiche l'histogramme de l’image courante dans une fenêtre popup.
     *
     * <p>
     * L’histogramme est calculé à l’aide de {@link PGMHistogram}. Le résultat
     * est affiché sous forme de texte brut (valeurs 0–255).
     * </p>
     */
    private void showHistogram() {
        if (currentImage == null) return;

        int[] hist = PGMHistogram.computeHistogram(currentImage);
        JTextArea txt = new JTextArea();

        for (int i = 0; i < hist.length; i++) {
            txt.append(i + ": " + hist[i] + "\n");
        }

        JOptionPane.showMessageDialog(this,
                new JScrollPane(txt),
                "Histogramme",
                JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Applique la différence absolue entre l’image courante et une deuxième image
     * sélectionnée par l’utilisateur.
     *
     * <p>
     * Utilise {@link PGMDifference#diff(PGMImage, PGMImage)}.
     * Si les dimensions ne correspondent pas, une erreur est affichée.
     * </p>
     */
    private void applyDifference() {
        if (currentImage == null) return;

        JFileChooser fc = new JFileChooser("C:\\Users\\hayta\\Desktop\\ImagesTestPGM");
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                PGMImage img2 = PGMReader.read(fc.getSelectedFile().getAbsolutePath());
                currentImage = PGMDifference.diff(currentImage, img2);
                displayImage();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur différence !");
            }
        }
    }

    /**
     * Convertit l’image PGM courante en {@link BufferedImage} et l’affiche
     * dans le composant Swing {@link JLabel}.
     *
     * <p>
     * Utilise {@link PGMUtils#toBufferedImage(PGMImage)} pour la conversion.
     * </p>
     */
    private void displayImage() {
        if (currentImage == null) return;

        BufferedImage bi = PGMUtils.toBufferedImage(currentImage);
        imageLabel.setIcon(new ImageIcon(bi));
        imageLabel.revalidate();
    }

    /**
     * Point d'entrée principal de l’application.
     * Lance simplement la fenêtre {@code PGMViewer}.
     *
     * @param args arguments non utilisés
     */
    public static void main(String[] args) {
        new PGMViewer();
    }
}
