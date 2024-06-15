package com.ldb.weblocalapi.repositories;

import com.ldb.weblocalapi.entities.Popup;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PopupRepository extends CrudRepository<Popup,Popup> {
}
