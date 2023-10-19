package com.hieuph.todosmanagement.controller.v2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hieuph.todosmanagement.Filter.Category.CategoryFilter;
import com.hieuph.todosmanagement.dto.request.CategoryDto;
import com.hieuph.todosmanagement.dto.request.Paging.PagingRequest;
import com.hieuph.todosmanagement.dto.request.Paging.Sorter;
import com.hieuph.todosmanagement.dto.response.MessageResponse;
import com.hieuph.todosmanagement.entity.Category;
import com.hieuph.todosmanagement.entity.User;
import com.hieuph.todosmanagement.exception.BadRequestException;
import com.hieuph.todosmanagement.exception.CustomExceptionRuntime;
import com.hieuph.todosmanagement.exception.InvalidVerificationTokenException;
import com.hieuph.todosmanagement.security.service.UserDetailImpl;
import com.hieuph.todosmanagement.service.CategoryService;
import com.hieuph.todosmanagement.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/v2/category")
@RestController("category/v2")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;
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
        if (user != null){
            categoryService.create(categoryDto, cat);
            return ResponseEntity.ok(new MessageResponse("Create successfully!"));
        }
        return ResponseEntity.badRequest().body(new InvalidVerificationTokenException("User not Found!"));

    }
    @GetMapping("")
    public ResponseEntity<?> categoryListOfUser(@RequestParam(name = "sort") String sort,
                                                @RequestParam(name = "range") String range,
                                                @RequestParam(name = "filter") String filter,
                                                HttpServletRequest request,
                                                HttpServletResponse response){
        try {
            authentication = SecurityContextHolder.getContext().getAuthentication();
            if(authentication instanceof AnonymousAuthenticationToken){
                throw new BadRequestException("The request has failed." +
                        " Please send the JWT of the user who needs to view the wishlist along with the request.");
            }
            User user = ((UserDetailImpl) authentication.getPrincipal()).getUser();
            List<String> _sort = objectMapper.readValue(sort, ArrayList.class);
            List<Integer> _range = objectMapper.readValue(range, ArrayList.class);
            CategoryFilter _filter = objectMapper.readValue(filter, CategoryFilter.class);
            StringBuilder contentRange = new StringBuilder("category ");
            contentRange.append(_range.get(0)).append("-").append(_range.get(1)).append("/").append(categoryService.count(_filter));
            response.setHeader("Content-Range", contentRange.toString());
            PagingRequest pagingRequest = new PagingRequest();
            pagingRequest.setLimit(_range.get(1) - _range.get(0) + 1);
            pagingRequest.setPage((int) Math.ceil(_range.get(0) / pagingRequest.getLimit() + 1));
            Sorter sorter = new Sorter();
            sorter.setName(_sort.get(0));
            sorter.setBy(_sort.get(1));
            pagingRequest.setSorter(sorter);

            List<Category> categories = categoryService.getAll(user, pagingRequest, _filter);
            if (categories.size()<1){
                throw new CustomExceptionRuntime(200, "Empty Category");
            }
            return ResponseEntity.ok(categories);
        }catch (Exception exception){
            throw new RuntimeException(exception);
        }
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
