package com.bgdevs.madness.controllers.card;

import com.bgdevs.madness.dao.entities.card.CardType;
import com.bgdevs.madness.service.card.CardService;
import com.bgdevs.madness.service.card.model.CardModel;
import com.bgdevs.madness.service.card.model.CreateCardModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Stream;

import static java.net.URI.create;
import static java.util.stream.Collectors.toList;

/**
 * @author Nikita Shaldenkov
 */
@RestController
@RequestMapping("api/cards")
public class CardController {


    @Autowired
    private CardService cardService;

    @GetMapping("/types")
    public ResponseEntity<Object> getCardTypes() {
        List<String> types = Stream.of(CardType.values()).map(CardType::getLabel).collect(toList());
        return ResponseEntity.ok(types);
    }

    @PostMapping
    public ResponseEntity<Object> createCard(@Valid @RequestBody CreateCardModel createCardModel) {
        CardModel cardModel = this.cardService.create(createCardModel);
        return ResponseEntity.created(create("/cards/" + cardModel.getId())).body(cardModel);
    }

    @PutMapping("/{cardId}")
    public ResponseEntity<Object> addLimitToCard(@PathVariable Long cardId,
                                                 @RequestBody @Valid LimitModel limit) {
        this.cardService.addLimitToCard(cardId, limit);
        return ResponseEntity.ok().build();

    }

}
