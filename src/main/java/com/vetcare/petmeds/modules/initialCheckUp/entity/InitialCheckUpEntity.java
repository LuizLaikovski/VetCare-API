package com.vetcare.petmeds.modules.initialCheckUp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.vetcare.petmeds.modules.animal.entity.AnimalEntity;
import com.vetcare.petmeds.modules.animal.entity.Sex;
import com.vetcare.petmeds.modules.animal.entity.Specie;
import com.vetcare.petmeds.modules.initialCheckUp.enumns.*;
import com.vetcare.petmeds.modules.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_checkup_initial")
@AllArgsConstructor
@NoArgsConstructor
public class InitialCheckUpEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador único do registro de check-up inicial

    // ==========================================
    // INFORMAÇÕES GERAIS E CABEÇALHO
    // ==========================================

    @Column(name = "examination_date", nullable = false)
    private LocalDateTime examinationDate = LocalDateTime.now(); // Data e hora em que o exame físico foi realizado

    @Column(length = 20)
    private Specie species; // Espécie do animal atendido

    @Column(length = 3)
    private Sex sex; // Sexo do animal (Macho / Fêmea)

    @ManyToOne
    @JoinColumn(name = "animal_id")
    private AnimalEntity animal; // Relacionamento com o cadastro principal do animal

    @ManyToOne
    @JoinColumn(name = "vet_id")
    private UserEntity veterinarian; // Médico responsável pelo checkup

    // ==========================================
    // SINAIS VITAIS E PARÂMETROS INICIAIS
    // ==========================================

    @Column(length = 50)
    private String crt; // TRC - Tempo de Preenchimento Capilar (avaliação da perfusão periférica)

    @Column(length = 50)
    private String rr;  // FR - Frequência Respiratória (movimentos respiratórios por minuto)

    @Column(length = 50)
    private String hr;  // FC - Frequência Cardíaca (batimentos cardíacos por minuto)

    @Column(length = 50)
    private String tpc; // Cor das mucosas / TPC detalhado conforme preenchimento clínico

    @Column(length = 100)
    private String hydration; // Grau de hidratação avaliado no animal (ex: normohidratado, desidratado X%)

    // ==========================================
    // ITENS DO EXAME FÍSICO (1 a 20)
    // ==========================================

    // 1. Pulso Arterial
    @Enumerated(EnumType.STRING)
    @Column(name = "arterial_pulse")
    private ArterialPulse arterialPulse; // Qualidade do pulso (Regular, Irregular, Fraco)

    // 2. Mucous Membranes (Mucosas)
    @Enumerated(EnumType.STRING)
    @Column(name = "mucous_membranes")
    private MucosaStatus mucousMembranes; // Coloração das mucosas (Rosadas/Normal, Congestas, Pálidas, Cianótica, Ictérica)

    // 3. Consciousness Level (Nível de Consciência)
    @Enumerated(EnumType.STRING)
    @Column(name = "consciousness_level")
    private ConsciousnessLevel consciousnessLevel; // Estado mental (Alerta, Deprimido, Coma)

    // 4. Nutritional Status (Estado Nutricional)
    @Enumerated(EnumType.STRING)
    @Column(name = "nutritional_status")
    private NutritionalStatus nutritionalStatus; // Condição corporal (Caquético, Magro, Normal, Sobrepeso, Obesidade Grau 1 a 3)

    // 5. Behavior (Comportamento)
    @Enumerated(EnumType.STRING)
    @Column(name = "behavior")
    private Behavior behavior; // Temperamento observado na consulta (Dócil, Inquieto, Agressivo, Medroso)

    // 6. Ectoparasitas (Presença de parasitas externos)
    @Column(name = "has_ectoparasites")
    private Boolean hasEctoparasites; // Indica se foram encontrados ectoparasitas (Sim / Não)

    @Column(name = "ectoparasites_details", length = 255)
    private String ectoparasitesDetails; // Descrição de quais ectoparasitas foram encontrados (ex: pulgas, carrapatos)

    // 7. Posture and Movement (Postura e Movimentação)
    @Enumerated(EnumType.STRING)
    @Column(name = "posture_movement")
    private ExamStatus postureMovement; // Avaliação postural e locomotora (Normal / Anormal)

    // 8. Eyes (Olhos)
    @Enumerated(EnumType.STRING)
    @Column(name = "eyes")
    private ExamStatus eyes; // Exame oftalmológico preliminar (Normal / Anormal)

    // 9. Ears (Orelhas / Ouvidos)
    @Enumerated(EnumType.STRING)
    @Column(name = "ears")
    private ExamStatus ears; // Exame otológico (Normal / Anormal)

    // 10. Oral Cavity (Cavidade Oral)
    @Enumerated(EnumType.STRING)
    @Column(name = "oral_cavity")
    private ExamStatus oralCavity; // Avaliação de dentes, gengivas e cavidade oral (Normal / Anormal)

    // 12. Respiratory System (Sistema Respiratório)
    @Enumerated(EnumType.STRING)
    @Column(name = "respiratory_system")
    private ExamStatus respiratory; // Ausculta pulmonar e vias aéreas (Normal / Anormal)

    // 13. Circulatory System (Sistema Circulatório)
    @Enumerated(EnumType.STRING)
    @Column(name = "circulatory_system")
    private ExamStatus circulatory; // Ausculta cardíaca e sistema vascular (Normal / Anormal)

    // 14. Hemolymphatic System (Sistema Hemolinfático)
    @Enumerated(EnumType.STRING)
    @Column(name = "hemolymphatic_system")
    private ExamStatus hemolymphatic; // Avaliação de linfonodos e baço palpável (Normal / Anormal)

    // 15. Digestive System (Sistema Digestório / Disgestório)
    @Enumerated(EnumType.STRING)
    @Column(name = "digestive_system")
    private ExamStatus digestive; // Palpação abdominal e trato gastrointestinal (Normal / Anormal)

    // 16. Genital System (Sistema Genital)
    @Enumerated(EnumType.STRING)
    @Column(name = "genital_system")
    private ExamStatus genital; // Avaliação de órgãos genitais externos e mamas (Normal / Anormal)

    // 17. Urinary System (Sistema Urinário)
    @Enumerated(EnumType.STRING)
    @Column(name = "urinary_system")
    private ExamStatus urinary; // Palpação de rins e Bexiga (Normal / Anormal)

    // 18. Nervous System (Sistema Nervoso)
    @Enumerated(EnumType.STRING)
    @Column(name = "nervous_system")
    private ExamStatus nervous; // Avaliação neurológica básica e reflexos (Normal / Anormal)

    // 19. Skin and Appendages (Pele e Anexos)
    @Enumerated(EnumType.STRING)
    @Column(name = "skin_and_appendages")
    private ExamStatus skinAndAppendages; // Avaliação de pelagem, integridade da pele e unhas (Normal / Anormal)

    // 20. Others (Outros Sistemas ou Observações Extras)
    @Enumerated(EnumType.STRING)
    @Column(name = "other_status")
    private ExamStatus otherStatus; // Status geral para o campo "Outros" (Normal / Anormal)

    @Column(name = "other_observations", columnDefinition = "TEXT")
    private String otherObservations; // Texto livre para anotações adicionais do médico veterinário
}