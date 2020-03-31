package sg.edu.nus.studentmanagementsystem.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sg.edu.nus.studentmanagementsystem.models.entitymodels.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

    @Query("SELECT a FROM Admin a WHERE a.username = :username")
    public Admin findByUsername(@Param("username") String username);

    @Query("SELECT a FROM Admin a WHERE UPPER(a.firstName) LIKE UPPER(CONCAT('%', :name, '%')) OR UPPER(a.lastName) LIKE UPPER(CONCAT('%', :name, '%'))")
    public List<Admin> findByOneName(@Param("name") String name);

    @Query("SELECT a FROM Admin a WHERE UPPER(a.firstName) LIKE UPPER(CONCAT('%', :first, '%')) OR UPPER(a.lastName) LIKE UPPER(CONCAT('%', :second, '%'))"
            + "OR UPPER(a.firstName) LIKE UPPER(CONCAT('%', :second, '%')) OR UPPER(a.lastName) LIKE UPPER(CONCAT('%', :first, '%'))")
    public List<Admin> findByTwoNames(@Param("first") String first, @Param("second") String second);

    @Query("SELECT Max(a.id) FROM Admin a")
    public int findMaxId();
    
    @Modifying
    @Query("DELETE FROM Admin a WHERE a.id = :id")
    @Transactional
    void delete(@Param("id") int id);
}