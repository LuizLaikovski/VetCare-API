package com.vetcare.petmeds.model.image;

import com.vetcare.petmeds.applicationPrimary.animal.dto.AnimalSimpleDescriptionDTO;
import com.vetcare.petmeds.applicationPrimary.animal.entity.AnimalEntity;
import com.vetcare.petmeds.applicationPrimary.animal.entity.Specie;
import com.vetcare.petmeds.applicationPrimary.animal.repository.AnimalRepository;
import com.vetcare.petmeds.applicationPrimary.animal.service.AnimalService;
import com.vetcare.petmeds.applicationPrimary.medicine.service.MedicineService;
import com.vetcare.petmeds.archives.images.entity.ImageEntity;
import com.vetcare.petmeds.archives.images.service.ImageService;
import com.vetcare.petmeds.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/** A imagem do animal é opcional: cadastro e consulta funcionam sem ela. */
@ExtendWith(MockitoExtension.class)
public class OptionalImageAnimalTest {

    @Mock
    private AnimalRepository animalRepository;
    @Mock
    private MedicineService medicineService;
    @Mock
    private ImageService imageService;

    @InjectMocks
    private AnimalService animalService;

    private AnimalEntity animal;

    @BeforeEach
    void setUp() {
        animal = new AnimalEntity();
        animal.setId(1L);
        animal.setName("Rex");
        animal.setSpecie(Specie.Cachorro);
    }

    @Test
    void createNewAnimal_ShouldNotRequireImage() {
        when(animalRepository.save(animal)).thenReturn(animal);

        AnimalEntity saved = animalService.createNewAnimal(animal);

        assertNull(saved.getImageId());
        verifyNoInteractions(imageService);
    }

    @Test
    void getById_ShouldWorkForAnimalWithoutImage() {
        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));

        assertNull(animalService.getById(1L).getImageId());
    }

    @Test
    void simpleDescriptionDTO_ShouldHaveNullImageWhenAnimalHasNone() {
        AnimalSimpleDescriptionDTO dto = new AnimalSimpleDescriptionDTO(animal, imageService);

        assertNull(dto.getImage());
        assertEquals("Rex", dto.getName());
        verify(imageService, never()).findImageByIdToEntity(any());
    }

    @Test
    void simpleDescriptionDTO_ShouldNotFailWhenReferencedImageNoLongerExists() {
        animal.setImageId(7L);
        when(imageService.findImageByIdToEntity(7L)).thenThrow(new ResourceNotFoundException("Imagem não encontrada!"));

        assertNull(new AnimalSimpleDescriptionDTO(animal, imageService).getImage());
    }

    @Test
    void setImage_ShouldAttachImageToAnimalCreatedWithoutOne() {
        ImageEntity image = new ImageEntity(7L, "rex.png", "image/png", new byte[]{1});
        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));
        when(imageService.uploadImageToEntity(any())).thenReturn(image);
        when(imageService.findImageByIdToEntity(7L)).thenReturn(image);
        when(animalRepository.save(animal)).thenReturn(animal);

        AnimalSimpleDescriptionDTO dto = animalService.setImage(1L,
                new MockMultipartFile("image", "rex.png", "image/png", new byte[]{1}));

        assertEquals(7L, animal.getImageId());
        assertArrayEquals(new byte[]{1}, dto.getImage());
    }

    @Test
    void getImage_ShouldReturn404WithClearMessageWhenAnimalHasNoImage() {
        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> animalService.getImage(1L));

        assertEquals("O Animal informado não possui imagem cadastrada!", ex.getMessage());
        verify(imageService, never()).findImageByIdToEntity(any());
    }

    @Test
    void updateImage_ShouldReturn404WhenAnimalHasNoImage() {
        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));

        assertThrows(ResourceNotFoundException.class, () -> animalService.updateImage(1L,
                new MockMultipartFile("image", "x.png", "image/png", new byte[]{1})));
        verify(imageService, never()).updateImageEntity(any(), any());
    }

    @Test
    void deleteImageAnimal_ShouldReturn404WhenAnimalHasNoImage() {
        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));

        assertThrows(ResourceNotFoundException.class, () -> animalService.deleteImageAnimal(1L));
    }

    @Test
    void deleteImageAnimal_ShouldReturnNoContentAndClearImageId() {
        animal.setImageId(7L);
        ImageEntity image = new ImageEntity(7L, "rex.png", "image/png", new byte[]{1});
        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));
        when(imageService.findImageByIdToEntity(7L)).thenReturn(image);

        var response = animalService.deleteImageAnimal(1L);

        assertEquals(204, response.getStatusCode().value());
        assertNull(animal.getImageId());
        verify(imageService).deletedImage(7L);
    }
}
