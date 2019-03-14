package br.com.ibm.controller.post;

import br.com.ibm.Exception.MensagemNotFoundException;
import br.com.ibm.controller.FilaController;
import br.com.ibm.controller.stub.MsgStub;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(FilaControllerTest.class)
public class FilaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FilaController filaController;

    @Test
    public void simularCip() throws Exception{
        doNothing().when(filaController).simularCip();

        this.mockMvc.perform(post("/ibm-fila/simular/filain"))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andExpect(status().is(204));
    }

    @Test
    public void transferirMensagensFila() throws Exception{
        doNothing().when(filaController).transferirMensagensFila();

        this.mockMvc.perform(post("/ibm-fila/transferir/filain-to-filaout"))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andExpect(status().is(204));
    }
}