package sg.edu.nus.studentmanagementsystem.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sg.edu.nus.studentmanagementsystem.models.entitymodels.Faculty;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Integer> {

	
	
	
    @Query("SELECT f FROM Faculty f WHERE f.id = :id")
    public Faculty findById(@Param("id") int id);
    
    @Query("SELECT f FROM Faculty f WHERE f.username = :username")
    public Faculty findByUsername(@Param("username") String username);

    @Query("SELECT a FROM Faculty a WHERE UPPER(a.firstName) LIKE UPPER(CONCAT('%', :name, '%')) OR UPPER(a.lastName) LIKE UPPER(CONCAT('%', :name, '%'))")
    public List<Faculty> findByOneName(@Param("name") String name);

    @Query("SELECT a FROM Faculty a WHERE UPPER(a.firstName) LIKE UPPER(CONCAT('%', :first, '%')) OR UPPER(a.lastName) LIKE UPPER(CONCAT('%', :second, '%'))"
            + "OR UPPER(a.firstName) LIKE UPPER(CONCAT('%', :second, '%')) OR UPPER(a.lastName) LIKE UPPER(CONCAT('%', :first, '%'))")
    public List<Faculty> findByTwoNames(@Param("first") String first, @Param("second") String second);

    @Query("SELECT Max(f.id) FROM Faculty f")
    public int findMaxId();
    
    
    
    @Modifying
    @Query("DELETE FROM Faculty f WHERE f.id = :id")
    @Transactional
    void delete(@Param("id") int id);
}