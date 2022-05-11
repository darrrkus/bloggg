package ua.tvsolutions.bloggg.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.tvsolutions.bloggg.models.Role;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByName(String name);
}
