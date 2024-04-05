package tarea3;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/**
 *
 * Cristian Alejandro Melgar Ordoñez 
 * 7690 21 8342
 */
class NodoB {
    List<Integer> keys;
    List<NodoB> hijo;
    boolean isLeaf;

    public NodoB(int order, boolean isLeaf) {
        this.keys = new ArrayList<>(order - 1);
        this.hijo = new ArrayList<>(order);
        this.isLeaf = isLeaf;
    }
}