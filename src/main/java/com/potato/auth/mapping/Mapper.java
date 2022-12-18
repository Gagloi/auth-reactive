package com.potato.auth.mapping;

import javax.swing.text.html.parser.Entity;

public interface Mapper <DTO, ENTITY>{

    DTO toDto(ENTITY entity);

    ENTITY toEntity(DTO dto);

}
