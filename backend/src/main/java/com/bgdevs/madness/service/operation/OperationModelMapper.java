package com.bgdevs.madness.service.operation;

import com.bgdevs.madness.dao.entities.card.operation.Operation;

import javax.annotation.Nonnull;

/**
 * @author Valeriy Knyazhev <valeriy.knayzhev@yandex.ru>
 */
public class OperationModelMapper {

    @Nonnull
    public static OperationModel toModel(@Nonnull Operation oper) {

        return new OperationModel(oper.getId(), oper.getCash(), oper.getType(),
                oper.getOperationDate(), oper.getDescription());
    }

}
