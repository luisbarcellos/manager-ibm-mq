package br.com.ibm.converter;

import br.com.ibm.model.Mensagem;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class XmlToObjectConverter {
    public static Mensagem parseXmlToObjext(String xml) {
        try {
            return new XmlMapper().readValue(xml, Mensagem.class);
        } catch (Exception e) {
            log.error("Erro ao parsear xml to object: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}