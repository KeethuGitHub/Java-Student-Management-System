package sg.edu.nus.studentmanagementsystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sg.edu.nus.studentmanagementsystem.models.entitymodels.Module;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Integer> {

	@Query("SELECT m FROM Module m WHERE m.modulecode = :modulecode")
	Module findByModulecode(@Param("modulecode") String modulecode);
	
	@Query("SELECT m FROM Module m WHERE m.modulename = :modulename")
	Module findByModulename(@Param("modulename") String modulename);

}
