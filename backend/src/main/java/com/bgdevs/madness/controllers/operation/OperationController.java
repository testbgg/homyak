package com.bgdevs.madness.controllers.operation;

import com.bgdevs.madness.dao.entities.card.operation.Operation;
import com.bgdevs.madness.dao.repositories.OperationRepository;
import com.bgdevs.madness.service.operation.ExecuteCardOperationModel;
import com.bgdevs.madness.service.operation.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nonnull;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author Valeriy Knyazhev <valeriy.knyazhev@yandex.ru>
 */
@RestController
public class OperationController {

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private OperationService operationService;

    @GetMapping("api/cards/{cardId}/operations")
    public ResponseEntity<Object> findByCardId(@Nonnull Long cardId) {
        List<Operation> operations = this.operationRepository.findByCardId(cardId).stream()
                .sorted(Comparator.comparing(Operation::getOperationDate))
                .collect(toList());
        return ResponseEntity.ok(operations);
    }

    @PostMapping("/operations/call")
    public ResponseEntity<Object> callOperation(@RequestBody ExecuteCardOperationModel model) {
        this.operationService.executeCallOperations(model.getCardIds(), model.getAmount(), model.getDescription());
        return ResponseEntity.ok("Call operations executed for cards with ids: " + model.getCardIds());
    }

    @PostMapping("/operations/put")
    public ResponseEntity<Object> putOperation(@RequestBody ExecuteCardOperationModel model) {
        this.operationService.executePutOperations(model.getCardIds(), model.getAmount(), model.getDescription());
        return ResponseEntity.ok("Put operations executed for cards with ids: " + model.getCardIds());
    }

}
