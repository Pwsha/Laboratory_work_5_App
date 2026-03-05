package org.example.command;

import org.example.program.StudyGroup;
import java.util.HashSet;
import java.util.Scanner;

public interface Command {

    String execute(String[] args, HashSet<StudyGroup> collection, Scanner scanner);

    String getName();

    String getDescription();

    String getSyntax();
}