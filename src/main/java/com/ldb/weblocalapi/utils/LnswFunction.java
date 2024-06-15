package com.ldb.weblocalapi.utils;

import com.ldb.weblocalapi.entities.Section;
import com.ldb.weblocalapi.repositories.SectionRepository;
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
    private SectionRepository sectionRepository;

    public String borderIdCondit(String columnName) {
        // ຊອກຫາ ROLE ທີໃຊ້ໃນການລອກອິນເຂົ້າລະບົບ
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        List<String> branch = new ArrayList<>();

        if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_BORDER"))) {
            branch = sectionRepository.findByBranchIdFromUserName(auth.getName()).stream().map(Section::getSecId).collect(Collectors.toList());
        } else {
            List<String> roleNames = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
            System.out.println("borderIds= " + roleNames);
            branch = sectionRepository.findByRolesName(roleNames).stream().map(Section::getSecId).collect(Collectors.toList());

        }
        String branchId = "";
        //=================================cheack session User then send to query =====================================================
        List<String> roleNamesBorder = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        String roleNamesBorderString = String.join(", ", roleNamesBorder);
        if(roleNamesBorderString.equals("ROLE_REPORT_ADMIN") ){
            branchId = "";
        }else {
            branchId = " AND " + columnName + " IN " + splitString(branch);
        }
        log.info("branchId id = " + branchId);
        return branchId;
        //=================================cheack session User then send to queey=====================================================
    }
    public static String splitString(String data) {
        String[] strData = data.split(",");
        String branch = "('";
        for (int i = 0; i < strData.length; i++) {
            if (i < strData.length - 1) {
                branch += strData[i] + "', '";
            } else {
                branch += strData[i] + "')";
            }
        }
        return branch;
    }

    public static String splitString(List<String> strData) {
//        String[] strData = data.split(",");
        String branch = "('";
        for (int i = 0; i < strData.size(); i++) {
            if (i < strData.size() - 1) {
                branch += strData.get(i) + "', '";
            } else {
                branch += strData.get(i) + "')";
            }
        }
        return branch;
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
