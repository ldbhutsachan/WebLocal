package com.ldb.weblocalapi.repositories;

import com.ldb.weblocalapi.entities.Respone.ReadDocument;
import com.ldb.weblocalapi.entities.Respone.UploadByUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UploadByUserRepository extends CrudRepository<UploadByUser,UploadByUser> {
    @Query(value ="SELECT * FROM V_INFO_USERSAVE", nativeQuery = true)
    List<UploadByUser> UploadByUserAll();

    @Query(value ="SELECT * FROM V_INFO_USERSAVE WHERE SAVE_BY=? and  DOC_NO=?  order by DOC_NO asc", nativeQuery = true)
    List<UploadByUser> UploadByUser(String userName,String docNo);
}
