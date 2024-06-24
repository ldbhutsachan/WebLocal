package com.ldb.weblocalapi.Model;

import com.ldb.weblocalapi.entities.Respone.ReadDocument;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DocumentSecMenu {
    private String keyId;
    private String docName;
    private String docNo;
    private String docTypeNo;
    private String docTypeName;
    private String docDate;
    private String saveDate;
    private String saveBy;
    private String name;
    private String docPath;
    private String docStatus;
    private String typeDocIn_Out;
    private String amtRead;
    private String relationUnit;


}