package com.bgdevs.madness.service.operation;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author Valeriy Knyazhev <valeriy.knayzhev@yandex.ru>
 */
@Data
public class ExecuteCardOperationModel {

    @NotNull
    private BigDecimal amount;

    @NotNull
    private String description;

}
