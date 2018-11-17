package com.bgdevs.madness.dao.entities.card.operation;

import javax.annotation.Nonnull;
import java.math.BigDecimal;

/**
 * @author Valeriy Knyazhev <valeriy.knayzhev@yandex.ru>
 */
public enum OperationType {

    DEBIT {
        @Override
        public BigDecimal calculateOperationSum(@Nonnull BigDecimal sum) {
            return sum;
        }
    },
    CREDIT {
        @Override
        public BigDecimal calculateOperationSum(@Nonnull BigDecimal sum) {
            return sum.negate();
        }
    };

    public abstract BigDecimal calculateOperationSum(@Nonnull BigDecimal sum);
}
