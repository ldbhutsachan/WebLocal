package com.ldb.weblocalapi.repositories;

import com.ldb.weblocalapi.entities.Menu;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends CrudRepository<Menu, Menu> {
    @Query(value ="SELECT \n" +
            "A.ID, A.MENU_ID, A.USER_ID, C.USER_NAME,MENU_PATH, ICON_MENU, MENU_LO, STATUS\n" +
            "FROM MAP_USER_MENU A INNER JOIN MENU B ON A.MENU_ID=B.MENU_ID\n" +
            "    INNER JOIN  USER_LOGIN C ON A.USER_ID=C.USER_ID WHERE C.USER_NAME IN ( ?1 ) order by MENU_ID ASC", nativeQuery = true)
    List<Menu> findByMenuMapUser(List<String> userId);
    @Query(value ="SELECT A.ID, A.MENU_ID, A.USER_ID, C.USER_NAME,MENU_PATH, ICON_MENU, MENU_LO,STATUS\n" +
            "FROM MAP_USER_MENU A INNER JOIN MENU B ON A.MENU_ID=B.MENU_ID\n" +
            "    INNER JOIN  USER_LOGIN C ON A.USER_ID=C.USER_ID WHERE C.USER_NAME = ? AND B.MENU_PATH !='/links/general-link' order by MENU_ID ASC", nativeQuery = true)
    List<Menu> findByMenuMapUserIdFromUserName(String userName);

    @Query(value ="SELECT A.ID, A.MENU_ID, A.USER_ID, C.USER_NAME,MENU_PATH, ICON_MENU, MENU_LO,STATUS\n" +
            "FROM MAP_USER_MENU A INNER JOIN MENU B ON A.MENU_ID=B.MENU_ID\n" +
            "    INNER JOIN  USER_LOGIN C ON A.USER_ID=C.USER_ID WHERE C.USER_NAME = ? and B.MENU_PATH !='/links' order by MENU_ID ASC", nativeQuery = true)
    List<Menu> findByMenuMapUserIdFromUserNameUser(String userName);


}
