package com.ldb.weblocalapi.services.Impl;

import com.ldb.weblocalapi.Model.BranchReq;
import com.ldb.weblocalapi.Model.DocumentSecMenu;
import com.ldb.weblocalapi.entities.DocShareBandUnit;
import com.ldb.weblocalapi.entities.Document;
import com.ldb.weblocalapi.entities.DocumentSharing;
import com.ldb.weblocalapi.entities.Respone.DocumentRespone;
import com.ldb.weblocalapi.entities.Respone.DocumentSecMenuRespone;
import com.ldb.weblocalapi.entities.Respone.ReadDocument;
import com.ldb.weblocalapi.entities.Respone.UploadByUser;
import com.ldb.weblocalapi.messages.request.DocReq;
import com.ldb.weblocalapi.messages.request.RequestReportDate;
import com.ldb.weblocalapi.messages.response.DataResponse;
import com.ldb.weblocalapi.messages.response.LoginResponse;
import com.ldb.weblocalapi.repositories.*;
import com.ldb.weblocalapi.services.DocumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private DocumentServiceImpl documentSecMenuRepositoryTest;
//***check
    @Override
    public DataResponse documentList(DocumentRespone documentRespone, HttpServletRequest request) {
        String secCod = documentRespone.getKeyId();
        String saveBy = documentRespone.getSaveBy();
        log.info("keyId:"+saveBy);
        DataResponse dataResponse = new DataResponse();
        try {
            //***********************check keyId if not null ***************************
                dataResponse.setDataResponse(documentRepository.findDocAllNyUserName(saveBy));
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
    public DataResponse documentListByBandMenu(String secCode,BranchReq documentRespone) {
        DataResponse dataResponse = new DataResponse();
        if(documentRespone.getType().equals("IN")){
            documentRespone.setType("ຂາເຂົ້າ");
        } else if(documentRespone.getType().equals("OUT")){
            documentRespone.setType("ຂາອອກ");
        }else {
            documentRespone.setType("0");
        }
        try{
            if(documentRespone.getType().equals("0")){
                log.info("show All:");
                dataResponse.setDataResponse(documentSecMenuRepository.finBandMenuAll(secCode));
            }
            else if(documentRespone.getType().equals("ຂາເຂົ້າ")){
                log.info("show IN:");
                dataResponse.setDataResponse(documentSecMenuRepository.finBandMenuAllWithTypeIn(secCode,secCode));
            }
            else {
                log.info("show OUT:");
                dataResponse.setDataResponse(documentSecMenuRepository.finBandMenuAllWithTypeOut(secCode,secCode));
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
    public DataResponse documentListByBandMenuByDate(BranchReq docReq, HttpServletRequest request,String secCod) {
        DataResponse dataResponse = new DataResponse();
        //*******************************cehck data from q*****************************************************
        String conditionSecCodOut = "";
        String conditionSmallBand = "";
        String conditionBandCode = "";
        String conditionSecCodIn = "";
        String conditionDate = "";
        String conditionDocTypeNo = "";
        String conditionInputText = "";
        String bandCode = docReq.getBrandCode();
        String smallBandCode = docReq.getSmallBrandCode();
        String docTypeNo = docReq.getDocType();
        String inputText = docReq.getInBoxText();
        //********************************check date ******************************************
        String startDate = "";
        String endDate ="";
        String outputDateStart  = "";
        String outputDateEnd = "";
        if(startDate.isEmpty()){
             startDate = docReq.getStartDate();
             endDate = docReq.getEndDate();
        }else {
             startDate = docReq.getStartDate();
             endDate = docReq.getEndDate();
            String outputFormat = "dd/MM/yyyy";
            LocalDate date = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyyMMdd"));
             outputDateStart = date.format(DateTimeFormatter.ofPattern(outputFormat));
            LocalDate dateEnd = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyyMMdd"));
             outputDateEnd = dateEnd.format(DateTimeFormatter.ofPattern(outputFormat));
        }
        try{
            if(docReq.getType().equals("IN")){
                docReq.setType("ຂາເຂົ້າ");
            } else if(docReq.getType().equals("OUT")){
                docReq.setType("ຂາອອກ");
            }else {
                docReq.setType("0");
            }
            if(!bandCode.equals("0")){
                conditionBandCode = " AND RELATION_UNIT ='"+bandCode+"' ";
            }
            if(!smallBandCode.equals("0")){
                conditionSmallBand = " AND RELATION_UNIT_SEC ='"+smallBandCode+"' ";
            }
            if(!secCod.equals("")){
                conditionSecCodOut = " AND BRANCH_ID ='"+secCod+"' ";
            }
            if(!secCod.equals("")){
                conditionSecCodIn = " AND BRANCH_ID !='"+secCod+"' ";
            }
            if (!outputDateStart.equals("")) {
                conditionDate += " AND DOC_DATE >= '" + outputDateStart + "' AND DOC_DATE <= '" + outputDateEnd + "'";
            }
            if (!docTypeNo.equals("0")) {
                conditionDocTypeNo += " AND DOCTYPENO='" + docTypeNo + "' ";
            }
            if (!inputText.equals("")) {
                conditionInputText += " AND DOC_NO like '%" + inputText + "%' OR  DOCNAME like '%" + inputText + "%' ";
            }
            //===========================condition All ================================
            //**********************check small Band **********************************
            String countAllConditionAll = "";
            String countAllConditionIn = "";
            String countAllConditionOut = "";
            if(!smallBandCode.equals("0")){
                 countAllConditionAll =conditionSecCodOut+conditionDate+ conditionDocTypeNo+ conditionInputText+conditionBandCode+conditionSmallBand;
                //==========================condition IN=============================
                 countAllConditionIn =conditionSecCodIn+ conditionDate+ conditionDocTypeNo+ conditionInputText+conditionBandCode+conditionSmallBand;
                //==========================condition  OUT=============================
                 countAllConditionOut =conditionSecCodOut+ conditionDate+ conditionDocTypeNo+ conditionInputText+conditionBandCode+conditionSmallBand;
                if(docReq.getType().equals("0")){
                    log.info("show 01");
                    dataResponse.setDataResponse(documentSecMenuRepositoryTest.finBandMenuAllWithTypeConditionSmallBandALL(countAllConditionAll));
                }
                else if(docReq.getType().equals("ຂາເຂົ້າ")){
                    log.info("show 02 ຂາເຂົ້າ============");
                    dataResponse.setDataResponse(documentSecMenuRepositoryTest.finBandMenuAllWithTypeConditionSmallBandALL(countAllConditionIn));
                }
                else {
                    log.info("show 03 ຂາອອກ ==========");
                    dataResponse.setDataResponse(documentSecMenuRepositoryTest.finBandMenuAllWithTypeConditionSmallBandALL(countAllConditionOut));
                }
            }else {
                 countAllConditionAll =conditionSecCodOut+conditionDate+ conditionDocTypeNo+ conditionInputText+conditionBandCode;
                //==========================condition IN=============================
                 countAllConditionIn =conditionSecCodIn+ conditionDate+ conditionDocTypeNo+ conditionInputText+conditionBandCode;
                //==========================condition  OUT=============================
                 countAllConditionOut =conditionSecCodOut+ conditionDate+ conditionDocTypeNo+ conditionInputText+conditionBandCode;
                if(docReq.getType().equals("0")){
                    log.info("show 01");
                    dataResponse.setDataResponse(documentSecMenuRepositoryTest.finBandMenuAllWithTypeConditionALL(countAllConditionAll));
                }
                else if(docReq.getType().equals("ຂາເຂົ້າ")){
                    log.info("show 02 ຂາເຂົ້າ============");
                    dataResponse.setDataResponse(documentSecMenuRepositoryTest.finBandMenuAllWithTypeConditionALL(countAllConditionIn));
                }
                else {
                    log.info("show 03 ຂາອອກ ==========");
                    dataResponse.setDataResponse(documentSecMenuRepositoryTest.finBandMenuAllWithTypeConditionALL(countAllConditionOut));
                }
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

    //====================================for query ========================================================================
    @PersistenceContext
    private EntityManager entityManager;
    public List<DocumentSecMenuRespone> finBandMenuAllWithTypeConditionALL(String countAllCondition) {
        // Base query
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM V_BAND_MENU_COUNTER WHERE 1=1");
        // Append dynamic conditions
        if (countAllCondition != null && !countAllCondition.isEmpty()) {
            queryBuilder.append(countAllCondition);
        }
        queryBuilder.append(" ORDER BY ID DESC");
        // Create query
        Query query = entityManager.createNativeQuery(queryBuilder.toString(), DocumentSecMenuRespone.class);
        // Execute query
        return query.getResultList();
    }

    public List<DocumentSecMenuRespone> finBandMenuAllWithTypeConditionSmallBandALL(String countAllCondition) {
        // Base query
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM V_BAND_MENU_COUNT_SMALLCODE WHERE 1=1");
        // Append dynamic conditions
        if (countAllCondition != null && !countAllCondition.isEmpty()) {
            queryBuilder.append(countAllCondition);
        }
        queryBuilder.append(" ORDER BY ID DESC");
        // Create query
        Query query = entityManager.createNativeQuery(queryBuilder.toString(), DocumentSecMenuRespone.class);
        // Execute query
        return query.getResultList();
    }


    @Override
    public DataResponse documentListBySecCodeMenuByDate(BranchReq documentRespone, HttpServletRequest request) {
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
    public DataResponse HomeDocumentListByContion(RequestReportDate documentRespone, HttpServletRequest request,String secCod) {
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
                log.info("Q1:");
                log.info("D1:"+outputDateStart);
                log.info("D2:"+outputDateEnd);
                dataResponse.setDataResponse(documentRepository.findHomeDocAllByDateToEndDate(secCod,outputDateStart,outputDateEnd));
            }
            else if( !inFoBox.equals("") && !outputDateStart.equals("") || !outputDateEnd.equals("") ){
                log.info("Q2:");
                dataResponse.setDataResponse(documentRepository.findHomeDocAllByDateToEndDateText(secCod,outputDateStart,outputDateEnd,inFoBox));
            }else {
                log.info("Q2:");
                dataResponse.setDataResponse(documentRepository.findHomeDocAllByText(secCod,inFoBox));
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
    public DataResponse HomeDocumentList(String secCod) {
        DataResponse dataResponse = new DataResponse();
        try {
                dataResponse.setDataResponse(documentRepository.findDocAll(secCod));
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
        String saveBy = documentRespone.getSaveBy();
        try {
            //***********************check keyId if not null ***************************
            dataResponse.setDataResponse(documentRepository.findByDocFromKeyDocName(saveBy,documentRespone.getDocName(),documentRespone.getDocName()));
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
            List<String> docNos = documentSharingRepository.findDocNo(document.getDocNo());
            log.info("show size:"+docNos.size());
            //============================CHECK DOCNO IN DATABASE========================
            if(docNos.size() >= 1){
                dataResponse.setStatus(Constant.RESPONSE_CODE.DATA_NOT_FOUND);
                dataResponse.setMessage(Constant.RESPONSE_FAIL_MESSAGE.STORE_MSG_CHECK+" ເລກທີ: "+document.getDocNo());
            }else {
                dataResponse.setDataResponse(storeDocumentRepository.save(document));
                if (dataResponse.getDataResponse() != null) {
                    dataResponse.setStatus(Constant.RESPONSE_CODE.SUCCESS);
                    dataResponse.setMessage(Constant.RESPONSE_MESSAGE.STORE_MSG);
                } else {
                    dataResponse.setStatus(Constant.RESPONSE_CODE.DATA_NOT_FOUND);
                    dataResponse.setMessage(Constant.RESPONSE_FAIL_MESSAGE.STORE_MSG);
                }
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
        long keyId = document.getId();
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
                    keyId,
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
        long keyId = document.getId();
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
                    keyId,
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
                if (dataResponse.getDataResponse() != null) {
                    dataResponse.setStatus(Constant.RESPONSE_CODE.SUCCESS);
                    dataResponse.setMessage(Constant.RESPONSE_MESSAGE.STORE_MSG);
                } else {
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
    public DataResponse saveSharingDocumentYou(String secCode, String docNo) {
        DataResponse dataResponse = new DataResponse();
        try{
            String relationUnit = secCode;
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
            if (dataResponse.getDataResponse() != null) {
                dataResponse.setStatus(Constant.RESPONSE_CODE.SUCCESS);
                dataResponse.setMessage(Constant.RESPONSE_MESSAGE.STORE_MSG);
            } else {
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
@Override
public  DataResponse  SecCodeMenuByDateALL(DocReq documentRespone, HttpServletRequest request,String secCod) {
    DataResponse dataResponse = new DataResponse();
    if(documentRespone.getType().equals("IN")){
        documentRespone.setType("ຂາເຂົ້າ");
    } else if(documentRespone.getType().equals("OUT")){
        documentRespone.setType("ຂາອອກ");
    }else {
        documentRespone.setType("0");
    }
    try{
        log.info("show:"+documentRespone.getType());
        if(documentRespone.getType().equals("0")){
            dataResponse.setDataResponse(documentSecMenuRepository.finSecMenuAll(documentRespone.getCode()));
        }
        else if(documentRespone.getType().equals("ຂາເຂົ້າ")){
            dataResponse.setDataResponse(documentSecMenuRepository.finSecMenuAllWithTypeIn(secCod,documentRespone.getCode()));
        }
        else {
            dataResponse.setDataResponse(documentSecMenuRepository.finSecMenuAllWithTypeOut(secCod,documentRespone.getCode()));
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
public DataResponse SecCodeMenuByDate(DocReq docReq, HttpServletRequest request,String secCod) {
    DataResponse dataResponse = new DataResponse();
    //*******************************cehck data from q*****************************************************
    String conditionSecCodOut = "";
    String conditionSecCodIn = "";
    String conditionDate = "";
    String conditionCode = "";
    String conditionDocTypeNo = "";
    String conditionInputText = "";
    String code = docReq.getCode();
    String docTypeNo = docReq.getDocType();
    String inputText = docReq.getInBoxText();
    //********************************check date ******************************************
    String startDate = docReq.getStartDate();
    String endDate = docReq.getEndDate();
    String outputFormat = "dd/MM/yyyy";
    LocalDate date = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyyMMdd"));
    String outputDateStart = date.format(DateTimeFormatter.ofPattern(outputFormat));
    LocalDate dateEnd = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyyMMdd"));
    String outputDateEnd = dateEnd.format(DateTimeFormatter.ofPattern(outputFormat));
    try{
        if(docReq.getType().equals("IN")){
            docReq.setType("ຂາເຂົ້າ");
        } else if(docReq.getType().equals("OUT")){
            docReq.setType("ຂາອອກ");
        }else {
            docReq.setType("0");
        }
        if(!secCod.equals("")){
            conditionSecCodOut = " AND BRANCH_ID ='"+secCod+"' ";
        }
        if(!secCod.equals("")){
            conditionSecCodIn = " AND BRANCH_ID !='"+secCod+"' ";
        }
        if (!outputDateStart.equals("")) {
            conditionDate += " AND DOC_DATE >= '" + outputDateStart + "' AND DOC_DATE <= '" + outputDateEnd + "'";
        }
        if (!code.equals("0")) {
            conditionCode += " AND RELATION_UNIT='" + code + "' ";
        }
        if (!docTypeNo.equals("0")) {
            conditionDocTypeNo += " AND DOCTYPENO='" + docTypeNo + "' ";
        }
        if (!inputText.equals("")) {
            conditionInputText += " AND DOC_NO like '%" + inputText + "%' OR  DOCNAME like '%" + inputText + "%' ";
        }
        //===========================condition All ================================
        String countAllConditionAll =conditionDate+ conditionCode+ conditionDocTypeNo+ conditionInputText;

        //==========================condition IN=============================
        String countAllConditionIn =conditionSecCodIn+ conditionDate+ conditionCode+ conditionDocTypeNo+ conditionInputText;

        //==========================condition  OUT=============================
        String countAllConditionOut =conditionSecCodOut+ conditionDate+ conditionCode+ conditionDocTypeNo+ conditionInputText;

        if(docReq.getType().equals("0")){
            log.info("show 01");
          dataResponse.setDataResponse(documentSecMenuRepositoryTest.finSecMenuAllWithTypeConditionALL(countAllConditionAll));
        }
        else if(docReq.getType().equals("ຂາເຂົ້າ")){
            log.info("show 02 ຂາເຂົ້າ============");
            dataResponse.setDataResponse(documentSecMenuRepositoryTest.finSecMenuAllWithTypeConditionALL(countAllConditionIn));
        }
        else {
            log.info("show 03 ຂາອອກ ==========");
            dataResponse.setDataResponse(documentSecMenuRepositoryTest.finSecMenuAllWithTypeConditionALL(countAllConditionOut));
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

//====================================for query ========================================================================
    public List<DocumentSecMenuRespone> finSecMenuAllWithTypeConditionALL(String countAllCondition) {
        // Base query
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM V_SECTION_MENU_COUNTER WHERE 1=1");
        // Append dynamic conditions
        if (countAllCondition != null && !countAllCondition.isEmpty()) {
            queryBuilder.append(countAllCondition);
        }
        queryBuilder.append(" ORDER BY ID DESC");
        // Create query
        Query query = entityManager.createNativeQuery(queryBuilder.toString(), DocumentSecMenuRespone.class);
        // Execute query
        return query.getResultList();
    }

}

