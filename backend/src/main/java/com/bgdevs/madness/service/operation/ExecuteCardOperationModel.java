package com.bgdevs.madness.service.operation;

import lombok.Value;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author Valeriy Knyazhev <valeriy.knayzhev@yandex.ru>
 */
@Value
public class ExecuteCardOperationModel {

    @NotNull
    private BigDecimal amount;

    @NotNull
    private String description;

}
