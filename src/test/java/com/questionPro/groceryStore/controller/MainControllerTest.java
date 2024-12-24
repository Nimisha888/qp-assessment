package com.questionPro.groceryStore.controller;

import com.questionPro.groceryStore.entity.GroceryItem;
import com.questionPro.groceryStore.entity.Role;
import com.questionPro.groceryStore.service.AdminService;
import com.questionPro.groceryStore.service.TokenService;
import com.questionPro.groceryStore.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class MainControllerTest {

    private MainController mainController;

    @Mock
    private AdminService adminService;

    @Mock
    private UserService userService;

    @Mock
    private TokenService tokenService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mainController = new MainController();
        mainController.adminService = adminService;
        mainController.userService = userService;
        mainController.tokenService = tokenService;
    }

    @Test
    void testGetGroceries() {
        // Arrange
        String token = "Bearer valid_token";
        when(tokenService.getRoleFromToken("valid_token")).thenReturn(String.valueOf(Role.USER));
        List<GroceryItem> groceries = List.of(
                new GroceryItem(1, "Apple", 1.5, 100)
        );
        when(userService.getAvailableGroceries()).thenReturn(groceries);

        // Act
        ResponseEntity<List<GroceryItem>> response = mainController.getGroceries(token);

        // Assert
        assertNotNull(response);
        assertEquals(1, response.getBody().size());
        verify(userService, times(1)).getAvailableGroceries();
    }

    @Test
    void testAddGroceryItem() {
        // Arrange
        String token = "Bearer valid_token";
        GroceryItem groceryItem = new GroceryItem(1, "Apple", 1.5, 100);
        when(tokenService.getRoleFromToken("valid_token")).thenReturn(String.valueOf(Role.ADMIN));
        when(adminService.addGroceryItem(groceryItem)).thenReturn(groceryItem);

        // Act
        ResponseEntity<GroceryItem> response = mainController.addGroceryItem(token, groceryItem);

        // Assert
        assertNotNull(response);
        assertEquals("Apple", response.getBody().getName());
        verify(adminService, times(1)).addGroceryItem(groceryItem);
    }

    @Test
    void testUpdateGroceryItem() {
        // Arrange
        String token = "Bearer valid_token";
        GroceryItem updatedItem = new GroceryItem(1, "Banana", 2.0, 150);
        when(tokenService.getRoleFromToken("valid_token")).thenReturn(String.valueOf(Role.ADMIN));
        when(adminService.updateGroceryItem(1, updatedItem)).thenReturn(updatedItem);

        // Act
        ResponseEntity<GroceryItem> response = mainController.updateGroceryItem(token, 1, updatedItem);

        // Assert
        assertNotNull(response);
        assertEquals("Banana", response.getBody().getName());
        verify(adminService, times(1)).updateGroceryItem(1, updatedItem);
    }

    @Test
    void testDeleteGroceryItem() {
        // Arrange
        String token = "Bearer valid_token";
        when(tokenService.getRoleFromToken("valid_token")).thenReturn(String.valueOf(Role.ADMIN));
        doNothing().when(adminService).deleteGroceryItem(1);

        // Act
        ResponseEntity<Void> response = mainController.deleteGroceryItem(token, 1);

        // Assert
        assertEquals(204, response.getStatusCode().value());
        verify(adminService, times(1)).deleteGroceryItem(1);
    }

}
