package deu.cse.spring_webmail.control;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class WriteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testWriteMail() throws Exception {
        mockMvc.perform(get("/write_mail"))
                .andExpect(status().isOk())
                .andExpect(view().name("write_mail/write_mail"));
    }

    @Test
    public void testWriteMailDo() throws Exception {
        // 메일 송신 테스트
        MockMultipartFile file = new MockMultipartFile("file1", "test.txt", "text/plain", "test content".getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/write_mail.do")
                        .file(file)
                        .param("to", "test")
                        .param("cc", "")
                        .param("subj", "Unit Test subj")
                        .param("body", "Unit Testing..."))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/main_menu"));
    }

}