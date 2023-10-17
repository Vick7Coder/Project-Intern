package com.hieuph.todosmanagement.controller.v2;

import com.hieuph.todosmanagement.dto.request.TodoDto;
import com.hieuph.todosmanagement.dto.response.MessageResponse;
import com.hieuph.todosmanagement.entity.Todo;
import com.hieuph.todosmanagement.entity.User;
import com.hieuph.todosmanagement.exception.BadRequestException;
import com.hieuph.todosmanagement.exception.CustomExceptionRuntime;
import com.hieuph.todosmanagement.security.service.UserDetailImpl;
import com.hieuph.todosmanagement.service.TodoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v2/todo")
@RestController
@Slf4j
public class TodoController {
    @Autowired
    private TodoService todoService;
    private Authentication authentication;
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id){
        todoService.delete(id);
        return ResponseEntity.ok(new MessageResponse("Delete Todo Successfully"));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @Valid @RequestBody TodoDto todoDto){
        todoService.update(id, todoDto);
        return ResponseEntity.ok(new MessageResponse("Update Todo Successfully"));
    }

    @PostMapping("")
    public ResponseEntity<?> create(@Valid @RequestBody TodoDto todoDto){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof AnonymousAuthenticationToken){
            throw new BadRequestException("The request has failed." +
                    " Please send the JWT of the user who needs to view the wishlist along with the request.");
        }

        User user = ((UserDetailImpl) authentication.getPrincipal()).getUser();
        if (user != null){
            todoService.create(todoDto, user);
            return ResponseEntity.ok(new MessageResponse("Create successfully!"));
        }
        return ResponseEntity.badRequest().body(new MessageResponse("User or Category is not Found!"));
    }

    @GetMapping("")
    public ResponseEntity<?> getAllTodoOfUser(){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof AnonymousAuthenticationToken){
            throw new BadRequestException("The request has failed." +
                    " Please send the JWT of the user who needs to view the wishlist along with the request.");
        }
        User user = ((UserDetailImpl) authentication.getPrincipal()).getUser();
        List<Todo> todos = todoService.getAll(user);
        if (todos.size()<1){
            return ResponseEntity.ok(new MessageResponse("Empty Todo!"));
        }
        return ResponseEntity.ok(todos);
    }
    @GetMapping("/category/{id}")
    public ResponseEntity<?> getAllTodoByCategoryOfUser(@PathVariable int id){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof AnonymousAuthenticationToken){
            throw new BadRequestException("The request has failed." +
                    " Please send the JWT of the user who needs to view the wishlist along with the request.");
        }
        User user = ((UserDetailImpl) authentication.getPrincipal()).getUser();
        List<Todo> todos = todoService.getAllByCategory(user, id);
        if (todos.size()<1){
            return ResponseEntity.ok(new MessageResponse("Empty Todo!"));
        }
        return ResponseEntity.ok(todos);

    }
    @GetMapping("/unfinished-list")
    public ResponseEntity<?> unfinishedList(){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof  AnonymousAuthenticationToken){
            throw new BadRequestException("The request has failed." +
                    " Please send the JWT of the user who needs to view the wishlist along with the request.");
        }
        Integer userId =  ((UserDetailImpl) authentication.getPrincipal()).getUser().getId();
        List<Todo> todos = todoService.getAllUnfinished(userId);
        if(todos.size()<1){
            throw new CustomExceptionRuntime(200, "Empty complete todo");
        }
        return ResponseEntity.ok(todos);
    }

    @GetMapping("/finished-list")
    public ResponseEntity<?> finishedList(){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof  AnonymousAuthenticationToken){
            throw new BadRequestException("The request has failed." +
                    " Please send the JWT of the user who needs to view the wishlist along with the request.");
        }
        Integer userId =  ((UserDetailImpl) authentication.getPrincipal()).getUser().getId();
        List<Todo> todos = todoService.getALlFinished(userId);
        if(todos.size()<1){
            throw new CustomExceptionRuntime(200, "Empty complete todo");
        }
        return ResponseEntity.ok(todos);
    }

    @PutMapping("/complete/{id}")
    public ResponseEntity<?> setComplete(@PathVariable int id){
        todoService.switchState(id);
        return ResponseEntity.ok(new MessageResponse("Complete!"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable int id){
        Todo todo = todoService.findById(id);
        return ResponseEntity.ok(todo);
    }


}
