package com.ldb.weblocalapi.repositories;

import com.ldb.weblocalapi.entities.Popup;
import com.ldb.weblocalapi.entities.Respone.DocumentRespone;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PopupRepository extends CrudRepository<Popup,Popup> {
    @Query(value = "SELECT * FROM V_POPUP_DOC where RELATION_UNIT=? ORDER BY RELATION_UNIT DESC" , nativeQuery = true)
    List<Popup> findDocAllById(String relationUnitId);
}
