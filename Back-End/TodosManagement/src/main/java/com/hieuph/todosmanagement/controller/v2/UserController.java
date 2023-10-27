package com.hieuph.todosmanagement.controller.v2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hieuph.todosmanagement.Filter.Category.CategoryFilter;
import com.hieuph.todosmanagement.Filter.User.UserFilter;
import com.hieuph.todosmanagement.dto.request.Paging.PagingRequest;
import com.hieuph.todosmanagement.dto.request.Paging.Sorter;
import com.hieuph.todosmanagement.dto.request.PasswordDto;
import com.hieuph.todosmanagement.dto.response.MessageResponse;
import com.hieuph.todosmanagement.dto.response.UserI4Response;
import com.hieuph.todosmanagement.entity.Category;
import com.hieuph.todosmanagement.entity.User;
import com.hieuph.todosmanagement.exception.BadRequestException;
import com.hieuph.todosmanagement.exception.CustomExceptionRuntime;
import com.hieuph.todosmanagement.security.service.UserDetailImpl;
import com.hieuph.todosmanagement.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RequestMapping("/api/v2/user")
@RestController("user/v2")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ObjectMapper objectMapper;

    private Authentication authentication;
    @GetMapping("")
    public ResponseEntity<?> userList(@RequestParam(name = "filter") String filter,
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
            UserFilter _filter = objectMapper.readValue(filter, UserFilter.class);
            StringBuilder contentRange = new StringBuilder("user ");
            contentRange.append(_range.get(0))
                    .append("-")
                    .append(_range.get(1))
                    .append("/")
                    .append(userService
                            .count(_filter));
            response.setHeader("Content-Range", contentRange.toString());
            PagingRequest pagingRequest = new PagingRequest();
            pagingRequest.setLimit(_range.get(1) - _range.get(0) + 1);
            pagingRequest.setPage((int) Math.ceil(_range.get(0) / pagingRequest.getLimit() + 1));
            Sorter sorter = new Sorter();
            sorter.setName(_sort.get(0));
            sorter.setBy(_sort.get(1));
            pagingRequest.setSorter(sorter);
            List<User> userList = userService.getAll(_filter, pagingRequest);
            if (userList == null){
                userList = new ArrayList<>();
            }
            return ResponseEntity.ok(userList);
        }catch (JsonProcessingException exception){
            throw new RuntimeException(exception);
        }
    }
    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody PasswordDto passwordDto){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof AnonymousAuthenticationToken){
            throw new BadRequestException("The request has failed." +
                    " Please send the JWT of the user who needs to view the wishlist along with the request.");
        }
        User user = ((UserDetailImpl) authentication.getPrincipal()).getUser();
        if(!userService.oldPasswordIsValid(user, passwordDto.getOldPassword())){
            return ResponseEntity.badRequest().body(new MessageResponse("Incorrect old password!"));
        }
        userService.changePassword(user,passwordDto.getNewPassword());
        return ResponseEntity.ok().body(new MessageResponse("Password changed successfully!"));
    }

}
