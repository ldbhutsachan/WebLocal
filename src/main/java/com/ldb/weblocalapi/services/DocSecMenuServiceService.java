package com.ldb.weblocalapi.services;

import com.ldb.weblocalapi.Model.BranchReq;
import com.ldb.weblocalapi.Model.DocumentSecMenu;
import com.ldb.weblocalapi.messages.request.DocReq;

import java.util.List;

public interface DocSecMenuServiceService {
    List<DocumentSecMenu> findDocAllDocumentListBySecCodeMenuByDateTotDateByDocTypeAll(DocReq docReq);
    List<DocumentSecMenu> findDocAllDocumentListByBranchMenu(BranchReq docReq);
    public List<DocumentSecMenu> findDocAllDocumentListByBandAll();
}
