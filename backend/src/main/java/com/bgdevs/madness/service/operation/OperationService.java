package com.bgdevs.madness.service.operation;

import com.bgdevs.madness.dao.entities.card.Card;
import com.bgdevs.madness.dao.entities.card.operation.Operation;
import com.bgdevs.madness.dao.repositories.CardRepository;
import com.bgdevs.madness.dao.repositories.OperationRepository;
import com.bgdevs.madness.service.exceptions.ElementNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author Nikita Shaldenkov
 */
@Service
public class OperationService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private OperationRepository operationRepository;

    @Transactional
    public void executeCallOperations(@Nonnull List<Long> cardIds, @Nonnull BigDecimal amount, @Nonnull String description) {
        cardIds.forEach(id -> executeCallOperation(id, amount, description));
    }

    @Transactional
    public void executePutOperations(@Nonnull List<Long> cardIds, @Nonnull BigDecimal amount, @Nonnull String description) {
        cardIds.forEach(id -> executePutOperation(id, amount, description));
    }

    private void executeCallOperation(@Nonnull Long cardId, @Nonnull BigDecimal amount, @Nonnull String description) {
        Card card = this.cardRepository.findById(cardId)
                .orElseThrow(() -> new ElementNotFoundException(cardId));
        Operation operation = card.executeCallOperation(amount, description);
        this.operationRepository.save(operation);
    }

    private void executePutOperation(@Nonnull Long cardId, @Nonnull BigDecimal amount, @Nonnull String description) {
        Card card = this.cardRepository.findById(cardId)
                .orElseThrow(() -> new ElementNotFoundException(cardId));
        Operation operation = card.executePutOperation(amount, description);
        this.operationRepository.save(operation);
    }

}
