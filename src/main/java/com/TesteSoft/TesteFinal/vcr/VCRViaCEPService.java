package com.TesteSoft.TesteFinal.vcr;

import com.TesteSoft.TesteFinal.model.ViaCEP;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class VCRViaCEPService {

    private final VCRService vcrService;
    private RestTemplate restTemplate;

    public VCRViaCEPService(VCRService vcrService) {
        this.vcrService = vcrService;
        this.restTemplate = new RestTemplate();
    }

    public ViaCEP buscarEnderecoPorCEP(String cep, String cassetteName, boolean recordMode) {
        String cepFormatado = cep.replaceAll("[^0-9]", "");

        if (cepFormatado.length() != 8) {
            throw new IllegalArgumentException("CEP inválido: deve conter 8 dígitos");
        }

        String url = "https://viacep.com.br/ws/" + cepFormatado + "/json/";

        if (recordMode) {
            VCRInterceptor interceptor = new VCRInterceptor(vcrService, cassetteName, true);
            restTemplate = new RestTemplate();
            restTemplate.getInterceptors().add(interceptor);
        } else {
            if (!vcrService.cassetteExists(cassetteName)) {
                throw new RuntimeException("Cassette " + cassetteName + " não encontrado. Execute em modo record primeiro.");
            }

            try {
                VCRRecording recording = vcrService.loadCassette(cassetteName);

                if (!recording.getInteractions().isEmpty()) {
                    String recordedResponse = recording.getInteractions().get(0).getResponseBody();
                    return parseViaCEPResponse(recordedResponse);
                }
            } catch (Exception e) {
                throw new RuntimeException("Erro ao carregar resposta gravada: " + e.getMessage(), e);
            }
        }

        try {
            ResponseEntity<ViaCEP> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    ViaCEP.class
            );

            ViaCEP result = response.getBody();
            if (result != null) {
                result.setCep(cepFormatado);
            }

            return result;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar CEP na API ViaCEP: " + e.getMessage(), e);
        }
    }

    private ViaCEP parseViaCEPResponse(String jsonResponse) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ViaCEP viaCEP = mapper.readValue(jsonResponse, ViaCEP.class);

            if (viaCEP.getCep() != null) {
                viaCEP.setCep(viaCEP.getCep().replaceAll("[^0-9]", ""));
            }

            return viaCEP;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter JSON gravado em ViaCEP: " + e.getMessage(), e);
        }
    }
}
