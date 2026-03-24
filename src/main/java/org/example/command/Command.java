package org.example.command;

import org.example.init.StudyGroup;

/**
 * Интерфейс для команд
 * @author Pwsha
 * @version v1.3
 */
public interface Command {
    /**
     * Метод выполнения команды
     * @param group
     */
    String execute(StudyGroup group);

    /**
     * Функция получения значения имени
     */
    String getName();

    /**
     * Функция получения значения описания
     */
    String getDescription();

    /**
     * Функция получения значения синтаксиса
     */
    String getSyntax();
}