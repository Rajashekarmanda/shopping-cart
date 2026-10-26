package com.codewithraj.shoppingcart.service;

import com.codewithraj.shoppingcart.model.Category;
import com.codewithraj.shoppingcart.repository.CategoryRepository;
import com.codewithraj.shoppingcart.response.AlreadyExistsException;
import com.codewithraj.shoppingcart.response.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found!"));

    }

    @Override
    public Category addCategory(Category category) {
       if(categoryRepository.existsByName(category.getName())){
           throw new AlreadyExistsException( category.getName() + " already exist");
       }

       // ignore if client sent any id
        category.setId(null);
       return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Category category, Long id) {
        Category existingCategory = getCategoryById(id); // throws 404 case if missing because it's already method define above.
        String newName = category.getName();

        if(!newName.equals(existingCategory.getName()) && categoryRepository.existsByName(newName)){
            throw new AlreadyExistsException(category.getName() + " is already exist");
        }
        existingCategory.setName(newName);
        return categoryRepository.save(existingCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = getCategoryById(id); // throws 404 case if missing because it's already method define above.
        categoryRepository.delete(category);
    }
}
