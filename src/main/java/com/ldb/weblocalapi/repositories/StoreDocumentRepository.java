package com.ldb.weblocalapi.repositories;

import com.ldb.weblocalapi.entities.Document;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface StoreDocumentRepository extends CrudRepository<Document,Document> {
    @Transactional
    @Modifying
    @Query("UPDATE Document d SET d.docType = :docType, d.docPath = :docPath," +
            "d.docDate = :docDate, d.updateBy = :updateBy, " +
            "d.note = :note, d.docName = :docName, d.popup = :popup, d.type = :type, d.relationUnit = :relationUnit," +
            " d.relationUnitSec = :relationUnitSec, d.popupStart = :popupStart, d.popupEnd = :popupEnd WHERE d.docNo = :docNo")
                int update(
                @Param("docNo") String docNo,
                @Param("docType") String docType,
                @Param("docPath") String docPath,
                @Param("docDate") String docDate,
                @Param("updateBy") String updateBy,
                @Param("note") String note,
                @Param("docName") String docName,
                @Param("popup") String popup,
                @Param("type") String type,
                @Param("relationUnit") String relationUnit,
                @Param("relationUnitSec") String relationUnitSec,
                @Param("popupStart") String popupStart,
                @Param("popupEnd") String popupEnd);

    @Transactional
    @Modifying
    @Query("UPDATE Document d SET d.docType = :docType," +
            "d.docDate = :docDate, d.updateBy = :updateBy, " +
            "d.note = :note, d.docName = :docName, d.popup = :popup, d.type = :type, d.relationUnit = :relationUnit," +
            " d.relationUnitSec = :relationUnitSec, d.popupStart = :popupStart, d.popupEnd = :popupEnd WHERE d.docNo = :docNo")
    int updateNoFile(
            @Param("docNo") String docNo,
            @Param("docType") String docType,
            @Param("docDate") String docDate,
            @Param("updateBy") String updateBy,
            @Param("note") String note,
            @Param("docName") String docName,
            @Param("popup") String popup,
            @Param("type") String type,
            @Param("relationUnit") String relationUnit,
            @Param("relationUnitSec") String relationUnitSec,
            @Param("popupStart") String popupStart,
            @Param("popupEnd") String popupEnd);

    @Transactional
    @Modifying
    @Query("delete Document d  WHERE d.docNo = :docNo")
    int delDocumentFile(@Param("docNo") String docNo);

}
