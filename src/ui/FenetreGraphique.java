package ui;

import model.*;
import utils.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FenetreGraphique extends JFrame {
    private Labyrinthe labyrinthe;
    private JPanel grillePanel;
    private JTextArea infoArea;
    private final int TAILLE_CELLULE = 30;
    

/**
 Constructeur de la fenêtre principale.
 Initialise le titre, la taille, la position, le menu et les composants graphiques, puis rend la fenêtre visible.
 */    
    public FenetreGraphique() {
        setTitle("Résolveur de Labyrinthe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        initMenu();
        initComponents();
        
        setVisible(true);
    }
    

/**
 Crée la barre de menu avec deux menus :
 - "Fichier" : Charger labyrinthe, Générer aléatoire, Quitter
 - "Résoudre" : DFS, BFS
 Associe chaque action à sa méthode correspondante via des expressions lambda.
 */
    private void initMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fichierMenu = new JMenu("Fichier");
        
        JMenuItem chargerItem = new JMenuItem("Charger labyrinthe");
        chargerItem.addActionListener(e -> chargerLabyrinthe());
        
        JMenuItem genererItem = new JMenuItem("Générer aléatoire");
        genererItem.addActionListener(e -> genererLabyrinthe());
        
        JMenuItem quitterItem = new JMenuItem("Quitter");
        quitterItem.addActionListener(e -> System.exit(0));
        
        fichierMenu.add(chargerItem);
        fichierMenu.add(genererItem);
        fichierMenu.addSeparator();
        fichierMenu.add(quitterItem);
        
        JMenu resolverMenu = new JMenu("Résoudre");
        
        JMenuItem dfsItem = new JMenuItem("DFS");
        dfsItem.addActionListener(e -> resoudreDFS());
        
        JMenuItem bfsItem = new JMenuItem("BFS");
        bfsItem.addActionListener(e -> resoudreBFS());
        
        resolverMenu.add(dfsItem);
        resolverMenu.add(bfsItem);
        
        menuBar.add(fichierMenu);
        menuBar.add(resolverMenu);
        
        setJMenuBar(menuBar);
    }
    
/**
 Initialise les composants graphiques principaux :
 - grillePanel : panneau personnalisé pour dessiner le labyrinthe case par case
 - JScrollPane pour la grille (scroll si nécessaire)
 - infoArea : zone de texte non éditable affichant les résultats des résolutions
 La disposition est BorderLayout (centre pour la grille, est pour les infos).
 */    
    private void initComponents() {
        setLayout(new BorderLayout());
        
        grillePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (labyrinthe != null) {
                    dessinerGrille(g);
                }
            }
        };
        grillePanel.setPreferredSize(new Dimension(600, 500));
        grillePanel.setBackground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(grillePanel);
        add(scrollPane, BorderLayout.CENTER);
        
        infoArea = new JTextArea(10, 30);
        infoArea.setEditable(false);
        add(new JScrollPane(infoArea), BorderLayout.EAST);
    }
 

    /**
 Dessine le labyrinthe dans le panneau grillePanel.
 Chaque case est représentée par un carré de TAILLE_CELLULE pixels.
 Couleurs :
 - Mur (#) : noir
 - Départ (S) : vert avec lettre S noire
 - Sortie (E) : rouge avec lettre E noire
 - Chemin (+) : bleu
 - Passage (par défaut) : blanc avec contour gris
 */
    private void dessinerGrille(Graphics g) {
        char[][] grille = labyrinthe.getGrille();
        int lignes = labyrinthe.getLignes();
        int colonnes = labyrinthe.getColonnes();
        
        grillePanel.setPreferredSize(new Dimension(colonnes * TAILLE_CELLULE, lignes * TAILLE_CELLULE));
        
        for (int i = 0; i < lignes; i++) {
            for (int j = 0; j < colonnes; j++) {
                int x = j * TAILLE_CELLULE;
                int y = i * TAILLE_CELLULE;
                
                switch (grille[i][j]) {
                    case '#':
                        g.setColor(Color.BLACK);
                        g.fillRect(x, y, TAILLE_CELLULE - 1, TAILLE_CELLULE - 1);
                        break;
                    case 'S':
                        g.setColor(Color.GREEN);
                        g.fillRect(x, y, TAILLE_CELLULE - 1, TAILLE_CELLULE - 1);
                        g.setColor(Color.BLACK);
                        g.drawString("S", x + 10, y + 20);
                        break;
                    case 'E':
                        g.setColor(Color.RED);
                        g.fillRect(x, y, TAILLE_CELLULE - 1, TAILLE_CELLULE - 1);
                        g.setColor(Color.BLACK);
                        g.drawString("E", x + 10, y + 20);
                        break;
                    case '+':
                        g.setColor(Color.BLUE);
                        g.fillRect(x, y, TAILLE_CELLULE - 1, TAILLE_CELLULE - 1);
                        break;
                    default:
                        g.setColor(Color.WHITE);
                        g.fillRect(x, y, TAILLE_CELLULE - 1, TAILLE_CELLULE - 1);
                        g.setColor(Color.GRAY);
                        g.drawRect(x, y, TAILLE_CELLULE - 1, TAILLE_CELLULE - 1);
                }
            }
        }
        grillePanel.revalidate();
        grillePanel.repaint();
    }
    

 /**
 Ouvre un dialogue de sélection de fichier (filtre .txt dans le dossier "labyrinthes").
 Charge le labyrinthe sélectionné via LectureFichier.charger().
 Met à jour l'affichage et affiche un message dans infoArea.
 En cas d'erreur (fichier invalide), affiche une boîte de dialogue d'erreur.
 */   
    private void chargerLabyrinthe() {
        JFileChooser chooser = new JFileChooser("labyrinthes");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichiers texte", "txt");
        chooser.setFileFilter(filter);
        
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                labyrinthe = LectureFichier.charger(chooser.getSelectedFile().getPath());
                infoArea.setText("Labyrinthe chargé : " + chooser.getSelectedFile().getName() + "\n");
                dessinerGrille(grillePanel.getGraphics());
                grillePanel.repaint();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Erreur de chargement : " + e.getMessage());
            }
        }
    }

    
/**
 Demande à l'utilisateur le nombre de lignes et de colonnes via des boîtes de dialogue.
 Génère un labyrinthe aléatoire résoluble grâce à GenerationAleatoire.generer().
 Met à jour l'affichage et affiche les dimensions dans infoArea.
 En cas de saisie invalide, affiche une boîte d'erreur.
 */    
    private void genererLabyrinthe() {
        String lignes = JOptionPane.showInputDialog(this, "Nombre de lignes :", "10");
        String colonnes = JOptionPane.showInputDialog(this, "Nombre de colonnes :", "10");
        
        try {
            int l = Integer.parseInt(lignes);
            int c = Integer.parseInt(colonnes);
            labyrinthe = GenerationAleatoire.generer(l, c);
            infoArea.setText("Labyrinthe généré aléatoirement (" + l + "x" + c + ")\n");
            grillePanel.repaint();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Valeurs invalides");
        }
    }
    

/**
 Résout le labyrinthe courant avec DFS.
 Vérifie d'abord qu'un labyrinthe est chargé ou généré.
 Crée une copie du labyrinthe, exécute SolveurDFS, puis marque le chemin sur la copie.
 Remplace le labyrinthe courant par la copie (pour afficher le chemin).
 Affiche les résultats (longueur, étapes, temps) dans infoArea.
 */    
    private void resoudreDFS() {
        if (labyrinthe == null) {
            JOptionPane.showMessageDialog(this, "Chargez ou générez d'abord un labyrinthe");
            return;
        }
        
        Labyrinthe copie = labyrinthe.copie();
        SolveurDFS dfs = new SolveurDFS(copie);
        
        if (dfs.aTrouve()) {
            copie.marquerChemin(dfs.getChemin());
            labyrinthe = copie;
            infoArea.append("\n=== DFS ===\n");
            infoArea.append("Chemin trouvé ! Longueur : " + dfs.getChemin().size() + "\n");
            infoArea.append(String.format("Étapes : %d, Temps : %.3f ms\n", dfs.getEtapes(), dfs.getTempsExecution() / 1_000_000.0));
            grillePanel.repaint();
        } else {
            infoArea.append("\nDFS : Aucun chemin trouvé\n");
        }
    }
  
    
/**
 Résout le labyrinthe courant avec BFS.
 Même principe que resoudreDFS() mais avec SolveurBFS.
 BFS garantit le chemin le plus court en nombre de cases.
 */    
    private void resoudreBFS() {
        if (labyrinthe == null) {
            JOptionPane.showMessageDialog(this, "Chargez ou générez d'abord un labyrinthe");
            return;
        }
        
        Labyrinthe copie = labyrinthe.copie();
        SolveurBFS bfs = new SolveurBFS(copie);
        
        if (bfs.aTrouve()) {
            copie.marquerChemin(bfs.getChemin());
            labyrinthe = copie;
            infoArea.append("\n=== BFS ===\n");
            infoArea.append("Chemin trouvé ! Longueur : " + bfs.getChemin().size() + "\n");
            infoArea.append(String.format("Étapes : %d, Temps : %.3f ms\n", bfs.getEtapes(), bfs.getTempsExecution() / 1_000_000.0));
            grillePanel.repaint();
        } else {
            infoArea.append("\nBFS : Aucun chemin trouvé\n");
        }
    }
  
    
/**
 Point d'entrée de l'application graphique.
 Utilise SwingUtilities.invokeLater() pour garantir un bon fonctionnement sur toutes les plateformes (thread de l'EDT).
 */    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FenetreGraphique());
    }
}