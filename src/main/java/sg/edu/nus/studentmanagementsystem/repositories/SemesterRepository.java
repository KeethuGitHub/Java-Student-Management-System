package sg.edu.nus.studentmanagementsystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sg.edu.nus.studentmanagementsystem.models.entitymodels.Semester;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, Integer> {
    @Query("SELECT s FROM Semester s WHERE s.id = :id")
    public Semester findById(@Param("id") int id);
}

/* Changes to Method Names */
// findBySemesterCode --> findById