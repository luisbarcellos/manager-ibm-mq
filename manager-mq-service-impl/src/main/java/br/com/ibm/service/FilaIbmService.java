package br.com.ibm.service;

import br.com.ibm.converter.XmlToObjectConverter;
import br.com.ibm.exception.MensagemNotFoundException;
import br.com.ibm.facade.IntegracaoFacade;
import br.com.ibm.model.Mensagem;
import lombok.AllArgsConstructor;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
public class FilaIbmService {
    private IntegracaoFacade integracaoFacade;

    public void simularFilaIn(){
        integracaoFacade.simularFilaIn();
    }

    public void transferirMensagensFila(){
        integracaoFacade.mudarMsgFilaInToFilaOut();
    }

    public List<Mensagem> buscarMensagensFilaOut(Integer quantidade) {
        return convertStringToObject()
            .apply(buscarFilaOut(quantidade));
    }

    public List<Mensagem> buscarMensagensFilaIn(Integer quantidade) {
        return convertStringToObject()
                .apply(buscarFilaIn(quantidade));
    }

    private List<String> buscarFilaOut(Integer quantidade) {
        return Optional.ofNullable(integracaoFacade.buscarMensagensFilaOut(quantidade))
                .filter(list -> !ObjectUtils.isEmpty(list))
                .orElseThrow(() -> new MensagemNotFoundException());
    }

    private List<String> buscarFilaIn(Integer quantidade) {
        return Optional.ofNullable(integracaoFacade.buscarMensagensFilaIn(quantidade))
                .filter(list -> !ObjectUtils.isEmpty(list))
                .orElseThrow(() -> new MensagemNotFoundException());
    }

    private Function<List<String>, List<Mensagem>> convertStringToObject(){
        return list -> list.stream().parallel()
                .map(message -> XmlToObjectConverter.parseXmlToObjext(message))
                .collect(Collectors.toList());
    }
}