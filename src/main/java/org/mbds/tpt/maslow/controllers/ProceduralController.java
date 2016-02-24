package org.mbds.tpt.maslow.controllers;

import org.mbds.tpt.maslow.dao.ProceduralDao;
import org.mbds.tpt.maslow.entities.Operation;
import org.mbds.tpt.maslow.entities.Procedural;
import org.mbds.tpt.maslow.entities.ProceduralPK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gael on 17/02/2016.
 */
@RestController
@RequestMapping("/u/{idUtilisateur}/p")
public class ProceduralController {

    @Autowired
    private ProceduralDao proceduralDao;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String createProcedural(@RequestBody Procedural procedural) {

        return null;
    }

    @RequestMapping(value = "/{idProcedural}", method = RequestMethod.GET)
    public ResponseEntity<String> readProcedure(@PathVariable int idUtilisateur, @PathVariable int idProcedural) {
        return new ResponseEntity<>(proceduralDao.findOne(new ProceduralPK(idUtilisateur, idProcedural)).toString(), HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<Procedural> create() {
        ProceduralPK pk = new ProceduralPK(1, 2);
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
