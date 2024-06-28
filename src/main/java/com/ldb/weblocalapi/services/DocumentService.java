package com.ldb.weblocalapi.services;

import com.ldb.weblocalapi.Model.BranchReq;
import com.ldb.weblocalapi.Model.DocumentSecMenu;
import com.ldb.weblocalapi.entities.Document;
import com.ldb.weblocalapi.entities.Respone.DocumentRespone;
import com.ldb.weblocalapi.entities.Respone.ReadDocument;
import com.ldb.weblocalapi.entities.Respone.UploadByUser;
import com.ldb.weblocalapi.messages.request.DocReq;
import com.ldb.weblocalapi.messages.request.RequestReportDate;
import com.ldb.weblocalapi.messages.response.DataResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
public interface DocumentService {
    public DataResponse documentList(DocumentRespone documentRespone, HttpServletRequest request);

    public DataResponse documentListByBandMenu(String secCode,BranchReq req);
    //****
    public DataResponse documentListByBandMenuByDate(BranchReq documentRespone, HttpServletRequest request);
    public DataResponse SecCodeMenuByDate(DocReq documentRespone, HttpServletRequest request);
    public DataResponse  SecCodeMenuByDateALL(DocReq documentRespone, HttpServletRequest request);

    public DataResponse documentListBySecCodeMenuByDate(BranchReq documentRespone, HttpServletRequest request);
    public DataResponse HomeDocumentListByContion(RequestReportDate documentRespone, HttpServletRequest request);
    public DataResponse HomeDocumentList(String secCod);
    public DataResponse documentListByTextBox(DocumentRespone documentRespone, HttpServletRequest request);
    public DataResponse ReadDocDocumentByDocNo(ReadDocument document, HttpServletRequest request);
    public DataResponse getUserSaveInfo(UploadByUser uploadByUser, HttpServletRequest request);
    public DataResponse save(Document document);
    public DataResponse update(Document document);
    public DataResponse updateNoFile(Document document);
    public DataResponse delDocumentFile(Document document);
    public DataResponse upDateSharingDocument(String docNo, String relationUnit);

    public DataResponse upDateSharingDocumentSec(String docNo);

    public DataResponse saveSharingDocument(String relationUnit,String docNo);
    public DataResponse saveSharingDocumentSec(String relationUnitSec,String docNo);

}
