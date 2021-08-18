/*
 * JacksonSerializationIgnoreUnitTest.java
 *
 * Copyright (C) 2021 by Evotek. All right reserved.
 * This software is the confidential and proprietary information of Evotek
 */
package com.nagakawa.guarantee.dto.jackson;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nagakawa.guarantee.dto.MixInForIgnoreType;
import com.nagakawa.guarantee.model.dto.PrivilegeDTO;
import com.nagakawa.guarantee.model.dto.UserDTO;

/**
 * 05/06/2021 - LinhLH: Create new
 *
 * @author LinhLH
 */
public class JacksonSerializationIgnoreUnitTest {
    // tests - single entity to json

    // ignore

    @Test
    public final void givenOnlyNonDefaultValuesAreSerializedAndDtoHasOnlyDefaultValues_whenSerializing_thenCorrect()
            throws JsonParseException, IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final String dtoAsString = mapper.writeValueAsString(new UserDTO());

        assertThat(dtoAsString, not(containsString("id")));
        System.out.println(dtoAsString);
    }

    @Test
    public final void givenOnlyNonDefaultValuesAreSerializedAndDtoHasNonDefaultValue_whenSerializing_thenCorrect()
            throws JsonParseException, IOException {
        final ObjectMapper mapper = new ObjectMapper();
        
        final UserDTO userDTO = new UserDTO();
        
        userDTO.setStatus(1);

        final String dtoAsString = mapper.writeValueAsString(userDTO);

        assertThat(dtoAsString, containsString("status"));
        
        System.out.println(dtoAsString);
    }

    @Test
    public final void givenFieldIsIgnoredByName_whenDtoIsSerialized_thenCorrect()
            throws JsonParseException, IOException {
        final ObjectMapper mapper = new ObjectMapper();
        
        final UserDTO userDTO = new UserDTO();
        
        userDTO.setId(1L);

        final String dtoAsString = mapper.writeValueAsString(userDTO);

        assertThat(dtoAsString, not(containsString("id")));
        assertThat(dtoAsString, containsString("status"));
        
        System.out.println(dtoAsString);
    }

    @Test
    public final void givenFieldIsIgnoredDirectly_whenDtoIsSerialized_thenCorrect()
            throws JsonParseException, IOException {
        final ObjectMapper mapper = new ObjectMapper();
        
        final UserDTO userDTO = new UserDTO();

        final String dtoAsString = mapper.writeValueAsString(userDTO);

        assertThat(dtoAsString, not(containsString("id")));
        assertThat(dtoAsString, containsString("status"));
        
        System.out.println(dtoAsString);
    }

    // @Ignore("Jackson 2.7.1-1 seems to have changed the API for this case")
    @Test
    public final void givenFieldTypeIsIgnored_whenDtoIsSerialized_thenCorrect() throws JsonParseException, IOException {
        final ObjectMapper mapper = new ObjectMapper();
        
        mapper.addMixIn(String[].class, MixInForIgnoreType.class);
        
        final UserDTO userDTO = new UserDTO();
        
        userDTO.setStatus(1);

        final String dtoAsString = mapper.writeValueAsString(userDTO);

        assertThat(dtoAsString, containsString("id"));
        assertThat(dtoAsString, containsString("status"));
        assertThat(dtoAsString, not(containsString("lastModifiedDate")));
        
        System.out.println(dtoAsString);
    }

    @Test
    public final void givenNullsIgnoredOnClass_whenWritingObjectWithNullField_thenIgnored()
            throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        
        final UserDTO userDTO = new UserDTO();

        final String dtoAsString = mapper.writeValueAsString(userDTO);

        assertThat(dtoAsString, containsString("id"));
        assertThat(dtoAsString, containsString("status"));
        assertThat(dtoAsString, not(containsString("lastModifiedDate")));
        
        System.out.println(dtoAsString);
    }

    @Test
    public final void givenNullsIgnoredGlobally_whenWritingObjectWithNullField_thenIgnored()
            throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        
        mapper.setSerializationInclusion(Include.NON_NULL);
        
        final PrivilegeDTO privilegeDTO = new PrivilegeDTO();

        privilegeDTO.setName("UPDATE_ROLE");
        
        final String dtoAsString = mapper.writeValueAsString(privilegeDTO);

        assertThat(dtoAsString, containsString("id"));
        assertThat(dtoAsString, containsString("name"));
        assertThat(dtoAsString, not(containsString("description")));
        
        System.out.println(dtoAsString);
    }
}
