package com.ldb.weblocalapi.repositories;

import com.ldb.weblocalapi.entities.Respone.UploadByUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ViewSaveByUserRepository extends CrudRepository<UploadByUser,UploadByUser> {
    @Query(value ="select * from V_INFO_USERSAVE WHERE ID IN ( ?1 )", nativeQuery = true)
    List<UploadByUser> findByViewSaveByKeyId(Long iD);
    @Query(value ="select * from V_INFO_USERSAVE WHERE ID= ? order by ID asc", nativeQuery = true)
    List<UploadByUser> findByViewSaveByFromId(Long iD);

    @Query(value = "select * from V_INFO_USERSAVE order by ID asc" , nativeQuery = true)
    List<UploadByUser> findViewSaveDocumentByAll();
}
