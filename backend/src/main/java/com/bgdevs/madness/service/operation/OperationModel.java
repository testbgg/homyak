package com.bgdevs.madness.service.operation;

import com.bgdevs.madness.dao.entities.card.operation.OperationType;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Valeriy Knyazhev <valeriy.knayzhev@yandex.ru>
 */
@Value
public class OperationModel {

    private long id;

    private BigDecimal cash;

    private OperationType type;

    private LocalDateTime operationDate;

    private String description;

}
