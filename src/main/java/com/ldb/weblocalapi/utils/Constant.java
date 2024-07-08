package com.ldb.weblocalapi.utils;

public class Constant {
    public static class RESPONSE_CODE{
        public static final String SUCCESS="00"; // Use for internal server error only
        public static final String DATA_NOT_FOUND="05"; // Use for when user Authentication not found
        public static final String DATA_USER="01"; // Use for when user Authentication not found
        public static final String DATA_PASS="EE"; // Use for when user Authentication not found
    }

    public static class RESPONSE_MESSAGE{
        public static final String SUCCESS_MSG = "ສໍາເລັດ";
        public static final String STORE_MSG = "ບັນທຶກຂໍ້ມູນສໍາເລັດ";
        public static final String UPDATE_MSG="ເເກ້ໄຂຂໍ້ມູນສໍາເລັດ";
        public static final String DEL_MSG="ລຶບຂໍ້ມູນສໍາເລັດ";
        public static final String DATA_NOT_FOUND_MSG="ບໍ່ມີຂໍ້ມູນ";
        public static final String DATA_NOT_DIFICATE="ລະຫັດໃໝ່ ເເລະ ລະຫັດຢືນຢັນ ບໍ່ຕົງກັນ !!!";
        public static final String DATA_OLD_PASS_FAIL="ລະຫັດຜ່ານເກົ່າ ຂອງທ່່ານບໍ່ຖືກຕ້ອງ !!!";
        public static final String DATA_DON_CHANGE="ທ່ານປ່ຽນລະຫັດຜ່ານສໍາເລັດເເລ້ວ";
    }
    public static class RESPONSE_FAIL_MESSAGE{
        public static final String STORE_MSG = "ບັນທຶກຂໍ້ມູນບໍ່ສໍາເລັດ";
        public static final String STORE_MSG_CHECK = "ເລກທີເອກະສານນີ້ຊໍ້າກັນ ຫຼື ມີໃນລະບົບເເລ້ວ !!!! ກະລຸນາກວດສອບຄືນ";
        public static final String UPDATE_MSG="ເເກ້ໄຂຂໍ້ມູນບໍ່ສໍາເລັດ";
        public static final String DEL_MSG="ລຶບຂໍ້ມູນບໍ່ສໍາເລັດ";


    }
}
