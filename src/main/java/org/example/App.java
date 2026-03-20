package org.example;

import org.example.command.*;
import org.example.command.list.*;
import org.example.program.InitCollection;
import java.util.*;

/**
 * Главный класс приложения
 * @author Pwsha
 * @version v1.3
 */
public class App {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Ошибка: укажите имя файла");
            System.out.println("Пример: java -cp . org.example.App data.csv");
            System.exit(1);
        }

        InitCollection manager = new InitCollection(args[0]);
        manager.run();
    }
}