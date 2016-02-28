package org.mbds.tpt.maslow.controllers;

import org.mbds.tpt.maslow.dao.AppareilDao;
import org.mbds.tpt.maslow.entities.Appareil;
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
 * Created by Zac on 27/02/2016.
 */
@RestController
@RequestMapping("/a")
public class AppareilController {

    @Autowired
    private AppareilDao appareilDao;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<List<Appareil>> addAppareil(@RequestBody List<Appareil> appareils){
        List<Appareil> appareil = new ArrayList<>();
        for(Appareil app:appareils){
            appareil.add(appareilDao.save(app));

        }
        return new ResponseEntity<>(appareil, HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<Appareil>> getAppareil(){
        return new ResponseEntity<List<Appareil>>((List<Appareil>) appareilDao.findAll(), HttpStatus.OK);
    }
}
