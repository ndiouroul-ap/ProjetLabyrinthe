package utils;

import model.Labyrinthe;
import java.util.*;

public class GenerationAleatoire {
    public static Labyrinthe generer(int lignes, int colonnes) {
        if (lignes < 3 || colonnes < 3) {
            lignes = 5;
            colonnes = 5;
        }
        
        char[][] grille = new char[lignes][colonnes];
        
        // Remplir de murs
        for (int i = 0; i < lignes; i++) {
            Arrays.fill(grille[i], '#');
        }
        
        // Créer un chemin simple aléatoire
        Random rand = new Random();
        
        // Départ en haut à gauche
        int startX = 1;
        int startY = 1;
        grille[startX][startY] = 'S';
        
        // Sortie en bas à droite
        int endX = lignes - 2;
        int endY = colonnes - 2;
        grille[endX][endY] = 'E';
        
        // Créer un chemin aléatoire entre S et E
        int currentX = startX;
        int currentY = startY;
        
        while (currentX != endX || currentY != endY) {
            if (currentX < endX && (rand.nextBoolean() || currentY == endY)) {
                currentX++;
            } else if (currentX > endX) {
                currentX--;
            } else if (currentY < endY && (rand.nextBoolean() || currentX == endX)) {
                currentY++;
            } else if (currentY > endY) {
                currentY--;
            }
            
            if (grille[currentX][currentY] != 'S' && grille[currentX][currentY] != 'E') {
                grille[currentX][currentY] = '=';
            }
        }
        
        // Remplir quelques passages aléatoires
        for (int i = 0; i < (lignes * colonnes) / 5; i++) {
            int x = rand.nextInt(lignes);
            int y = rand.nextInt(colonnes);
            if (grille[x][y] == '#' && !(x == startX && y == startY) && !(x == endX && y == endY)) {
                grille[x][y] = '=';
            }
        }
        
        return new Labyrinthe(grille);
    }
}