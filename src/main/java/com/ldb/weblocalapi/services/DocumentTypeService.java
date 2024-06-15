package com.ldb.weblocalapi.services;

import com.ldb.weblocalapi.entities.DocType;
import com.ldb.weblocalapi.messages.response.DataResponse;

import javax.servlet.http.HttpServletRequest;

public interface DocumentTypeService {
    public DataResponse getDocTypeLink(DocType docType, HttpServletRequest request);
    public DataResponse storeDocTypeLink(DocType docType, HttpServletRequest request);
    public DataResponse updateDocTypeLink(DocType docType, HttpServletRequest request);
    public DataResponse delDocTypeLink(DocType docType, HttpServletRequest request);
}
