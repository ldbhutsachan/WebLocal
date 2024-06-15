package com.ldb.weblocalapi.repositories;

import com.ldb.weblocalapi.entities.DisplayLink;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface DisplayLinkRepository extends CrudRepository<DisplayLink,DisplayLink> {
    @Transactional
    @Modifying
    @Query("UPDATE DisplayLink dl SET dl.name = :name, dl.note = :note, dl.link = :link WHERE dl.id = :id")
    int updateDisplayLinkNoFile(@Param("name") String name, @Param("note") String note, @Param("link") String link,
                            @Param("id") String id);

    @Transactional
        @Modifying
        @Query("UPDATE DisplayLink dl SET dl.name = :name, dl.note = :note, dl.link = :link, dl.imagePath = :imagePath, dl.saveBy = :saveBy WHERE dl.keyId = :keyId")
        int updateDisplayLink(@Param("name") String name, @Param("note") String note, @Param("link") String link, @Param("imagePath") String imagePath, @Param("saveBy") String saveBy, @Param("keyId") String keyId);

    @Transactional
    @Modifying
    @Query("DELETE FROM DisplayLink dl WHERE dl.id = :id")
    int deleteDisPlayLink(@Param("id") String id);

    @Query(value = "SELECT dl.id, dl.name, dl.note, dl.link, dl.saveBy,dl.IMAGE_PATH FROM DisplayLink dl WHERE dl.name LIKE %:name% ORDER BY dl.id desc", nativeQuery = true)
    List<DisplayLink> findByDisplayLinkNameLike(@Param("name") String name);

    @Query(value ="SELECT  ID,NAME,NOTE,LINK,SAVEBY,IMAGE_PATH FROM DISPLAYLINK WHERE ID=?  ORDER BY ID desc", nativeQuery = true)
    List<DisplayLink> findByDisPlayFromId(@Param("keyId") String keyId);

    @Query(value = "SELECT  ID,NAME,NOTE,LINK,SAVEBY,IMAGE_PATH FROM DISPLAYLINK ORDER BY ID desc" , nativeQuery = true)
    List<DisplayLink> findViewDisPlayByAll();


}
