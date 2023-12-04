package com.zayzou.day02;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Map.entry;

public class Day02 {
    static final String FILE = "com/zayzou/day02/example.txt";
    static Pattern pattern;
    static Matcher matcher;

    //12 cubes red, 13 cubes verts et 14 cubes bleus
    static Map<String, Integer> cubesThreshold = Map.ofEntries(
            entry("red", 0),
            entry("green", 0),
            entry("blue", 0)
    );

    public static void main(String[] args) {
        String currentLine = "";
        int sum = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            while ((currentLine = br.readLine()) != null) {
                int gameId = getGameId(currentLine);
                String sets = getGameSets(currentLine);//3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
                int powerOfSet = thePowerOfTheGame(sets);
                System.out.println("Game id : #" + gameId + " power " + powerOfSet);
                sum += powerOfSet;
            }
            System.out.println(sum);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    public static int thePowerOfTheGame(String sets) {
        String[] subSet = sets.split(";");///3 blue, 4 red
        int minRed;
        int minBlue;
        int minGreen = minBlue = minRed = 0;
        for (String s : subSet) {
            String[] result = s.trim().split(",");//3 blue
            for (String string : result) {
                String[] s1 = string.trim().split(" ");//3
                if ("blue".equals(s1[1])) {
                    minBlue = Math.max(minBlue, Integer.parseInt(s1[0]));
                }
                if ("red".equals(s1[1])) {
                    minRed = Math.max(minRed, Integer.parseInt(s1[0]));
                }
                if ("green".equals(s1[1])) {
                    minGreen = Math.max(minGreen, Integer.parseInt(s1[0]));
                }
            }
        }
//        System.out.println(minRed + " " + minGreen + " " + minBlue);
        return minRed * minGreen * minBlue;
    }


    public static boolean theGameIsOk(String sets) {
        String[] subSet = sets.split(";");///3 blue, 4 red
        boolean setStatus = true;
        for (String s : subSet) {
            String[] result = s.trim().split(",");//3 blue
            for (String string : result) {
                String[] s1 = string.trim().split(" ");//3

                setStatus = switch (s1[1]) {
                    case "blue" -> cubesThreshold.get("blue") >= Integer.parseInt(s1[0]);
                    case "red" -> cubesThreshold.get("red") >= Integer.parseInt(s1[0]);
                    case "green" -> cubesThreshold.get("green") >= Integer.parseInt(s1[0]);
                    default -> throw new IllegalStateException("Unexpected value: " + s1[1]);
                };
                if (!setStatus) {
                    return false;
                }
            }
        }
        return true;
    }

    public static int getGameId(String game) {
        final String regex = " \\d+:";
        pattern = Pattern.compile(regex, Pattern.MULTILINE);
        matcher = pattern.matcher(game);
        if (matcher.find()) {
            String id = matcher.group(0)
                    .replace(":", "")
                    .trim();
            return Integer.parseInt(id);
        }
        return -1;
    }

    public static String getGameSets(String game) {
        final String regex = "(?<=Game \\d?\\d\\d?: ).*";
        pattern = Pattern.compile(regex, Pattern.MULTILINE);
        matcher = pattern.matcher(game);
        if (matcher.find()) {
            return matcher.group(0);
        }
        return "";
    }
}
