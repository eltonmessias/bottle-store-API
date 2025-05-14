package com.bigbrother.bottleStore.product.Category;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("Should get the category successfully")
    public void findByName() {
        String name = "Wine";
        CategoryRequest categoryRequest = new CategoryRequest(
                UUID.randomUUID(),
                "Wine",
                "No desc"
        );
        this.createCategory(categoryRequest);

        Optional<Category> result = categoryRepository.findByName(name);


        assertTrue(result.isPresent());
        assertEquals("Wine", result.get().getName());
    }

    private void createCategory(CategoryRequest request) {
        Category category = new Category(request);
        this.entityManager.persist(category);
    }
}

