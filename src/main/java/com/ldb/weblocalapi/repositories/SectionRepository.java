package com.ldb.weblocalapi.repositories;

import com.ldb.weblocalapi.entities.Section;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectionRepository extends CrudRepository<Section, Section> {
    @Query(value ="SELECT A.*,B.* from SECTION A\n" +
            "JOIN WEB_LOCAL_ROLE B ON a.sec_id = B.BORDER_ID \n" +
            "JOIN ROLE TR on B.ROLE_ID = TR.ROLE_ID\n" +
            "WHERE TR.ROLE_NAME IN ( ?1 )", nativeQuery = true)
    List<Section> findByRolesName(List<String> roleId);
    @Query(value ="SELECT A.* from SECTION A JOIN USER_LOGIN TUL on A.SEC_ID = TUL.BRANCH_ID WHERE USER_NAME = ?", nativeQuery = true)
    List<Section> findByBranchIdFromUserName(String userName);

}
