package com.zayzou.day00;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class Day01 {

    public static void main(String[] args) {
        final var FILE = "com/zayzou/day00/input.txt";
        System.out.println(sumOfAllValues(FILE));
    }

    private static int sumOfAllValues(String FILE) {
        //use auto close ressource to read the file
        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            var ligne = "";
            int sum = 0;
            //while there is new line we go through...
            while ((ligne = br.readLine()) != null) {
                final var l = ligne;
                //look for the first digit
                OptionalInt first = ligne.chars()
                        .filter(Character::isDigit)
                        .map(Character::getNumericValue)
                        .findFirst();


                //look for the last digit

                Optional<Integer> last = IntStream.range(0, ligne.length())
                        .boxed()
                        .sorted(Collections.reverseOrder())
                        .filter(i -> Character.isDigit(l.charAt(i)))
                        .map(i -> Character.getNumericValue(l.charAt(i)))
                        .findFirst();


                //create the number from the 2 digits
                Integer number = Integer.valueOf(first.getAsInt() + "" + last.get());
                sum += number;

            }
            return sum;
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}
