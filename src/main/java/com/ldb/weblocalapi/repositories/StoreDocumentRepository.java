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
    //d.docPath = :docPath
    //@Param("docPath") String docPath,
    @Query("UPDATE Document d\n" +
            "SET d.docNo = :docNo,\n" +
            "    d.docType = :docType," +
            "    d.docPath = :docPath, \n" +
            "    d.docDate = :docDate,\n" +
            "    d.updateBy = :updateBy,\n" +
            "    d.note = :note,\n" +
            "    d.docName = :docName,\n" +
            "    d.popup = :popup,\n" +
            "    d.type = :type,\n" +
            "    d.relationUnit = :relationUnit,\n" +
            "    d.relationUnitSec = :relationUnitSec,\n" +
            "    d.popupStart = :popupStart,\n" +
            "    d.popupEnd = :popupEnd\n" +
            "WHERE d.id = :keyId")
                int update(
            @Param("keyId") Long keyId,
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
    @Query("UPDATE Document d\n" +
            "SET d.docNo = :docNo,\n" +
            "    d.docType = :docType,\n" +
            "    d.docDate = :docDate,\n" +
            "    d.updateBy = :updateBy,\n" +
            "    d.note = :note,\n" +
            "    d.docName = :docName,\n" +
            "    d.popup = :popup,\n" +
            "    d.type = :type,\n" +
            "    d.relationUnit = :relationUnit,\n" +
            "    d.relationUnitSec = :relationUnitSec,\n" +
            "    d.popupStart = :popupStart,\n" +
            "    d.popupEnd = :popupEnd\n" +
            "WHERE d.id = :keyId")
    int updateNoFile(
            @Param("keyId") Long keyId,
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
