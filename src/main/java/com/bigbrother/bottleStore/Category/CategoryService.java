package com.bigbrother.bottleStore.Category;

import com.bigbrother.bottleStore.exceptions.CategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

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

    public CategoryDTO getCategoryById(long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("Category not found"));
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
