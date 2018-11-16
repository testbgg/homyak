package com.bgdevs.madness.controllers.card;

import com.bgdevs.madness.dao.entities.card.CardType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * @author Nikita Shaldenkov
 */
@RestController
@RequestMapping("/cards")
public class CardController {


    @GetMapping("/types")
    public ResponseEntity<?> getCardTypes() {
        List<String> types = Stream.of(CardType.values()).map(CardType::getLabel).collect(toList());
        return ResponseEntity.ok(types);
    }


}
