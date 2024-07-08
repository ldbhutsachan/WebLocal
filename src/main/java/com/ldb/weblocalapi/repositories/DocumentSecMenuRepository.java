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

    @Query(value = "SELECT * FROM V_SECTION_MENU_COUNTER where BRANCH_ID !=:secCod and RELATION_UNIT=:code ORDER BY ID DESC" , nativeQuery = true)
    List<DocumentSecMenuRespone> finSecMenuAllWithTypeIn(
            @Param("secCod") String secCod,
            @Param("code") String code);

    @Query(value = "SELECT * FROM V_SECTION_MENU_COUNTER where BRANCH_ID =:secCod and RELATION_UNIT=:code ORDER BY ID DESC" , nativeQuery = true)
    List<DocumentSecMenuRespone> finSecMenuAllWithTypeOut(
            @Param("secCod") String secCod,
            @Param("code") String code);



    //=====================band ==============
    @Query(value = "SELECT * FROM V_BAND_MENU_COUNTER where  RELATION_UNIT=:secCod ORDER BY ID DESC" , nativeQuery = true)
    List<DocumentSecMenuRespone> finBandMenuAll(
            @Param("secCod") String secCod);

    @Query(value = "SELECT * FROM V_BAND_MENU_COUNTER where BRANCH_ID !=:secCod and RELATION_UNIT=:code ORDER BY ID DESC" , nativeQuery = true)
    List<DocumentSecMenuRespone> finBandMenuAllWithTypeIn(
            @Param("secCod") String secCod,
            @Param("code") String code);

    @Query(value = "SELECT * FROM V_BAND_MENU_COUNTER where BRANCH_ID =:secCod and RELATION_UNIT=:code ORDER BY ID DESC" , nativeQuery = true)
    List<DocumentSecMenuRespone> finBandMenuAllWithTypeOut(
            @Param("secCod") String secCod,
            @Param("code") String code);


}
