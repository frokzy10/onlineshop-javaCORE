package application.util;

import java.util.Scanner;

public class ScannerUtil {
    private static Scanner input = new Scanner(System.in);

    public static int nextInt(){
        while(!input.hasNextInt()){
            input.next();
        }
        return input.nextInt();
    }
    public static String nextLine(){
        String line = input.nextLine();
        while(line.isEmpty() || line.isBlank()){
            line = input.nextLine();
        }
        return line;
    }
}
