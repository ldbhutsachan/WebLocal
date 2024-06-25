package com.ldb.weblocalapi.services;

import com.ldb.weblocalapi.Model.BranchReq;
import com.ldb.weblocalapi.Model.DocumentSecMenu;
import com.ldb.weblocalapi.entities.Respone.DocumentRespone;
import com.ldb.weblocalapi.messages.request.DocReq;
import com.ldb.weblocalapi.messages.response.DataResponse;

import java.util.List;

public interface DocSecMenuServiceService {

    List<DocumentSecMenu> findDocAllDocumentListByBranchMenu(BranchReq docReq);
    public List<DocumentSecMenu> findDocAllDocumentListByBandAll(String secCode);
    public List<DocumentSecMenu> findSecMenu(DocReq docReq);
}
