package com.example.test;

import com.example.wallet.WalletAppApplication;
import com.example.wallet.controllers.WalletController;
import com.example.wallet.dto.request.TransferRequest;
import com.example.wallet.dto.request.WalletOperationRequest;
import com.example.wallet.dto.response.CustomSuccessResponse;
import com.example.wallet.dto.response.WalletOperationResponse;
import com.example.wallet.service.WalletService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.UUID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ContextConfiguration(classes = WalletAppApplication.class)
@WebMvcTest(WalletController.class)
class WalletControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WalletService walletService;

    @Autowired
    private ObjectMapper objectMapper;

    private WalletOperationRequest walletOperationRequest;
    private TransferRequest transferRequest;
    private WalletOperationResponse walletOperationResponse;
    private CustomSuccessResponse<String> customSuccessResponse;

    @BeforeEach
    void setUp() {
        walletOperationRequest = new WalletOperationRequest("CREATE", UUID.randomUUID(), 100L);
        transferRequest = new TransferRequest(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"),
                UUID.fromString("123e4567-e89b-12d3-a456-426614174001"), 1L);
        walletOperationResponse = new WalletOperationResponse(UUID.fromString(
                "123e4567-e89b-12d3-a456-426614174000"), 100L);
        customSuccessResponse = new CustomSuccessResponse<>("Transfer successful");
    }

    @Test
    void createWallet_shouldCallServiceAndReturnResponse() throws Exception {
        when(walletService.createWallet(any(WalletOperationRequest.class)))
                .thenReturn(walletOperationResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/wallets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(walletOperationRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(walletOperationResponse)));
    }

    @Test
    void updateWallet_shouldCallServiceAndReturnResponse() throws Exception {
        when(walletService.updateWallet(any(WalletOperationRequest.class)))
                .thenReturn(walletOperationResponse);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/wallets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(walletOperationRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(walletOperationResponse)));
    }

    @Test
    void deleteWallet_shouldCallServiceAndReturnNoContent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/wallets/{walletId}", UUID.randomUUID()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void getWallet_shouldCallServiceAndReturnResponse() throws Exception {
        when(walletService.getWallet(any(UUID.class)))
                .thenReturn(walletOperationResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/wallets/{walletId}", UUID.randomUUID()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(walletOperationResponse)));
    }

    @Test
    void performOperation_shouldCallServiceAndReturnResponse() throws Exception {
        when(walletService.performOperation(any(WalletOperationRequest.class)))
                .thenReturn(walletOperationResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/wallets/operation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(walletOperationRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(walletOperationResponse)));
    }

    @Test
    void transfer_shouldCallServiceAndReturnResponse() throws Exception {
        when(walletService.transfer(any(TransferRequest.class)))
                .thenReturn(customSuccessResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/wallets/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transferRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(customSuccessResponse)));
    }
}