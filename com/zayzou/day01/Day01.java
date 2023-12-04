package com.zayzou.day01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day01 {

    final static HashMap<String, String> digitsMap = new HashMap<>();
    final static String FILE = "com/zayzou/day01/input.txt";

    static {
        digitsMap.put("one", "1");
        digitsMap.put("two", "2");
        digitsMap.put("three", "3");
        digitsMap.put("four", "4");
        digitsMap.put("five", "5");
        digitsMap.put("six", "6");
        digitsMap.put("seven", "7");
        digitsMap.put("eight", "8");
        digitsMap.put("nine", "9");
    }

    public static void main(String[] args) {

        //use auto close ressource to read the file
        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            var line = "";
            int sum = 0;
            String first = "";
            String last = "";
            while ((line = br.readLine()) != null) {
                final String l = line;
                last="";
                for (int i = 0; i < line.length(); i++) {
                    final int index = i;
                    if (Character.isDigit(line.charAt(i))) {
                        first = String.valueOf(line.charAt(i));
                        break;
                    }
                    Optional<String> firstOptional = digitsMap.keySet().stream()
                            .filter(key -> index + key.length() < l.length())//to prevent out of bound
                            .filter(key -> key.equals(l.substring(index, index + key.length())))
                            .findFirst();
                    if (firstOptional.isPresent()) {
                        first = digitsMap.get(firstOptional.get());
                        break;
                    }
                }
                for (int i = line.length() - 1; i >= 0; i--) {
                    if (Character.isDigit(line.charAt(i))) {
                        last = String.valueOf(line.charAt(i));
                        break;
                    }

                    for (Map.Entry<String, String> entry : digitsMap.entrySet()) {
                        //                                two1nine      i=7-
                        if ((i - entry.getKey().length() + 1) >= 0) {
                            String substring = line.substring(i - entry.getKey().length() + 1, i + 1);
                            if (substring.compareTo(entry.getKey()) == 0) {
                                last = entry.getValue();
                                break;
                            }
                        }

                    }
                    if (!last.isBlank()) {
                        break;
                    }

                }


                System.out.println("calibration values : for "+line + " are " + first + "" + last);
                sum += Integer.valueOf(first + "" + last);
            }
            System.out.println(sum);
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}
