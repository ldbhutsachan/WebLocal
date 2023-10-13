package com.ldb.reportcustom.repositories;

import com.ldb.reportcustom.entities.Border;
import com.ldb.reportcustom.entities.Roles;
import lombok.Value;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * Create at 03/01/2023 - 9:01 AM
 * Project Name reportCustom
 *
 * @author kataii
 */
@Repository
public interface BorderRepository extends CrudRepository<Border, Border> {
    @Query(value ="SELECT A.*,B.* from border.TAX_BORDER A\n" +
            "JOIN border.TAX_BORDER_ROLE B ON A.BORDER_ID = B.BORDER_ID\n" +
            "JOIN border.TAX_ROLES TR on B.ROLE_ID = TR.ROLE_ID\n" +
            "WHERE TR.ROLE_NAME IN ( ?1 )", nativeQuery = true)
//            "WHERE A.BORDER_ID IN ( ?1 )", nativeQuery = true)
    List<Border> findByRolesName(List<String> roleId);
//    List<Border> findByRoles(Collection<Roles> roleId);

    @Query(value ="SELECT A.* from border.TAX_BORDER A JOIN TAX_USER_LOGIN TUL on A.BORDER_ID = TUL.BORDER_ID WHERE USER_NAME = ?", nativeQuery = true)
    List<Border> findByBorderIdFromUserName(String userName);
}
