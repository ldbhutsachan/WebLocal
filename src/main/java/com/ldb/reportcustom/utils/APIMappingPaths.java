package com.ldb.reportcustom.utils;

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
        public static final String API_LOGIN_GATEWAY_PATH = "/token";
        public static final String API_PROFILE_GATEWAY_PATH = "/profile.service";
    }
    public static final class CREATEUSERS{
        public static final String API_CREATE_USER_GATEWAY_PATH = "/createUser.service";
        //API_CHANGE_PASSWORD_USER_GATEWAY_PATH
        public static final String API_CHANGE_PASSWORD_USER_GATEWAY_PATH = "/changePasswordUser.service";

    }
    public static final class ReportAdmin{
        public static final String API_CUSTOM_SINGLE_WINDOW_GATEWAY_PATH_PROVINCE = "/getProvince.service";
        public static final String API_CUSTOM_SINGLE_WINDOW_GATEWAY_PATH_ADMIN_BORDER = "/getBorder.service";
        //Report 01
        public static final String API_CUSTOM_SINGLE_WINDOW_GATEWAY_PATH_ADMIN = "/singleWindowDailyAdmin.service";
        //Report 02
        public static final String API_CUSTOM_SINGLE_WINDOW_GATEWAY_COMPARE_PATH_ADMIN = "/singleWindowCompareAdmin.service";
        public static final String API_CUSTOM_SINGLE_WINDOW_GATEWAY_TOTAL_PATH_ADMIN_SELECT_ALL = "/singleWindowTotalAdminSelectAll.service";
        //Report 03
        public static final String API_CUSTOM_SINGLE_WINDOW_GATEWAY_TOTAL_PATH_ADMIN = "/singleWindowTotalAdmin.service";
//ລາຍງານຜູ້ສໍາລະ
        public static final String API_CUSTOM_SINGLE_WINDOW_GATEWAY_COMPANY_PATH = "/company.service";


    }
    public static final class REPORT_SERVICE {
        public static final String API_CUSTOM_SINGLE_GET_PROVINCE_TOTAL_PATH = "/getProvince.service";
        public static final String ALL_REPORT_SUMMARY_GATEWAY_PATH = "/allReportSummary.service";
        public static final String ALL_REPORT_BORDER_DETAIL_GATEWAY_PATH = "/reportBorderDetail.service";
        public static final String ALL_BAR_CHART_SUM_TOTAL_GATEWAY_PATH = "/barChartSumTotal.service";
        public static final String ALL_PIE_CHART_SUM_TOTAL_GATEWAY_PATH = "/pieChartSumTotal.service";
        public static final String DOWNLOAD_PDF_FILE_GATEWAY_PATH = "/downloadPdf/{fileName:.+}";
        public static final String GENERATE_PDF_FILE_GATEWAY_PATH = "/generatePdf";
        public static final String API_DUTY_DAILY_GATEWAY_PATH = "/dutyChart.service";
        public static final String API_CUSTOM_SINGLE_WINDOW_GATEWAY_PATH = "/singleWindowDaily.service";
        //report admin
        public static final String API_CUSTOM_SINGLE_WINDOW_GATEWAY_COMPARE_PATH = "/singleWindowCompare.service";
        public static final String API_CUSTOM_SINGLE_WINDOW_GATEWAY_TOTAL_PATH = "/singleWindowTotal.service";

    }


}
