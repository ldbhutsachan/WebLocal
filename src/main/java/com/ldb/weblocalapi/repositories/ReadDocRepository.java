package com.ldb.weblocalapi.repositories;

import com.ldb.weblocalapi.entities.Respone.BranchRespone;
import com.ldb.weblocalapi.entities.Respone.ReadDocument;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReadDocRepository extends CrudRepository<ReadDocument,ReadDocument> {
    @Query(value ="SELECT * FROM V_DOC_VIEW", nativeQuery = true)
    List<ReadDocument> findDocumentViewAll();

    @Query(value ="SELECT * FROM V_DOC_VIEW WHERE  DOC_NO= ? order by DOC_NO asc", nativeQuery = true)
    List<ReadDocument> ReadDocDocumentByDocNo(String docNo);

}
