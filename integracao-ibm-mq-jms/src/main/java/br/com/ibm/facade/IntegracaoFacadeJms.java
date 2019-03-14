package br.com.ibm.facade;

import br.com.ibm.config.IntegracaoPropertiesLoader;
import br.com.ibm.exception.MensagemJmsException;
import br.com.ibm.mock.MsgJmsStub;
import br.com.ibm.service.IntegracaoServiceInJms;
import br.com.ibm.service.IntegracaoServiceOutJms;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@AllArgsConstructor
public class IntegracaoFacadeJms {
    private IntegracaoPropertiesLoader integracaoPropertiesLoader;
    private IntegracaoServiceInJms integracaoServiceInJms;
    private IntegracaoServiceOutJms integracaoServiceOutJms;

    public void simularFilaIn() {
        try {
            new ForkJoinPool(integracaoPropertiesLoader.getNumeroThreads()).submit(() ->
                    IntStream.range(0, integracaoPropertiesLoader.getQtdInicialFilaIn())
                            .parallel()
                            .forEach(i -> integracaoServiceInJms.enviarMensagem(MsgJmsStub.getMsg())));
        } catch (Exception e) {
            e.printStackTrace();
            throw new MensagemJmsException("Erro ao simular fila de entrada: " + e.getMessage());
        }
    }

    public void mudarMsgFilaInToFilaOut() {
        try {
            new ForkJoinPool(integracaoPropertiesLoader.getNumeroThreads()).submit(() ->
                    buscarMensagensFilaInJms(integracaoPropertiesLoader.getQtdInicialFilaIn())
                            .stream()
                            .parallel()
                            .forEach(mensagem -> Optional.ofNullable(integracaoServiceInJms.buscarMensagem())
                                    .ifPresent(mensagemIn -> integracaoServiceOutJms.enviarMensagem(mensagemIn))));
        } catch (Exception e) {
            e.printStackTrace();
            throw new MensagemJmsException("Erro ao transferir mensagens: " + e.getMessage());
        }
    }

    public List<String> buscarMensagensFilaInJms(Integer quantidade) {
        try {
            return new ForkJoinPool(integracaoPropertiesLoader.getNumeroThreads()).submit(() ->
                        IntStream.range(0, quantidade)
                                .parallel()
                                .mapToObj(i -> integracaoServiceInJms.buscarMensagem())
                                .filter(Objects::nonNull)
                                .collect(Collectors.toList())).get();
        } catch (Exception e) {
            e.printStackTrace();
            throw new MensagemJmsException("Erro ao buscar mensagem: " + e.getMessage());
        }
    }

    public List<String> buscarMensagensFilaOutJms(Integer quantidade) {
        try {
            return new ForkJoinPool(integracaoPropertiesLoader.getNumeroThreads()).submit(() ->
                    IntStream.range(0, quantidade)
                            .parallel()
                            .mapToObj(i -> integracaoServiceOutJms.buscarMensagem())
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList())).get();
        } catch (Exception e) {
            e.printStackTrace();
            throw new MensagemJmsException("Erro ao buscar mensagem: " + e.getMessage());
        }
    }
}