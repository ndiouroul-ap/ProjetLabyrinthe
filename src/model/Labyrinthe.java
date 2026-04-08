/**
 * Représente un labyrinthe sous forme d'une matrice de caractères.
 * Contient les positions de départ (S) et d'arrivée (E).
 * Permet d'afficher le labyrinthe et de marquer un chemin avec '+'.
 */


package model;

import java.util.List;

/**
 Grille représentant le labyrinthe.
 Chaque case contient :
 - '#' pour un mur
 - '=' pour un passage
 - 'S' pour le point de départ
 - 'E' pour le point de sortie
 - '+' pour le chemin solution (après résolution)
 -  Coordonnées startX (ligne) et startY (colonne) du point de départ S
    Coordonnées endX (ligne) et endY (colonne) du point de sortie E
    Nombre de lignes de la grille (hauteur) 
    Nombre de colonness de la grille (largeur) 
 */

public class Labyrinthe {
    private char[][] grille;
    private int startX, startY;
    private int endX, endY;
    private int lignes, colonnes;

// Crée un labyrinthe vide de dimensions lignes x colonnes
    public Labyrinthe(int lignes, int colonnes) {
        this.lignes = lignes;
        this.colonnes = colonnes;
        this.grille = new char[lignes][colonnes];
    }

// Construit un labyrinthe à partir d'une grille fournie, puis localise S et E.
    public Labyrinthe(char[][] grille) {
        this.grille = grille;
        this.lignes = grille.length;
        this.colonnes = grille[0].length;
        trouverStartEnd();
    }

// Parcourt la grille pour trouver les coordonnées de S et E.
    private void trouverStartEnd() {
        for (int i = 0; i < lignes; i++) {
            for (int j = 0; j < colonnes; j++) {
                if (grille[i][j] == 'S') {
                    startX = i;
                    startY = j;
                } else if (grille[i][j] == 'E') {
                    endX = i;
                    endY = j;
                }
            }
        }
    }

    // Remplace les cases du chemin par des '+' (sauf S et E).
    public void marquerChemin(List<int[]> chemin) {
        for (int[] pos : chemin) {
            int x = pos[0], y = pos[1];
            if (grille[x][y] != 'S' && grille[x][y] != 'E') {
                grille[x][y] = '+';
            }
        }
    }

// Affiche la grille du labyrinthe dans la console.
    public void afficherConsole() {
        for (int i = 0; i < lignes; i++) {
            for (int j = 0; j < colonnes; j++) {
                System.out.print(grille[i][j]);
            }
            System.out.println();
        }
    }

// Retourne une copie indépendante du labyrinthe.
    public Labyrinthe copie() {
        char[][] newGrille = new char[lignes][colonnes];
        for (int i = 0; i < lignes; i++) {
            System.arraycopy(grille[i], 0, newGrille[i], 0, colonnes);
        }
        return new Labyrinthe(newGrille);
    }

    // Getters
    public char[][] getGrille() { return grille; }   // Retourne la grille complète
    public int getStartX() { return startX; }        // Ligne du départ
    public int getStartY() { return startY; }        // Colonne du départ
    public int getEndX() { return endX; }            // Ligne de la sortie
    public int getEndY() { return endY; }            // Colonne de la sortie
    public int getLignes() { return lignes; }        // Hauteur
    public int getColonnes() { return colonnes; }    // Largeur
    public char getCellule(int x, int y) { return grille[x][y]; }         // Caractère à (x,y)
    public void setCellule(int x, int y, char c) { grille[x][y] = c; }    // Modifie une case
}