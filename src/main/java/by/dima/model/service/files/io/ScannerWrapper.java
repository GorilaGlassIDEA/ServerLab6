package by.dima.model.service.files.io;

import lombok.Getter;

import java.util.Scanner;

@Getter
public class ScannerWrapper {

    private Scanner scanner;

    public String[] newLineSplitSpace() {
        scanner = new Scanner(System.in);
        String nextLine = scanner.nextLine().trim();
        return nextLine.split(" ");
    }

    public void closeScanner() {
        scanner.close();
    }

}
