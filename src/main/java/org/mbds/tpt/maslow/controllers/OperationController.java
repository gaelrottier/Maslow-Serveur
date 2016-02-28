package org.mbds.tpt.maslow.controllers;

import org.mbds.tpt.maslow.dao.OperationDao;
import org.mbds.tpt.maslow.entities.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Zac on 27/02/2016.
 */
@RestController
@RequestMapping(value = "/o")
public class OperationController {

    @Autowired
    private OperationDao operationDao;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<?> createOperation(@RequestBody Operation operation){
        return new ResponseEntity<Operation>(operationDao.save(operation), HttpStatus.OK);
    }
}
