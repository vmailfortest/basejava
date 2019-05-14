package ru.javawebinar.basejava;

import java.io.File;

public class MainRecursive {
    public static void main(String[] args) {
        File dir = new File("./");
        Recursive(dir);
    }

    public static void Recursive(File dir) {
        File[] list = dir.listFiles();
        for (File name : list) {
            if (name.isDirectory()) {
                Recursive(name);
            } else {
                System.out.println(name.getName());
            }
        }
    }
}
