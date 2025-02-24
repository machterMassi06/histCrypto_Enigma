package com.mycrypto.utils;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;

public class Utils{

    public static String readFile(String filePath){
        try {
            return Files.readString(Path.of(filePath));
        }catch (IOException e){
            System.err.println("Error reading file: " + e.getMessage());
            return "";
        }
    }

    public static void writeFile(String filePath , String content){
        try {
            Files.writeString(Path.of(filePath), content);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}


