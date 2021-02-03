package admin.asset.controller;

import admin.asset.entity.Member;
import admin.asset.entity.MemberForm;
import admin.asset.entity.enumclass.MemberType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Rollback(value = false)
@AutoConfigureMockMvc
class MemberControllerTest  {

    @Autowired private MockMvc mockMvc;
    @Autowired private AdminController adminController;
    @Autowired private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() throws Exception {

        mockMvc = MockMvcBuilders.standaloneSetup(adminController)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
//                .alwaysDo(print())
                .build();

    }

    @Test
    public void hello() throws Exception {
        mockMvc.perform(get("/hello")
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @BeforeEach
    public void singUp() throws Exception  {
        MemberForm memberForm = MemberForm.builder()
                .email("gassina92@gmail.com")
                .password("gmltyd0380")
                .checkPassword("gmltyd0380")
                .name("hs")
                .contactA("01000000000")
                .contactB("01011111111")
                .build();
        String content = objectMapper.writeValueAsString(memberForm);

        mockMvc.perform(post("/join")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void login() throws Exception {
        registerAdmin();
        Member member = Member.builder()
                .email("gassina92@gmail.com")
                .password("gmltyd0380")
                .build();
        String content = objectMapper.writeValueAsString(member);

        mockMvc.perform(post("/admin/assetList")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void registerAdmin() throws Exception {
        Member member = Member.builder()
                .email("gassina92@gmail.com")
                .build();
        String content = objectMapper.writeValueAsString(member);

        mockMvc.perform(post("/admin/register")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    public void assetList() throws Exception {
        registerAdmin();
        Member member = Member.builder()
                .email("gassina92@gmail.com")
                .build();
        String content = objectMapper.writeValueAsString(member);

        mockMvc.perform(post("/admin/assetList")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

}