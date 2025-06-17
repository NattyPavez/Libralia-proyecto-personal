package com.nataliapavez.libralia.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageLinks{
    private String smallThumbnail;
    private String getSmallThumbnail;

    public String getSmallThumbnail() {
        return smallThumbnail;
    }

    public String getGetSmallThumbnail() {
        return getSmallThumbnail;
    }
}
