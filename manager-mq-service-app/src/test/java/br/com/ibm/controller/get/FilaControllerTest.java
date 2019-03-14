package br.com.ibm.controller.get;

import br.com.ibm.controller.FilaController;
import br.com.ibm.controller.stub.MsgStub;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    public void buscarMensagemSucesso() throws Exception{
        when(filaController.buscarMensagensFilaIn(1)).thenReturn(MsgStub.getMsgList());

        this.mockMvc.perform(get("/ibm-fila/buscar/filain?quantidade=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(status().is(200))
                .andReturn().getRequest().equals(MsgStub.getMsgList());
    }

    @Test
    public void buscarMensagemNotFound() throws Exception{
        when(filaController.buscarMensagensFilaIn(1)).thenThrow(MensagemNotFoundException.class);

        this.mockMvc.perform(get("/ibm-fila/buscar/filain?quantidade=1"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(status().is(404));
    }
}