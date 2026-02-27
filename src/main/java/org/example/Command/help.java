package org.example.Command;

public class help extends Command {
    public static void printHelp() {
        System.out.println("Доступные команды:");
        System.out.println("  help : вывести справку по доступным командам");
        System.out.println("  info : вывести информацию о коллекции");
        System.out.println("  show : вывести все элементы коллекции");
        System.out.println("  add {element} : добавить новый элемент");
        System.out.println("  update id {element} : обновить элемент по id");
        System.out.println("  remove_by_id id : удалить элемент по id");
        System.out.println("  clear : очистить коллекцию");
        System.out.println("  save : сохранить коллекцию в файл");
        System.out.println("  execute_script file_name : выполнить скрипт из файла");
        exit.description();
        System.out.println("  add_if_max {element} : добавить элемент, если он максимальный");
        System.out.println("  remove_greater {element} : удалить элементы, превышающие заданный");
        System.out.println("  history : вывести последние 5 команд");
        System.out.println("  remove_any_by_students_count studentsCount : удалить элемент по studentsCount");
        System.out.println("  min_by_semester_enum : вывести элемент с минимальным semesterEnum");
        System.out.println("  count_greater_than_expelled_students expelledStudents : количество элементов с expelledStudents > заданного");
    }
}
