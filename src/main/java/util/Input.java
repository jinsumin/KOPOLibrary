package util;

import java.util.Scanner;

/**
 * Created by soomin on 2022/04/02
 */

public class Input {
    private static final Scanner scanner = new Scanner(System.in);

    public static String getString(String input) {
        System.out.println(input);
        return scanner.next();
    }

    public static int getInt(String input) {
        System.out.println(input);
        return scanner.nextInt();
    }
}
