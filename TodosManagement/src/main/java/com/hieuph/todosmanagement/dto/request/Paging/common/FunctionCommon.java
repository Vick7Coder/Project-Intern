package com.hieuph.todosmanagement.dto.request.Paging.common;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;


public class FunctionCommon {
    public static String generateCode(String name) {
        return Normalizer.normalize(name, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .toLowerCase()
                .replaceAll("đ", "d")
                .replaceAll(" ", "-");
    }

    public static Map<String, Object> getResponseEntity(int status, Object data, boolean isSuccess) {
        Map<String, Object> result = new HashMap<>();
        result.put("status", status);
        String dataName = isSuccess ? "data" : "error";
        result.put(dataName, data);

        return result;
    }
}
