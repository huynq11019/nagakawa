/*
 * EmailAnnotationPlugin.java
 *
 * Copyright (C) 2021 by Evotek. All right reserved.
 * This software is the confidential and proprietary information of Evotek
 */
package com.nagakawa.guarantee.springfox.plugin;

import static springfox.bean.validators.plugins.Validators.annotationFromBean;

import java.util.Optional;

import javax.validation.constraints.Email;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import springfox.bean.validators.plugins.Validators;
import springfox.documentation.builders.StringElementFacetBuilder;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;

/**
 * 03/06/2021 - LinhLH: Create new
 *
 * @author LinhLH
 */
@Component
@Order(Validators.BEAN_VALIDATOR_PLUGIN_ORDER)
public class EmailAnnotationPlugin implements ModelPropertyBuilderPlugin {

    @Override
    public boolean supports(DocumentationType delimiter) {
        return false;
    }

    @Override
    public void apply(ModelPropertyContext context) {
        Optional<Email> email = annotationFromBean(context, Email.class);
        
        if (email.isPresent()) {
            context.getSpecificationBuilder().facetBuilder(StringElementFacetBuilder.class)
              .pattern(email.get().regexp());
            context.getSpecificationBuilder().example("email@email.com");
        }
    }

}
