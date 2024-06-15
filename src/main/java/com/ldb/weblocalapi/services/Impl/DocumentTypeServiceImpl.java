package com.ldb.weblocalapi.services.Impl;

import com.ldb.weblocalapi.entities.DocType;
import com.ldb.weblocalapi.messages.response.DataResponse;
import com.ldb.weblocalapi.repositories.DocTypeRepository;
import com.ldb.weblocalapi.services.DocumentTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Service
public class DocumentTypeServiceImpl implements DocumentTypeService {
@Autowired
DocTypeRepository docTypeRepository;
    @Override
    public DataResponse getDocTypeLink(DocType docType, HttpServletRequest request) {
        String keyId= docType.getKeyId();
        log.info("show key:"+keyId);
        DataResponse dataResponse = new DataResponse();
        try {
            if(!keyId.equals("0")){
                dataResponse.setDataResponse(docTypeRepository.findByDocTypeFromId(keyId));
            }else {
                dataResponse.setDataResponse(docTypeRepository.findDocTypeAll());
            }
            if(dataResponse.getDataResponse() !=null){
                dataResponse.setStatus("00");
                dataResponse.setMessage("Success");
            }else {
                dataResponse.setStatus("05");
                dataResponse.setMessage("Data Not Found");
            }
        }catch (Exception ex){
            dataResponse.setStatus("05");
            dataResponse.setMessage("Data Not Found");
        }
        return dataResponse;
    }

    @Override
    public DataResponse storeDocTypeLink(DocType docType, HttpServletRequest request) {
        DataResponse dataResponse = new DataResponse();
        try{
            dataResponse.setDataResponse(docTypeRepository.save(docType));
            if(dataResponse.getDataResponse() !=null){
                dataResponse.setStatus("00");
                dataResponse.setMessage("Store DocType Success");
            }else {
                dataResponse.setStatus("05");
                dataResponse.setMessage("Can't DocType Success");
            }
        }catch (Exception e){
            dataResponse.setStatus("05");
            dataResponse.setMessage("Can't DocType Success");
            e.printStackTrace();
        }
        return dataResponse;
    }

    @Override
    public DataResponse updateDocTypeLink(DocType docType, HttpServletRequest request) {
        DataResponse dataResponse = new DataResponse();
        try{
            String docTypeName = docType.getDocTypeName();
            String status = docType.getStatus();
            String keyId = docType.getKeyId();
          // dataResponse.setDataResponse(null);
            dataResponse.setDataResponse(docTypeRepository.UpdateDataDocumentType(docTypeName,status,keyId));
            if(dataResponse.getDataResponse() !=null){
                dataResponse.setStatus("00");
                dataResponse.setMessage("Update DocType Success");
            }else {
                dataResponse.setStatus("05");
                dataResponse.setMessage("Can't Update DocType");
            }
        }catch (Exception e){
            dataResponse.setStatus("05");
            dataResponse.setMessage("Can't Update DocType");
            e.printStackTrace();
        }
        return dataResponse;
    }

    @Override
    public DataResponse delDocTypeLink(DocType docType, HttpServletRequest request) {
        DataResponse dataResponse = new DataResponse();
        try{
            String status = docType.getStatus();
            String keyId = docType.getKeyId();
            dataResponse.setDataResponse(docTypeRepository.ChangeStatus(status,keyId));
            if(dataResponse.getDataResponse() !=null){
                dataResponse.setStatus("00");
                dataResponse.setMessage("Update DocType Success");
            }else {
                dataResponse.setStatus("05");
                dataResponse.setMessage("Can't Update DocType");
            }
        }catch (Exception e){
            dataResponse.setStatus("05");
            dataResponse.setMessage("Can't Update DocType");
            e.printStackTrace();
        }
        return dataResponse;
    }
}
