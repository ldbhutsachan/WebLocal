package com.ldb.weblocalapi.repositories;

import com.ldb.weblocalapi.entities.Respone.BranchRespone;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BranchRepository extends CrudRepository<BranchRespone, BranchRespone> {
    @Query(value ="select * from BRANCH WHERE BRANCH_CODE IN ( ?1 )", nativeQuery = true)
    List<BranchRespone> findByDocTypeByKeyId(String branchCode);
    @Query(value ="SELECT * FROM BRANCH WHERE  BRANCH_CODE= ? order by ID asc", nativeQuery = true)
    List<BranchRespone> findByDocTypeFromKeyId(String branchCode);

    @Query(value = "SELECT * FROM BRANCH order by ID asc" , nativeQuery = true)
    List<BranchRespone> findDocTypeAll();

    @Query(value = "SELECT * FROM BRANCH where BRANCH_CODE like '%LA%' and BRANCH_CODE !='LA0010001' order by ID asc" , nativeQuery = true)
    List<BranchRespone> findDocTypeAllOnlyBand();
}
