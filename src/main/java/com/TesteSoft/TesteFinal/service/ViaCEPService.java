package com.TesteSoft.TesteFinal.service;

import com.TesteSoft.TesteFinal.model.ViaCEP;
import com.TesteSoft.TesteFinal.vcr.VCRViaCEPService;
import org.springframework.stereotype.Service;

@Service
public class ViaCEPService {

    private final VCRViaCEPService vcrViaCEPService;

    public ViaCEPService(VCRViaCEPService vcrViaCEPService) {
        this.vcrViaCEPService = vcrViaCEPService;
    }

    public ViaCEP buscarEnderecoPorCEP(String cep) {
        String cassetteName = "via_cep_cassette";

        boolean recordMode = false;

        return vcrViaCEPService.buscarEnderecoPorCEP(cep, cassetteName, recordMode);
    }

    public ViaCEP buscarEnderecoPorCEPGravar(String cep) {
        String cassetteName = "via_cep_cassette";
        boolean recordMode = true;

        return vcrViaCEPService.buscarEnderecoPorCEP(cep, cassetteName, recordMode);
    }
}
