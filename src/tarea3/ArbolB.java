package tarea3;
/**
 *
 * Cristian Alejandro Melgar Ordoñez 
 * 7690 21 8342
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ArbolB {
    private NodoB root;
    private int order;

    public ArbolB(int order) {
        this.root = new NodoB(order, true);
        this.order = order;
    }

    public void insert(int key) {
        if (root.keys.size() == order - 1) {
            NodoB newRoot = new NodoB(order, false);
            newRoot.hijo.add(root);
            splitChild(newRoot, 0, root);
            root = newRoot;
        }
        insertNonFull(root, key);
    }

    private void insertNonFull(NodoB node, int key) {
        int i = node.keys.size() - 1;
        if (node.isLeaf) {
            while (i >= 0 && key < node.keys.get(i)) {
                i--;
            }
            node.keys.add(i + 1, key);
        } else {
            while (i >= 0 && key < node.keys.get(i)) {
                i--;
            }
            i++;
            if (node.hijo.get(i).keys.size() == order - 1) {
                splitChild(node, i, node.hijo.get(i));
                if (key > node.keys.get(i)) {
                    i++;
                }
            }
            insertNonFull(node.hijo.get(i), key);
        }
    }

    private void splitChild(NodoB parent, int index, NodoB child) {
        NodoB newChild = new NodoB(order, child.isLeaf);
        int mid = order / 2;

        for (int i = mid; i < child.keys.size(); i++) {
            newChild.keys.add(child.keys.get(i));
        }
        for (int i = mid; i < child.hijo.size(); i++) {
            newChild.hijo.add(child.hijo.get(i));
        }

        while (child.keys.size() > mid) {
            child.keys.remove(mid);
        }
        while (child.hijo.size() > mid + 1) {
            child.hijo.remove(mid + 1);
        }

        parent.keys.add(index, child.keys.get(mid));
        parent.hijo.add(index + 1, newChild);
        child.keys.remove(mid);
    }

    public void delete(int key) {
        delete(root, key);
    }

    private void delete(NodoB node, int key) {
        int index = 0;
        while (index < node.keys.size() && key > node.keys.get(index)) {
            index++;
        }

        if (index < node.keys.size() && key == node.keys.get(index)) {
            // Caso 1: Clave encontrada en un nodo hoja
            if (node.isLeaf) {
                node.keys.remove(index);
                return;
            }

            // Caso 2: Clave encontrada en un nodo interno
            int predecessorKey = getPredecessorKey(node, index);
            node.keys.set(index, predecessorKey);
            delete(node.hijo.get(index), predecessorKey);
        } else {
            // Caso 3: Clave no está presente en el nodo actual
            if (node.isLeaf) {
                System.out.println("La clave " + key + " no existe en el árbol.");
                return;
            }

            boolean isLastChild = (index == node.keys.size());
            NodoB child = node.hijo.get(index);

            if (child.keys.size() < (order / 2)) {
                // Reemplazar el hijo con un hijo adecuadamente lleno
                if (!isLastChild && node.hijo.get(index + 1).keys.size() >= (order / 2) + 1) {
                    borrowFromNext(node, index);
                } else if (index != 0 && node.hijo.get(index - 1).keys.size() >= (order / 2) + 1) {
                    borrowFromPrev(node, index);
                } else {
                    // Fusionar el hijo con su hermano
                    if (!isLastChild) {
                        merge(node, index);
                    } else {
                        merge(node, index - 1);
                    }
                }
            }
            delete(node.hijo.get(index), key);
        }
    }

    private int getPredecessorKey(NodoB node, int index) {
        NodoB current = node.hijo.get(index);
        while (!current.isLeaf) {
            current = current.hijo.get(current.hijo.size() - 1);
        }
        return current.keys.get(current.keys.size() - 1);
    }

    private void borrowFromNext(NodoB node, int index) {
        NodoB child = node.hijo.get(index);
        NodoB sibling = node.hijo.get(index + 1);

        child.keys.add(node.keys.get(index));
        node.keys.set(index, sibling.keys.get(0));
        sibling.keys.remove(0);

        if (!sibling.isLeaf) {
            child.hijo.add(sibling.hijo.get(0));
            sibling.hijo.remove(0);
        }
    }

    private void borrowFromPrev(NodoB node, int index) {
        NodoB child = node.hijo.get(index);
        NodoB sibling = node.hijo.get(index - 1);

        child.keys.add(0, node.keys.get(index - 1));
        node.keys.set(index - 1, sibling.keys.get(sibling.keys.size() - 1));
        sibling.keys.remove(sibling.keys.size() - 1);

        if (!sibling.isLeaf) {
            child.hijo.add(0, sibling.hijo.get(sibling.hijo.size() - 1));
            sibling.hijo.remove(sibling.hijo.size() - 1);
        }
    }

    private void merge(NodoB node, int index) {
        NodoB child = node.hijo.get(index);
        NodoB sibling = node.hijo.get(index + 1);

        child.keys.add(node.keys.get(index));

        for (int i = 0; i < sibling.keys.size(); i++) {
            child.keys.add(sibling.keys.get(i));
        }

        for (int i = 0; i < sibling.hijo.size(); i++) {
            child.hijo.add(sibling.hijo.get(i));
        }

        node.keys.remove(index);
        node.hijo.remove(index + 1);
    }

    // Método para imprimir el árbol B (para propósitos de prueba)
    public void print() {
        print(root, "");
    }

    private void print(NodoB node, String prefix) {
        if (node != null) {
            System.out.print(prefix);
            for (Integer key : node.keys) {
                System.out.print(key + " ");
            }
            System.out.println();
            if (!node.isLeaf) {
                for (NodoB child : node.hijo) {
                    print(child, prefix + "  ");
                }
            }
        }
    }

    public boolean search(int key) {
        return search(root, key);
    }

    private boolean search(NodoB node, int key) {
        int index = 0;
        while (index < node.keys.size() && key > node.keys.get(index)) {
            index++;
        }

        if (index < node.keys.size() && key == node.keys.get(index)) {
            return true;
        }

        if (node.isLeaf) {
            return false;
        }

        return search(node.hijo.get(index), key);
    }
}