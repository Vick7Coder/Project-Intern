package com.hieuph.todosmanagement.controller.v2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hieuph.todosmanagement.Filter.Todo.TodoFilter;
import com.hieuph.todosmanagement.Filter.User.UserFilter;
import com.hieuph.todosmanagement.dto.request.Paging.PagingRequest;
import com.hieuph.todosmanagement.dto.request.Paging.Sorter;
import com.hieuph.todosmanagement.dto.request.TodoDto;
import com.hieuph.todosmanagement.dto.response.MessageResponse;
import com.hieuph.todosmanagement.entity.Todo;
import com.hieuph.todosmanagement.entity.User;
import com.hieuph.todosmanagement.exception.BadRequestException;
import com.hieuph.todosmanagement.exception.CustomExceptionRuntime;
import com.hieuph.todosmanagement.security.service.UserDetailImpl;
import com.hieuph.todosmanagement.service.TodoService;
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

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/v2/todo")
@RestController("todo/v2")
@Slf4j
public class TodoController {
    @Autowired
    private TodoService todoService;
    @Autowired
    private UserService userService;
    @Autowired
    private ObjectMapper objectMapper;
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
        User inp = userService.getUserByUsername(todoDto.getUsername());
        if (user != null){
            todoService.create(todoDto, inp);
            return ResponseEntity.ok(todoDto);
        }
        return ResponseEntity.badRequest().body(new MessageResponse("User or Category is not Found!"));
    }

    @GetMapping("")
    public ResponseEntity<?> todoList(@RequestParam(name = "filter") String filter,
                                      @RequestParam(name = "range") String range,
                                      @RequestParam(name = "sort") String sort,
                                      HttpServletRequest request,
                                      HttpServletResponse response){
        try {
            authentication = SecurityContextHolder.getContext().getAuthentication();
            if(authentication instanceof AnonymousAuthenticationToken){
                throw new BadRequestException("The request has failed." +
                        " Please send the JWT of the user who needs to view the wishlist along with the request.");
            }
            List<String> _sort = objectMapper.readValue(sort, ArrayList.class);
            List<Integer> _range = objectMapper.readValue(range, ArrayList.class);
            TodoFilter _filter = objectMapper.readValue(filter, TodoFilter.class);
            StringBuilder contentRange = new StringBuilder("todo ");
            contentRange.append(_range.get(0))
                    .append("-")
                    .append(_range.get(1))
                    .append("/")
                    .append(todoService
                            .count(_filter));
            response.setHeader("Content-Range", contentRange.toString());
            PagingRequest pagingRequest = new PagingRequest();
            pagingRequest.setLimit(_range.get(1) - _range.get(0) + 1);
            pagingRequest.setPage((int) Math.ceil(_range.get(0) / pagingRequest.getLimit() + 1));
            Sorter sorter = new Sorter();
            sorter.setName(_sort.get(0));
            sorter.setBy(_sort.get(1));
            pagingRequest.setSorter(sorter);
            List<Todo> todoList = todoService.getAll(_filter, pagingRequest);
            if (todoList == null){
                throw new CustomExceptionRuntime(200, "Empty Category");
            }
            return ResponseEntity.ok(todoList);
        }catch (JsonProcessingException exception){
            throw new RuntimeException(exception);
        }
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
