package ru.javawebinar.basejava;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainRecursive {
    public static void main(String[] args) throws IOException {
//        File dir = new File("./");
//        recursive(dir);
        Path path = Paths.get("./");
        recursiveWithTabs(path);
    }

    public static void recursiveWithTabs(Path path) throws IOException {
        String dir = new String(new char[path.getNameCount() - 1]).replace("\0", "\t")
                + "[" + path.getFileName() + "]";
        System.out.println(dir);
        Files.list(path).forEach(p -> {
            if (Files.isDirectory(p)) {
                try {
                    recursiveWithTabs(p);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                String file = new String(new char[p.getNameCount() - 1]).replace("\0", "\t")
                        + p.getFileName();
                System.out.println(file);
            }
        });
    }

//    public static void recursive(File dir) {
//        File[] list = dir.listFiles();
//        Objects.requireNonNull(list, "ERROR: Path '" + dir + "' is invalid!");
//        for (File name : list) {
//            if (name.isDirectory()) {
//                recursive(name);
//            } else {
//                System.out.println(name.getName());
//            }
//        }
//    }
}
