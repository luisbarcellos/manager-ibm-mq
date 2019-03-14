package br.com.ibm.service;

import br.com.ibm.converter.XmlToObjectConverter;
import br.com.ibm.exception.MensagemNotFoundException;
import br.com.ibm.facade.IntegracaoFacadeJms;
import br.com.ibm.model.Mensagem;
import lombok.AllArgsConstructor;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
public class FilaIbmJmsService {
    private IntegracaoFacadeJms integracaoFacadeJms;

    public void simularFilaIn(){
        integracaoFacadeJms.simularFilaIn();
    }

    public void transferirMensagensFila(){
        integracaoFacadeJms.mudarMsgFilaInToFilaOut();
    }

    public List<Mensagem> buscarMensagensFilaInJms(Integer quantidade) {
        return convertStringToObject()
                .apply(buscarFilaInJms(quantidade));
    }

    public List<Mensagem> buscarMensagensFilaOut(Integer quantidade) {
        return convertStringToObject()
                .apply(buscarFilaOut(quantidade));
    }

    private List<String> buscarFilaOut(Integer quantidade) {
        return Optional.ofNullable(integracaoFacadeJms.buscarMensagensFilaOutJms(quantidade))
                .filter(list -> !ObjectUtils.isEmpty(list))
                .orElseThrow(() -> new MensagemNotFoundException());
    }

    private List<String> buscarFilaInJms(Integer quantidade) {
        return Optional.ofNullable(integracaoFacadeJms.buscarMensagensFilaInJms(quantidade))
                .filter(list -> !ObjectUtils.isEmpty(list))
                .orElseThrow(() -> new MensagemNotFoundException());
    }

    private Function<List<String>, List<Mensagem>> convertStringToObject(){
        return list -> list.stream().parallel()
                .map(message -> XmlToObjectConverter.parseXmlToObjext(message))
                .collect(Collectors.toList());
    }
}