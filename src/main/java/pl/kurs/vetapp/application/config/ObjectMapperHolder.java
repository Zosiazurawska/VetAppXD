package pl.kurs.vetapp.application.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;

public enum ObjectMapperHolder {
    INSTANCE;
    private final ObjectMapper mapper;

    private static ObjectMapper create(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
        return mapper;
    }
    ObjectMapperHolder() {
        this.mapper = create();
    }
    public ObjectMapper getMapper() {
        return mapper;
    }


}