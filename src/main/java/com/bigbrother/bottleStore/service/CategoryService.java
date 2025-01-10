package com.bigbrother.bottleStore.service;

import com.bigbrother.bottleStore.dto.CategoryDTO;
import com.bigbrother.bottleStore.exceptions.CategoryNotFoundException;
import com.bigbrother.bottleStore.model.Category;
import com.bigbrother.bottleStore.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    private CategoryDTO convertToCategoryDTO(Category category) {
        return new CategoryDTO(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
    }

    public List<CategoryDTO> getAllCategories() {
        try {
            return categoryRepository.findAll().stream().map(this::convertToCategoryDTO).collect(Collectors.toList());
        } catch (Exception e) {
            throw new CategoryNotFoundException("Category not found");
        }
    }

    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setName(categoryDTO.name());
        category.setDescription(categoryDTO.description());
        categoryRepository.save(category);
        return convertToCategoryDTO(category);
    }

    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        category.setName(categoryDTO.name());
        category.setDescription(categoryDTO.description());
        categoryRepository.save(category);
        return convertToCategoryDTO(category);
    }

    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        categoryRepository.delete(category);
    }


}
