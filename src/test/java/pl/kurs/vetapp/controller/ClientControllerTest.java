package pl.kurs.vetapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.kurs.vetapp.application.service.ClientService;
import pl.kurs.vetapp.domain.model.Client;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@WebMvcTest(ClientController.class)
@AutoConfigureMockMvc
class ClientControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClientService clientService;

    @Test
    void createClient() throws Exception {
        Client client = new Client();
        client.setId(1L);
        client.setFirstname("John Doe");

        when(clientService.createClient(any(Client.class))).thenReturn(1L);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/client/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(client)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string("Client created property with ID: 1"))
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        System.out.println(responseBody);
    }

//    @Test
//    void editClient() throws Exception {
//        Client client = new Client();
//        client.setId(1L);
//        client.setFirstname("John Doe");
//
//        when(clientService.editClient(anyLong(), any(Client.class))).thenReturn(client);
//
//        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/client/edit/{id}", 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(client)))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().string("Client edited property"))
//                .andReturn();
//
//        String responseBody = mvcResult.getResponse().getContentAsString();
//        System.out.println(responseBody);
//    }

    @Test
    void deleteClient() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/client/delete/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Client deleted property with ID:"))
                .andDo(print());
    }

    @Test
    void getClientByID() throws Exception {
        Client client = new Client();
        client.setId(1L);
        client.setFirstname("John Doe");

        when(clientService.findById(anyLong())).thenReturn(client);

        mockMvc.perform(MockMvcRequestBuilders.get("/client/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstname").value("John Doe"))
                .andDo(print());
    }

//    @Test
//    void getDoctorsWithPagination() throws Exception {
//        Page<Client> clientPage = ...; // Initialize with mock data
//
//        when(clientService.findAllWithPagination(any())).thenReturn(clientPage);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/client/getall?page=0&size=2"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content.length()").value(2))
//                .andDo(print());
//    }

    @Test
    void getDoctorsWithoutPagination() throws Exception {
        List<Client> clients = Arrays.asList(new Client(), new Client());

        when(clientService.findAllWithoutPagination()).thenReturn(clients);

        mockMvc.perform(MockMvcRequestBuilders.get("/client"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andDo(print());
    }

    @Test
    void sayHello() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/client/hello"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("hello!"))
                .andDo(print());
    }
}
