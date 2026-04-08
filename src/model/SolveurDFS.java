/**
 Résout un labyrinthe avec l'algorithme DFS (Depth First Search) récursif.
 Compte le nombre d'étapes explorées et le temps d'exécution.
 */


package model;

import java.util.*;

public class SolveurDFS {
    private int etapes;
    private long tempsExecution;
    private List<int[]> chemin;


     //Lance la résolution et mesure le temps d'exécution.
    public SolveurDFS(Labyrinthe laby) {
        etapes = 0;
        chemin = null;
        long debut = System.nanoTime();
        resoudre(laby);
        tempsExecution = System.nanoTime() - debut;
    }

    // Lance la recherche DFS et stocke le chemin trouvé.
    private void resoudre(Labyrinthe laby) {
        char[][] grille = laby.getGrille();
        boolean[][] visite = new boolean[laby.getLignes()][laby.getColonnes()];
        List<int[]> cheminCourant = new ArrayList<>();
        
        if (dfs(grille, laby.getStartX(), laby.getStartY(), 
                laby.getEndX(), laby.getEndY(), visite, cheminCourant, laby.getLignes(), laby.getColonnes())) {
            chemin = new ArrayList<>(cheminCourant);
        }
    }

// Parcours récursif DFS avec backtracking. Retourne true si un chemin est trouvé.
    private boolean dfs(char[][] grille, int x, int y, int endX, int endY, 
                        boolean[][] visite, List<int[]> cheminCourant, int lignes, int colonnes) {
        etapes++;
        
        if (x == endX && y == endY) {
            cheminCourant.add(new int[]{x, y});
            return true;
        }
        
        if (x < 0 || x >= lignes || y < 0 || y >= colonnes) return false;
        if (grille[x][y] == '#' || visite[x][y]) return false;
        
        visite[x][y] = true;
        cheminCourant.add(new int[]{x, y});
        
        // Ordre : haut, bas, gauche, droite
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};
        
        for (int i = 0; i < 4; i++) {
            if (dfs(grille, x + dx[i], y + dy[i], endX, endY, visite, cheminCourant, lignes, colonnes)) {
                return true;
            }
        }
        
        cheminCourant.remove(cheminCourant.size() - 1);
        return false;
    }

    public List<int[]> getChemin() { return chemin; }
    public int getEtapes() { return etapes; }
    public long getTempsExecution() { return tempsExecution; }
    public boolean aTrouve() { return chemin != null; }
}