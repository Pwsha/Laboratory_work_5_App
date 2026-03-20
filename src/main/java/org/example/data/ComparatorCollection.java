package org.example.data;

import org.example.program.StudyGroup;
import java.util.Comparator;
import java.util.HashSet;

/**
 * Класс для сравнения элементов коллекции
 * @author Pwsha
 * @version v1.3
 */
public class ComparatorCollection {

    public static final Comparator<StudyGroup> COMPARATOR = Comparator
            .comparingLong(StudyGroup::getStudentsCount)
            .thenComparingInt(StudyGroup::getExpelledStudents)
            .thenComparing(StudyGroup::getFormOfEducation)
            .thenComparing(StudyGroup::getSemesterEnum)
            .thenComparing(StudyGroup::getName)
            .thenComparing(StudyGroup::getId);

    public static boolean isGreater(StudyGroup g1, StudyGroup g2) {
        return COMPARATOR.compare(g1, g2) > 0;
    }

    public static StudyGroup findMax(HashSet<StudyGroup> collection) {
        return collection.stream()
                .max(COMPARATOR)
                .orElse(null);
    }
}