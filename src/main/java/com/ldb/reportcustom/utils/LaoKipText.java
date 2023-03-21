package com.ldb.reportcustom.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * @author Somxay
 */
public class LaoKipText {
    private static final String[] SCALE_LA = {"ລ້ານ", "ສິບ", "ຮ້ອຍ", "ພັນ", "ໝື່ນ", "ແສນ", ""};
    private static final String[] DIGIT_LA = {"ສູນ", "ໜຶ່ງ", "ສອງ", "ສາມ", "ສີ", "ຫ້າ", "ຫົກ", "ເຈັດ", "ແປດ", "ເກົ້າ"};
    private static final String[] SYMBOLS_LA = {"ລົບ", "ກີບ", "", "ອັດ", "ຊາວ", "ເອັດ", ",", " ", "₭"};
//    private static final String[] SYMBOLS_LA = { "ລົບ", "ກີບ", "ຖ້ວນ", "ອັດ" ,"ຊາວ", "ເອັດ", ",", " ", "₭"};

    private String valueText;

    // ···········Methods··············//
    public String getText(double amount) {
        BigDecimal value = new BigDecimal(amount);
        this.valueText = getLaoKip(value);
        return this.valueText;
    }

    public String getText(float amount) {
        BigDecimal value = new BigDecimal(amount);
        this.valueText = getLaoKip(value);
        return this.valueText;
    }

    public String getText(int amount) {
        BigDecimal value = new BigDecimal(amount);
        this.valueText = getLaoKip(value);
        return this.valueText;
    }

    public String getText(long amount) {
        BigDecimal value = new BigDecimal(amount);
        this.valueText = getLaoKip(value);
        return this.valueText;
    }

    public String getText(String amount) {
        //ບໍ່ຕ້ອງການເຄື່ອງໝາຍຈຸດ, ບໍຕ້ອງການຊ່່ອງຫວ່າງ, ບໍ່ຕ້ອງການໂຕໜັງສືກ ກີບ, ບໍ່ຕ້ອງການສັນຍາລັກສະກຸນເງິນກີບ
        for (String element : SYMBOLS_LA) {
            amount = amount.replace(element, "");
        }

        BigDecimal value = new BigDecimal(amount.trim());
        this.valueText = getLaoKip(value);
        return this.valueText;
    }

    public String getText(Number amount) {
        BigDecimal value = new BigDecimal(String.valueOf(amount));
        this.valueText = getLaoKip(value);
        return this.valueText;
    }

    private static String getLaoKip(BigDecimal amount) {
        StringBuilder builder = new StringBuilder();
        BigDecimal absolute = amount.abs();
        int precision = absolute.precision();
        int scale = absolute.scale();
        int rounded_precision = ((precision - scale) + 2);
        MathContext mc = new MathContext(rounded_precision, RoundingMode.HALF_UP);
        BigDecimal rounded = absolute.round(mc);
        BigDecimal[] compound = rounded.divideAndRemainder(BigDecimal.ONE);
        boolean negative_amount = (-1 == amount.compareTo(BigDecimal.ZERO));

        compound[0] = compound[0].setScale(0);
        compound[1] = compound[1].movePointRight(2);

        if (negative_amount) {
            builder.append(SYMBOLS_LA[0].toString());
        }

        builder.append(getNumberText(compound[0].toBigIntegerExact()));
        builder.append(SYMBOLS_LA[1].toString());

        if (0 == compound[1].compareTo(BigDecimal.ZERO)) {
            builder.append(SYMBOLS_LA[2].toString());
        } else {
            builder.append(getNumberText(compound[1].toBigIntegerExact()));
            builder.append(SYMBOLS_LA[3].toString());
        }
        return builder.toString();
    }

    private static String getNumberText(BigInteger number) {
        StringBuffer buffer = new StringBuffer();
        char[] digits = number.toString().toCharArray();

        for (int index = digits.length; index > 0; --index) {
            int digit = Integer.parseInt(String.valueOf(digits[digits.length - index]));
            String digit_text = DIGIT_LA[digit];
            int scale_idx = ((1 < index) ? ((index - 1) % 6) : 6);

            // ກວດສອບຖ້າວ່າເປັນເລກ 2 ແຕ່ຫົວໜ່ວຍເປັນຊາວ ໃຫ້ປ່ຽນຈາກສອງເປັນຊາວ
            if ((1 == scale_idx) && (2 == digit)) {
                digit_text = SYMBOLS_LA[4].toString();
            }

            if (1 == digit) {
                switch (scale_idx) {
                    case 0:
                    case 6:
                        buffer.append((index < digits.length) ? SYMBOLS_LA[5].toString() : digit_text);
                        break;
                    case 1:
                        break;
                    default:
                        buffer.append(digit_text);
                        break;
                }
            } else if (0 == digit) {
                if (0 == scale_idx) {
                    buffer.append(SCALE_LA[scale_idx]);
                }
                continue;
            } else if ((1 == scale_idx) && (2 == digit)) {  //ກວດສອບວ່າເປັນຫົວໜ່ວຍ ຊາວ ຫລືບໍ່
                buffer.append(digit_text);
                continue;
            } else {
                buffer.append(digit_text);
            }
            buffer.append(SCALE_LA[scale_idx]);
        }
        return buffer.toString();
    }
}
