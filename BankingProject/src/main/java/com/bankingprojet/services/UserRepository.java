package com.bankingprojet.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bankingprojet.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByUsername(String username);
	Boolean existsByUsername(String username);
	Optional<User> findByPhone(String phone);
	void deleteByUsername(String username);
	@Query( "select distinct u from User u inner join u.roles r where r.id = :id_role" )
	List<User> findBySpecificRoles(@Param("id_role") long id_role);
	@Modifying
	@Query(value="DELETE FROM user_roles WHERE role_id = ?1 AND user_id = ?2", nativeQuery=true)
	public void deleteRoleFromUsersWithRole(long roleId, long id);
}
