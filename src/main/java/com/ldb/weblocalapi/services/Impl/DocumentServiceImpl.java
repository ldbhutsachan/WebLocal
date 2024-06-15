package com.ldb.weblocalapi.services.Impl;

import com.ldb.weblocalapi.Model.BranchReq;
import com.ldb.weblocalapi.entities.DocShareBandUnit;
import com.ldb.weblocalapi.entities.Document;
import com.ldb.weblocalapi.entities.DocumentSharing;
import com.ldb.weblocalapi.entities.Respone.DocumentRespone;
import com.ldb.weblocalapi.entities.Respone.ReadDocument;
import com.ldb.weblocalapi.entities.Respone.UploadByUser;
import com.ldb.weblocalapi.messages.request.DocReq;
import com.ldb.weblocalapi.messages.request.RequestReportDate;
import com.ldb.weblocalapi.messages.response.DataResponse;
import com.ldb.weblocalapi.repositories.*;
import com.ldb.weblocalapi.services.DocumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import com.ldb.weblocalapi.utils.Constant;

@Slf4j
@Service
public class DocumentServiceImpl implements DocumentService {
@Autowired
    DocumentSecMenuRepository documentSecMenuRepository;
    @Autowired
    DocumentRepository documentRepository;
    @Autowired
    ReadDocRepository readDocRepository;
    @Autowired
    UploadByUserRepository uploadByUserRepository;
    @Autowired
    ViewSaveByUserRepository  viewSaveByUserRepository;
    @Autowired
    StoreDocumentRepository storeDocumentRepository;
    @Autowired
    DocumentSharingRepository documentSharingRepository;

    @Autowired
    RelationUnitRepository relationUnitRepository;

    @Autowired
    DocSecMenuServiceImpl docSecMenuService;

    @Override
    public DataResponse documentList(DocumentRespone documentRespone, HttpServletRequest request) {
        String keyId = documentRespone.getKeyId();
        log.info("keyId:"+keyId);
        DataResponse dataResponse = new DataResponse();
        try {
            //***********************check keyId if not null ***************************
                dataResponse.setDataResponse(documentRepository.findDocAll());
            if(dataResponse.getDataResponse() !=null){
                dataResponse.setStatus(Constant.RESPONSE_CODE.SUCCESS);
                dataResponse.setMessage(Constant.RESPONSE_MESSAGE.SUCCESS_MSG);
            }else {
                dataResponse.setStatus(Constant.RESPONSE_CODE.DATA_NOT_FOUND);
                dataResponse.setMessage(Constant.RESPONSE_MESSAGE.DATA_NOT_FOUND_MSG);
            }
        }catch (Exception e){
            dataResponse.setStatus(Constant.RESPONSE_CODE.DATA_NOT_FOUND);
            dataResponse.setMessage(Constant.RESPONSE_MESSAGE.DATA_NOT_FOUND_MSG);
            e.printStackTrace();
        }
        return dataResponse;
    }

    @Override
    public DataResponse documentListBySecCodeMenu(DocReq documentRespone, HttpServletRequest request) {
        String code = documentRespone.getCode();
        String type = documentRespone.getType();
        if(type.equals("IN")){
            type = "ຂາເຂົ້າ";
        }else if(type.equals("OUT")) {
            type = "ຂາອອກ";
        }else {
            type = "0";
        }
        log.info("show type:"+type);
        DataResponse dataResponse = new DataResponse();
        try {
            //***********************check documentListBySecCodeMenu if not null ***************************
            if(type.equals("0")){
                dataResponse.setDataResponse(documentRepository.findDocAllDocumentListBySecCodeMenuByCode(code));
            }else {
                dataResponse.setDataResponse(documentRepository.findDocAllDocumentListBySecCodeMenuByTypeCode(type,code));
            }
            if(dataResponse.getDataResponse() !=null){
                dataResponse.setStatus(Constant.RESPONSE_CODE.SUCCESS);
                dataResponse.setMessage(Constant.RESPONSE_MESSAGE.SUCCESS_MSG);
            }else {
                dataResponse.setStatus(Constant.RESPONSE_CODE.DATA_NOT_FOUND);
                dataResponse.setMessage(Constant.RESPONSE_MESSAGE.DATA_NOT_FOUND_MSG);
            }
        }catch (Exception e){
            dataResponse.setStatus(Constant.RESPONSE_CODE.DATA_NOT_FOUND);
            dataResponse.setMessage(Constant.RESPONSE_MESSAGE.DATA_NOT_FOUND_MSG);
            e.printStackTrace();
        }
        return dataResponse;
    }

    @Override
    public DataResponse documentListByBandMenu() {
        DataResponse dataResponse = new DataResponse();
        try {
            dataResponse.setDataResponse(docSecMenuService.findDocAllDocumentListByBandAll());
            if(dataResponse.getDataResponse() !=null){
                dataResponse.setStatus(Constant.RESPONSE_CODE.SUCCESS);
                dataResponse.setMessage(Constant.RESPONSE_MESSAGE.SUCCESS_MSG);
            }else {
                dataResponse.setStatus(Constant.RESPONSE_CODE.DATA_NOT_FOUND);
                dataResponse.setMessage(Constant.RESPONSE_MESSAGE.DATA_NOT_FOUND_MSG);
            }
        }catch (Exception e){
            dataResponse.setStatus(Constant.RESPONSE_CODE.DATA_NOT_FOUND);
            dataResponse.setMessage(Constant.RESPONSE_MESSAGE.DATA_NOT_FOUND_MSG);
            e.printStackTrace();
        }
        return dataResponse;
    }

    @Override
    public DataResponse documentListByBandMenuByDate(BranchReq documentRespone, HttpServletRequest request) {
        DataResponse dataResponse = new DataResponse();
        if(documentRespone.getType().equals("IN")){
            documentRespone.setType("ຂາເຂົ້າ");
        } else if(documentRespone.getType().equals("OUT")){
            documentRespone.setType("ຂາອອກ");
        }else {
            documentRespone.setType("0");
        }
        try{
            dataResponse.setDataResponse(docSecMenuService.findDocAllDocumentListByBranchMenu(documentRespone));
            if(dataResponse.getDataResponse() !=null){
                dataResponse.setStatus(Constant.RESPONSE_CODE.SUCCESS);
                dataResponse.setMessage(Constant.RESPONSE_MESSAGE.SUCCESS_MSG);
            }else {
                dataResponse.setStatus(Constant.RESPONSE_CODE.DATA_NOT_FOUND);
                dataResponse.setMessage(Constant.RESPONSE_MESSAGE.DATA_NOT_FOUND_MSG);
            }
        }catch (Exception e){
            dataResponse.setStatus(Constant.RESPONSE_CODE.DATA_NOT_FOUND);
            dataResponse.setMessage(Constant.RESPONSE_MESSAGE.DATA_NOT_FOUND_MSG);
            e.printStackTrace();
        }
        return dataResponse;
    }

    @Override
    public DataResponse documentListBySecCodeMenuByDate(DocReq documentRespone, HttpServletRequest request) {
       DataResponse dataResponse = new DataResponse();
       if(documentRespone.getType().equals("IN")){
           documentRespone.setType("ຂາເຂົ້າ");
       } else if(documentRespone.getType().equals("OUT")){
           documentRespone.setType("ຂາອອກ");
       }else {
           documentRespone.setType("0");
       }
      try{
          dataResponse.setDataResponse(docSecMenuService.findDocAllDocumentListBySecCodeMenuByDateTotDateByDocTypeAll(documentRespone));
            if(dataResponse.getDataResponse() !=null){
                dataResponse.setStatus(Constant.RESPONSE_CODE.SUCCESS);
                dataResponse.setMessage(Constant.RESPONSE_MESSAGE.SUCCESS_MSG);
            }else {
                dataResponse.setStatus(Constant.RESPONSE_CODE.DATA_NOT_FOUND);
                dataResponse.setMessage(Constant.RESPONSE_MESSAGE.DATA_NOT_FOUND_MSG);
            }
        }catch (Exception e){
            dataResponse.setStatus(Constant.RESPONSE_CODE.DATA_NOT_FOUND);
            dataResponse.setMessage(Constant.RESPONSE_MESSAGE.DATA_NOT_FOUND_MSG);
            e.printStackTrace();
        }
        return dataResponse;
    }

    @Override
    public DataResponse HomeDocumentListByContion(RequestReportDate documentRespone, HttpServletRequest request) {
        String startDate = documentRespone.getStartDate();
        String endDate = documentRespone.getEndDate();
        String inFoBox = documentRespone.getInFoBox();
        String outputFormat = "dd/MM/yyyy";
        LocalDate date = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyyMMdd"));
        String outputDateStart = date.format(DateTimeFormatter.ofPattern(outputFormat));
        LocalDate dateEnd = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyyMMdd"));
        String outputDateEnd = dateEnd.format(DateTimeFormatter.ofPattern(outputFormat));
        DataResponse dataResponse = new DataResponse();
        try {
            //***********************check keyId if not null ***************************
            if(inFoBox.equals("") && !outputDateStart.equals("") && !outputDateEnd.equals("")){
                dataResponse.setDataResponse(documentRepository.findHomeDocAllByDateToEndDate(outputDateStart,outputDateEnd));
            }
            else if( !inFoBox.equals("") && !outputDateStart.equals("") || !outputDateEnd.equals("") ){
                dataResponse.setDataResponse(documentRepository.findHomeDocAllByDateToEndDateText(outputDateStart,outputDateEnd,inFoBox));
            }else {
                dataResponse.setDataResponse(documentRepository.findHomeDocAllByText(inFoBox));
            }
            if(dataResponse.getDataResponse() !=null){
                dataResponse.setStatus(Constant.RESPONSE_CODE.SUCCESS);
                dataResponse.setMessage(Constant.RESPONSE_MESSAGE.SUCCESS_MSG);
            }else {
                dataResponse.setStatus(Constant.RESPONSE_CODE.DATA_NOT_FOUND);
                dataResponse.setMessage(Constant.RESPONSE_MESSAGE.DATA_NOT_FOUND_MSG);
            }
        }catch (Exception e){
            dataResponse.setStatus(Constant.RESPONSE_CODE.DATA_NOT_FOUND);
            dataResponse.setMessage(Constant.RESPONSE_MESSAGE.DATA_NOT_FOUND_MSG);
            e.printStackTrace();
        }
        return dataResponse;
    }

    @Override
    public DataResponse HomeDocumentList() {
        DataResponse dataResponse = new DataResponse();
        try {
                dataResponse.setDataResponse(documentRepository.findDocAll());
            if(dataResponse.getDataResponse() !=null){
                dataResponse.setStatus(Constant.RESPONSE_CODE.SUCCESS);
                dataResponse.setMessage(Constant.RESPONSE_MESSAGE.SUCCESS_MSG);
            }else {
                dataResponse.setStatus(Constant.RESPONSE_CODE.DATA_NOT_FOUND);
                dataResponse.setMessage(Constant.RESPONSE_MESSAGE.DATA_NOT_FOUND_MSG);
            }
        }catch (Exception e){
            dataResponse.setStatus(Constant.RESPONSE_CODE.DATA_NOT_FOUND);
            dataResponse.setMessage(Constant.RESPONSE_MESSAGE.DATA_NOT_FOUND_MSG);
            e.printStackTrace();
        }
        return dataResponse;
    }

    @Override
    public DataResponse documentListByTextBox(DocumentRespone documentRespone, HttpServletRequest request) {
        DataResponse dataResponse = new DataResponse();
        try {
            //***********************check keyId if not null ***************************
            dataResponse.setDataResponse(documentRepository.findByDocFromKeyDocName(documentRespone.getDocName(),documentRespone.getDocName()));
            if(dataResponse.getDataResponse() !=null){
                dataResponse.setStatus(Constant.RESPONSE_CODE.SUCCESS);
                dataResponse.setMessage(Constant.RESPONSE_MESSAGE.SUCCESS_MSG);
            }else {
                dataResponse.setStatus(Constant.RESPONSE_CODE.DATA_NOT_FOUND);
                dataResponse.setMessage(Constant.RESPONSE_MESSAGE.DATA_NOT_FOUND_MSG);
            }
        }catch (Exception e){
            dataResponse.setStatus(Constant.RESPONSE_CODE.DATA_NOT_FOUND);
            dataResponse.setMessage(Constant.RESPONSE_MESSAGE.DATA_NOT_FOUND_MSG);
            e.printStackTrace();
        }
        return dataResponse;
    }

    @Override
    public DataResponse ReadDocDocumentByDocNo(ReadDocument document, HttpServletRequest request) {
        String docNo = document.getDocNo();
        DataResponse dataResponse = new DataResponse();
        try {
            //***********************check docNo if not null ***************************
            dataResponse.setDataResponse(readDocRepository.ReadDocDocumentByDocNo(docNo));
            if(dataResponse.getDataResponse() !=null){
                dataResponse.setStatus(Constant.RESPONSE_CODE.SUCCESS);
                dataResponse.setMessage(Constant.RESPONSE_MESSAGE.SUCCESS_MSG);
            }else {
                dataResponse.setStatus(Constant.RESPONSE_CODE.DATA_NOT_FOUND);
                dataResponse.setMessage(Constant.RESPONSE_MESSAGE.DATA_NOT_FOUND_MSG);
            }
        }catch (Exception e){
            dataResponse.setStatus(Constant.RESPONSE_CODE.DATA_NOT_FOUND);
            dataResponse.setMessage(Constant.RESPONSE_MESSAGE.DATA_NOT_FOUND_MSG);
            e.printStackTrace();
        }
        return dataResponse;
    }

    @Override
    public DataResponse getUserSaveInfo(UploadByUser uploadByUser, HttpServletRequest request) {
        String saveBy = uploadByUser.getSaveBy();
        String docNo = uploadByUser.getDocNo();
        DataResponse dataResponse = new DataResponse();
        try {
            //***********************check docNo if not null ***************************
            dataResponse.setDataResponse(uploadByUserRepository.UploadByUser(saveBy,docNo));
            if(dataResponse.getDataResponse() !=null){
                dataResponse.setStatus(Constant.RESPONSE_CODE.SUCCESS);
                dataResponse.setMessage(Constant.RESPONSE_MESSAGE.SUCCESS_MSG);
            }else {
                dataResponse.setStatus(Constant.RESPONSE_CODE.DATA_NOT_FOUND);
                dataResponse.setMessage(Constant.RESPONSE_MESSAGE.DATA_NOT_FOUND_MSG);
            }
        }catch (Exception e){
            dataResponse.setStatus(Constant.RESPONSE_CODE.DATA_NOT_FOUND);
            dataResponse.setMessage(Constant.RESPONSE_MESSAGE.DATA_NOT_FOUND_MSG);
            e.printStackTrace();
        }
        return dataResponse;
    }
    @Override
    public DataResponse save(Document document) {
        DataResponse dataResponse = new DataResponse();
        try{
            dataResponse.setDataResponse(storeDocumentRepository.save(document));
            if(dataResponse.getDataResponse() !=null){
                dataResponse.setStatus(Constant.RESPONSE_CODE.SUCCESS);
                dataResponse.setMessage(Constant.RESPONSE_MESSAGE.STORE_MSG);
            }else {
                dataResponse.setStatus(Constant.RESPONSE_CODE.DATA_NOT_FOUND);
                dataResponse.setMessage(Constant.RESPONSE_FAIL_MESSAGE.STORE_MSG);
            }
        }catch (Exception e){
            dataResponse.setStatus(Constant.RESPONSE_CODE.DATA_NOT_FOUND);
            dataResponse.setMessage(Constant.RESPONSE_FAIL_MESSAGE.STORE_MSG);
            e.printStackTrace();
        }
        return dataResponse;
    }
    @Override
    public DataResponse update(Document document) {
        DataResponse dataResponse = new DataResponse();
        String docNo = document.getDocNo();
        String docType =document.getDocType();
        String docPath = document.getDocPath();
        String docDate = document.getDocDate();
        String updateBy = document.getUpdateBy();
        String note = document.getNote();
        String docName = document.getDocName();
        String popup = document.getPopup();
        String type = document.getType();
        String relationUnit = document.getRelationUnit();
        String relationUnitSec = document.getRelationUnitSec();
        String popupStart = document.getPopupStart();
        String popupEnd = document.getPopupEnd();
        try{
            dataResponse.setDataResponse(storeDocumentRepository.update(
                     docNo,
                     docType,
                     docPath,
                     docDate,
                     updateBy,
                     note,
                     docName,
                     popup,
                     type,
                     relationUnit,
                     relationUnitSec,
                     popupStart,
                     popupEnd));
            if(dataResponse.getDataResponse() !=null){
                dataResponse.setStatus(Constant.RESPONSE_CODE.SUCCESS);
                dataResponse.setMessage(Constant.RESPONSE_MESSAGE.UPDATE_MSG);
            }else {
                dataResponse.setStatus(Constant.RESPONSE_CODE.DATA_NOT_FOUND);
                dataResponse.setMessage(Constant.RESPONSE_FAIL_MESSAGE.UPDATE_MSG);
            }
        }catch (Exception e){
            dataResponse.setStatus(Constant.RESPONSE_CODE.DATA_NOT_FOUND);
            dataResponse.setMessage(Constant.RESPONSE_FAIL_MESSAGE.UPDATE_MSG);
            e.printStackTrace();
        }
        return dataResponse;
    }
    @Override
    public DataResponse updateNoFile(Document document) {
        DataResponse dataResponse = new DataResponse();
        String docNo = document.getDocNo();
        String docType =document.getDocType();
        String docDate = document.getDocDate();
        String updateBy = document.getUpdateBy();
        String note = document.getNote();
        String docName = document.getDocName();
        String popup = document.getPopup();
        String type = document.getType();
        String relationUnit = document.getRelationUnit();
        String relationUnitSec = document.getRelationUnitSec();
        String popupStart = document.getPopupStart();
        String popupEnd = document.getPopupEnd();
        try{
            dataResponse.setDataResponse(storeDocumentRepository.updateNoFile(
                     docNo,
                     docType,
                     docDate,
                     updateBy,
                     note,
                     docName,
                     popup,
                     type,
                     relationUnit,
                     relationUnitSec,
                     popupStart,
                     popupEnd));
            if(dataResponse.getDataResponse() !=null){
                dataResponse.setStatus(Constant.RESPONSE_CODE.SUCCESS);
                dataResponse.setMessage(Constant.RESPONSE_MESSAGE.UPDATE_MSG);
            }else {
                dataResponse.setStatus(Constant.RESPONSE_CODE.DATA_NOT_FOUND);
                dataResponse.setMessage(Constant.RESPONSE_FAIL_MESSAGE.UPDATE_MSG);
            }
        }catch (Exception e){
            dataResponse.setStatus(Constant.RESPONSE_CODE.DATA_NOT_FOUND);
            dataResponse.setMessage(Constant.RESPONSE_FAIL_MESSAGE.UPDATE_MSG);
            e.printStackTrace();
        }
        return dataResponse;
    }
    @Override
    public DataResponse delDocumentFile(Document document) {
        DataResponse dataResponse = new DataResponse();
        String docNo = document.getDocNo();
        try{
            dataResponse.setDataResponse(storeDocumentRepository.delDocumentFile(document.getDocNo()));
            //*****************DOC_SHARING
            documentSharingRepository.update(docNo);
            //*****************DOC_SHARING_BAND_UNIT
            relationUnitRepository.update(docNo);
            if(dataResponse.getDataResponse() !=null){
                dataResponse.setStatus(Constant.RESPONSE_CODE.SUCCESS);
                dataResponse.setMessage(Constant.RESPONSE_MESSAGE.DEL_MSG);
            }else {
                dataResponse.setStatus(Constant.RESPONSE_CODE.DATA_NOT_FOUND);
                dataResponse.setMessage(Constant.RESPONSE_FAIL_MESSAGE.DEL_MSG);
            }
        }catch (Exception e){
            dataResponse.setStatus(Constant.RESPONSE_CODE.DATA_NOT_FOUND);
            dataResponse.setMessage(Constant.RESPONSE_FAIL_MESSAGE.DEL_MSG);
            e.printStackTrace();
        }
        return dataResponse;
    }

    @Override
    public DataResponse upDateSharingDocument(String relationUnitReq, String docNo) {
        DataResponse dataResponse = new DataResponse();
        try{
            String relationUnit = relationUnitReq;
            String trimmedStr = relationUnit.trim();
            String[] relationUnitArray = trimmedStr.split(",");
            List<Integer> savedDocumentSharing = new ArrayList<>();
            for (String relation : relationUnitArray) {
                DocumentSharing documentSharing = new DocumentSharing();
                documentSharing.setRelationUnit(relation.trim());
                documentSharing.setDocNo(docNo.trim());
                savedDocumentSharing.add(documentSharingRepository.update(docNo));
            }
            dataResponse.setDataResponse(savedDocumentSharing);
            if(dataResponse.getDataResponse() !=null){
                dataResponse.setStatus(Constant.RESPONSE_CODE.SUCCESS);
                dataResponse.setMessage(Constant.RESPONSE_MESSAGE.STORE_MSG);
            }else {
                dataResponse.setStatus(Constant.RESPONSE_CODE.DATA_NOT_FOUND);
                dataResponse.setMessage(Constant.RESPONSE_FAIL_MESSAGE.STORE_MSG);
            }
        }catch (Exception e){
            dataResponse.setStatus(Constant.RESPONSE_CODE.DATA_NOT_FOUND);
            dataResponse.setMessage(Constant.RESPONSE_FAIL_MESSAGE.STORE_MSG);
            e.printStackTrace();
        }
        return dataResponse;
    }

    @Override
    public DataResponse upDateSharingDocumentSec(String docNo) {
        DataResponse dataResponse = new DataResponse();
        try{
             relationUnitRepository.update(docNo);

        }catch (Exception e){
            dataResponse.setStatus("05");
            dataResponse.setMessage("Can't Document Success");
            e.printStackTrace();
        }
        return dataResponse;
    }

    @Override
    public DataResponse saveSharingDocument(String relationUnitReq,String docNo) {
        DataResponse dataResponse = new DataResponse();
        try{
            String relationUnit = relationUnitReq;
            String trimmedStr = relationUnit.trim();
            String[] relationUnitArray = trimmedStr.split(",");
            List<DocumentSharing> savedDocumentSharing = new ArrayList<>();
            for (String relation : relationUnitArray) {
                DocumentSharing documentSharing = new DocumentSharing();
                documentSharing.setRelationUnit(relation.trim());
                documentSharing.setDocNo(docNo.trim());
                documentSharing.setCreateDate(new Date());
                savedDocumentSharing.add(documentSharingRepository.save(documentSharing));
            }

            dataResponse.setDataResponse(savedDocumentSharing);
            if(dataResponse.getDataResponse() !=null){
                dataResponse.setStatus(Constant.RESPONSE_CODE.SUCCESS);
                dataResponse.setMessage(Constant.RESPONSE_MESSAGE.STORE_MSG);
            }else {
                dataResponse.setStatus(Constant.RESPONSE_CODE.DATA_NOT_FOUND);
                dataResponse.setMessage(Constant.RESPONSE_FAIL_MESSAGE.STORE_MSG);
            }
        }catch (Exception e){
            dataResponse.setStatus(Constant.RESPONSE_CODE.DATA_NOT_FOUND);
            dataResponse.setMessage(Constant.RESPONSE_FAIL_MESSAGE.STORE_MSG);
            e.printStackTrace();
        }
        return dataResponse;
    }
    @Override
    public DataResponse saveSharingDocumentSec(String relationUnitSec,String docNo) {
        DataResponse dataResponse = new DataResponse();
        try{
            String relationUnit = relationUnitSec;
            String trimmedStr = relationUnit.trim();
            String[] relationUnitArray = trimmedStr.split(",");
            List<DocShareBandUnit> savedDocumentSharing = new ArrayList<>();
            for (String relation : relationUnitArray) {
                DocShareBandUnit documentSharing = new DocShareBandUnit();
                documentSharing.setRelationUnitSec(relation.trim());
                documentSharing.setDocNo(docNo.trim());
                documentSharing.setCreateDate(new Date());
                savedDocumentSharing.add(relationUnitRepository.save(documentSharing));
            }

            dataResponse.setDataResponse(savedDocumentSharing);
            if(dataResponse.getDataResponse() !=null){
                dataResponse.setStatus(Constant.RESPONSE_CODE.SUCCESS);
                dataResponse.setMessage(Constant.RESPONSE_MESSAGE.STORE_MSG);
            }else {
                dataResponse.setStatus(Constant.RESPONSE_CODE.DATA_NOT_FOUND);
                dataResponse.setMessage(Constant.RESPONSE_FAIL_MESSAGE.STORE_MSG);
            }
        }catch (Exception e){
            dataResponse.setStatus(Constant.RESPONSE_CODE.DATA_NOT_FOUND);
            dataResponse.setMessage(Constant.RESPONSE_FAIL_MESSAGE.STORE_MSG);
            e.printStackTrace();
        }
        return dataResponse;
    }

//    public int saveSharingDoBranchNoarray(Document document) {
//        log.info("==========share data to User==========");
//        String ListBranch = document.getRelationUnit();
//        SQL="insert into DOC_SHARING (DOC_TYPE,SHAREBYBRANCH,CREATE_DATE,SESSION_TYPE,SES_STATUS) values(?,?,sysdate,?,'U')";
//        IADOCJdbcTemplate.update(SQL,
//                documentReq.getDocNo(),
//                ListBranch,
//                documentReq.getSharingType());
//        return 1;
//    }
}
