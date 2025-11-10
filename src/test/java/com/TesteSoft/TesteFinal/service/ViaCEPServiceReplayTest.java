package com.TesteSoft.TesteFinal.service;

import com.TesteSoft.TesteFinal.model.ViaCEP;
import com.TesteSoft.TesteFinal.vcr.VCRViaCEPService;
import com.TesteSoft.TesteFinal.vcr.VCRService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ViaCEPServiceReplayTest {

    @Autowired
    private VCRService vcrService;

    @Autowired
    private VCRViaCEPService vcrViaCEPService;

    private static final String CASSETTE_NAME = "via_cep_cassette";

    @BeforeAll
    static void limparCasseteAntigo(@Autowired VCRService vcrService) {
        // executa apenas uma vez, antes de todos os testes
        vcrService.deleteCassetteIfExists(CASSETTE_NAME);
    }

    @Test
    void deveGravarCasseteComChamadaReal() {
        String cep = "01001000";

        ViaCEP resultado = vcrViaCEPService.buscarEnderecoPorCEP(cep, CASSETTE_NAME, true);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getCep()).isEqualTo("01001000");
        assertThat(resultado.getLocalidade()).isEqualTo("São Paulo");
        assertThat(resultado.getUf()).isEqualTo("SP");

        System.out.println("Cassete gravado com sucesso:");
        System.out.println("CEP: " + resultado.getCep());
        System.out.println("Logradouro: " + resultado.getLogradouro());
        System.out.println("Localidade: " + resultado.getLocalidade());
        System.out.println("UF: " + resultado.getUf());
    }

    @Test
    void deveReproduzirCasseteGravadoSemAcessarAPIReal() {
        String cep = "01001000";

        ViaCEP resultado = vcrViaCEPService.buscarEnderecoPorCEP(cep, CASSETTE_NAME, false);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getCep()).isEqualTo("01001000");

        System.out.println("Cassete reproduzido com sucesso:");
        System.out.println("CEP: " + resultado.getCep());
        System.out.println("Logradouro: " + resultado.getLogradouro());
        System.out.println("Localidade: " + resultado.getLocalidade());
        System.out.println("UF: " + resultado.getUf());
    }
}
