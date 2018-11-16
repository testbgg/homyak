package com.bgdevs.madness.service.card;

import com.bgdevs.madness.dao.entities.card.Card;
import com.bgdevs.madness.dao.repositories.CardRepository;
import com.bgdevs.madness.service.card.model.CardModel;
import com.bgdevs.madness.service.card.model.CardModelMapper;
import com.bgdevs.madness.service.card.model.CreateCardModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.bgdevs.madness.service.card.model.CardModelMapper.toModel;

/**
 * @author Nikita Shaldenkov
 */
@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    //todo add unique validation
    public CardModel create(CreateCardModel createCardModel) {
        Card saved = this.cardRepository.save(toEntity(createCardModel));
        return toModel(saved);
    }

    //todo
    private Card toEntity(CreateCardModel createCardModel) {
        return null;
    }

    public Page<CardModel> findByInvoiceId(long invoiceId, Pageable pageable) {
        return this.cardRepository.findByInvoiceId(invoiceId, pageable)
                .map(CardModelMapper::toModel);
    }
}
