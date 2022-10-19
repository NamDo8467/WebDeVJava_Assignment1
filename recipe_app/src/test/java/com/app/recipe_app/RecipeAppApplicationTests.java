package com.app.recipe_app;

import com.app.recipe_app.model.Recipe;
import com.app.recipe_app.repository.RecipeRepository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
@SpringBootTest
class RecipeAppApplicationTests {
	@Autowired
	RecipeRepository repo;

	@Test
	public void testAddNew() {
		Recipe recipe = new Recipe();
		recipe.setTitle("abc");
		recipe.setDescription("xyz");

		Recipe savedRecipe = repo.save(recipe);

		assertThat(savedRecipe).isNotNull();
		assertThat(savedRecipe.getId()).isGreaterThan(0);

	}

}
