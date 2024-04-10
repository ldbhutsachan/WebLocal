package com.ldb.reportcustom.services.Impl;

import com.ldb.reportcustom.messages.request.RequestDatebyCompany;
import com.ldb.reportcustom.messages.request.RequestReportByStartDate;
import com.ldb.reportcustom.messages.request.RequestReportDate;
import com.ldb.reportcustom.messages.response.DataResponse;
import com.ldb.reportcustom.messages.response.reportSW.RespAccountBorder;
import com.ldb.reportcustom.messages.response.reportSW.RespSingleWinDaily;
import com.ldb.reportcustom.messages.response.reportSW.*;
import com.ldb.reportcustom.services.GetDataService;
import com.ldb.reportcustom.services.SingleWindowService;
import com.ldb.reportcustom.utils.LaoKipText;
import com.ldb.reportcustom.utils.LnswFunction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Create at 12/12/2022 - 5:55 PM
 * Project Name reportCustom
 *
 * @author kataii
 */
@Slf4j
@Service
public class SingleWindowServiceImpl implements SingleWindowService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private GetDataService getDataService;

    @Override
    public DataResponse paymentReport(RequestReportByStartDate dataRequest) {
        DataResponse response = new DataResponse();
        response.setStatus("05");
        response.setMessage("fail");
        List<RespMainObj> listMain = new ArrayList<>();
        DecimalFormat numfm = new DecimalFormat("#,###,###,##0");
        HashMap<String, Object> dataValue = new HashMap<String, Object>();

        try {
            List<RespSingleWinDaily> listRp = getDataService.listReportPayment(dataRequest);
            assert listRp != null;
            List<String> refIds = listRp.stream().map(RespSingleWinDaily::getRefId).distinct().collect(Collectors.toList());
//            System.out.println("RefId = " + refIds);
//            System.out.println("DATA = " + listRp);
            RespMainObj mainObj = new RespMainObj();
            RespPrintBill printBill = new RespPrintBill();
            RespPrintBillHeader header = new RespPrintBillHeader();
            RespPrintBillDetail detail = new RespPrintBillDetail();
            List<RespPrintBillDetail> listDetail = new ArrayList<>();
            RespPrintBillFooter footer = new RespPrintBillFooter();
            double totalAmount = 0;
            String borderName = "";
            String paymentDate = "";
            double totalAmounts = 0;
            Date issueDate = new Date();
            HashMap<String, Object> total = new HashMap<String, Object>();
            HashMap<String, Object> dataValues = new HashMap<String, Object>();
            SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
//
            for (String refId : refIds) {

                totalAmounts += listRp.stream().filter(p -> p.getRefId().equals(refId)).map(RespSingleWinDaily::getAmount).findFirst().orElse(0.0);

                paymentDate = listRp.stream().filter(p -> p.getRefId().equals(refId)).map(RespSingleWinDaily::getPaymentDate).findFirst().orElse("");
                issueDate = new SimpleDateFormat("yyyy-MM-dd").parse(listRp.stream().filter(p -> p.getRefId().equals(refId)).map(RespSingleWinDaily::getIssueDate).findFirst().orElse(""));
                /**
                 * Set data in main object
                 */
                totalAmount = listRp.stream().filter(p -> p.getRefId().equals(refId)).map(RespSingleWinDaily::getAmount).findFirst().orElse(0.0);
                mainObj = new RespMainObj();
                mainObj.setPaymentDate(paymentDate);
                mainObj.setCompanyName(listRp.stream().filter(p -> p.getRefId().equals(refId)).map(RespSingleWinDaily::getCompanyName).findFirst().orElse(""));
                mainObj.setTypeInvoice(listRp.stream().filter(p -> p.getRefId().equals(refId)).map(RespSingleWinDaily::getTypeInvoice).findFirst().orElse(""));
                mainObj.setInvoiceNumber(listRp.stream().filter(p -> p.getRefId().equals(refId)).map(RespSingleWinDaily::getInvoiceNumber).findFirst().orElse(""));
                mainObj.setInstance(listRp.stream().filter(p -> p.getRefId().equals(refId)).map(RespSingleWinDaily::getInstance).findFirst().orElse(""));
                mainObj.setReceiptNumber(listRp.stream().filter(p -> p.getRefId().equals(refId)).map(RespSingleWinDaily::getReceiptNumber).findFirst().orElse(""));
                mainObj.setPaymentChanel(listRp.stream().filter(p -> p.getRefId().equals(refId)).map(RespSingleWinDaily::getPaymentChanel).findFirst().orElse(""));
                mainObj.setAmount(totalAmount);
                /**
                 * Set header data
                 */
                header = new RespPrintBillHeader();
                header.setBillNo(listRp.stream().filter(p -> p.getRefId().equals(refId)).map(RespSingleWinDaily::getReceiptNumber).findFirst().orElse(""));
                header.setNumber(listRp.stream().filter(p -> p.getRefId().equals(refId)).map(RespSingleWinDaily::getInvoiceNumber).findFirst().orElse(""));

                header.setInvoices(listRp.stream().filter(p -> p.getRefId().equals(refId)).map(RespSingleWinDaily::getRefId).findFirst().orElse(""));

                borderName = listRp.stream().filter(p -> p.getRefId().equals(refId)).map(RespSingleWinDaily::getBroderName).findFirst().orElse("");
                header.setBorderName(borderName + " ສົກປີ: " + new SimpleDateFormat("yyyy").format(issueDate));
                header.setTypeInvoice(listRp.stream().filter(p -> p.getRefId().equals(refId)).map(RespSingleWinDaily::getTypeInvoice).findFirst().orElse(""));
                header.setSoi(listRp.stream().filter(p -> p.getRefId().equals(refId)).map(RespSingleWinDaily::getSoi).findFirst().orElse(""));
                header.setIssueDate(sf.format(issueDate));
//                header.setIssueDate(listRp.stream().filter(p -> p.getRefId().equals(refId)).map(RespSingleWinDaily::getPaymentDate).findFirst().orElse(""));
                header.setCompanyName(listRp.stream().filter(p -> p.getRefId().equals(refId)).map(RespSingleWinDaily::getCompanyName).findFirst().orElse(""));
                printBill = new RespPrintBill();
                printBill.setHeader(header);

                listDetail = new ArrayList<>();
                for (RespSingleWinDaily rspList : listRp) {
                    if (rspList.getRefId().equals(refId)) {
                        /**
                         * ແຍກລາຍການຍ່ອຍອອກຈາກ STD ໂດຍການຕັດແຕ່ລະ ສ່ວນອອກຈາກ "|" ຫລັງຈາກນັ້ນຈືງເອົາຂໍ້ມູນໂດຍການຕັດອອກຈາກ "*"
                         * ແລະ ກວດສອບວ່າຖ້າຍອດເງິນເປັນ 0 ບໍ່ໃຫ້ເອົາຄ່າມາສະແດງ
                         * ປະຕິບັດຄົບແລ້ວ ສັ່ງໃຫ້ loop ເລີ່ມໄປຄ່າໃໝ່ເລີຍ
                         */
                        if (rspList.getTaxCode().equals("STD")) {
                            String[] taxCodes = rspList.getMoreInfo().split("\\|");
                            for (String taxCode : taxCodes) {
                                String[] moreInfoArray = taxCode.split("\\*");
                                detail = new RespPrintBillDetail();
                                if (Double.parseDouble(moreInfoArray[2]) > 0) {
                                    detail.setTitle(moreInfoArray[1]);
                                    detail.setAmount(numfm.format(Double.parseDouble(moreInfoArray[2])));
                                    listDetail.add(detail);
                                }
                            }
                            continue;
                        }
                        /**
                         * Set Detail data
                         */
                        detail = new RespPrintBillDetail();
                        detail.setTitle(rspList.getTaxReceiptName());
                        detail.setAmount(numfm.format(rspList.getDetailAmount()));
                        listDetail.add(detail);
                    }
                }

                printBill.setDetail(listDetail);
                /**
                 * Set Footer data
                 */
                footer = new RespPrintBillFooter();
                footer.setTotalAmount(numfm.format(totalAmount));
                footer.setNameAmount(new LaoKipText().getText(totalAmount));
                footer.setPaymentDate(paymentDate);
                footer.setQrCode(refId);
                printBill.setFooter(footer);
                mainObj.setPrintBill(printBill);
                listMain.add(mainObj);
            }
            ///start
            dataValue.put("dataValue", listMain);
            //get amount total
            dataValue.put("sumTotal", totalAmounts);

            response.setStatus("00");
            response.setMessage("success");
            response.setDataResponse(dataValue);
        } catch (Exception e) {
            log.error("Cannot Get data = {}", e.getMessage());
            e.printStackTrace();
        }
        return response;
    }
    @Override
    public DataResponse paymentCompareReport(RequestReportByStartDate dataRequest) {
        DataResponse response = new DataResponse();
        response.setStatus("05");
        response.setMessage("fail");
        List<RespMainObj> listMain = new ArrayList<>();
        DecimalFormat numfm = new DecimalFormat("#,###,###,##0");
        try {
            List<HashMap> dataList = new ArrayList<>();
            HashMap<String, Object> header = new HashMap<String, Object>();
            HashMap<String, Object> data = new HashMap<String, Object>();
            HashMap<String, Object> nameObj = new HashMap<String, Object>();
            HashMap<String, Object> total = new HashMap<String, Object>();
            HashMap<String, Object> dataValue = new HashMap<String, Object>();

            List<RespAccountBorder> listAcctBorder =  getDataService.findBorderAccount();;
            List<RespSingleWinDaily> listRp = getDataService.listReportPaymentCompare(dataRequest);
//            System.out.println("List = " + listRp);
            assert listRp != null;
            List<String> refIds = listRp.stream().map(RespSingleWinDaily::getRefId).distinct().collect(Collectors.toList());
            String paymentDate = "";
            String receiptNum = "";
            double totalAmount = 0;
            String borderCode = "";

            /**
             * loop ເອົາເລກໃບບິນ ມາເປັນເງືອນໄຂໃນການຊອກຫາລາຍລະອຽດຂອງແຕ່ລະບິນ
             */
            for (String refId : refIds) {
                nameObj = new HashMap<String, Object>();
                data = new HashMap<String, Object>();
                header = new HashMap<String, Object>();
//                totalAmount = listRp.stream().map(RespSingleWinDaily::getAmount).distinct().reduce(0.0, Double::sum);
                /**
                 * sum ຍອດເງິນທັງໝົດໂດຍການບວກໄປເທືອລະລາຍການຕາມບິນ
                 */
                totalAmount += listRp.stream().filter(p -> p.getRefId().equals(refId)).map(RespSingleWinDaily::getAmount).findFirst().orElse(0.0);
//                System.out.println(refId + " | Total = " + numfm.format(totalAmount) );
                paymentDate = listRp.stream().filter(p -> p.getRefId().equals(refId)).map(RespSingleWinDaily::getPaymentDate).findFirst().orElse("");
                receiptNum = listRp.stream().filter(p -> p.getRefId().equals(refId)).map(RespSingleWinDaily::getTypeInvoice).findFirst().orElse("") + ":" +
                        listRp.stream().filter(p -> p.getRefId().equals(refId)).map(RespSingleWinDaily::getSoi).findFirst().orElse("") + " " +
                        listRp.stream().filter(p -> p.getRefId().equals(refId)).map(RespSingleWinDaily::getReceiptNumber).findFirst().orElse("");
//                taxCode = listRp.stream().filter(p -> p.getRefId().equals(refId)).map(RespSingleWinDaily::getTaxCode).findFirst().orElse(0);
//                borderCode = listRp.stream().filter(p -> p.getRefId().equals(refId)).map(RespSingleWinDaily::getBorderCode).findFirst().orElse("");

                /**
                 * loop ເອົາຊື່ແຕ່ລະປະເພດຂອງ Tax Code
                 */
                for (RespAccountBorder accountBorder : listAcctBorder) {
                    nameObj.put(accountBorder.getTaxCode(), accountBorder.getTaxName());
//                    data.put(accountBorder.getTaxCode(), listRp.stream().filter(p -> p.getRefId().equals(refId) && p.getTaxCode().equals( accountBorder.getTaxCode())).map(RespSingleWinDaily::getDetailAmount).findFirst().orElse(0.0));
                    /**
                     * ກຳນົດຄ່າໃຫ້ກ່ອນຕາມແຕ່ລະປະເພດໃຫ້ເປັນ 0 ໜ້າປະເພດໃດມີຄ່າໃຫ້ເສັດຄ່າໃໝ່ ຕາມຈຳນວນທີດືງອອກມາຈາກຖານຂ້ມູນ
                     */
                    data.put(accountBorder.getTaxCode(), 0.0);

                    /**
                     * loop ເອົາລາຍລະອຽດຂອງແຕ່ລະບິນ
                     */
                    for (RespSingleWinDaily winDaily : listRp) {
                        /**
                         * ກວດເອົາສະເພາະລາຍການຍ່ອຍທີມີນຳ tax code
                         */
                        //System.out.println("getfff"+winDaily.getRefId());
                        if (winDaily.getTaxCode().equals(accountBorder.getTaxCode())) {
                            if (winDaily.getRefId().equals(refId)) {
                                data.put(accountBorder.getTaxCode(), (winDaily.getDetailAmount()));
                            }
                            //---------------check RefId And TaxCode -----------------
                            if (winDaily.getRefId().equals(refId) && accountBorder.getTaxCode().equals("STD")) {
                                String[] taxCodes = winDaily.getMoreInfo().split("\\|");
                                for (String taxCode : taxCodes) {
                                    String[] moreInfoArray = taxCode.split("\\*");
//                                    System.out.println("moreInfoArray = " + moreInfoArray[0] + " | " + moreInfoArray[2]);
                                    data.put(moreInfoArray[0], (Double.parseDouble(moreInfoArray[2])));
                                }
                            }
                            //--------------------------------
                        }
                    }
//                    data.put(accountBorder.getTaxCode(), listRp.stream().filter(p -> p.getRefId().equals(refId) && p.getTaxCode().equals( accountBorder.getTaxCode())).map(RespSingleWinDaily::getDetailAmount).findFirst().orElse(0.0));
//                    total.put(accountBorder.getTaxCode(), listRp.stream().filter(p ->  p.getTaxCode() == accountBorder.getId()).map(RespSingleWinDaily::getDetailAmount).reduce(0.0, Double::sum));
                }
                /**
                 * ເພີມວັນທີ ພິມບິນ ແລະ ເລກທີໃບຮັບເງິນ ເຂົ້າ object
                 */
                data.put("paymentDate", paymentDate);
                data.put("receiptNumber", receiptNum);
                nameObj.put("paymentDate", "ວັນທີ ແລະ ເວລາຊຳລະ");
                nameObj.put("receiptNumber", "ເລກທີໃບແຈ້ງ");

                /**
                 * ກຳນົດຄ່າໃຫ້ object header ແລະ ກຳນົດຄ່າໃຫ້ object data
                 */
                header.put("header", nameObj);
                header.put("data", data);

                dataList.add(header);
            }

            dataValue.put("dataValue", dataList);



//            System.out.println("dataValue = " + dataList);
            Map<String, Long> sum = new HashMap<>();

            List<Object> mapList = new ArrayList<>();
            List<Map<String, Double>> listData = new ArrayList();
            /**
             * filter data from boder.dataList and set to mapList
             */
            mapList =  dataList.stream().filter(element -> element.containsKey("data")).map(element -> element.get("data")).collect(Collectors.toList());

            /**
             * Sum total by taxCode and set object sumTotal to response
             */
            for (RespAccountBorder accountBorder : listAcctBorder) {

                /**
                 * ກຳນົດຄ່າໃຫ້ກ່ອນຕາມແຕ່ລະປະເພດໃຫ້ເປັນ 0 ໜ້າປະເພດໃດມີຄ່າໃຫ້ເສັດຄ່າໃໝ່ ຕາມຈຳນວນທີດືງອອກມາຈາກຖານຂ້ມູນ
                 */

                final Map<String, Double>[] map1 = new Map[]{new HashMap<>()};
                AtomicInteger i = new AtomicInteger(1);
                /**
                 * ແຍກເອົາສະເພາະເງິນອອກມາ ແລ້ວ ໄປເກັບ ໃສ່ listData
                 */
                mapList.forEach(
                        map -> {
                            if (map instanceof Map) {
                                map1[0] = new HashMap<>();
//                                System.out.println(accountBorder.getTaxCode()+ " = " + ((Map<?, ?>) map).get(accountBorder.getTaxCode()));
                                map1[0].put(accountBorder.getTaxCode(), ((Map<?, ?>) map).get(accountBorder.getTaxCode()) == null ? 0.0 : Double.parseDouble(((Map<?, ?>) map).get(accountBorder.getTaxCode()).toString()));
                                listData.add(map1[0]);

                                // System.out.println("map " + i + " = " + map1[0]);
//                                System.out.println("SUM = " + listData);
                            }
                            i.getAndIncrement();
                        }
                );
            }
          //  System.out.println("listData 2 = " + listData);
            /**
             * sum ຍອດເງິນຕາມແຕ່ລະປະເພດຂອງ tax code
             */
            Map<String, Double> sumTotal = LnswFunction.reduceDoubles(listData);
//            System.out.println("suma = " + sumTotal);
            sumTotal.put("total", (totalAmount));
            total.put("total", numfm.format(totalAmount));
            dataValue.put("sumTotal", sumTotal);

            response.setStatus("00");
            response.setMessage("success");
            response.setDataResponse(dataValue);
        } catch (Exception e) {
            log.error("Cannot Get data = {}", e.getMessage());
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public DataResponse paymentTotalReport(RequestReportByStartDate dataRequest) {
        DataResponse response = new DataResponse();
        response.setStatus("05");
        response.setMessage("fail");
        try {
            DecimalFormat numfm = new DecimalFormat("#,###,###,##0");
            List<HashMap> dataList = new ArrayList<>();
            HashMap<String, Object> mainObj = new HashMap<String, Object>();
            HashMap<String, Object> data = new HashMap<String, Object>();
            HashMap<String, Object> nameObj = new HashMap<String, Object>();
            HashMap<String, Object> dataValue = new HashMap<String, Object>();
            HashMap<String, Object> total = new HashMap<String, Object>();
            //for (String refId : refIds) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            List<RespAccountBorder> listAcctBorder = new ArrayList<>();
            List<RespSumTaxCode> listRp = getDataService.listReportSumTaxCodeLNSW(dataRequest);
           // System.out.println("listRp = " + listRp);
            List<String> refIds = listRp.stream().map(RespSumTaxCode::getReference).distinct().collect(Collectors.toList());
            assert listRp != null;
            List<String> datePays = listRp.stream().map(RespSumTaxCode::getPaymentDate).distinct().collect(Collectors.toList());
            List<String> borderCodes = listRp.stream().map(RespSumTaxCode::getBorderCode).distinct().collect(Collectors.toList());
            String paymentDate = "";
            String borderCode = "";
            double totalAmount = 0;
           // System.out.println("borderCodes = " + borderCodes + " datePays = " + datePays);

            for (String datePay : datePays) {
                borderCode = listRp.stream().filter(p -> p.getPaymentDate().equals(datePay)).map(RespSumTaxCode::getBorderCode).findFirst().orElse("");
                for (String border : borderCodes) {
                    if (border.equals(borderCode)) {

                        nameObj = new HashMap<String, Object>();
                        data = new HashMap<String, Object>();
                        mainObj = new HashMap<String, Object>();
                        paymentDate = listRp.stream().filter(p -> p.getPaymentDate().equals(datePay) && p.getBorderCode().equals(border)).map(RespSumTaxCode::getPaymentDate).findFirst().orElse("");
                        listAcctBorder = getDataService.findBorderAccount();

                       // System.out.println("listAcctBorder = " + listAcctBorder);
                        Double CD= 0.0;
                        Double VAT= 0.0;
                        Double EXC= 0.0;
                        Double STD = 0.0;
                        for (String refId : refIds) {
                        for (RespAccountBorder accountBorder : listAcctBorder) {

                            nameObj.put(accountBorder.getTaxCode(), accountBorder.getTaxName());
                            //data.put(accountBorder.getTaxCode(), 0.0);
                            if(accountBorder.getTaxCode().equals("AFE")){
                                data.put(accountBorder.getTaxCode(), 0.0);
                            }
                            if(accountBorder.getTaxCode().equals("PSC")){
                                data.put(accountBorder.getTaxCode(), 0.0);
                            }
                            if(accountBorder.getTaxCode().equals("STA")){
                                data.put(accountBorder.getTaxCode(), 0.0);
                            }
                            if(accountBorder.getTaxCode().equals("LNSW")){
                                data.put(accountBorder.getTaxCode(), 0.0);
                            }
                            if(accountBorder.getTaxCode().equals("SCV")){
                                data.put(accountBorder.getTaxCode(), 0.0);
                            }
                            if(accountBorder.getTaxCode().equals("EMT")){
                                data.put(accountBorder.getTaxCode(), 0.0);
                            }
                            if(accountBorder.getTaxCode().equals("PFD")){
                                data.put(accountBorder.getTaxCode(), 0.0);
                            }
                            if(accountBorder.getTaxCode().equals("PRS")){
                                data.put(accountBorder.getTaxCode(), 0.0);
                            }
                            if(accountBorder.getTaxCode().equals("EXD")){
                                data.put(accountBorder.getTaxCode(), 0.0);
                            }
                            if(accountBorder.getTaxCode().equals("BT")){
                                data.put(accountBorder.getTaxCode(), 0.0);
                            }
                            if(accountBorder.getTaxCode().equals("TBC")){
                                data.put(accountBorder.getTaxCode(), 0.0);
                            }
                            if(accountBorder.getTaxCode().equals("SCF")){
                                data.put(accountBorder.getTaxCode(), 0.0);
                            }
                            if(accountBorder.getTaxCode().equals("SCI")){
                                data.put(accountBorder.getTaxCode(), 0.0);
                            }
                            if(accountBorder.getTaxCode().equals("ITF")){
                                data.put(accountBorder.getTaxCode(), 0.0);
                            }
                            if(accountBorder.getTaxCode().equals("VT")){
                                data.put(accountBorder.getTaxCode(), 0.0);
                            }
                            //data.put(accountBorder.getTaxCode(), 0.0);
                         //  System.out.println("accountBorder.getTaxName()"+accountBorder.getTaxCode());
                            for (RespSumTaxCode winDaily : listRp) {

                                if (winDaily.getTaxCode().equals(accountBorder.getTaxCode())) {
                                    if (winDaily.getReference().equals(refId)) {
                                        data.put(accountBorder.getTaxCode(), (winDaily.getDetailAmount()));
                                    }
                                    //---------------check RefId And TaxCode -----------------
                                    if (winDaily.getReference().equals(refId) && accountBorder.getTaxCode().equals("STD")) {
                                        String[] taxCodes = winDaily.getMoreInfo().split("\\|");
                                        for (String taxCode : taxCodes) {
                                            String[] moreInfoArray = taxCode.split("\\*");
                                            //System.out.println("moreInfoArray = " + moreInfoArray[0] + " | " + moreInfoArray[2]);
                                            switch (moreInfoArray[0]){
                                                case "CD" :
                                                    CD += Double.valueOf(moreInfoArray[2]);
                                                    break;
                                                case "VAT" :
                                                    VAT += Double.valueOf(moreInfoArray[2]);
                                                    break;
                                                case "EXC" :
                                                    EXC += Double.valueOf(moreInfoArray[2]);
                                                    break;
                                            }
                                            //set data to array amount details
                                           // System.out.println("moreInfoArray5551 = " + CD + " | " + VAT +"|"+ EXC);
                                            data.put("CD", CD);
                                            data.put("VAT", VAT);
                                            data.put("EXC", EXC);
                                         //   System.out.println("moreInfoArray VAT = " +VAT);
                                        }
                                        STD = CD+VAT+EXC;
                                        data.put("STD",STD);
                                    }
                                    else  {
                                     ///   System.out.println("winDaily"+  winDaily.getTotalAmount() );
                                     //   System.out.println("getTaxCode"+  winDaily.getTaxCode() );
                                        data.put(accountBorder.getTaxCode(), listRp.stream().filter(p -> p.getPaymentDate().equals(datePay) && p.getTaxCode().equals(accountBorder.getTaxCode())).map(RespSumTaxCode::getTotalAmount).reduce(0.0, Double::sum));
                                    }
                                }
                            }
                        }
                    }
                        nameObj.put("paymentDate", "ວັນທີ");
                        data.put("paymentDate", paymentDate);
                        mainObj.put("header", nameObj);
                        mainObj.put("data", data);
                        dataList.add(mainObj);
                      //  System.out.println("DataList = " + dataList);
                    }
                }
            }
            dataValue.put("dataValue", dataList);
            //----------------------------------------------------------get map from boder.data details
            Map<String, Long> sum = new HashMap<>();
            List<Object> mapList = new ArrayList<>();
            List<Map<String, Double>> listData = new ArrayList();
            /**
             * filter data from boder.dataList and set to mapList
             */
            mapList =  dataList.stream().filter(element -> element.containsKey("data")).map(element -> element.get("data")).collect(Collectors.toList());
         //   System.out.println("mapList = " + mapList);
           // log.info("mapList:"+mapList);
            for (RespAccountBorder accountBorder : listAcctBorder) {
                /**
                 * ກຳນົດຄ່າໃຫ້ກ່ອນຕາມແຕ່ລະປະເພດໃຫ້ເປັນ 0 ໜ້າປະເພດໃດມີຄ່າໃຫ້ເສັດຄ່າໃໝ່ ຕາມຈຳນວນທີດືງອອກມາຈາກຖານຂ້ມູນ
                 */
                final Map<String, Double>[] map1 = new Map[]{new HashMap<>()};
                AtomicInteger i = new AtomicInteger(1);
                /**
                 * ແຍກເອົາສະເພາະເງິນອອກມາ ແລ້ວ ໄປເກັບ ໃສ່ listData
                 */
                mapList.forEach(
                        map -> {
                            if (map instanceof Map) {
                                map1[0] = new HashMap<>();
                                map1[0].put(accountBorder.getTaxCode(), ((Map<?, ?>) map).get(accountBorder.getTaxCode()) == null ? 0.0 : Double.parseDouble(((Map<?, ?>) map).get(accountBorder.getTaxCode()).toString()));
                                listData.add(map1[0]);
                            }
                            i.getAndIncrement();
                        }
                );
            }
          //  System.out.println("listData 2 = " + listData);
            //-------set amount sum from boder.DB to total
            totalAmount = listRp.stream().map(RespSumTaxCode::getTotalAmount).reduce(0.0, Double::sum);
            /**
             * sum ຍອດເງິນຕາມແຕ່ລະປະເພດຂອງ tax code
             */
          //  System.out.println("show total="+totalAmount);
            Map<String, Double> sumTotal = LnswFunction.reduceDoubles(listData);
            sumTotal.put("total", (totalAmount));
            total.put("total", numfm.format(totalAmount));
            dataValue.put("sumTotal", sumTotal);
            //---------------------------------------------------------- end get map from boder.data details
            response.setStatus("00");
            response.setMessage("success");
            response.setDataResponse(dataValue);
        } catch (Exception e) {
            log.error("Cannot Get data = {}", e.getMessage());
            e.printStackTrace();
        }
        return response;
    }
///report company
@Override
public DataResponse ReportCompany(RequestDatebyCompany dataRequest) {
    DataResponse response = new DataResponse();
    response.setStatus("05");
    response.setMessage("fail");
    List<RespMainCompanyObj> listMain = new ArrayList<>();
    DecimalFormat numfm = new DecimalFormat("#,###,###,##0");
    HashMap<String, Object> dataValue = new HashMap<String, Object>();

    try {
        List<RespSingleWinDaily> listRp = getDataService.listReportCompanyAll(dataRequest);
        assert listRp != null;
        List<String> refIds = listRp.stream().map(RespSingleWinDaily::getRefId).distinct().collect(Collectors.toList());
        RespMainCompanyObj mainObj = new RespMainCompanyObj();
        double totalAmount = 0;
        String borderName = "";
        String paymentDate = "";
        double totalAmounts = 0;
        Date issueDate = new Date();
        HashMap<String, Object> total = new HashMap<String, Object>();
        HashMap<String, Object> dataValues = new HashMap<String, Object>();
        SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
//
        for (String refId : refIds) {
            totalAmounts += listRp.stream().filter(p -> p.getRefId().equals(refId)).map(RespSingleWinDaily::getAmount).findFirst().orElse(0.0);
            paymentDate = listRp.stream().filter(p -> p.getRefId().equals(refId)).map(RespSingleWinDaily::getPaymentDate).findFirst().orElse("");
            issueDate = new SimpleDateFormat("yyyy-MM-dd").parse(listRp.stream().filter(p -> p.getRefId().equals(refId)).map(RespSingleWinDaily::getIssueDate).findFirst().orElse(""));
            /**
             * Set data in main object
             */
            totalAmount = listRp.stream().filter(p -> p.getRefId().equals(refId)).map(RespSingleWinDaily::getAmount).findFirst().orElse(0.0);
            mainObj = new RespMainCompanyObj();
            mainObj.setPaymentDate(paymentDate);
           mainObj.setReference(listRp.stream().filter(p -> p.getRefId().equals(refId)).map(RespSingleWinDaily::getRefId).findFirst().orElse(""));
            mainObj.setCompanyName(listRp.stream().filter(p -> p.getRefId().equals(refId)).map(RespSingleWinDaily::getCompanyName).findFirst().orElse(""));
            mainObj.setTypeInvoice(listRp.stream().filter(p -> p.getRefId().equals(refId)).map(RespSingleWinDaily::getTypeInvoice).findFirst().orElse(""));
            mainObj.setInvoiceNumber(listRp.stream().filter(p -> p.getRefId().equals(refId)).map(RespSingleWinDaily::getInvoiceNumber).findFirst().orElse(""));
            mainObj.setInstance(listRp.stream().filter(p -> p.getRefId().equals(refId)).map(RespSingleWinDaily::getInstance).findFirst().orElse(""));
            mainObj.setReceiptNumber(listRp.stream().filter(p -> p.getRefId().equals(refId)).map(RespSingleWinDaily::getReceiptNumber).findFirst().orElse(""));
            mainObj.setPaymentChanel(listRp.stream().filter(p -> p.getRefId().equals(refId)).map(RespSingleWinDaily::getPaymentChanel).findFirst().orElse(""));
            mainObj.setBorder(listRp.stream().filter(p -> p.getRefId().equals(refId)).map(RespSingleWinDaily::getBorderCode).findFirst().orElse(""));
            mainObj.setBorderName(listRp.stream().filter(p -> p.getRefId().equals(refId)).map(RespSingleWinDaily::getBroderName).findFirst().orElse(""));
            mainObj.setAmount(totalAmount);
            /**
             * Set header data
             */
            listMain.add(mainObj);
        }
        ///start
        dataValue.put("dataValue", listMain);
        //get amount total
        dataValue.put("sumTotal", totalAmounts);
        response.setStatus("00");
        response.setMessage("success");
        response.setDataResponse(dataValue);
    } catch (Exception e) {
        log.error("Cannot Get data = {}", e.getMessage());
        e.printStackTrace();
    }
    return response;
}
}
