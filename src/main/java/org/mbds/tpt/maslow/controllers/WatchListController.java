package org.mbds.tpt.maslow.controllers;

import org.mbds.tpt.maslow.dao.*;
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
@RequestMapping("/w")
public class WatchListController {

    @Autowired
    WatchListDao watchListDao;

    @Autowired
    UtilisateurDao utilisateurDao;

    @Autowired
    AppareilDao appareilDao;

    @Autowired
    EvenementDao evenementDao;

    @Autowired
    ProceduralDao proceduralDao;

    @Autowired
    OperationDao operationDao;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<?> readWatchList() {
   /*     WatchList w = new WatchList();
        w.setId(1);
        List<Appareil> appareils = new ArrayList<>();
        List<Evenement> evenements = new ArrayList<>();

        Evenement e1 = new Evenement();
        e1.setIdOrchestra("azerty");
        e1.setAlias("{'nomMaslow':'on', 'nomOrchestra' : 'switchOn'}");
        evenements.add(e1);

        Evenement e2 = new Evenement();
        e2.setIdOrchestra("qsdfg");
        e2.setAlias("{'nomMaslow':'off', 'nomOrchestra' : 'switchOff'}");
        evenements.add(e2);

        Appareil a1 = new Appareil();
        appareilDao.save(a1);
        evenementDao.save(e1);
        evenementDao.save(e2);
        a1.setEvenements(evenements);

        appareils.add(a1);

        evenements = new ArrayList<>();

        Evenement e3 = new Evenement();
        e3.setIdOrchestra("azerty");
        e3.setAlias("{'nomMaslow':'on', 'nomOrchestra' : 'switchOn'}");
        evenements.add(e3);

        Evenement e4 = new Evenement();
        e4.setIdOrchestra("qsdfg");
        e4.setAlias("{'nomMaslow':'off', 'nomOrchestra' : 'switchOff'}");
        evenements.add(e4);

        Appareil a2 = new Appareil();
        appareilDao.save(a2);

        evenementDao.save(e3);
        evenementDao.save(e4);
        a2.setEvenements(evenements);

        appareils.add(a2);

        w.setAppareils(appareils);

        watchListDao.save(w);
*/
        ProceduralPK pk = new ProceduralPK(1, 7);
        Procedural p = new Procedural(pk);

        List<Operation> operations = new ArrayList<>();
        Operation o1 = new Operation();
        o1.setIdOrchestra("jhkazekjhazkejheaz");
        o1.setParametres("{'param1', 'param2'}");

        Operation o2 = new Operation();
        o2.setIdOrchestra("poiuytreza");
        o2.setParametres("{'param1', 'param2'}");

        o1.setProcedural(p);
        o2.setProcedural(p);

        operations.add(o1);
        operations.add(o2);

        p.setOperations(operations);

        proceduralDao.save(p);
        operationDao.save(o1);
        operationDao.save(o2);

        return new ResponseEntity<>(p.toString(), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> readWatchList(@PathVariable int id, @RequestParam String token) {
        try {

            if (utilisateurDao.existsWithToken(token)) {

                return new ResponseEntity<>(watchListDao.findOne(id), HttpStatus.OK);


            } else {
                throw new IllegalAccessException("Le token est erroné");
            }

        } catch (NullPointerException e) {
            return new ResponseEntity<>("La WatchList demandée n'existe pas.", HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
