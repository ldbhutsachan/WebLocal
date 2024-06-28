package com.ldb.weblocalapi.utils;

/**
 * Create at 26/06/2020 - 5:32 PM
 * Project Name kiwi_multi
 *
 * @author yor
 */
public class APIMappingPaths {

    public static final String API_MB_GATEWAY_VERSION_PATH = "/api/v1";

    public static final String API_MB_REPORT_PATH = "/custom";
    public static final String API_AUTHENTICATION_GATEWAY_PATH = "/authenticate";
    public static final class  LOGIN {
    public static final String API_LOGIN_GATEWAY_PATH = "/login";
    public static final String API_PROFILE_GATEWAY_PATH = "/profile.service";
        public static final String API_CHANGE_PASSWORD_MENU = "/ChanePassword.service";
    }
    public static final class DOCTYPE{
        public static final String API_DOCTYPE = "/docType.service";
        public static final String API_STORE_DOCTYPE = "/storeDocType.service";
        public static final String API_UPDATE_DOCTYPE = "/updateDocType.service";
        public static final String API_DDEL_OCTYPE = "/delDocType.service";
    }
    public static final class SECTION{
        public static final String API_SECTION = "/section.service";
    }
    public static final class BRANCH{
        public static final String API_BRANCH = "/branch.service";
        public static final String API_BRANCH_LIST = "/branchList.service";
    }
    public static final class DOCUMENT{
        public static final String API_DOCUMENT = "/getListDocument.service";
        public static final String API_DOCUMENT_BYTEXTBOX = "/documentListByTextBox.service";
        public static final String API_USER_READ_DOCUMENT = "/ReadDocDocumentByDocNo.service";
        public static final String API_USER_VIEW_INFO_BUTTON = "/userViewInfo.service";
        public static final String API_SAVE_DOCUMENT = "/saveDocument.service";
        public static final String API_UPDATE_DOCUMENT = "/updateDocument.service";
        public static final String API_DEL_DOCUMENT = "/delDocument.service";
    }
    public static final class DISPLAYLINK{
        public static final String API_DISPLAYLINK = "/displayLink.service";
        public static final String API_NAME_DISPLAYLINK = "/searchDisPlayByText.service";
        public static final String API_STORE_DISPLAYLINK = "/storeDisplayLink.service";
        public static final String API_UPDATE_DISPLAYLINK = "/updateDisPlayLink.service";
        public static final String API_DEL_DISPLAYLINK = "/deleteDisPlayLink.service";
    }
    public static final class HOME{
        public static final String API_HOME = "/Home.service";
        public static final String API_HOME_CONDITION = "/getSaveDocumentCondition.service";
        public static final String API_HOME_POPUP = "/popUp.service";
        public static final String API_HOME_READ = "/read.service";
    }
    public static final class SECTION_MENUCONDITION{
        public static final String API_SECTION_MENU = "/SectionListDoc.service";
        public static final String API_SECTION_MENU_CONDITION = "/SectionListDocByDate.service";
    }
    public static final class BAND_MENUCONDITION{
        public static final String API_BAND_MENU = "/bandListDoc.service";
        public static final String API_BAND_MENU_CONDITION = "/bandListDocByDate.service";
    }
    public static final class FEE{
        public static final String API_FEE_MENU = "/feeListDoc.service";
        public static final String API_FEE_MENU_CONDITION = "/feeListDocByDate.service";
    }


}
