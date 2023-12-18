package com.techbank.account.cmd.api.controller;

import com.techbank.account.cmd.api.commands.OpenAccountCommand;
import com.techbank.account.cmd.api.dto.OpenAccountResponse;
import com.techbank.account.common.dto.BaseResponse;
import com.techbank.cqrs.core.infra.CommandDispatcher;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/openBankAccount")
@Log4j2
public class OpenAccountController {
    @Autowired
    private CommandDispatcher commandDispatcher;

    @PostMapping("")
    public ResponseEntity<BaseResponse> openAccount(@RequestBody OpenAccountCommand command) {
        var id = UUID.randomUUID().toString();
        command.setId(id);
        try {
            commandDispatcher.send(command);
            return new ResponseEntity<>(new OpenAccountResponse("Bank Account creation rq completed successfully", id), HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            log.warn("Client made a bad rq {}", e.toString());
            return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            var message = MessageFormat.format("Error while processing request to open a new bank account for id {0}", id);
            log.error(message);
            return new ResponseEntity<>(new OpenAccountResponse(message, id), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
