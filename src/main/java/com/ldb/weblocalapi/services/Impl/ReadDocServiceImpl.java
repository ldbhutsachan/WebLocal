package com.ldb.weblocalapi.services.Impl;

import com.ldb.weblocalapi.entities.Read;
import com.ldb.weblocalapi.entities.Respone.ReadDocument;
import com.ldb.weblocalapi.messages.response.DataResponse;
import com.ldb.weblocalapi.repositories.ReadDocumentRepository;
import com.ldb.weblocalapi.services.ReadDocService;
import com.ldb.weblocalapi.utils.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
@Slf4j
public class ReadDocServiceImpl implements ReadDocService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

@Autowired
ReadDocumentRepository readDocumentRepository;
    @Override
    public DataResponse storeSaveRead(Read readDocument) {
        String userId = readDocument.getUserId();
        String docKey = readDocument.getDocKeyId();
        DataResponse dataResponse = new DataResponse();
        try{
            //******check user and keyno frist save****
            List<Read> rspListUserInfo = getCheckUserRead(readDocument);
            if(!rspListUserInfo.isEmpty()){
                    log.info("no record");
            }else {
                    readDocumentRepository.save(readDocument);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return dataResponse;
    }
    public List<Read> getCheckUserRead(Read readDocument){
        String userId = readDocument.getUserId();
        String docNoKey = readDocument.getDocKeyId();
            try{
                String query = "select * from READ_DOC where  USER_ID='"+userId+"' and  DOC_KEY_ID='"+docNoKey+"'";
                return jdbcTemplate.query(query, new RowMapper<Read>() {
                    @Override
                    public Read mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Read tr =new Read();
                        tr.setKeyId(rs.getLong("READ_ID"));
                        tr.setReadDate(rs.getDate("READ_DATE"));
                        tr.setUserId(rs.getString("USER_ID"));
                        tr.setDocKeyId(rs.getString("DOC_KEY_ID"));
                        return tr;
                    }
                });
            }catch (Exception e){
                e.printStackTrace();
            }
            return  null;
    }

}
