package com.vetcare.petmeds.applicationPrimary.animal.service;

import com.vetcare.petmeds.applicationPrimary.animal.dto.AnimalDescriptionDTO;
import com.vetcare.petmeds.applicationPrimary.animal.dto.AnimalSimpleDescriptionDTO;
import com.vetcare.petmeds.archives.images.entity.ImageEntity;
import com.vetcare.petmeds.archives.images.service.ImageService;
import com.vetcare.petmeds.exception.ImageStorageException;
import com.vetcare.petmeds.exception.ResourceNotFoundException;
import com.vetcare.petmeds.applicationPrimary.animal.entity.AnimalEntity;
import com.vetcare.petmeds.applicationPrimary.animal.repository.AnimalRepository;
import com.vetcare.petmeds.applicationPrimary.medicine.service.MedicineService;
import com.vetcare.petmeds.shared.ResponseDTO;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Service
@AllArgsConstructor
public class AnimalService {
    private static final Logger logger = LoggerFactory.getLogger(AnimalService.class);
    private AnimalRepository animalRepository;
    private MedicineService medicineService;
    private ImageService imageService;

    public Page<AnimalDescriptionDTO> getAll(Pageable pageable) {
        return animalRepository.findAll(pageable).map(AnimalDescriptionDTO::new);
    }

    public AnimalEntity getById(Long id) {
        return animalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Animal não encontrado"));
    }

    public Page<AnimalEntity> getByName(String name, Pageable pageable) {
        return animalRepository.findByNameContainingIgnoreCase(name, pageable);
    }

    public ResponseDTO setMedicine(Long idAnimal, Long idMedicine) {
        AnimalEntity animalExist = getById(idAnimal);

        animalExist.getMedicine().add(medicineService.getMedicineByIdEntity(idMedicine));

        animalRepository.save(animalExist);

        return new ResponseDTO(
                new Date(),
                "Medicamento salvo com sucesso",
                "Medicamento salvo com sucesso"
        );
    }

    public AnimalSimpleDescriptionDTO setImage(Long idAnimal, MultipartFile imageFile) {
        try {
            AnimalEntity animalExist = getById(idAnimal);

            ImageEntity  imageEntity = imageService.uploadImageToEntity(imageFile);

            animalExist.setImageId(imageEntity.getId());
            animalRepository.save(animalExist);

            return new AnimalSimpleDescriptionDTO(animalExist, imageService);
        } catch (Exception e) {
            throw new ImageStorageException("Houve um erro ao adicionar a imagem ao animal!", e);
        }
    }

    public ResponseEntity<Resource> getImage(Long idAnimal) {
        try {
            AnimalEntity animal =  getById(idAnimal);
            ImageEntity image = imageService.findImageByIdToEntity(animal.getImageId());

            MediaType mediaType;

            try {
                mediaType = MediaType.parseMediaType(image.getTipoConteudo());
            } catch (Exception e) {
                mediaType = MediaType.APPLICATION_OCTET_STREAM;
            }

            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .contentLength(image.getDados().length)
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            ContentDisposition.attachment()
                                    .filename(image.getNome())
                                    .build().toString()
                    )
                    .body(new ByteArrayResource(image.getDados()));

        } catch (Exception e) {
            throw new ResourceNotFoundException("Imagem não encontrada!", e);
        }
    }

    public AnimalEntity createNewAnimal(AnimalEntity animal) {
        return animalRepository.save(animal);
    }

    public AnimalEntity updateAnimal(Long id, AnimalEntity animal)  {
        AnimalEntity existing = getById(id);

        existing.update(
                animal.getWeight(),
                animal.getAge(),
                animal.getRace(),
                animal.getOwner()
        );

        return animalRepository.save(existing);
    }

    public ResponseEntity<Resource> updateImage(Long idAnimal, MultipartFile imageFile) {
        try {
            AnimalEntity animalExist = getById(idAnimal);
            if (animalExist.getImageId() == null) {
                throw new ResourceNotFoundException("o Animal informado não possui imagem cadastrada!");
            }

            ImageEntity image = imageService.updateImageEntity(animalExist.getImageId(), imageFile);

            MediaType mediaType;

            try {
                mediaType = MediaType.parseMediaType(image.getTipoConteudo());
            } catch (Exception e) {
                mediaType = MediaType.APPLICATION_OCTET_STREAM;
            }

            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .contentLength(image.getDados().length)
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            ContentDisposition.attachment()
                                    .filename(image.getNome())
                                    .build().toString()
                    )
                    .body(new ByteArrayResource(image.getDados()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteById(Long id) {
        if (!animalRepository.existsById(id)) {
            throw new ResourceNotFoundException("Animal não encontrado");
        }
        animalRepository.deleteById(id);
    }

    public ResponseEntity<Void> deleteImageAnimal(Long idAnimal) {
        try {
            AnimalEntity animal =  getById(idAnimal);
            if (animal.getImageId() == null) {
                throw new ResourceNotFoundException("O Animal informado não possui imagem cadastrada!!");
            }
            ImageEntity image = imageService.findImageByIdToEntity(animal.getImageId());

            animal.setImageId(null);
            animalRepository.save(animal);

            imageService.deletedImage(image.getId());
        } catch (Exception e) {
            throw new ImageStorageException("Houve um erro ao deletar a imagem!", e);
        }
        return null;
    }

}
