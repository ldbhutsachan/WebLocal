package com.ldb.weblocalapi.services.Impl;

import com.ldb.weblocalapi.entities.DisplayLink;
import com.ldb.weblocalapi.messages.response.DataResponse;
import com.ldb.weblocalapi.repositories.DisplayLinkRepository;
import com.ldb.weblocalapi.services.DisplayLinkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
@Slf4j
@Service
public class DisplayLinkServiceImpl implements DisplayLinkService {
    @Autowired
    DisplayLinkRepository displayLinkRepository;

    @Override
    public DataResponse getDisPlayLink(DisplayLink displayLink, HttpServletRequest request) {
        String keyId= displayLink.getKeyId();
        String name= displayLink.getName();
        log.info("show key:"+keyId);
        DataResponse dataResponse = new DataResponse();
        try {
            if(!keyId.equals("0")){
                dataResponse.setDataResponse(displayLinkRepository.findByDisPlayFromId(keyId));
            }else {
                dataResponse.setDataResponse(displayLinkRepository.findViewDisPlayByAll());
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
    public DataResponse getDisPlayLinkByName(DisplayLink displayLink, HttpServletRequest request) {
        String name= displayLink.getName();
        DataResponse dataResponse = new DataResponse();
        try {
                dataResponse.setDataResponse(displayLinkRepository.findByDisplayLinkNameLike(name));
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
    public DataResponse storeLink(DisplayLink displayLink, HttpServletRequest request) {
        DataResponse dataResponse = new DataResponse();
        try{
            dataResponse.setDataResponse(displayLinkRepository.save(displayLink));
            if(dataResponse.getDataResponse() !=null){
                dataResponse.setStatus("00");
                dataResponse.setMessage("Store Display Link Success");
            }else {
                dataResponse.setStatus("05");
                dataResponse.setMessage("Can't Store Display Link ");
            }
        }catch (Exception e){
            dataResponse.setStatus("05");
            dataResponse.setMessage("Can't Store Display Link ");
            e.printStackTrace();
        }
        return dataResponse;
    }

    @Override
    public DataResponse updateDisplayLinkNoFile(DisplayLink displayLink, HttpServletRequest request) {
        DataResponse dataResponse = new DataResponse();
        try{
            String name = displayLink.getName();
            String note = displayLink.getNote();
            String link = displayLink.getLink();
            String keyId = displayLink.getKeyId();
            dataResponse.setDataResponse(displayLinkRepository.updateDisplayLinkNoFile(name,note,link,keyId));
            if(dataResponse.getDataResponse() !=null){
                dataResponse.setStatus("00");
                dataResponse.setMessage("Update Display Link Success");
            }else {
                dataResponse.setStatus("05");
                dataResponse.setMessage("Can't Update Display Link ");
            }
        }catch (Exception e){
            dataResponse.setStatus("05");
            dataResponse.setMessage("Can't Update Display Link ");
            e.printStackTrace();
        }
        return dataResponse;
    }

    @Override
    public DataResponse updateLinkFile(DisplayLink displayLink, HttpServletRequest request) {
        DataResponse dataResponse = new DataResponse();
        try{
            String name = displayLink.getName();
            String note = displayLink.getNote();
            String link = displayLink.getLink();
            String keyId = displayLink.getKeyId();
            String imagePath = displayLink.getImagePath();
            String saveBy = displayLink.getSaveBy();
            dataResponse.setDataResponse(displayLinkRepository.updateDisplayLink(  name,  note,  link,  imagePath,saveBy,keyId));
            if(dataResponse.getDataResponse() !=null){
                dataResponse.setStatus("00");
                dataResponse.setMessage("Update Display Link Success");
            }else {
                dataResponse.setStatus("05");
                dataResponse.setMessage("Can't Update Display Link ");
            }
        }catch (Exception e){
            dataResponse.setStatus("05");
            dataResponse.setMessage("Can't Update Display Link ");
            e.printStackTrace();
        }
        return dataResponse;
    }

    @Override
    public DataResponse delLink(DisplayLink displayLink, HttpServletRequest request) {
        DataResponse dataResponse = new DataResponse();
        try{
            String keyId = displayLink.getKeyId();
            dataResponse.setDataResponse(displayLinkRepository.deleteDisPlayLink(keyId));
            if(dataResponse.getDataResponse() !=null){
                dataResponse.setStatus("00");
                dataResponse.setMessage("Delete Display Link Success");
            }else {
                dataResponse.setStatus("05");
                dataResponse.setMessage("Can't Delete Display Link ");
            }
        }catch (Exception e){
            dataResponse.setStatus("05");
            dataResponse.setMessage("Can't Delete Display Link ");
            e.printStackTrace();
        }
        return dataResponse;
    }

}
