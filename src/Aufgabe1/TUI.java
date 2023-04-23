package Aufgabe1;


import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class TUI{
    private static Dictionary<String,String> dictionary = new SortedArrayDictionary<>();

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        while (true){
            if(!scanner.hasNext()) continue;
            String[] command = scanner.nextLine().split(" ");

            switch(command[0]) {
                case "create" ->{
                    if (command.length == 2) {
                        dictionary = create(command);
                    }
                }
                case "exit" -> {
                    break;
                }
                case "p" -> {
                    System.out.println("print");
                    print(dictionary);
                }
                case "s" -> {
                    if (command.length==2){
                        printEng(command,dictionary);
                    }
                }
                case "i" -> {
                    if (command.length==3) {
                        insert(command, dictionary);
                    }
                }
                case "d" -> {
                    if (command.length == 2)
                        delete(command, dictionary);
                }
                case "r" ->{
                    dictionary = read(command,dictionary);
                }
            }
        }
    }
    private static Dictionary<String, String> read(String[] command, Dictionary<String, String> dictionary)  {
        System.out.println("Read");

        int n = 0;
        if(command.length==3){
           n = Integer.parseInt(command[1]);
        }
        Scanner sc;
        try{
            sc = new Scanner(new File("src/Aufgabe1/"+command[command.length - 1]));
        } catch (Exception e){
            System.out.println("File not found");
            throw new RuntimeException(e);
        }

        if(n<1){
            while(sc.hasNext()){
                if (sc.hasNextLine()) {
                    String[] currentArgs = sc.nextLine().split(" ");
                    dictionary.insert(currentArgs[0], currentArgs[1]);
                    System.out.println("Hallo");
                }
            }
        } else {
            while(n!=0){
                if (sc.hasNextLine()) {
                    String[] currentArgs = sc.nextLine().split(" ");
                    dictionary.insert(currentArgs[0], currentArgs[1]);
                    System.out.println("Hallo");
                }
                n--;
            }
        }
        return dictionary;
    }


    private static void delete(String[] command, Dictionary<String, String> dictionary) {
        System.out.println(dictionary.remove(command[1]));
    }
    private static void insert(String[] command, Dictionary<String, String> dictionary) {
        dictionary.insert(command[1],command[2]);
    }

    private static void printEng(String[] command, Dictionary<String,String> dictionary){
        System.out.println(dictionary.search(command[1]));
    }
    private static void print(Dictionary<String,String> dictionary){
        StringBuilder sb = new StringBuilder();
        sb.append("\n Dictionary \n");

        for(Dictionary.Entry<String,String> d : dictionary){
            sb.append(d.getKey());
            sb.append(" -> ");
            sb.append(d.getValue()).append("\n");
        }

        System.out.println(sb);

    }
    private static Dictionary<String,String>  create (String[] command){
        switch (command[1]){
            case "BinaryTreeDictionary": {
                return new BinaryTreeDictionary<String,String>();
            }
            case "HashDictionary" : {
                return new HashDictionary<String,String>(3);
            }
            case "SortedArrayDictionary" :{
                return new SortedArrayDictionary<String,String>();
            }
            default:  return new SortedArrayDictionary<String,String>();

        }

    }
}