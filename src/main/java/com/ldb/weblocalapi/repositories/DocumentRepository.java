package com.ldb.weblocalapi.repositories;

import com.ldb.weblocalapi.entities.DocType;
import com.ldb.weblocalapi.entities.Respone.DocumentRespone;
import com.ldb.weblocalapi.entities.Respone.DocumentSecMenuRespone;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface DocumentRepository extends CrudRepository<DocumentRespone,DocumentRespone> {
    @Query(value = "SELECT * FROM V_SAVE_DOCUMENT_COUNTER WHERE ID IN ( ?1 )  ORDER BY ID DESC", nativeQuery = true)
    List<DocumentRespone> findByDocByKeyId(String docNo);

    @Query(value = "SELECT * FROM V_SAVE_DOCUMENT_COUNTER WHERE  ID= ?  ORDER BY ID DESC", nativeQuery = true)
    List<DocumentRespone> findByDocFromKeyId(String keyId);

    @Query(value = "SELECT * FROM V_SAVE_DOCUMENT_COUNTER WHERE SAVE_BY=:saveBy and DOCNAME LIKE %:docName% OR DOC_NO like %:docNo%  ORDER BY ID DESC", nativeQuery = true)
    List<DocumentRespone> findByDocFromKeyDocName(String saveBy, String docName, String docNo);

    //========Query Menu Home Only ========================
    @Query(value = "SELECT * FROM V_HOME_F1_REP where RELATION_UNITS=? ORDER BY ID DESC", nativeQuery = true)
    List<DocumentRespone> findDocAll(String relationUnitId);

    @Query(value = "SELECT * FROM V_SAVE_DOCUMENT_MAIN where SAVE_BY=? ORDER BY ID DESC", nativeQuery = true)
    List<DocumentRespone> findDocAllNyUserName(String saveBy);

    //========Query Menu Home Only by ConDiction 01 ========================DOC_DATE_Q
    @Query(value = "SELECT * FROM V_HOME_F1_REP WHERE RELATION_UNITS=:secCod and to_char(DOC_DATE_Q,'DD/MM/YYYY') >= :startDate AND to_char(DOC_DATE_Q,'DD/MM/YYYY') <= :endDate ORDER BY ID DESC", nativeQuery = true)
    List<DocumentRespone> findHomeDocAllByDateToEndDate(@Param("secCod") String secCod,@Param("startDate") String startDate, @Param("endDate") String endDate);

    @Query(value = "SELECT * FROM V_HOME_F1_REP WHERE RELATION_UNITS=:secCod and to_char(DOC_DATE_Q,'DD/MM/YYYY') >= :startDate AND to_char(DOC_DATE_Q,'DD/MM/YYYY') <= :endDate and DOCTYPENO like %:inFoBox% ORDER BY ID DESC", nativeQuery = true)
    List<DocumentRespone> findHomeDocAllByDateToEndDateText(@Param("secCod") String secCod,@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("inFoBox") String inFoBox);

    @Query(value = "SELECT * FROM V_HOME_F1_REP WHERE RELATION_UNITS=:secCod and DOCNAME like %:inFoBox% OR DOC_NO like %:inFoBox% ORDER BY ID DESC", nativeQuery = true)
    List<DocumentRespone> findHomeDocAllByText(@Param("secCod") String secCod,@Param("inFoBox") String inFoBox);

    @Query(value = "SELECT * FROM V_SAVE_DOCUMENT_COUNTER ORDER BY ID DESC", nativeQuery = true)
    List<DocumentRespone> findHomeDocAll();

    @Query(value = "SELECT * FROM V_SAVE_DOCUMENT_COUNTER where TYPE=? and RELATION_UNITS=? ORDER BY ID DESC", nativeQuery = true)
    List<DocumentRespone> findDocAllDocumentListBySecCodeMenuByTypeCode(String code, String type);

    @Query(value = "SELECT * FROM V_SAVE_DOCUMENT_COUNTER where RELATION_UNITS=? ORDER BY ID DESC", nativeQuery = true)
    List<DocumentRespone> findDocAllDocumentListBySecCodeMenuByCode(String code);


}