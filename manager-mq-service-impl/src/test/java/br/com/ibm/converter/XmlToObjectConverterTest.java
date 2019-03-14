package br.com.ibm.converter;

import br.com.ibm.exception.ConverterException;
import br.com.ibm.stub.MsgStub;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class XmlToObjectConverterTest {
    @Test
    public void deverRealizarParseXmlToObjext(){
        assertEquals(XmlToObjectConverter.parseXmlToObjext(MsgStub.getMsgXml()),
                     MsgStub.getMsg());
    }

    @Test(expected = ConverterException.class)
    public void deveRetornarErroAoParsearXml() {
        XmlToObjectConverter.parseXmlToObjext(MsgStub.getMsgXmlComErro());
    }
}