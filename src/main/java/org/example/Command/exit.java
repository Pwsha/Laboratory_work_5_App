package org.example.Command;

public class exit extends Command {
    public static void exitProgram() {
        System.out.println("Программа завершена.");
        System.exit(0);
    }

    public static void description(){
        System.out.println( "  exit : завершить программу");
    }
}
