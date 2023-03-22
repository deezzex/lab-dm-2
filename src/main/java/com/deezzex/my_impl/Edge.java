package com.deezzex.my_impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

@AllArgsConstructor
@ToString
@Getter
@Setter
public class Edge {
    private String path;
    private int length;
    public boolean isSame(String other) {
        char[] path1 = path.toCharArray();
        char[] path2 = other.toCharArray();
        Arrays.sort(path1);
        Arrays.sort(path2);

        return new String(path1).equals(new String(path2));
    }

    public boolean intersect(String other) {
       String str = String.valueOf(this.path.charAt(0)
               + this.path.charAt(this.path.length() - 1)
               + other.charAt(0)
               + other.charAt(other.length() - 1));

        char[] chars = str.toCharArray();
        ArrayList<Character> characters = new ArrayList<>();

        for (char c: chars) {
            characters.add(c);
        }

        String collect1 = characters.stream().sorted().map(Object::toString).collect(Collectors.joining(""));
        String collect2 = characters.stream().sorted().distinct().map(Object::toString).collect(Collectors.joining(""));

        System.out.println(collect1.equals(collect2));

        return collect1.equals(collect2);
    }
}
