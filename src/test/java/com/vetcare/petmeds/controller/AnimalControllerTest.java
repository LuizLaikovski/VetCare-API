package com.vetcare.petmeds.controller;

import com.vetcare.petmeds.dto.ResponseDTO;
import com.vetcare.petmeds.model.Animal;
import com.vetcare.petmeds.service.AnimalService;
import com.vetcare.petmeds.utilities.Specie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AnimalController.class)
public class AnimalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AnimalService animalService;

    @Autowired
    private ObjectMapper objectMapper;

    private Animal animal;

    @BeforeEach
    void setUp() {
        animal = new Animal();
        animal.setId(1L);
        animal.setName("Rex");
        animal.setSpecie(Specie.Cachorro);
    }

    @Test
    void createAnimal_ShouldReturnOk() throws Exception {
        when(animalService.createNewAnimal(any(Animal.class))).thenReturn(animal);

        mockMvc.perform(post("/animal/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(animal)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Animal cadastrado com sucesso"));
    }

    @Test
    void getAllAnimals_ShouldReturnOk() throws Exception {
        when(animalService.getAll()).thenReturn(Arrays.asList(animal));

        mockMvc.perform(get("/animal/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Rex"));
    }

    @Test
    void getAnimalById_ShouldReturnOk() throws Exception {
        when(animalService.getById(1L)).thenReturn(animal);

        mockMvc.perform(get("/animal/id/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Rex"));
    }

    @Test
    void getAnimalByName_ShouldReturnOk() throws Exception {
        when(animalService.getByName("Rex")).thenReturn(Optional.of(animal));

        mockMvc.perform(get("/animal/Rex"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Rex"));
    }

    @Test
    void updateAnimal_ShouldReturnOk() throws Exception {
        when(animalService.updateAnimal(anyLong(), any(Animal.class))).thenReturn(animal);

        mockMvc.perform(put("/animal/edit/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(animal)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Animal atualizado com sucesso"));
    }

    @Test
    void deleteAnimal_ShouldReturnOk() throws Exception {
        when(animalService.deleteById(1L)).thenReturn(new ResponseDTO("Animal deletado com sucesso"));

        mockMvc.perform(delete("/animal/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Animal deletado com sucesso"));
    }
}
