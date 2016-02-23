package org.mbds.tpt.maslow.controllers;

import org.mbds.tpt.maslow.dao.ProceduralDao;
import org.mbds.tpt.maslow.entities.Operation;
import org.mbds.tpt.maslow.entities.Procedural;
import org.mbds.tpt.maslow.entities.ProceduralPK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gael on 17/02/2016.
 */
@RestController
@RequestMapping("/p")
public class ProceduralController {

    @Autowired
    private ProceduralDao proceduralDao;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String createProcedural(@RequestBody Procedural procedural) {

        return null;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<Procedural> create() {
        ProceduralPK pk = new ProceduralPK(1, 21);
        Procedural p = new Procedural(pk);
        List<Operation> operations = new ArrayList<>();
        operations.add(new Operation());
        operations.add(new Operation());
        operations.add(new Operation());
        operations.add(new Operation());
        operations.add(new Operation());
        p.setOperations(operations);

        proceduralDao.save(p);
        return new ResponseEntity<Procedural>(proceduralDao.findOne(pk), HttpStatus.I_AM_A_TEAPOT);
    }

}
