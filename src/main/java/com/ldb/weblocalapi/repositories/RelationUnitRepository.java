package com.ldb.weblocalapi.repositories;

import com.ldb.weblocalapi.entities.DocShareBandUnit;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface RelationUnitRepository extends CrudRepository<DocShareBandUnit,DocShareBandUnit> {
    @Transactional
    @Modifying
    @Query(value ="DELETE FROM DOC_SHARING_BAND_UNIT WHERE DOC_NO=? ", nativeQuery = true)
    int update(@Param("docNo") String docNo);

}
