package org.mbds.tpt.maslow.controllers;

import org.mbds.tpt.maslow.dao.AppareilDao;
import org.mbds.tpt.maslow.dao.EvenementDao;
import org.mbds.tpt.maslow.entities.Appareil;
import org.mbds.tpt.maslow.entities.Evenement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zac on 27/02/2016.
 */
@RestController
@RequestMapping(value = "/e", method = RequestMethod.POST)
public class EvenementController {

    @Autowired
    private EvenementDao evenementDao;

    @Autowired
    private AppareilDao appareilDao;

    @RequestMapping(value = "/{idAppareil}", method = RequestMethod.POST)
    public ResponseEntity<List<Evenement>> addEvenementsAppareil(@RequestBody List<Evenement> evenements, @PathVariable("idAppareil") Integer idapp){
        List<Evenement> events = new ArrayList<>();
        for (Evenement ev: evenements){
            events.add(evenementDao.save(ev));
        }
        Appareil app = appareilDao.findOne(idapp);
        app.setEvenements(events);
        appareilDao.save(app);
        return new ResponseEntity<List<Evenement>>(events,HttpStatus.OK);
    }



}
