package com.zayzou.day00;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.IntStream;

public class Day01 {

    final static HashMap<String, String> digitsMap = new HashMap<>();
    final static String FILE = "com/zayzou/day00/input.txt";

    public static void main(String[] args) {
        digitsMap.put("one", "1");
        digitsMap.put("two", "2");
        digitsMap.put("three", "3");
        digitsMap.put("four", "4");
        digitsMap.put("five", "5");
        digitsMap.put("six", "6");
        digitsMap.put("seven", "7");
        digitsMap.put("eight", "8");
        digitsMap.put("nine", "9");

        //use auto close ressource to read the file
        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            var line = "";
            int sum = 0;
            String first = "";
            String last = "";
            while ((line = br.readLine()) != null) {

                final String l = line;
                for (int i = 0; i < line.length(); i++) {
                    final int index = i;
                    if (Character.isDigit(line.charAt(i))) {
                        first = String.valueOf(line.charAt(i));
                        break;
                    }
                    Optional<String> firstOptional = digitsMap.keySet().stream()
                            .filter(key -> key.equals(l.substring(index, index + key.length())))
                            .findFirst();
                    if (firstOptional.isPresent()) {
                        first = digitsMap.get(firstOptional.get());
                        break;
                    }

                }

                for (int i = line.length() - 1; i >= 0; i--) {
                    final int index = i;
                    if (Character.isDigit(line.charAt(i))) {
                        last = String.valueOf(line.charAt(i));
                        break;
                    }
                    Optional<String> lastOptional = digitsMap.keySet().stream()
                            .filter(ee -> l.lastIndexOf(ee) == ((l.length()) - ee.length()))
                            .findFirst();
                    if (lastOptional.isPresent()) {
                        last = digitsMap.get(lastOptional.get());
                        break;
                    }
                }

                sum+=Integer.valueOf(first + "" + last);

            }
            System.out.println(sum);
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }


    }

    private static int sumOfAllValues(String line) {

        //look for the first digit
        OptionalInt first = line.chars()
                .filter(Character::isDigit)
                .map(Character::getNumericValue)
                .findFirst();

        //look for the last digit
        Optional<Integer> last = IntStream.range(0, line.length())
                .boxed()
                .sorted(Collections.reverseOrder())
                .filter(i -> Character.isDigit(line.charAt(i)))
                .map(i -> Character.getNumericValue(line.charAt(i)))
                .findFirst();

        //create the number from the 2 digits
        Integer number = Integer.valueOf(first.getAsInt() + "" + last.get());
        return number;
    }


    public static void part2(String line) {
        //look for the last digit
//        Map.Entry<String, String> entry =
//                getFirstDigit(line);
        //look for the last digit
   /*     Map.Entry<String, Integer> last = digitsMap.entrySet().stream()
                .filter(ee -> line.lastIndexOf(ee.getKey()) == ((line.length()) - ee.getKey().length()))
                .findFirst().get();*/
//        System.out.println(entry.getValue() + "" + last.getValue());

    }

/*
    private static Integer getFirstDigit(String line) {
        return digitsMap.entrySet().stream()

                .filter(ee -> line.indexOf(ee.getKey()) == 0)
                .findFirst().get().getValue();
    }
*/

/*    private static Integer getLastDigit(String line) {
        return digitsMap.entrySet().stream()
                .filter(ee -> line.lastIndexOf(ee.getKey()) == ((line.length()) - ee.getKey().length()))
                .findFirst().get().getValue();
    }*/


}
