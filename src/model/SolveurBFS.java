package model;

import java.util.*;

/**
 * Résout un labyrinthe à l'aide de l'algorithme BFS (Breadth-First Search).
 * BFS garantit de trouver le chemin le plus court en nombre de cases.
 * Cette classe mesure également le nombre d'étapes explorées et le temps d'exécution.
 */

public class SolveurBFS {
    private int etapes;
    private long tempsExecution;
    private List<int[]> chemin;
/**
     * Constructeur qui lance immédiatement la résolution du labyrinthe donné.
     * Mesure le temps d'exécution et stocke le résultat.
     * 
     * param laby le labyrinthe à résoudre 
     */
    
    public SolveurBFS(Labyrinthe laby) {
        etapes = 0;
        chemin = null;
        long debut = System.nanoTime();
        resoudre(laby);
        tempsExecution = System.nanoTime() - debut;
    }

 /**
     * Exécute l'algorithme BFS à partir du départ jusqu'à la sortie.
     * Utilise une file (Queue) pour explorer les cases niveau par niveau.
     * Un tableau parent permet de reconstruire le chemin une fois la sortie atteinte.
     * 
     * param laby le labyrinthe à résoudre
     */

    private void resoudre(Labyrinthe laby) {
        char[][] grille = laby.getGrille();
        int lignes = laby.getLignes();
        int colonnes = laby.getColonnes();
        boolean[][] visite = new boolean[lignes][colonnes];
        int[][] parentX = new int[lignes][colonnes];
        int[][] parentY = new int[lignes][colonnes];
        
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{laby.getStartX(), laby.getStartY()});
        visite[laby.getStartX()][laby.getStartY()] = true;
        parentX[laby.getStartX()][laby.getStartY()] = -1;
        
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};
        
        boolean trouve = false;
        
        while (!queue.isEmpty() && !trouve) {
            int[] courant = queue.poll();
            int x = courant[0], y = courant[1];
            etapes++;
            
            if (x == laby.getEndX() && y == laby.getEndY()) {
                trouve = true;
                break;
            }
            
            for (int i = 0; i < 4; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];
                
                if (nx >= 0 && nx < lignes && ny >= 0 && ny < colonnes && 
                    !visite[nx][ny] && grille[nx][ny] != '#') {
                    visite[nx][ny] = true;
                    parentX[nx][ny] = x;
                    parentY[nx][ny] = y;
                    queue.add(new int[]{nx, ny});
                }
            }
        }
        
        if (trouve) {
            chemin = new ArrayList<>();
            int x = laby.getEndX();
            int y = laby.getEndY();
            while (x != -1) {
                chemin.add(0, new int[]{x, y});
                int px = parentX[x][y];
                int py = parentY[x][y];
                x = px;
                y = py;
            }
        }
    }



/**
     * Retourne le chemin trouvé par BFS.
     * 
     * return une liste de paires [x, y] représentant le chemin de S à E,
     *         ou null si aucun chemin n'existe.
     */

    public List<int[]> getChemin() { return chemin; }

/**
     * Retourne le nombre total de cases visitées pendant la recherche.
     * 
     * return nombre d'étapes explorées
     */

    public int getEtapes() { return etapes; }

  /**
     * Retourne le temps d'exécution de la résolution en nanosecondes.
     * 
     * return temps en nanosecondes
     */

    public long getTempsExecution() { return tempsExecution; }

 /**
     * Indique si BFS a trouvé un chemin.
     * 
     * return true si un chemin existe, false sinon
     */

    public boolean aTrouve() { return chemin != null; }
}