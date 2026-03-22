package org.example.command.list;

import org.example.command.Command;
import org.example.command.CommandHelper;
import org.example.command.GroupParser;
import org.example.init.StudyGroup;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Класс команды обновления элемента по id
 * @author Pwsha
 * @version v1.3
 */
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

            StudyGroup newGroup;
            if (args.length == 1) {
                System.out.println("Редактирование элемента с id: " + id);
                System.out.println("Введите новые данные:");
                newGroup = CommandHelper.readStudyGroup(scanner, collection);

            } else if (args.length == 2) {
                String[] dataArgs = new String[args.length - 1];
                System.arraycopy(args, 1, dataArgs, 0, args.length - 1);
                String input = String.join(" ", dataArgs);

                if (input.startsWith("{") && input.endsWith("}")) {
                    input = input.substring(1, input.length() - 1);
                }
                newGroup = GroupParser.parseFromString(input, collection);
            } else {
                return "Введено неверное количество аргументов";
            }

            StudyGroup updatedGroup = new StudyGroup.Builder()
                    .id(id)
                    .name(newGroup.getName())
                    .coordinates(newGroup.getCoordinates())
                    .creationDate(existing.getCreationDate())
                    .studentsCount(newGroup.getStudentsCount())
                    .expelledStudents(newGroup.getExpelledStudents())
                    .formOfEducation(newGroup.getFormOfEducation())
                    .semesterEnum(newGroup.getSemesterEnum())
                    .groupAdmin(newGroup.getGroupAdmin())
                    .build();

            collection.remove(existing);
            collection.add(updatedGroup);

            return "Элемент с id " + id + " успешно обновлен";

        } catch (NumberFormatException e) {
            return "Ошибка: id должен быть числом";
        } catch (IllegalArgumentException e) {
            return "Ошибка: " + e.getMessage();
        }
    }

    @Override
    public String getName() { return "update"; }

    @Override
    public String getDescription() { return "обновить элемент по id"; }

    @Override
    public String getSyntax() { return "update id"; }
}