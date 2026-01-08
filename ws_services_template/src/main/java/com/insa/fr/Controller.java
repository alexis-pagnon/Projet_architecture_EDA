package com.insa.fr;

import com.insa.fr.entity.Alive;
import com.insa.fr.entity.Students;
import com.insa.fr.exceptions.NotAllowedOperationException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.insa.fr.services.Services_Interface;
import com.insa.fr.tools.ApiKey_Secure;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

/***************
 *
 * @author herve
 *************/

@RestController
@Api(value = "Application Template")
public class Controller {
 
    String VERSION="0.1";
    
    String WHOAMI="ws_services_template";
    
    @Autowired
    Services_Interface execservice;
    
    @Autowired
    JdbcTemplate jdbctemplate= new JdbcTemplate();
    
  //Construire les tools
    //====================
    
    //construire instance pour appel secureapi...
      @Autowired
       ApiKey_Secure securapi;
    
    /*************************************
     * Service de vie des service logs
     * @param xapikey
     * @return 
     **************************************/    
    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Tester la vie du service")
    public Alive IAmAlive(@RequestHeader("x-api-key") String xapikey)
    {
       //if (securapi.verif_apikey(xapikey)==true)
       if (securapi.verif_apikeydb(jdbctemplate,xapikey)==true)
        {
        return new Alive("42", VERSION, WHOAMI);
        } else {throw new NotAllowedOperationException("NotAllowedOperationException : Contactez l'administrateur pour obtenir un acces...");}
    }

    /*************************************
     * Service de vie des service logs
     * @param xapikey
     * @return 
     **************************************/        
    @GetMapping("/service")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Tester la vie du service")
    public Alive IAmAlive2(@RequestHeader("x-api-key") String xapikey)
    {
       //if (securapi.verif_apikey(xapikey)==true)
       if (securapi.verif_apikeydb(jdbctemplate,xapikey)==true)
        {
        return new Alive("42", VERSION, WHOAMI);
        } else {throw new NotAllowedOperationException("NotAllowedOperationException : Contactez l'administrateur pour obtenir un acces...");}
    }

    /*************************************
     * Service de vie des service logs
     * @param xapikey
     * @return 
     **************************************/    
    @GetMapping("/service/isalive")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Tester la vie du service")
    public Alive IAmAlive3(@RequestHeader("x-api-key") String xapikey)
    {
       //if (securapi.verif_apikey(xapikey)==true)
       if (securapi.verif_apikeydb(jdbctemplate,xapikey)==true)
        {
        return new Alive("42", VERSION, WHOAMI);
        } else {throw new NotAllowedOperationException("NotAllowedOperationException : Contactez l'administrateur pour obtenir un acces...");}
    }
    
    /****************************************
     * Lister tous les étudiants
     * @param xapikey
     * @return 
     ****************************************/
    @GetMapping(value="/service/students",produces={"application/json"})
    @ApiOperation(value =" Récuperer la liste des étudiants de la base postgresql")
    public ResponseEntity<Object> getStudents(@RequestHeader("x-api-key") String xapikey) {
       //if (securapi.verif_apikey(xapikey)==true)
       if (securapi.verif_apikeydb(jdbctemplate,xapikey)==true)
        {    
            //get data
           
            return new ResponseEntity<>(execservice.getStudents(), HttpStatus.OK);
        } else {throw new NotAllowedOperationException("NotAllowedOperationException : Contactez l'administrateur pour obtenir un acces...");}
   }

  //   /****************************************
  //    * Rechercher un étudiant par son Id
  //    * @param studentId
  //    * @param xapikey
  //    * @return 
  //    ****************************************/
  //   @GetMapping(value="/service/student/{id}",produces={"application/json"})
  //   @ApiOperation(value =" Récuperer un étudiant par son id")
  //   public ResponseEntity<Object> getStudent(@PathVariable(value = "id") String studentId,@RequestHeader("x-api-key") String xapikey) {
  //      //if (securapi.verif_apikey(xapikey)==true)
  //      if (securapi.verif_apikeydb(jdbctemplate,xapikey)==true)
  //       {        
  //           //get data
  //           return new ResponseEntity<>(execservice.getStudent(studentId), HttpStatus.OK);
  //       } else {throw new NotAllowedOperationException("NotAllowedOperationException : Contactez l'administrateur pour obtenir un acces...");}
  //  }

    
     /****************************************
     * Envoyer les données d'un étudiant
     * @param stud
     * @param xapikey
     * @return 
     ****************************************/
    @PostMapping(value="/service/student/add", consumes={"application/json"}, produces ={"application/json"})
    @ApiOperation(value = "Inserer un nouvel étudiant")
    public ResponseEntity<String> insertStudent(@RequestBody Students stud,@RequestHeader("x-api-key") String xapikey) {
       //if (securapi.verif_apikey(xapikey)==true)
       if (securapi.verif_apikeydb(jdbctemplate,xapikey)==true)
        {        
            //insert data
            int idr=execservice.createStudent(stud);
    
          return new ResponseEntity<>("{\"reponse\":\"student created: id="+idr+"\"}", HttpStatus.CREATED);
        } else {throw new NotAllowedOperationException("NotAllowedOperationException : Contactez l'administrateur pour obtenir un acces...");}
   }
    
   
  //    /****************************************
  //    * Modifier les données d'un étudiant
  //    * @param id
  //    * @param stud
  //    * @param xapikey
  //    * @return 
  //    ****************************************/
  //   @PatchMapping(value="/service/student/update/{id}", consumes={"application/json"}, produces ={"application/json"})
  //   @ApiOperation(value = "update un étudiant dans la base postgresql")
  //   public ResponseEntity<String> updateStudent(@PathVariable (value = "id") String id,@RequestBody Students stud,@RequestHeader("x-api-key") String xapikey) {
  //      //if (securapi.verif_apikey(xapikey)==true)
  //      if (securapi.verif_apikeydb(jdbctemplate,xapikey)==true)
  //       {        
  //           //update data
  //           execservice.updateStudent(stud,id);
    
  //         return new ResponseEntity<>("{\"reponse\":\"student updated\"}", HttpStatus.CREATED);
  //       } else {throw new NotAllowedOperationException("NotAllowedOperationException : Contactez l'administrateur pour obtenir un acces...");}
  //  }
    
  //    /****************************************
  //    * Supprimer un étudiant par son Id
  //    * @param id
  //    * @param xapikey
  //    * @return 
  //    ****************************************/
  //   @DeleteMapping(value="/service/student/delete/{id}")
  //   @ApiOperation(value = "Supprimer un étudiant dans la base postgresql")
  //   public ResponseEntity<String> deleteStudent(@PathVariable (value = "id") String id,@RequestHeader("x-api-key") String xapikey) {
  //      //if (securapi.verif_apikey(xapikey)==true)
  //      if (securapi.verif_apikeydb(jdbctemplate,xapikey)==true)
  //       {        
  //           //delete data
  //           execservice.deleteStudent(id);
    
  //         return new ResponseEntity<>("{\"reponse\":\"deleted\"}", HttpStatus.ACCEPTED);
  //       } else {throw new NotAllowedOperationException("NotAllowedOperationException : Contactez l'administrateur pour obtenir un acces...");}
  //  }
    
    
}