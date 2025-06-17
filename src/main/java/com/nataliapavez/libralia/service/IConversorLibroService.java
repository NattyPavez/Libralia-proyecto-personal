package com.nataliapavez.libralia.service;

public interface IConversorLibroService {

    <T> T convertir(String json, Class<T> clase);

}
