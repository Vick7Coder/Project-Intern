package com.hieuph.todosmanagement.service.Impl;

import com.hieuph.todosmanagement.Filter.Category.CategoryFilter;
import com.hieuph.todosmanagement.Filter.Category.GenericCategorySpecification;
import com.hieuph.todosmanagement.dto.request.CategoryDto;
import com.hieuph.todosmanagement.dto.request.Paging.Pagination;
import com.hieuph.todosmanagement.dto.request.Paging.PagingRequest;
import com.hieuph.todosmanagement.entity.Category;
import com.hieuph.todosmanagement.entity.User;
import com.hieuph.todosmanagement.exception.BadRequestException;
import com.hieuph.todosmanagement.exception.NotFoundException;
import com.hieuph.todosmanagement.repository.CategoryRepository;
import com.hieuph.todosmanagement.repository.UserRepository;
import com.hieuph.todosmanagement.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GenericCategorySpecification genericCategorySpecification;

    @Override
    public List<Category> getAll(User user) {
        return categoryRepository.findByUser(user);
    }

    @Override
    public List<Category> getAll(User user, CategoryFilter categoryFilter) {
        return categoryRepository.findByUser(user,genericCategorySpecification.generic(categoryFilter).toString());
    }

    @Override
    public List<Category> getAll(User user, PagingRequest pagingRequest, CategoryFilter categoryFilter) {
        List<Category> categoryList = categoryRepository
                .findByUser(user, Pagination.initPageable(pagingRequest), genericCategorySpecification.generic(categoryFilter))
                .getContent();
        List<Category> respList =   new ArrayList<>();
        if(!categoryList.isEmpty()){
            for(int i =0; i < categoryList.size(); i++){
                respList.add(categoryList.get(i));
            }
            return respList;
        }
        return null;
    }

    @Override
    public List<Category> getAllEnabled(Integer userId) {
        return categoryRepository.findAllEnableByUser(userId);
    }

    @Override
    public Category get(int id) {
        return categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found Category!"));
    }

    @Override
    public Category create(CategoryDto categoryDto, User user) {
        Optional<Category> cat = categoryRepository.findByName(user.getId(),categoryDto.getName());
        if(cat.isPresent()){
            throw new BadRequestException("Category: "+categoryDto.getName()+"already exist!");
        }
        Optional<User> managedUser = userRepository.findById(user.getId());
        if(!managedUser.isPresent()){
            throw new NotFoundException("Not found user");
        }
        Category newCat = new Category();
        newCat.setName(categoryDto.getName());
        newCat.setEnabled(categoryDto.isEnabled());
        newCat.setUser(managedUser.get());
        return categoryRepository.save(newCat);
    }

    @Override
    public void update(int id, CategoryDto categoryDto) {
        Category cat = categoryRepository.findById(id).orElseThrow(()-> new NotFoundException("Not Found Category with ID: "+id));
        cat.setName(categoryDto.getName());
        categoryRepository.save(cat);
    }

    @Override
    public void enable(int id) {
        Category cat = categoryRepository.findById(id).orElseThrow(()->new NotFoundException("Not Found Category with ID: "+id));
        cat.setEnabled(!cat.isEnabled());
        categoryRepository.save(cat);
    }

    @Override
    public void delete(int id) {

        categoryRepository.deleteById(id);

    }

    @Override
    public Category findById(int id) {
        return categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found Category!"));
    }

    @Override
    public Long count(CategoryFilter categoryFilter) {
        return categoryRepository.count(genericCategorySpecification.generic(categoryFilter));
    }
}
