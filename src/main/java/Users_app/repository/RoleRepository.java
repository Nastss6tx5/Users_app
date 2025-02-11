package Users_app.repository;

import Users_app.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("SELECT r FROM Role r WHERE r.id IN :roleIds")
    List<Role> findAllByIdIn(@Param("roleIds") Collection<Long> roleIds);

    Role findByName(String name);
}