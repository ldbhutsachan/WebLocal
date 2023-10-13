package com.ldb.reportcustom.utils;

import com.ldb.reportcustom.entities.Border;
import com.ldb.reportcustom.repositories.BorderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Create at 03/01/2023 - 9:00 AM
 * Project Name reportCustom
 *
 * @author kataii
 */
@Slf4j
@Component
public class LnswFunction {

    @Autowired
    private BorderRepository borderRepository;

    public String borderIdCondit(String columnName) {
        // ຊອກຫາ ROLE ທີໃຊ້ໃນການລອກອິນເຂົ້າລະບົບ
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        List<String> borders = new ArrayList<>();
        /**
         * ກວດສອບວ່າ ROLE ທີ່ໃຊ້ໃນການລອກອິນເຂົ້າລະບົບເປັນ ROLE_BORDER ຫຼື ບໍ່ ຖ້າແມ່ນໃຫ້ເອົາ BORDER_ID ທີ່ມີຢູ່.o
         */
        if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_BORDER"))) {
            borders = borderRepository.findByBorderIdFromUserName(auth.getName()).stream().map(Border::getBorderId).collect(Collectors.toList());
        } else {
            List<String> roleNames = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
            System.out.println("borderIds= " + roleNames);

            /**
             * Get detail User from boder.login
             */
            borders = borderRepository.findByRolesName(roleNames).stream().map(Border::getBorderId).collect(Collectors.toList());

        }

        String borderId = "";

        borderId = " AND " + columnName + " IN " + splitString(borders);

        log.info("Border id = " + borderId);
        return borderId;
    }


    public static String splitString(String data) {
        String[] strData = data.split(",");
        String border = "('";
        for (int i = 0; i < strData.length; i++) {
            if (i < strData.length - 1) {
                border += strData[i] + "', '";
            } else {
                border += strData[i] + "')";
            }
        }
        return border;
    }

    public static String splitString(List<String> strData) {
//        String[] strData = data.split(",");
        String border = "('";
        for (int i = 0; i < strData.size(); i++) {
            if (i < strData.size() - 1) {
                border += strData.get(i) + "', '";
            } else {
                border += strData.get(i) + "')";
            }
        }
        return border;
    }


    /**
     * Function sum object Map<String, Double>
     * @param maps
     * @return Map<String, Double>
     */
    public static Map<String, Double> reduceDoubles(List<Map<String, Double>> maps) {
        return maps.stream()
                .flatMap(map -> map.entrySet().stream())
                .reduce(new HashMap<>(), (map, e) -> {
                    map.compute(e.getKey(), (k ,v) -> v == null ? e.getValue() : e.getValue() + v);
                    return map;
                }, (m1, m2) -> { throw new UnsupportedOperationException(); });
    }

    public static void main(String[] args) {
        List<Map<String, Long>> mapList = new ArrayList();
        Map<String, Long>       map1    = new HashMap<>();
        Map<String, Long>       map2    = new HashMap<>();
        Map<String, Long>       map3    = new HashMap<>();
        map1.put("col1", 90L);
        map1.put("col2", 50L);
        map1.put("col3", 10L);
        map2.put("col1", 90L);
        map2.put("col2", 50L);
        map2.put("col3", 10L);
        map3.put("col1", 90L);
        map3.put("col2", 50L);
        map3.put("col3", 10L);
        mapList.add(map1);
        mapList.add(map2);
        mapList.add(map3);

        Map<String, Long> sum = new HashMap<>();
        mapList.forEach(map -> map.keySet().forEach(
                s -> {
                    mapList.stream()
                            .collect(Collectors.groupingBy(foo -> s,
                                    Collectors.summingLong(foo -> map.get(s)))).forEach(
                                    (id, sumTargetCost) ->
                                            sum.put(s, sumTargetCost)
                            );
                }

        ));

        Long sumVal1 = sum.get("col1"); // 270
        Long sumVal2 = sum.get("col2"); // 150
        Long sumVal3 = sum.get("col3"); // 30

        System.out.println("SumVal1: " + sumVal1 + ", SumVal2: " + sumVal2 + ", SumVal3: " + sumVal3);
    }
}
