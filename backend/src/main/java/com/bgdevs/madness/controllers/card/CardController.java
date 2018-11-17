package com.bgdevs.madness.controllers.card;

import com.bgdevs.madness.dao.entities.card.Card;
import com.bgdevs.madness.dao.entities.card.CardType;
import com.bgdevs.madness.dao.repositories.CardRepository;
import com.bgdevs.madness.service.card.CardService;
import com.bgdevs.madness.service.card.CardService.AddLimitsModel;
import com.bgdevs.madness.service.card.model.CardModel;
import com.bgdevs.madness.service.card.model.CreateCardModel;
import com.bgdevs.madness.service.operation.ExecuteCardOperationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
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

    @Autowired
    private CardRepository cardRepository;

    @GetMapping("/types")
    public ResponseEntity<Object> getCardTypes() {
        List<String> types = Stream.of(CardType.values()).map(CardType::getLabel).collect(toList());
        return ResponseEntity.ok(types);
    }

    @GetMapping("/{cardId}")
    public ResponseEntity<Object> card(@PathVariable Long cardId) {
        Optional<Card> card = this.cardRepository.findById(cardId);
        return card.<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Object> requestCard(@Valid @RequestBody CreateCardModel createCardModel) {

        CardModel cardModel = this.cardService.requestCard(createCardModel);
        return ResponseEntity.created(create("/cards/" + cardModel.getId())).body(cardModel);
    }

    @PutMapping("/limits")
    public ResponseEntity<Object> addLimitsToCards(@RequestBody AddLimitsModel addLimitsModel) {
        this.cardService.addLimitToCard(addLimitsModel);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{cardId}/activate")
    public ResponseEntity<Object> activateCard(@PathVariable Long cardId) {
        this.cardService.activateCard(cardId);
        return ResponseEntity.ok("Card was activated.");
    }

    @PostMapping("/{cardId}/block")
    public ResponseEntity<Object> blockCard(@PathVariable Long cardId) {
        this.cardService.blockCard(cardId);
        return ResponseEntity.ok("Card was blocked.");
    }

    @PostMapping("/{cardId}/close")
    public ResponseEntity<Object> closeCard(@PathVariable Long cardId) {
        this.cardService.closeCard(cardId);
        return ResponseEntity.ok("Card was closed.");
    }

    @PostMapping("/{cardId}/reissue")
    public ResponseEntity<Object> reissueCard(@PathVariable Long cardId) {
        CardModel cardModel = this.cardService.reissueCard(cardId);
        return ResponseEntity.created(create("/cards/" + cardModel.getId())).body(cardModel);
    }

    @PostMapping("/{cardId}/operations/call")
    public ResponseEntity<Object> callOperation(@PathVariable Long cardId,
                                                @RequestBody ExecuteCardOperationModel model) {
        this.cardService.executeCallOperation(cardId, model.getAmount(), model.getDescription());
        return ResponseEntity.ok("Call operation executed for card with id: " + cardId);
    }

    @PostMapping("/{cardId}/operations/put")
    public ResponseEntity<Object> putOperation(@PathVariable Long cardId,
                                               @RequestBody ExecuteCardOperationModel model) {
        this.cardService.executePutOperation(cardId, model.getAmount(), model.getDescription());
        return ResponseEntity.ok("Put operation executed for card with id: " + cardId);
    }
}
