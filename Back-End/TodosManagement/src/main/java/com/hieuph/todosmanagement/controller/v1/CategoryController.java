package com.hieuph.todosmanagement.controller.v1;

import com.hieuph.todosmanagement.dto.request.CategoryDto;
import com.hieuph.todosmanagement.dto.response.MessageResponse;
import com.hieuph.todosmanagement.entity.Category;
import com.hieuph.todosmanagement.entity.User;
import com.hieuph.todosmanagement.exception.BadRequestException;
import com.hieuph.todosmanagement.exception.CustomExceptionRuntime;
import com.hieuph.todosmanagement.exception.InvalidVerificationTokenException;
import com.hieuph.todosmanagement.security.service.UserDetailImpl;
import com.hieuph.todosmanagement.service.CategoryService;
import com.hieuph.todosmanagement.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/category")
@RestController("category/v1")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;
    private Authentication authentication;
    @PostMapping("")
    public ResponseEntity<?> addCategory(@Valid @RequestBody CategoryDto categoryDto){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof AnonymousAuthenticationToken){
            throw new BadRequestException("The request has failed." +
                    " Please send the JWT of the user who needs to view the wishlist along with the request.");
        }
        User user = ((UserDetailImpl) authentication.getPrincipal()).getUser();
        User cat = user;
        categoryDto.setUsername(cat.getUsername());
        if (user != null){
            categoryService.create(categoryDto, cat);
            return ResponseEntity.ok(new MessageResponse("Create successfully!"));
        }
        return ResponseEntity.badRequest().body(new InvalidVerificationTokenException("User not Found!"));

    }
    @GetMapping("")
    public ResponseEntity<?> categoryListOfUser(){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof AnonymousAuthenticationToken){
            throw new BadRequestException("The request has failed." +
                    " Please send the JWT of the user who needs to view the wishlist along with the request.");
        }
        User user = ((UserDetailImpl) authentication.getPrincipal()).getUser();
        List<Category> categories = categoryService.getAll(user.getId());
        if (categories.size()<1){
            throw new CustomExceptionRuntime(200, "Empty Category");
        }
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/enabled-list")
    public ResponseEntity<?> enableCategoryList(){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken){
            throw new BadRequestException("The request has failed." +
                    " Please send the JWT of the user who needs to view the wishlist along with the request.");
        }
        Integer userId = ((UserDetailImpl) authentication.getPrincipal()).getUser().getId();
        List<Category> categories = categoryService.getAllEnabled(userId);
        if (categories.size()<1){
            throw new CustomExceptionRuntime(200, "Empty Category");
        }
        return ResponseEntity.ok(categories);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable int id, @Valid @RequestBody CategoryDto categoryDto){
        categoryService.update(id, categoryDto);
        return ResponseEntity.ok(new MessageResponse("Update Category Successfully"));
    }
    @PutMapping("/enable/{id}")
    public ResponseEntity<?> enableCatgory(@PathVariable int id){
        categoryService.enable(id);
        return ResponseEntity.ok(new MessageResponse("Enable successfully!"));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id){
        categoryService.delete(id);
        return ResponseEntity.ok(new MessageResponse("Delete successfully!"));
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable int id){
        Category category = categoryService.get(id);
        return ResponseEntity.ok(category);
    }


}
