package sg.edu.nus.studentmanagementsystem.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sg.edu.nus.studentmanagementsystem.models.entitymodels.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    @Query("SELECT s FROM Student s WHERE s.id = :id")
    public Student findById(@Param("id") int id);

    @Query("SELECT s FROM Student s WHERE s.username = :username")
    public Student findByUsername(@Param("username") String username);

    @Query("SELECT a FROM Student a WHERE UPPER(a.firstName) LIKE UPPER(CONCAT('%', :name, '%')) OR UPPER(a.lastName) LIKE UPPER(CONCAT('%', :name, '%'))")
    public List<Student> findByOneName(@Param("name") String name);

    @Query("SELECT a FROM Student a WHERE UPPER(a.firstName) LIKE UPPER(CONCAT('%', :first, '%')) OR UPPER(a.lastName) LIKE UPPER(CONCAT('%', :second, '%'))"
            + "OR UPPER(a.firstName) LIKE UPPER(CONCAT('%', :second, '%')) OR UPPER(a.lastName) LIKE UPPER(CONCAT('%', :first, '%'))")
    public List<Student> findByTwoNames(@Param("first") String first, @Param("second") String second);
    
    @Query("SELECT Max(s.id) FROM Student s")
    public int findMaxId();
    
    @Modifying
    @Query("DELETE FROM Student s WHERE s.id = :id")
    @Transactional
    void delete(@Param("id") int id);
}

/* Changes to Method Names */
// searchStudentByAName --> findByOneName
// searchStudentByTwoNames --> findByTwoNames