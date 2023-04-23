package Aufgabe1;

import java.util.Arrays;
import java.util.Iterator;

public class SortedArrayDictionary<K extends Comparable<? super K>, V> implements Dictionary<K,V>{


    private int size;
    private Entry<K,V>[] data;

    @SuppressWarnings("unchecked")
    public SortedArrayDictionary(){
        size=0;
        data= new Entry[10];
    }

    @Override
    public V insert(K key, V value) {
        int i = searchKey(key);
        //Vorhandener Eintrag wird überschrieben
        if (i != -1){
            V va = data[i].getValue();
            data[i].setValue(value);
            return va;
        }

        // Neuer Eintrag
        if(data.length == size){
            data = Arrays.copyOf(data,2*size);
        }
        int j = size -1;
        while(j >= 0 && key.compareTo(data[j].getKey()) < 0){
            data[j+1] = data[j];
            j--;
        }
        data[j+1] = new Entry<K,V>(key,value);
        size++;
        return null;
    }


    private int searchKey(K key){
        int li =0;
        int re = size -1;

        while (re >= li){
            int m = (li+re)/2;
            if (key.compareTo(data[m].getKey())<0){
                re = m - 1;
            } else if(key.compareTo(data[m].getKey())>0){
                li = m +1;
            } else {
                return m;
            }
        }
        return -1;
    }
    @Override
    public V search(K key) {
        int i = searchKey(key);

        if(i>-1){
            return data[i].getValue();
        }
        return null;
    }

    @Override
    public V remove(K key) {
        int i = searchKey(key);

        if (i<0){
            return null;
        }
        V va = data[i].getValue();
        for(int x=i; x != size;x++){
            data[x] = data[x+1];
        }
        size--;
        return va;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new Iterator<>() {
            int index = 0;

            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public Entry<K,V > next() {
                return data[index++];
            }
        };
    }
}
