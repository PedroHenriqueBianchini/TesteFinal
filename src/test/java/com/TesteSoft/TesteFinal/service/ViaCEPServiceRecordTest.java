package com.TesteSoft.TesteFinal.service;

import com.TesteSoft.TesteFinal.model.ViaCEP;
import com.TesteSoft.TesteFinal.vcr.VCRViaCEPService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ViaCEPServiceRecordTest {

    @Autowired
    private VCRViaCEPService vcrViaCEPService;

    @Test
    void deveGravarCasseteComRespostaReal() {

        String cep = "01001000";

        String cassetteName = "via_cep_cassette";

        ViaCEP resposta = vcrViaCEPService.buscarEnderecoPorCEP(cep, cassetteName, true);

        Assertions.assertNotNull(resposta);
        Assertions.assertEquals("01001000", resposta.getCep());
        Assertions.assertNotNull(resposta.getLogradouro());
        Assertions.assertNotNull(resposta.getLocalidade());
        Assertions.assertNotNull(resposta.getUf());

        System.out.println("Cassete gravado com sucesso em src/test/resources/vcr_cassettes/");
    }
}
