package com.bgdevs.madness.controllers.operation;

import com.bgdevs.madness.dao.repositories.OperationRepository;
import com.bgdevs.madness.service.operation.ExecuteCardOperationModel;
import com.bgdevs.madness.service.operation.OperationModel;
import com.bgdevs.madness.service.operation.OperationModelMapper;
import com.bgdevs.madness.service.operation.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<Object> findByCardId(@PathVariable Long cardId) {
        List<OperationModel> operations = this.operationRepository.findByCardIdOrderByOperationDate(cardId).stream()
                .map(OperationModelMapper::toModel)
                .collect(toList());
        return ResponseEntity.ok(operations);
    }

    @PostMapping("api/operations/call")
    public ResponseEntity<Object> callOperation(@Valid @RequestBody ExecuteCardOperationModel model) {
        this.operationService.executeCallOperations(model.getCardIds(), model.getAmount(), model.getDescription());
        return ResponseEntity.ok("Call operations executed for cards with ids: " + model.getCardIds());
    }

    @PostMapping("api/operations/put")
    public ResponseEntity<Object> putOperation(@Valid @RequestBody ExecuteCardOperationModel model) {
        this.operationService.executePutOperations(model.getCardIds(), model.getAmount(), model.getDescription());
        return ResponseEntity.ok("Put operations executed for cards with ids: " + model.getCardIds());
    }

}
