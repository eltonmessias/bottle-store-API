package com.bigbrother.bottleStore.product.Category;

import com.bigbrother.bottleStore.exceptions.CategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper mapper;


    public List<CategoryResponse> getAllCategories() {

        return categoryRepository.findAll().stream().map(mapper::toCategoryResponse).collect(Collectors.toList());

    }

    public CategoryResponse createCategory(CategoryRequest request) {
        var category = mapper.toCategory(request);
        categoryRepository.save(category);
        return mapper.toCategoryResponse(category);
    }

    public CategoryResponse getCategoryById(UUID id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        return mapper.toCategoryResponse(category);
    }

    public CategoryResponse updateCategory(CategoryRequest request, UUID id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        mapper.toCategory(request);
        categoryRepository.save(category);
        return mapper.toCategoryResponse(category);
    }

    public void deleteCategory(UUID id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        categoryRepository.delete(category);
    }


}
