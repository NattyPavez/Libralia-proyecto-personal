package com.nataliapavez.libralia.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConversorLibroService implements IConversorLibroService{
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T convertir(String json, Class<T> clase) {
        try {
            return objectMapper.readValue(json, clase);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}


