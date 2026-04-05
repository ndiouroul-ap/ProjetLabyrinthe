package model;

import java.util.*;

public class SolveurBFS {
    private int etapes;
    private long tempsExecution;
    private List<int[]> chemin;

    public SolveurBFS(Labyrinthe laby) {
        etapes = 0;
        chemin = null;
        long debut = System.nanoTime();
        resoudre(laby);
        tempsExecution = System.nanoTime() - debut;
    }

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

    public List<int[]> getChemin() { return chemin; }
    public int getEtapes() { return etapes; }
    public long getTempsExecution() { return tempsExecution; }
    public boolean aTrouve() { return chemin != null; }
}