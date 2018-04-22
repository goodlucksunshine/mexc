package com.mexc.web.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 17/12/10
 * Time: 下午4:02
 */
public class JsonObjectMapper extends ObjectMapper {

    private static final long serialVersionUID = 1L;

    public JsonObjectMapper() {
        super();
        // 空值处理为空串
        this.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
            @Override
            public void serialize(Object value, JsonGenerator jg, SerializerProvider sp) throws IOException, JsonProcessingException {
                if (value instanceof Collection) {
                    jg.writeString("[]");
                } else if (value instanceof Map) {
                    jg.writeString("{}");
                } else if (value instanceof String) {
                    jg.writeString("");
                } else if (value instanceof Number) {
                    jg.writeString("");
                } else if (value instanceof Boolean) {
                    jg.writeString("");
                } else if (value instanceof Character) {
                    jg.writeString("");
                } else if (isJavaClass(value.getClass())) {
                    jg.writeString("");
                }

            }
        });
    }

    public static boolean isJavaClass(Class<?> clz) {
        return clz != null && clz.getClassLoader() == null;
    }
}
