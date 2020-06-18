package sgsits.cse.dis.user.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sgsits.cse.dis.user.model.Group;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, String> {
    List<Group> findByCreatedBy(String username);
    Group findByGroupId(String groupId);
    @Query(value = "SELECT groups.group_name FROM groups where groups.created_by = ?1 ", nativeQuery = true)
    List<Object> findAllGroupNameByCreatedBy(String username);
}
