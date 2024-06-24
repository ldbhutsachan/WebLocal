package com.ldb.weblocalapi.MediaUpload;

import com.ldb.weblocalapi.entities.Document;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public interface MediaUploadService {

    public String uploadMediaDoc(MultipartFile file, Document document,String originFile);
    public String uploadMediaDocUpdate(MultipartFile file, Document document,String originFile);
    public String uploadMedia(MultipartFile file);
}
