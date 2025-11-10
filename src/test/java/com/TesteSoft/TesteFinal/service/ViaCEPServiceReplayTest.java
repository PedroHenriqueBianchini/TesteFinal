package com.TesteSoft.TesteFinal.service;

import com.TesteSoft.TesteFinal.model.ViaCEP;
import com.TesteSoft.TesteFinal.vcr.VCRViaCEPService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ViaCEPServiceReplayTest {

    @Autowired
    private VCRViaCEPService vcrViaCEPService;

    @Test
    void deveLerCasseteGravadoSemAcessarAPIReal() {
        String cep = "01001000";
        String cassetteName = "via_cep_cassette";

        ViaCEP resposta = vcrViaCEPService.buscarEnderecoPorCEP(cep, cassetteName, false);

        Assertions.assertNotNull(resposta, "A resposta não deveria ser nula");
        Assertions.assertEquals("01001000", resposta.getCep(), "O CEP retornado deve coincidir");
        Assertions.assertNotNull(resposta.getLogradouro(), "Logradouro deve estar preenchido");
        Assertions.assertNotNull(resposta.getLocalidade(), "Localidade deve estar preenchida");
        Assertions.assertNotNull(resposta.getUf(), "UF deve estar preenchida");

        System.out.println("Replay realizado com sucesso utilizando cassete salvo em src/test/resources/vcr_cassettes/");
    }
}
