// Define o pacote onde o serviço está localizado
package com.example.employeemanagement.service;

// Importa os DTOs, exceções, entidades e repositórios usados no serviço
import com.example.employeemanagement.dto.RoleDTO;
import com.example.employeemanagement.exception.ResourceNotFoundException;
import com.example.employeemanagement.model.Role;
import com.example.employeemanagement.repository.RoleRepository;

// Importa utilitário do Spring para copiar propriedades entre objetos
import org.springframework.beans.BeanUtils;

// Anotações para injeção de dependência e marcação como serviço Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Importa classes Java para listas e processamento funcional
import java.util.List;
import java.util.stream.Collectors;

@Service // Indica que esta classe é um serviço gerenciado pelo Spring
public class RoleService {

    @Autowired // Injeta automaticamente a instância do repositório RoleRepository
    private RoleRepository roleRepository;

    /**
     * Cria uma nova função/cargo (Role) a partir de um DTO.
     * @param roleDTO Dados de entrada
     * @return RoleDTO com os dados salvos (incluindo ID)
     */
    public RoleDTO createRole(RoleDTO roleDTO) {
        Role role = new Role(); // Cria uma nova entidade Role
        BeanUtils.copyProperties(roleDTO, role); // Copia os dados do DTO para a entidade
        role = roleRepository.save(role); // Salva no banco de dados
        BeanUtils.copyProperties(role, roleDTO); // Copia os dados salvos (incluindo ID) de volta para o DTO
        return roleDTO; // Retorna o DTO com os dados persistidos
    }

    /**
     * Busca todas as funções (roles) cadastradas no banco.
     * @return Lista de RoleDTOs
     */
    public List<RoleDTO> getAllRoles() {
        return roleRepository.findAll().stream() // Busca todas as roles e transforma em stream
            .map(role -> {
                RoleDTO dto = new RoleDTO(); // Cria um DTO para cada role
                BeanUtils.copyProperties(role, dto); // Copia os dados da entidade para o DTO
                return dto;
            })
            .collect(Collectors.toList()); // Coleta todos os DTOs em uma lista
    }

    /**
     * Busca uma função/cargo por ID.
     * @param id ID da Role
     * @return RoleDTO correspondente
     * @throws ResourceNotFoundException se não for encontrada
     */
    public RoleDTO getRoleById(Long id) {
        Role role = roleRepository.findById(id) // Busca a entidade Role pelo ID
            .orElseThrow(() -> new ResourceNotFoundException("Cargo não encontrada com id " + id));
        RoleDTO dto = new RoleDTO(); // Cria o DTO de retorno
        BeanUtils.copyProperties(role, dto); // Copia os dados da entidade para o DTO
        return dto;
    }

    /**
     * Atualiza uma função/cargo existente.
     * @param id ID da Role a ser atualizada
     * @param roleDTO Dados atualizados
     * @return RoleDTO atualizado
     * @throws ResourceNotFoundException se a Role não existir
     */
    public RoleDTO updateRole(Long id, RoleDTO roleDTO) {
        Role role = roleRepository.findById(id) // Busca a Role existente
            .orElseThrow(() -> new ResourceNotFoundException("Cargo não encontrada com id " + id));
        BeanUtils.copyProperties(roleDTO, role, "id"); // Atualiza os campos (exceto o ID)
        role = roleRepository.save(role); // Persiste as mudanças
        BeanUtils.copyProperties(role, roleDTO); // Atualiza o DTO com os dados salvos
        return roleDTO;
    }

    /**
     * Exclui uma função/cargo pelo ID.
     * @param id ID da Role
     * @throws ResourceNotFoundException se não existir
     */
    public void deleteRole(Long id) {
        if (!roleRepository.existsById(id)) { // Verifica se a Role existe
            throw new ResourceNotFoundException("Cargo não encontrada com id " + id);
        }
        roleRepository.deleteById(id); // Exclui a Role do banco
    }
}
