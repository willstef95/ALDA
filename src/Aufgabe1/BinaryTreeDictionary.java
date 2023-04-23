// O. Bittel
// 22.09.2022
package Aufgabe1;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implementation of the Dictionary interface as AVL tree.
 * <p>
 * The entries are ordered using their natural ordering on the keys,
 * or by a Comparator provided at set creation time, depending on which constructor is used.
 * <p>
 * An iterator for this dictionary is implemented by using the parent node reference.
 *
 * @param <K> Key.
 * @param <V> Value.
 */
public class BinaryTreeDictionary<K extends Comparable<? super K>, V> implements Dictionary<K, V> {

    static private class Node<K, V> {
        K key;
        V value;
        int height;
        Node<K, V> left;
        Node<K, V> right;
        Node<K, V> parent;

        Node(K k, V v) {
            key = k;
            value = v;
            height = 0;
            left = null;
            right = null;
            parent = null;
        }
    }

    private Node<K, V> root = null;
    private int size = 0;



    private V oldValue;
    @Override
    public V insert(K key, V value) {
        root = insertR(key,value,root);
        return oldValue;
    }

    private Node<K,V> insertR (K key, V value, Node<K,V> root){
        if (root==null){
            oldValue =null;
            return new Node<K,V>(key,value);
        } else if(key.compareTo(root.key)<0){
            root.left= insertR(key,value,root.left);
        } else if(key.compareTo(root.key)>0){
            root.right=insertR(key,value,root.right);
        } else{
            oldValue = root.value;
            root.value = value;
        }
        return root;
    }
    @Override
    public V search(K key) {
        return searchR(key,root);
    }
    private V searchR(K key, Node<K,V> node){
        if(node == null){
            return null;
        } else if(key.compareTo(node.key)<0){
            return searchR(key,node.left);
        }else if (key.compareTo(node.key)>0){
            return searchR(key,node.right);
        }else{
            return node.value;
        }
    }
    @Override
    public V remove(K key) {
        root = removeR(key,root);
        return null;
    }
    private Node<K,V> removeR(K key, Node<K,V> root){
        if(root==null){
            oldValue =null;
        }else if(key.compareTo(root.key)<0){
            root.left=removeR(key,root.left);
        }else if(key.compareTo(root.key)>0){
            root.right=removeR(key,root.right);
        } else if(root.left==null||root.right==null){
            oldValue =root.value;
            if(root.left==null){
                root = root.right;
            }else{
                root = root.left;
            }

        } else { //zwei kinder

            MinEntry<K,V> min= new MinEntry<K,V>();
            root.right = getRemMinR(root.right,min);
            oldValue =root.value;
            root.key = min.key;
            root.value= min.value;
        }
        return root;
    }

    private Node<K,V> getRemMinR(Node<K,V>p,MinEntry<K,V>min){
        assert p!=null;
        if (p.left == null) {
            min.key = p.key;
            min.value = p.value;
            p = p.right;
        }else{
            p.left = getRemMinR(p.left, min);
        }
        return p;
    }
    private static class MinEntry<K,V>{
        private K key;
        private V value;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return null;
    }

    // ...

    /**
     * Pretty prints the tree
     */
    public void prettyPrint() {
        printR(0, root);
    }

    private void printR(int level, Node<K, V> p) {
        printLevel(level);
        if (p == null) {
            System.out.println("#");
        } else {
            System.out.println(p.key + " " + p.value + "^" + ((p.parent == null) ? "null" : p.parent.key));
            if (p.left != null || p.right != null) {
                printR(level + 1, p.left);
                printR(level + 1, p.right);
            }
        }
    }

    private static void printLevel(int level) {
        if (level == 0) {
            return;
        }
        for (int i = 0; i < level - 1; i++) {
            System.out.print("   ");
        }
        System.out.print("|__");
    }
}
