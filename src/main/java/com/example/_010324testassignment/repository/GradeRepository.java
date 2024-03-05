package com.example._010324testassignment.repository;

import com.example._010324testassignment.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {

    List<Grade> findAllBySubject(Subject subject);

    List<Grade> findAllBySubjectIn(List<Subject> subjects);

    List<Grade> findByUserAndSubject(User user, Subject subject);


    @Query(value = """
            SELECT u.username AS studentName,
                   s.name AS subjectName,
                   AVG(g.grade) AS averageGrade,
                   grp.name AS groupName
            FROM grade g
                     INNER JOIN users u ON g.user_id = u.id
                     INNER JOIN subject s ON g.subject_id = s.id
                     INNER JOIN groups grp ON u.group_id = grp.id
            GROUP BY u.username, s.name, grp.name
            ORDER BY
                grp.name DESC, u.username DESC;
            """,
            nativeQuery = true)
    List<IStudentAverageGrade> calculateStudentAverageGrades();

    @Query(value = """
            SELECT
                grp.name AS groupName,
                s.name AS subjectName,
                AVG(g.grade) AS averageGrade
            FROM
                grade g
            INNER JOIN
                users u ON g.user_id = u.id
            INNER JOIN
                subject s ON g.subject_id = s.id
            INNER JOIN
                groups grp ON u.group_id = grp.id
            GROUP BY
                grp.name, s.name;
            """,
            nativeQuery = true)
    List<IAverageGradesInGroups> calculateAverageGradesInGroups();
}
