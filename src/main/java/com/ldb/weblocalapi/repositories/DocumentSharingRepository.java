package com.ldb.weblocalapi.repositories;

import com.ldb.weblocalapi.entities.DocumentSharing;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface DocumentSharingRepository extends CrudRepository<DocumentSharing,DocumentSharing> {
    @Transactional
    @Modifying
    @Query(value =" INSERT INTO DOC_SHARING (RELATION_UNIT) VALUE (?) ", nativeQuery = true)
    int insertSharing(@Param("relationUnit")  String relationUnit);

//    @Transactional
//    @Modifying
//    @Query("UPDATE DOC_SHARING ds SET ds.RELATION_UNIT = :relationUnit WHERE ds.DOC_NO = :docNo")
//    int update(@Param("docNo") String docNo, @Param("relationUnit") String relationUnit);

    @Transactional
    @Modifying
    @Query(value ="DELETE DOC_SHARING WHERE DOC_NO=? ", nativeQuery = true)
    int update(@Param("docNo") String docNo);


}