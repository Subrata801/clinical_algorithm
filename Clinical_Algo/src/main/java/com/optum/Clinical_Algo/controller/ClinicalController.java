package com.optum.Clinical_Algo.controller;

import com.optum.Clinical_Algo.dto.OrderRequest;
import org.drools.decisiontable.InputType;
import org.drools.decisiontable.SpreadsheetCompiler;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@RestController
public class ClinicalController {

    /*private final KieContainer kieContainer;

    public ClinicalController(KieContainer kieContainer) {
        this.kieContainer = kieContainer;
    }*/

    @GetMapping("/test")
    public ResponseEntity test(){
        System.out.println("hello test");
       return new ResponseEntity<String>(HttpStatus.OK);
    }

    @PostMapping("/discount")
    private OrderRequest getDiscountPercent(@RequestBody OrderRequest orderRequest) {
        //KieSession kieSession = kieContainer.newKieSession();
        KieSession kieSession = getSession();
        kieSession.insert(orderRequest);
        kieSession.fireAllRules();
        kieSession.dispose();
        return orderRequest;
    }

    /*
    * Use this method, when the .xls rule file can be changed during the application runtime
    * if the .xls file changed, then also we can get new rules, since the session is getting created each time
    * the request hits by the user. Make sure the .xls file is closed after update.
    * */
    private KieSession getSession(){
        String rulesExcel = "C:\\Users\\subprama\\Subrata\\MY_Personal\\Project\\SRM\\Ditipriya\\discount.xlsx";
        InputStream is = null;
        try {
            is= new FileInputStream(rulesExcel);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        SpreadsheetCompiler sc = new SpreadsheetCompiler();
        String rules=sc.compile(is, InputType.XLS);
        return new KieHelper().addContent(rules, ResourceType.DRL).build().newKieSession();
    }
}
