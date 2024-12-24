package com.questionPro.groceryStore.controller;

import com.questionPro.groceryStore.entity.GroceryItem;
import com.questionPro.groceryStore.entity.Orders;
import com.questionPro.groceryStore.entity.Role;
import com.questionPro.groceryStore.service.AdminService;
import com.questionPro.groceryStore.service.UserService;
import com.questionPro.groceryStore.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MainController {

    @Autowired
    AdminService adminService;

    @Autowired
    UserService userService;

    @Autowired
    TokenService tokenService;

    @GetMapping("/groceries")
    public ResponseEntity<List<GroceryItem>> getGroceries(@RequestHeader("Authorization") String token) {

        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        } else {
            throw new IllegalArgumentException("Invalid Authorization header format.");
        }
        tokenService.validateToken(token);
        String roleString = tokenService.getRoleFromToken(token);

        Role role;
        try {
            role = Role.valueOf(roleString);
        } catch (IllegalArgumentException e) {
            throw new AccessDeniedException("Invalid role!");
        }

        if (role == Role.ADMIN) {
            return ResponseEntity.ok(adminService.getAllGroceries());
        } else if (role == Role.USER) {
            return ResponseEntity.ok(userService.getAvailableGroceries());
        }

        throw new AccessDeniedException("Invalid role!");
    }
    @PostMapping("/groceries")
    public ResponseEntity<GroceryItem> addGroceryItem(
            @RequestHeader("Authorization") String token,
            @RequestBody GroceryItem groceryItem) {

        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        } else {
            throw new IllegalArgumentException("Invalid Authorization header format.");
        }

        tokenService.validateToken(token);
        String roleString = tokenService.getRoleFromToken(token);

        Role role;
        try {
            role = Role.valueOf(roleString);
        } catch (IllegalArgumentException e) {
            throw new AccessDeniedException("Invalid role!");
        }
        if (role == Role.ADMIN) {
            return ResponseEntity.ok(adminService.addGroceryItem(groceryItem));
        }
        throw new AccessDeniedException("Only admins can add grocery items!");
    }

    @PutMapping("/groceries/{id}")
    public ResponseEntity<GroceryItem> updateGroceryItem(
            @RequestHeader("Authorization") String token,
            @PathVariable Integer id,
            @RequestBody GroceryItem groceryItem) {

        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        } else {
            throw new IllegalArgumentException("Invalid Authorization header format.");
        }

        tokenService.validateToken(token);
        String roleString = tokenService.getRoleFromToken(token);

        Role role;
        try {
            role = Role.valueOf(roleString);
        } catch (IllegalArgumentException e) {
            throw new AccessDeniedException("Invalid role!");
        }
        if (role == Role.ADMIN) {
            return ResponseEntity.ok(adminService.updateGroceryItem(id, groceryItem));
        }
        throw new AccessDeniedException("Only admins can update grocery items!");
    }


    @PatchMapping("/groceries/{id}/inventory")
    public ResponseEntity<GroceryItem> updateInventory(
            @RequestHeader("Authorization") String token,
            @PathVariable Integer id,
            @RequestParam int quantity) {


        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        } else {
            throw new IllegalArgumentException("Invalid Authorization header format.");
        }

        tokenService.validateToken(token);
        String roleString = tokenService.getRoleFromToken(token);

        Role role;
        try {
            role = Role.valueOf(roleString);
        } catch (IllegalArgumentException e) {
            throw new AccessDeniedException("Invalid role!");
        }
        if (role == Role.ADMIN) {
            return ResponseEntity.ok(adminService.updateInventory(id, quantity));
        }
        throw new AccessDeniedException("Only admins can update inventory!");
    }


    @DeleteMapping("/groceries/{id}")
    public ResponseEntity<Void> deleteGroceryItem(
            @RequestHeader("Authorization") String token,
            @PathVariable Integer id) {


        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        } else {
            throw new IllegalArgumentException("Invalid Authorization header format.");
        }

        tokenService.validateToken(token);
        String roleString = tokenService.getRoleFromToken(token);


        Role role;
        try {
            role = Role.valueOf(roleString);
        } catch (IllegalArgumentException e) {
            throw new AccessDeniedException("Invalid role!");
        }
        if (role == Role.ADMIN) {
            adminService.deleteGroceryItem(id);
            return ResponseEntity.noContent().build();
        }
        throw new AccessDeniedException("Only admins can delete grocery items!");
    }


    @PostMapping("/orders")
    public ResponseEntity<Orders> placeOrder(
            @RequestHeader("Authorization") String token,
            @RequestBody Orders order) {


        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        } else {
            throw new IllegalArgumentException("Invalid Authorization header format.");
        }
        tokenService.validateToken(token);
        String roleString = tokenService.getRoleFromToken(token);


        Role role;
        try {
            role = Role.valueOf(roleString);
        } catch (IllegalArgumentException e) {
            throw new AccessDeniedException("Invalid role!");
        }
        if (role == Role.USER) {
            return ResponseEntity.ok(userService.placeOrder(order,token));
        }
        throw new AccessDeniedException("Only users can place orders!");
    }
}

