package com.ldb.weblocalapi.services;

import com.ldb.weblocalapi.entities.Read;
import com.ldb.weblocalapi.entities.Respone.ReadDocument;
import com.ldb.weblocalapi.messages.response.DataResponse;

public interface ReadDocService {
    public DataResponse storeSaveRead(Read readDocument);
}
