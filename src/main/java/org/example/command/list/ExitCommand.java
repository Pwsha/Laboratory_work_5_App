package org.example.command.list;

import org.example.command.Command;
import org.example.program.StudyGroup;
import java.util.HashSet;
import java.util.Scanner;


public class ExitCommand implements Command {

    @Override
    public String execute(String[] args, HashSet<StudyGroup> collection, Scanner scanner) {
        return "exit";
    }

    @Override
    public String getName() { return "exit"; }

    @Override
    public String getDescription() { return "завершить программу"; }

    @Override
    public String getSyntax() { return "exit"; }
}