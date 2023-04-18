package com.gymguru.external.api;

import com.gymguru.external.api.wger.WgerCategory;
import com.gymguru.external.api.wger.WgerConfiguration;
import com.gymguru.external.api.wger.WgerExercise;
import com.gymguru.external.api.wger.WgerMapper;
import com.gymguru.external.api.wger.dto.WgerCategoryBoardDto;
import com.gymguru.external.api.wger.dto.WgerExerciseBoardDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class WgerMapperTests {

    @Autowired
    private WgerMapper wgerMapper;

    @Test
    public void shouldMapToWgerExerciseDtos() {
        //Given
        WgerExerciseBoardDto wgerExerciseBoardDto = new WgerExerciseBoardDto();
        WgerExercise exercise1 = new WgerExercise();
        exercise1.setName("Exercise 1");
        exercise1.setDescription("<p>This is the description of exercise 1</p>");
        WgerExercise exercise2 = new WgerExercise();
        exercise2.setName("Exercise 2");
        exercise2.setDescription("<p>This is the description of exercise 2</p>");
        wgerExerciseBoardDto.setWgerExercises(Arrays.asList(exercise1, exercise2));

        //When
        List<WgerExercise> wgerExerciseDtos = wgerMapper.mapToWgerExerciseDtos(wgerExerciseBoardDto);

        //Then
        assertEquals(2, wgerExerciseDtos.size());
        assertEquals("Exercise 1", wgerExerciseDtos.get(0).getName());
        assertEquals("This is the description of exercise 1", wgerExerciseDtos.get(0).getDescription());
        assertEquals("Exercise 2", wgerExerciseDtos.get(1).getName());
        assertEquals("This is the description of exercise 2", wgerExerciseDtos.get(1).getDescription());
    }

    @Test
    public void shouldMapToWgerExerciseDtosWhenWgerExerciseBoardDtoIsNull() {
        //Given
        WgerExerciseBoardDto wgerExerciseBoardDto = null;

        //When
        List<WgerExercise> wgerExerciseDtos = wgerMapper.mapToWgerExerciseDtos(wgerExerciseBoardDto);

        //Then
        assertNotNull(wgerExerciseDtos);
        assertTrue(wgerExerciseDtos.isEmpty());
    }

    @Test
    public void shouldMapToWgerCategoryDtos() {
        //Given
        WgerCategoryBoardDto wgerCategoryBoardDto = new WgerCategoryBoardDto();
        WgerCategory category1 = new WgerCategory();
        category1.setId(1L);
        category1.setName("Category 1");
        WgerCategory category2 = new WgerCategory();
        category2.setId(2L);
        category2.setName("Category 2");
        wgerCategoryBoardDto.setWgerCategories(Arrays.asList(category1, category2));

        //When
        List<WgerCategory> wgerCategoryDtos = wgerMapper.mapToWgerCategoryDtos(wgerCategoryBoardDto);

        //Then
        assertEquals(2, wgerCategoryDtos.size());
        assertEquals(1L, wgerCategoryDtos.get(0).getId().longValue());
        assertEquals("Category 1", wgerCategoryDtos.get(0).getName());
        assertEquals(2L, wgerCategoryDtos.get(1).getId().longValue());
        assertEquals("Category 2", wgerCategoryDtos.get(1).getName());
    }

    @Test
    public void shouldMapToWgerCategoryDtosWhenWgerCategoryBoardDtoIsNull() {
        //Given
        WgerCategoryBoardDto wgerCategoryBoardDto = null;

        //When
        List<WgerCategory> wgerCategoryDtos = wgerMapper.mapToWgerCategoryDtos(wgerCategoryBoardDto);

        //Then
        assertNotNull(wgerCategoryDtos);
        assertTrue(wgerCategoryDtos.isEmpty());
    }
}


