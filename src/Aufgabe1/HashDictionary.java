package Aufgabe1;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.NoSuchElementException;

public class HashDictionary<K extends Comparable<? super K>, V> implements Dictionary<K, V> {


    private LinkedList <Entry<K,V>> [] data;
    int loadFactor=2;


    private int size;

    public HashDictionary(int i){
        if(!istPrimzahl(i)){
            do{
                i++;
            }while(!istPrimzahl(i));
        }
        data =new LinkedList[i];
    }
    public boolean istPrimzahl(int zahl) {
        if (zahl <= 1) {
            return false;
        }
        for (int i = 2; i*i <= zahl; i++) {
            if (zahl % i == 0) {
                return false;
            }
        }
        return true;
    }

    private int hash(K key){
        int m = data.length;
        int adr = key.hashCode();

        if (adr<0)
            adr =-adr;

        return adr%m;
    }

    @Override
    public V insert(K key, V value) {
        if(data[hash(key)] == null){
            data[hash(key)] = new LinkedList<>();
        }
        for (Entry<K,V> entry: data[hash(key)]){
                if (entry.getKey().equals(key)){
                    V va = entry.getValue();
                    entry.setValue(value);
                    return va;
                }
        }
        data[hash(key)].add(new Entry<>(key,value));
        size++;
        if (size > loadFactor * data.length)
            raiseArraySizeAndMoveEntries();
        return null;
    }

    private void raiseArraySizeAndMoveEntries() {
        HashDictionary<K,V> temp = new HashDictionary<K,V>(doubleSize());
        for (Entry<K,V> e : this){
            temp.insert(e.getKey(), e.getValue());
        }
        data = temp.data;
    }

    private int doubleSize(){
        int temp = data.length*2;
        while(!istPrimzahl(temp)){
            temp++;
        }
        return temp;
    }


    @Override
    public V search(K key) {
        if (data[hash(key)] == null){
            return null;
        }
        for(Entry<K,V> e:data[hash(key)]){
            if(e.getKey().equals(key)){
                return e.getValue();
            }
        }
        return null;
    }

    @Override
    public V remove(K key) {
        if (data[hash(key)] == null){
            return null;
        }
        for(Entry<K,V> e:data[hash(key)]){
            if(e.getKey().equals(key)){
                V va = e.getValue();
                data[hash(key)].remove(e); // Entry löschen
                size--;
                return va;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return this.size;
    }



    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new HashDictionaryIterator();
    }

    private class HashDictionaryIterator implements Iterator<Entry<K, V>> {

        private  int i=0;

        private int dataI;

        private int listI;

        @Override
        public boolean hasNext() {
            return i < size;
        }

        @Override
        public Entry<K, V> next() {
            if (!hasNext()) throw new NoSuchElementException();
            while(data[dataI]==null|| listI >= data[dataI].size()){
                dataI++;
                listI =0;
            }
            i++;
            return data[dataI].get(listI++);
        }
    }
}



