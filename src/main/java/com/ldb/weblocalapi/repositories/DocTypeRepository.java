package com.ldb.weblocalapi.repositories;


import com.ldb.weblocalapi.entities.DocType;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface DocTypeRepository extends CrudRepository<DocType, DocType> {

    @Transactional
    @Modifying
    @Query(value ="UPDATE DOCUMENT_TYPE SET DOC_TYPE_NAME=?,STATUS=? WHERE KEY_ID=? ", nativeQuery = true)
    int UpdateDataDocumentType(@Param("docTypeName") String docTypeName,@Param("status") String status,@Param("keyId") String keyId);

    @Transactional
    @Modifying
    @Query(value ="UPDATE DOCUMENT_TYPE SET STATUS=? WHERE KEY_ID=? ", nativeQuery = true)
    int ChangeStatus(@Param("status") String status,@Param("keyId") String keyId);

    @Query(value ="SELECT * FROM DOCUMENT_TYPE WHERE KEY_ID=?  ORDER BY KEY_ID desc", nativeQuery = true)
    List<DocType> findByDocTypeFromId(@Param("keyId") String keyId);

    @Query(value = "SELECT * FROM DOCUMENT_TYPE ORDER BY KEY_ID desc" , nativeQuery = true)
    List<DocType> findDocTypeAll();

}
