package com.example.employeemanagement.service;

// Importa o DTO do Employee, usado para transferir dados do funcionário entre as camadas da aplicação.
import com.example.employeemanagement.dto.EmployeeDTO;

// Importa o DTO do Role, usado para transferir dados do cargo entre as camadas da aplicação.
import com.example.employeemanagement.dto.RoleDTO;

// Importa a exceção personalizada EmailAlreadyExistsException, que é lançada quando um e-mail duplicado é tentado ao cadastrar ou atualizar um funcionário.
import com.example.employeemanagement.exception.EmailAlreadyExistsException;

// Importa a exceção personalizada ResourceNotFoundException, que é lançada quando um recurso (como um funcionário ou cargo) não é encontrado no banco de dados.
import com.example.employeemanagement.exception.ResourceNotFoundException;

// Importa a classe Employee, que é a entidade que representa os dados dos funcionários no banco de dados.
import com.example.employeemanagement.model.Employee;

// Importa a classe Role, que é a entidade que representa os cargos dos funcionários no banco de dados.
import com.example.employeemanagement.model.Role;

// Importa o repositório do Employee, que fornece as operações CRUD no banco de dados para a entidade Employee.
import com.example.employeemanagement.repository.EmployeeRepository;

// Importa o repositório do Role, que fornece as operações CRUD no banco de dados para a entidade Role.
import com.example.employeemanagement.repository.RoleRepository;

// Importa a classe BeanUtils do Spring, que é usada para copiar propriedades de um objeto para outro, facilitando a conversão entre entidades e DTOs.
import org.springframework.beans.BeanUtils;


// Importa as classes do Spring Data para lidar com paginação de resultados de consultas ao banco de dados.
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

// Importa a anotação @Service do Spring, que marca a classe como um serviço gerenciado pelo Spring Container. Essa anotação ajuda a identificar o serviço dentro da aplicação.
import org.springframework.stereotype.Service;

// Importa a anotação @Transactional do Spring, que indica que os métodos da classe devem ser executados dentro de uma transação. Se algum erro ocorrer, as operações no banco de dados são revertidas.
import org.springframework.transaction.annotation.Transactional;


/**
 * Serviço responsável pela lógica de negócio relacionada a empregados.
 */
@Service
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;

    /**
     * Construtor com injeção de dependências.
     * @param employeeRepository Repositório de empregados
     * @param roleRepository Repositório de cargos
     */
    public EmployeeService(EmployeeRepository employeeRepository, RoleRepository roleRepository) {
        this.employeeRepository = employeeRepository;
        this.roleRepository = roleRepository;
    }

    /**
     * Cria um novo empregado no sistema.
     * @param employeeDTO DTO com os dados do empregado a ser criado
     * @return DTO do empregado criado
     * @throws IllegalArgumentException Se a data de admissão não for informada
     * @throws EmailAlreadyExistsException Se o email já estiver cadastrado
     * @throws ResourceNotFoundException Se o cargo não for encontrado
     */
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        if (employeeDTO.getAdmissionDate() == null) {
            throw new IllegalArgumentException("A data de admissão é obrigatória");
        }
        
        if (employeeRepository.existsByEmail(employeeDTO.getEmail())) {
            throw new EmailAlreadyExistsException("E-mail já cadastrado");
        }

        Role role = roleRepository.findById(employeeDTO.getRole().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cargo não encontrada com id " + employeeDTO.getRole().getId()));
        
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        employee.setRole(role);
        
        return convertToDto(employeeRepository.save(employee));
    }

    /**
     * Retorna uma lista paginada de empregados, com filtros opcionais.
     * @param name Filtro por nome (opcional)
     * @param email Filtro por email (opcional)
     * @param roleName Filtro por nome do cargo (opcional)
     * @param page Número da página (começa em 0)
     * @param size Tamanho da página
     * @return Página de DTOs de empregados
     */
    public Page<EmployeeDTO> getAllEmployees(String name, String email, String roleName, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Employee> employeePage = employeeRepository.findByFilters(name, email, roleName, pageable);
        return employeePage.map(this::convertToDto);
    }

    /**
     * Busca um empregado pelo ID.
     * @param id ID do empregado
     * @return DTO do empregado encontrado
     * @throws ResourceNotFoundException Se o empregado não for encontrado
     */
    public EmployeeDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empregado não encontrado com id " + id));
        return convertToDto(employee);
    }

    /**
     * Atualiza os dados de um empregado existente.
     * @param id ID do empregado a ser atualizado
     * @param employeeDTO DTO com os novos dados
     * @return DTO do empregado atualizado
     * @throws ResourceNotFoundException Se empregado ou cargo não forem encontrados
     * @throws EmailAlreadyExistsException Se o novo email já estiver em uso por outro empregado
     */
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empregado não encontrado com id " + id));
        
        if (!employee.getEmail().equals(employeeDTO.getEmail())) {
            if (employeeRepository.existsByEmailAndIdNot(employeeDTO.getEmail(), id)) {
                throw new EmailAlreadyExistsException("E-mail já cadastrado por outro empregado");
            }
        }
        
        if (employeeDTO.getAdmissionDate() == null) {
            employeeDTO.setAdmissionDate(employee.getAdmissionDate());
        }
        
        Role role = roleRepository.findById(employeeDTO.getRole().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cargo não encontrada com id " + employeeDTO.getRole().getId()));
        
        BeanUtils.copyProperties(employeeDTO, employee, "id");
        employee.setRole(role);
        
        return convertToDto(employeeRepository.save(employee));
    }

    /**
     * Remove um empregado do sistema.
     * @param id ID do empregado a ser removido
     * @throws ResourceNotFoundException Se o empregado não for encontrado
     */
    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Empregado não encontrado com id " + id);
        }
        employeeRepository.deleteById(id);
    }

    /**
     * Converte uma entidade Employee para DTO.
     * @param employee Entidade a ser convertida
     * @return DTO convertido
     */
    private EmployeeDTO convertToDto(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        BeanUtils.copyProperties(employee, dto);
        
        RoleDTO roleDTO = new RoleDTO();
        BeanUtils.copyProperties(employee.getRole(), roleDTO);
        dto.setRole(roleDTO);
        
        return dto;
    }
}