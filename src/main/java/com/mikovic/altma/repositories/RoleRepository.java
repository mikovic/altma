package com.mikovic.altma.repositories;

/**
 * @author Sergey Petukhov
 */

import com.mikovic.altma.modeles.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    //добавление роли (   Role save(Role role){ };   ) и удаление роли по id есть в CrudRepository
    Role findByName(String name);

    void deleteRoleByName(String name);
    // нужно ли удалять по id и name одновременно?
    void deleteRoleByNameAndId(String name, Long id);

}
