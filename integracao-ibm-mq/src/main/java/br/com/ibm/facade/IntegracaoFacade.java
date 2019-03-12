package br.com.ibm.facade;

import br.com.ibm.Exception.MensagemException;
import br.com.ibm.config.IntegracaoPropertiesLoader;
import br.com.ibm.mock.MsgVirtualCobStub;
import br.com.ibm.service.IntegracaoServiceIn;
import br.com.ibm.service.IntegracaoServiceOut;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@AllArgsConstructor
public class IntegracaoFacade {
    private IntegracaoServiceIn integracaoServiceIn;
    private IntegracaoServiceOut integracaoServiceOut;
    private IntegracaoPropertiesLoader integracaoPropertiesLoader;

    public void simularFilaIn() {
        try {
            new ForkJoinPool(integracaoPropertiesLoader.getNumeroThreads()).submit(() ->
                    IntStream.range(0, integracaoPropertiesLoader.getQtdInicialFilaIn())
                            .parallel()
                            .forEach(i -> integracaoServiceIn.enviarMsg(MsgVirtualCobStub.getMsg())));
        } catch (Exception e) {
            e.printStackTrace();
            throw new MensagemException("Erro ao simular fila de entrada: " + e.getMessage());
        }
    }

    public void mudarMsgFilaInToFilaOut() {
        try {
            new ForkJoinPool(integracaoPropertiesLoader.getNumeroThreads()).submit(() ->
                    buscarMensagensFilaIn(integracaoPropertiesLoader.getQtdInicialFilaIn())
                            .stream()
                            .parallel()
                            .forEach(mensagem -> Optional.ofNullable(integracaoServiceIn.buscarMsg())
                                    .ifPresent(mensagemIn -> integracaoServiceOut.enviarMsg(mensagemIn))));
        } catch (Exception e) {
            e.printStackTrace();
            throw new MensagemException("Erro ao transferir mensagens: " + e.getMessage());
        }
    }

    public List<String> buscarMensagensFilaOut(Integer quantidade) {
        try {
            return new ForkJoinPool(integracaoPropertiesLoader.getNumeroThreads()).submit(() ->
                IntStream.range(0, quantidade)
                        .parallel()
                        .mapToObj(i -> integracaoServiceOut.buscarMsg())
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList())).get();
        } catch (Exception e) {
            e.printStackTrace();
            throw new MensagemException("Erro ao buscar mensagem: " + e.getMessage());
        }
    }

    public List<String> buscarMensagensFilaIn(Integer quantidade) {
        try {
            return new ForkJoinPool(integracaoPropertiesLoader.getNumeroThreads()).submit(() ->
                        IntStream.range(0, quantidade)
                                .parallel()
                                .mapToObj(i -> integracaoServiceIn.buscarMsg())
                                .filter(Objects::nonNull)
                                .collect(Collectors.toList())).get();
        } catch (Exception e) {
            e.printStackTrace();
            throw new MensagemException("Erro ao buscar mensagem: " + e.getMessage());
        }
    }
}