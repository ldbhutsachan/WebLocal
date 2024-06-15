package com.ldb.weblocalapi.repositories;

import com.ldb.weblocalapi.entities.Respone.DocumentSecMenuRespone;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DocumentSecMenuRepository  extends CrudRepository<DocumentSecMenuRespone,DocumentSecMenuRespone> {


    @Query(value = "SELECT * FROM V_SECTION_MENU_COUNTER where  RELATION_UNIT=:code and DOC_DATE >= :startDate AND DOC_DATE <= :endDate ORDER BY ID DESC" , nativeQuery = true)
    List<DocumentSecMenuRespone> findDocAllDocumentListBySecCodeMenuByDateTotDate(@Param("startDate") String startDate, @Param("endDate") String endDate,
                                                                                  @Param("code") String code);

    @Query(value = "SELECT * FROM V_SECTION_MENU_COUNTER where TYPE= :type  and DOC_DATE >= :startDate AND DOC_DATE <= :endDate ORDER BY ID DESC" , nativeQuery = true)
    List<DocumentSecMenuRespone> findDocAllDocumentListBySecCodeMenuByDateTotDateByTypeDocType(@Param("type") String type,@Param("startDate") String startDate,
                                                                                               @Param("endDate") String endDate);

    @Query(value = "SELECT * FROM V_SECTION_MENU_COUNTER where   RELATION_UNIT=:code and DOCTYPENO= :docType and " +
            "and DOC_DATE >= :startDate AND DOC_DATE <= :endDate ORDER BY ID DESC" , nativeQuery = true)
    List<DocumentSecMenuRespone> findDocAllDocumentListBySecCodeMenuByDateTotDateByDocTypeAll(
            @Param("code") String code,
            @Param("docType") String docType,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate);


    @Query(value = "SELECT * FROM V_SECTION_MENU_COUNTER where TYPE= :type and RELATION_UNIT=:code and DOCTYPENO= :docType and DOC_DATE >= :startDate AND DOC_DATE <= :endDate ORDER BY ID DESC" , nativeQuery = true)
    List<DocumentSecMenuRespone> findDocAllDocumentListBySecCodeMenuByDateTotDateAndDocType(
            @Param("type") String type,
            @Param("code") String code,
            @Param("docType") String docType,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate);

}
