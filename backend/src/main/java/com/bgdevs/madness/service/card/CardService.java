package com.bgdevs.madness.service.card;

import com.bgdevs.madness.dao.entities.card.Card;
import com.bgdevs.madness.dao.repositories.CardRepository;
import com.bgdevs.madness.service.card.model.CardModel;
import com.bgdevs.madness.service.card.model.CardModelMapper;
import com.bgdevs.madness.service.card.model.CreateCardModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.bgdevs.madness.service.card.model.CardModelMapper.toModel;
import static java.util.stream.Collectors.toList;

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

    public List<CardModel> findByInvoiceId(long invoiceId) {
        return this.cardRepository.findByInvoiceId(invoiceId).stream()
                .map(CardModelMapper::toModel)
                .collect(toList());
    }
}
