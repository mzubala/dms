package pl.com.bottega.dms.infrastructure;

import java.io.*;
import java.util.Scanner;

public class CesarDecipherTest {

    public static void main(String[] args) throws Exception {
        InputStream is = new CesarInputStream(new FileInputStream("/home/maciuch/tmp/cesarCiphered.txt"), 44);
        Scanner scanner = new Scanner(is);
        String deciphered = scanner.nextLine();
        System.out.println("Deciphered: " + deciphered);
    }

}
