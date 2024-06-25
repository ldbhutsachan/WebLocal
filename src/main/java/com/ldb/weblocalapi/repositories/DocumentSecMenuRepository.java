package com.ldb.weblocalapi.repositories;

import com.ldb.weblocalapi.entities.Respone.DocumentSecMenuRespone;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DocumentSecMenuRepository  extends CrudRepository<DocumentSecMenuRespone,DocumentSecMenuRespone> {


    @Query(value = "SELECT * FROM V_SECTION_MENU_COUNTER where  RELATION_UNIT=:code ORDER BY ID DESC" , nativeQuery = true)
    List<DocumentSecMenuRespone> finSecMenuAll(
            @Param("code") String code);

    @Query(value = "SELECT * FROM V_SECTION_MENU_COUNTER where TYPE =:type and RELATION_UNIT=:code ORDER BY ID DESC" , nativeQuery = true)
    List<DocumentSecMenuRespone> finSecMenuAllWithType(
            @Param("type") String type,
            @Param("code") String code);

//    @Query(value = "SELECT * FROM V_SECTION_MENU_COUNTER WHERE TYPE = ?1 AND RELATION_UNIT = ?2 ORDER BY ID DESC", nativeQuery = true)
//    List<DocumentSecMenuRespone> findByTypeAndRelationUnit(String type, String code);


}
