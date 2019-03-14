package br.com.ibm.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class IntegracaoPropertiesLoader {
    @Value("${ibm.mq.in.queueManager}")
    private String queueManagerIn;
    @Value("${ibm.mq.in.channel}")
    private String channelIn;
    @Value("${ibm.mq.in.host}")
    private String hostIn;
    @Value("${ibm.mq.in.port}")
    private Integer portIn;
    @Value("${ibm.mq.in.user}")
    private String userIn;
    @Value("${ibm.mq.in.password}")
    private String passwordIn;
    @Value("${ibm.mq.in.queue-name}")
    private String queueIn;

    @Value("${ibm.mq.out.queueManager}")
    private String queueManagerOut;
    @Value("${ibm.mq.out.channel}")
    private String channelOut;
    @Value("${ibm.mq.out.host}")
    private String hostOut;
    @Value("${ibm.mq.out.port}")
    private Integer portOut;
    @Value("${ibm.mq.out.user}")
    private String userOut;
    @Value("${ibm.mq.out.password}")
    private String passwordOut;
    @Value("${ibm.mq.out.queue-name}")
    private String queueOut;

    @Value("${numero-threads}")
    private Integer numeroThreads;

    @Value("${qtd-inicial-fila-in}")
    private Integer qtdInicialFilaIn;

    @Value("${qtd-transferir}")
    private Integer qtdTransferir;

    @Value("${time-out}")
    private Integer timeOut;
}