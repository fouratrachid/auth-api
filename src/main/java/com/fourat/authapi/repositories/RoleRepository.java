package com.fourat.authapi.repositories;
import com.fourat.authapi.models.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import  com.fourat.authapi.models.Role ;
public interface RoleRepository extends JpaRepository<Role,Integer> {
    Boolean existsByRoleName(RoleName roleName);
    Role findByRoleName(RoleName roleName);

}
