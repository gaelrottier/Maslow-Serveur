package org.mbds.tpt.maslow.ws;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/u")
public class UtilisateurResource {

    @RequestMapping(value = {"/", ""}, method = GET, produces = "application/json")
    public String getAllUsers() {
        JSONObject json = new JSONObject().put("name", "toto");
        return json.toString();
    }

    @RequestMapping(value = {"/", ""}, method = POST)
    public HttpStatus createUser(@RequestParam String nom, @RequestParam String prenom) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @RequestMapping(value = {"/", ""}, method = PUT)
    public HttpStatus updateUser(@RequestParam String nom, @RequestParam String prenom) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @RequestMapping(value = {"/", ""}, method = DELETE)
    public HttpStatus deleteUser(@RequestParam int id) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

}