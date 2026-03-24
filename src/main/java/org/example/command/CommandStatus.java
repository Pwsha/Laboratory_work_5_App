package org.example.command;

import org.example.init.StudyGroup;

import java.util.HashSet;
import java.util.Scanner;

/**
 * Класс для инициализации режимов ввода в командах
 * @author Pwsha
 * @version v1.1
 */
public class CommandStatus {

    public static StudyGroup fromConsole(Scanner scanner, HashSet<StudyGroup> collection) {
        return CommandHelper.readStudyGroup(scanner, collection);
    }

    public static StudyGroup fromString(String input, HashSet<StudyGroup> collection) {
        if (input.startsWith("{") && input.endsWith("}")) {
            input = input.substring(1, input.length() - 1);
        }
        return GroupParser.parseFromString(input, collection);
    }
}