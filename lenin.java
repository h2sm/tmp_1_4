import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class textAnalysis {
    public static void main(String[] args) throws IOException {
        new Lenin();//а где старый
        List<String> words = Lenin.getWords();
        LinkedHashSet<String> x = new First().withoutCapital(words);
        //new First().decreasingLength(x);
//       new Second();
//       TreeMap<String,Integer> treeMap = Second.countUniqueWordsButMap(words);
//       TreeMap<String, Integer> trmap = Second.countUniqueWordsButMapLimited(treeMap);
//        Second.countUniqueWordsButMapLimitedLexicographical(trmap);
//        new Three();
//        TreeMap<Integer,Integer> myMap = Three.mapping(words);
//       new Four().symbolsInText(words);
//       new Five().split(words);
        new Sixth().mostUsed(words);

    }
}
class First {
    LinkedHashSet<String> withoutCapital(List<String> words) throws IOException {
        LinkedHashSet<String> strings = new LinkedHashSet<>();
        for (String a : words) {
            String end = a.toLowerCase(Locale.getDefault());
            strings.add(end);
        }
            //System.out.println(strings); выводить в файл
        return strings;
    }

    void decreasingLength(LinkedHashSet<String> strings) {
            //SortedSet<String> sortedSet = new TreeSet<>(String::length);
        SortedSet<String> sortedSet = new TreeSet<>(String::compareToIgnoreCase);//компаратор который соритрует в лексикограф.порядке
        sortedSet.addAll(strings);
            //вывести в файл
        System.out.println(sortedSet);
    }
    void decreasingLengthLimited(SortedSet<String> sortedSet){
        TreeSet<String> sortedSet2 = new TreeSet<>();
        TreeSet<String> sortedSet1 = new TreeSet<String>(Comparator.comparing(as -> as.length()));
        for (String i : sortedSet){
            if (i.length()>=4&&i.length()<=7) sortedSet2.add(i);
        }
    }
}
class Second{
    void countUniqueWords(List<String> words){
        Set<String> wordsSet = new HashSet<String>(words);
        for(String s: wordsSet){
          System.out.println(s + " " + Collections.frequency(wordsSet,s));
        }
    }
    static TreeMap<String,Integer> countUniqueWordsButMap(List<String> words){//2а
        TreeMap<String,Integer> treeMap = new TreeMap<>();//слово - количество использований, treeset!!
        for (String s:words){
            if (!treeMap.containsKey(s)) treeMap.put(s,Collections.frequency(words,s));
            //System.out.println(treeMap);
        }
        return treeMap;
    }
    static TreeMap<String, Integer> countUniqueWordsButMapLimited(TreeMap<String,Integer>treeMap){//от 4 до 7
        TreeMap<String,Integer> treeMap1 = new TreeMap<>();
        for (int i = 0; i <treeMap.size() ; i++) {
            String key = (String) treeMap.keySet().toArray()[i];
            Integer value = treeMap1.get(i);
            if (key.length()>=4 &&key.length()<=7) treeMap1.put(key,value);
        }
        return treeMap1;
    }
    static void countUniqueWordsButMapLimitedLexicographical(TreeMap<String,Integer>treeMap){
        TreeMap<String,Integer> treeMap1 = new TreeMap<>(String::compareToIgnoreCase);
    }
}
class Three {
    static TreeMap<Integer,Integer> mapping(List<String> words) {
        TreeMap<Integer, Integer> treeMap = new TreeMap<>();//длина слова - количество использований
        for (String s : words){//перебираем слова
            int iter = 0;
            if (!treeMap.containsKey(s.length())){//если данной длины не было в ключах
                for (int i =0; i< words.size();i++){//то бежим по массиву
                    if (s.length() == words.get(i).length()){//сравниваем нужные длинны
                        treeMap.put(s.length(),++iter);//записываем
                    }
                }
            }
        }
        System.out.println(treeMap);
        return treeMap;
    }
}
class Four{
    void symbolsInText(List<String> words){
        int COUNT=0;
        for (String s:words){
            for (int i = 0; i < s.length(); i++) {
                //char ch = s.charAt(i);//берем букову
                COUNT++;
            }
        }
        System.out.println(COUNT + " символов");
    }
}
class Five {
    void split(List<String> words) {
        TreeMap<Character, Integer> treeMap = new TreeMap<>();//символ - количество использований
        for (String s : words) {//берем слово
            for (int i = 0; i < s.length(); i++) {
                char ch = s.charAt(i);//берем букову
                if (treeMap.containsKey(ch)){//если была букова
                    int count = treeMap.get(ch);
                    treeMap.put(ch,++count);//добавляем в использование
                }
                else treeMap.put(ch,0);
            }
        }
        System.out.println(treeMap);//ВЫВОДИТСЯ В ЛЕКСИКОГРАФИЧЕСКОМ ПОРЯДКЕ, ЗАДАНИЕ 5А ВЫПОЛНЯЕТСЯ ПАРАЛЛЕЛЬНО
        decreasingValues(treeMap);
    }
    void decreasingValues(TreeMap<Character,Integer> treeMap){
        TreeMap<Integer,Character> sortedTreeMap = new TreeMap<>(Collections.reverseOrder());
        for(Map.Entry<Character, Integer> item : treeMap.entrySet()){
            char key = item.getKey();
            int value = item.getValue();
            sortedTreeMap.put(value,key);
        }
        System.out.println(sortedTreeMap);
    }
}
class Sixth {


    void mostUsed(List<String> words) {
        TreeMap<Integer, TreeMap<Integer, String>> multimap = new TreeMap<>();//отображение "длина - количество использования - слово"
        for (String s : words) {//начальное слово
            int sLength = s.length();//берем длину слова
            s = s.toLowerCase(Locale.getDefault());

            TreeMap<Integer, String> mostCommonWords = new TreeMap<>(Collections.reverseOrder());//"самые популярные слова"
            if (!multimap.containsKey(sLength)){
                for (String word : words){
                    if (word.length() == sLength && mostCommonWords.size()!=15 ){
                        word = word.toLowerCase();
                        mostCommonWords.put(Collections.frequency(words, word), word);
                    }
                }
                multimap.put(sLength,mostCommonWords);
                System.out.println(multimap);
            }
        }
    }
}



class Lenin {

    static List<String> getWords() throws IOException {
        String filename = "D:/Java_laba/lenin_laba/lenin.txt";
        var splitter = Pattern.compile("[\\p{Punct}\\d\\s«…»–]+");
        return Files.lines(Path.of(filename))
                .flatMap(splitter::splitAsStream)
                .filter(w -> ! w.isEmpty())
                .collect(Collectors.toList());
    }

    static void writeResultToFile(String filename, List<String> lines) throws IOException {
        Files.write(Path.of(filename), lines);
    }
}


