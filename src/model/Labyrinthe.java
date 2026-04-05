/**
 * Représente un labyrinthe sous forme d'une matrice de caractères.
 * Contient les positions de départ (S) et d'arrivée (E).
 * Permet d'afficher le labyrinthe et de marquer un chemin avec '+'.
 */


package model;

import java.util.List;

public class Labyrinthe {
    private char[][] grille;
    private int startX, startY;
    private int endX, endY;
    private int lignes, colonnes;

    public Labyrinthe(int lignes, int colonnes) {
        this.lignes = lignes;
        this.colonnes = colonnes;
        this.grille = new char[lignes][colonnes];
    }

    public Labyrinthe(char[][] grille) {
        this.grille = grille;
        this.lignes = grille.length;
        this.colonnes = grille[0].length;
        trouverStartEnd();
    }

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

    public void marquerChemin(List<int[]> chemin) {
        for (int[] pos : chemin) {
            int x = pos[0], y = pos[1];
            if (grille[x][y] != 'S' && grille[x][y] != 'E') {
                grille[x][y] = '+';
            }
        }
    }

    public void afficherConsole() {
        for (int i = 0; i < lignes; i++) {
            for (int j = 0; j < colonnes; j++) {
                System.out.print(grille[i][j]);
            }
            System.out.println();
        }
    }

    public Labyrinthe copie() {
        char[][] newGrille = new char[lignes][colonnes];
        for (int i = 0; i < lignes; i++) {
            System.arraycopy(grille[i], 0, newGrille[i], 0, colonnes);
        }
        return new Labyrinthe(newGrille);
    }

    // Getters
    public char[][] getGrille() { return grille; }
    public int getStartX() { return startX; }
    public int getStartY() { return startY; }
    public int getEndX() { return endX; }
    public int getEndY() { return endY; }
    public int getLignes() { return lignes; }
    public int getColonnes() { return colonnes; }
    public char getCellule(int x, int y) { return grille[x][y]; }
    public void setCellule(int x, int y, char c) { grille[x][y] = c; }
}