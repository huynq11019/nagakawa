/*
 * IgnoreFieldsWithFilterUnitTest.java
 *
 * Copyright (C) 2021 by Evotek. All right reserved.
 * This software is the confidential and proprietary information of Evotek
 */
package com.nagakawa.guarantee.dto.jackson;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.nagakawa.guarantee.model.dto.UserDTO;

/**
 * 05/06/2021 - LinhLH: Create new
 *
 * @author LinhLH
 */
public class IgnoreFieldsWithFilterUnitTest {
    @Test
    public final void givenTypeHasFilterThatIgnoresFieldByName_whenDtoIsSerialized_thenCorrect()
            throws JsonParseException, IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final SimpleBeanPropertyFilter theFilter = SimpleBeanPropertyFilter.serializeAllExcept("intValue");
        final FilterProvider filters = new SimpleFilterProvider().addFilter("myFilter", theFilter);

        final UserDTO userDTO = new UserDTO();

        userDTO.setId(12L);
        userDTO.setFullname("LinhLH2");

        final String dtoAsString = mapper.writer(filters).writeValueAsString(userDTO);

        assertThat(dtoAsString, not(containsString("id")));
        assertThat(dtoAsString, containsString("fullName"));
        assertThat(dtoAsString, containsString("dateOfBirth"));

        System.out.println(dtoAsString);
    }

    @Test
    public final void givenTypeHasFilterThatIgnoresNegativeInt_whenDtoIsSerialized_thenCorrect()
            throws JsonParseException, IOException {
        final PropertyFilter theFilter = new SimpleBeanPropertyFilter() {
            @Override
            public final void serializeAsField(final Object pojo, final JsonGenerator jgen,
                    final SerializerProvider provider, final PropertyWriter writer) throws Exception {
                if (include(writer)) {
                    if (!writer.getName().equals("id")) {
                        writer.serializeAsField(pojo, jgen, provider);
                        return;
                    }

                    final long id = ((UserDTO) pojo).getId();
                    if (id >= 0) {
                        writer.serializeAsField(pojo, jgen, provider);
                    }
                } else if (!jgen.canOmitFields()) { // since 2.3
                    writer.serializeAsOmittedField(pojo, jgen, provider);
                }
            }

            @Override
            protected final boolean include(final BeanPropertyWriter writer) {
                return true;
            }

            @Override
            protected final boolean include(final PropertyWriter writer) {
                return true;
            }
        };
        
        final FilterProvider filters = new SimpleFilterProvider().addFilter("myFilter", theFilter);

        final UserDTO userDTO = new UserDTO();
        userDTO.setId(-1L);

        final ObjectMapper mapper = new ObjectMapper();
        final String dtoAsString = mapper.writer(filters).writeValueAsString(userDTO);

        assertThat(dtoAsString, not(containsString("id")));
        assertThat(dtoAsString, containsString("fullName"));
        assertThat(dtoAsString, containsString("dateOfBirth"));

        System.out.println(dtoAsString);
    }
}
