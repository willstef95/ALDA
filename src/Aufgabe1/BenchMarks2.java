package Aufgabe1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

    public class BenchMarks2 {

        public static void main(String[] args) {
            System.out.println("SortedArrayDictionary");
            test(new SortedArrayDictionary<>());
            System.out.println("+++++++++++++++++++++++++++++++++++++");
            System.out.println("HashDictionary");
            test(new HashDictionary<>(3));
            //System.out.println("BinaryTreeDictionary");
            //test(new BinaryTreeDictionary<>());
        }

        private static void test(Dictionary<String, String> dict) {
            System.out.println("Insert, n = 8000 -> " + testInsert(dict, 8000)+"ms");
            System.out.println("Insert, n = 16000 -> " + testInsert(dict, 16000)+"ms");
            System.out.println("Search, n = 8000, hit -> " + testSearch(dict, 8000, true)+"ms");
            System.out.println("Search, n = 16000, hit -> " + testSearch(dict, 16000, true)+"ms");
            System.out.println("Search, n = 8000, miss -> " + testSearch(dict, 8000, false)+"ms");
            System.out.println("Search, n = 16000, miss -> " + testSearch(dict, 16000, false)+"ms");
        }
        private static long testSearch(Dictionary<String, String> dict, int n, boolean hit) {
            dict = insert(dict, n);
            List<String> gerWords = new ArrayList<>();
            for (Dictionary.Entry<String, String> e: dict) {
                gerWords.add(hit ? e.getKey() : e.getValue());
            }
            long startTime = System.currentTimeMillis();
            for (String s: gerWords) {
                dict.search(s);
            }
            return System.currentTimeMillis() - startTime;
        }

        private static long testInsert(Dictionary<String, String> dict, int n) {
            long startTime = System.currentTimeMillis();
            insert(dict, n);
            return System.currentTimeMillis() - startTime;
        }

        private static Dictionary<String,String> insert(Dictionary<String, String> dict, int n) {
            Scanner scanner;
            String [] line;
            try {
                scanner = new Scanner(new File("src/Aufgabe1/dtengl.txt"));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            for(int i = 0; i < n; i++) {
                if(!scanner.hasNextLine()) break;
                line = scanner.nextLine().split(" ");
                dict.insert(line[0], line[1]);
            }
            return dict;
    }
}
