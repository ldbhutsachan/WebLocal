package com.ldb.weblocalapi.services;

import com.ldb.weblocalapi.entities.DisplayLink;
import com.ldb.weblocalapi.messages.response.DataResponse;

import javax.servlet.http.HttpServletRequest;

public interface DisplayLinkService {
    public DataResponse getDisPlayLink(DisplayLink displayLink, HttpServletRequest request);
    public DataResponse getDisPlayLinkByName(DisplayLink displayLink, HttpServletRequest request);
    public DataResponse storeLink(DisplayLink displayLink, HttpServletRequest request);
    public DataResponse updateLinkFile(DisplayLink displayLink, HttpServletRequest request);
    public DataResponse updateDisplayLinkNoFile(DisplayLink displayLink, HttpServletRequest request);
    public DataResponse delLink(DisplayLink displayLink, HttpServletRequest request);
}
