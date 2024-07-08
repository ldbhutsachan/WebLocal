package com.ldb.weblocalapi.repositories;

import com.ldb.weblocalapi.entities.Respone.SectionResponse;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectionResponseRepository extends CrudRepository<SectionResponse, SectionResponse> {
    @Query(value ="SELECT * from SECTION WHERE SEC_ID = ? ORDER BY SEC_ID ASC", nativeQuery = true)
    List<SectionResponse> findByBranchIdFromBySecId(String secId);

    @Query(value ="SELECT * from SECTION WHERE SEC_ID != ? ORDER BY SEC_ID ASC", nativeQuery = true)
    List<SectionResponse> findByBranchIdFromAll(String secId);
}
