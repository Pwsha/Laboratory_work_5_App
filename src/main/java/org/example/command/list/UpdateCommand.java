package org.example.command.list;

import org.example.command.Command;
import org.example.command.CommandHelper;
import org.example.program.StudyGroup;
import java.util.HashSet;
import java.util.Scanner;

public class UpdateCommand implements Command {

    @Override
    public String execute(String[] args, HashSet<StudyGroup> collection, Scanner scanner) {
        if (args.length == 0) {
            return "Ошибка: укажите id";
        }

        try {
            Long id = Long.parseLong(args[0]);
            StudyGroup existing = collection.stream()
                    .filter(g -> g.getId().equals(id))
                    .findFirst()
                    .orElse(null);

            if (existing == null) {
                return "Элемент с id " + id + " не найден";
            }

            collection.remove(existing);
            StudyGroup newGroup = CommandHelper.readStudyGroup(scanner, collection);
            newGroup.setId(id);
            collection.add(newGroup);

            return "Элемент с id " + id + " обновлен";
        } catch (NumberFormatException e) {
            return "Ошибка: id должен быть числом";
        }
    }

    @Override
    public String getName() { return "update"; }

    @Override
    public String getDescription() { return "обновить элемент по id"; }

    @Override
    public String getSyntax() { return "update id"; }
}