package com.bobwares.databus.common.model;

import lombok.Data;

import java.util.List;

@Data
public class HeaderField {

    private String label;
    private List<Field> properties;

}
