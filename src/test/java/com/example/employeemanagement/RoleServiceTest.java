// Define o pacote do teste, normalmente o mesmo da aplicação
package com.example.employeemanagement;

// Importa classes necessárias do projeto
import com.example.employeemanagement.dto.RoleDTO;
import com.example.employeemanagement.exception.ResourceNotFoundException;
import com.example.employeemanagement.model.Role;
import com.example.employeemanagement.repository.RoleRepository;
import com.example.employeemanagement.service.RoleService;

// Importa anotações e classes do JUnit 5 e Mockito
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

// Importa utilitários para listas e Optional
import java.util.Arrays;
import java.util.Optional;

// Importa métodos utilitários para verificação e asserções
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
// Habilita o uso de Mockito com JUnit 5
@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {

    @Mock // Cria um mock do repositório RoleRepository
    private RoleRepository roleRepository;

    @InjectMocks // Injeta o mock no serviço que está sendo testado
    private RoleService roleService;

    // Objetos de teste
    private Role role;
    private RoleDTO roleDTO;

    // Método executado antes de cada teste
    @BeforeEach
    void setUp() {
        // Cria uma instância de Role e RoleDTO com os mesmos dados
        role = new Role(1L, "Developer", "Software Developer");
        roleDTO = new RoleDTO(1L, "Developer", "Software Developer");
    }


    @Test
    void testCreateRole() {
        // Define comportamento do mock: ao salvar qualquer Role, retorna o objeto 'role'
        when(roleRepository.save(any(Role.class))).thenReturn(role);

        // Executa o método a ser testado
        RoleDTO result = roleService.createRole(roleDTO);

        // Verifica se o resultado contém os dados esperados
        assertEquals(roleDTO, result);

        // Verifica se o método save foi chamado exatamente uma vez
        verify(roleRepository, times(1)).save(any(Role.class));
    }


    @Test
    void testGetAllRoles() {
        // Simula retorno de uma lista com 1 Role no repositório
        when(roleRepository.findAll()).thenReturn(Arrays.asList(role));

        // Executa o método a ser testado
        assertEquals(1, roleService.getAllRoles().size());

        // Verifica se findAll foi chamado exatamente uma vez
        verify(roleRepository, times(1)).findAll();
    }


    @Test
    void testGetRoleById() {
        // Simula retorno de Role ao buscar por ID
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));

        // Executa o método e obtém o resultado
        RoleDTO result = roleService.getRoleById(1L);

        // Verifica se o resultado corresponde ao esperado
        assertEquals(roleDTO, result);

        // Verifica se o repositório foi chamado corretamente
        verify(roleRepository, times(1)).findById(1L);
    }


    @Test
    void testGetRoleByIdNotFound() {
        // Simula retorno vazio ao buscar uma Role inexistente
        when(roleRepository.findById(1L)).thenReturn(Optional.empty());

        // Verifica se a exceção esperada é lançada
        assertThrows(ResourceNotFoundException.class, () -> roleService.getRoleById(1L));
    }
}
