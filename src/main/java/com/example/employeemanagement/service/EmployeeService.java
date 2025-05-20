// Define o pacote onde o serviço está localizado
package com.example.employeemanagement.service;

// Importa utilitário para copiar propriedades entre objetos
import org.springframework.beans.BeanUtils;
// Importa classes para paginação
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
// Marca a classe como um componente de serviço do Spring
import org.springframework.stereotype.Service;
// Marca que os métodos do serviço devem ser executados dentro de transações
import org.springframework.transaction.annotation.Transactional;

// Importa os DTOs e entidades usados no serviço
import com.example.employeemanagement.dto.EmployeeDTO;
import com.example.employeemanagement.dto.RoleDTO;
import com.example.employeemanagement.exception.ResourceNotFoundException;
import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.model.Role;
// Importa os repositórios usados para acesso ao banco de dados
import com.example.employeemanagement.repository.EmployeeRepository;
import com.example.employeemanagement.repository.RoleRepository;


@Service // Indica que esta classe é um serviço gerenciado pelo Spring
@Transactional // Garante que os métodos sejam executados dentro de transações do banco de dados
public class EmployeeService {

    // Injeção dos repositórios usados pelo serviço
    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;

    // Construtor com injeção de dependência (Spring injeta os repositórios automaticamente)
    public EmployeeService(EmployeeRepository employeeRepository, RoleRepository roleRepository) {
        this.employeeRepository = employeeRepository;
        this.roleRepository = roleRepository;
    }


    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
    // Valida se a data de admissão foi fornecida
    if (employeeDTO.getAdmissionDate() == null) {
        throw new IllegalArgumentException("Admission date is mandatory");
    }

    // Verifica se o e-mail já está registrado
    if (employeeRepository.existsByEmail(employeeDTO.getEmail())) {
        throw new IllegalArgumentException("Email already registered");
    }

    // Busca a Role associada ao ID informado no DTO
    Role role = roleRepository.findById(employeeDTO.getRole().getId())
            .orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + employeeDTO.getRole().getId()));

    // Cria e popula a entidade Employee a partir do DTO
    Employee employee = new Employee();
    BeanUtils.copyProperties(employeeDTO, employee);
    employee.setRole(role);

    // Salva e retorna o DTO do funcionário criado
    return convertToDto(employeeRepository.save(employee));
}


    public Page<EmployeeDTO> getAllEmployees(String name, String email, String roleName, int page, int size) {
        Pageable pageable = PageRequest.of(page, size); // Configura a paginação
        Page<Employee> employeePage = employeeRepository.findByFilters(name, email, roleName, pageable);
        return employeePage.map(this::convertToDto); // Converte a lista de entidades para DTOs
    }


    public EmployeeDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));
        return convertToDto(employee); // Retorna o DTO correspondente
    }


        public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
        // Busca o funcionário no banco
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));

        // Se a data de admissão for nula, mantém a existente
        if (employeeDTO.getAdmissionDate() == null) {
            employeeDTO.setAdmissionDate(employee.getAdmissionDate());
        }

        // Verifica se o e-mail foi alterado e se já está sendo usado por outro
        if (!employee.getEmail().equals(employeeDTO.getEmail()) &&
            employeeRepository.existsByEmail(employeeDTO.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }

        // Busca e valida a nova Role
        Role role = roleRepository.findById(employeeDTO.getRole().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + employeeDTO.getRole().getId()));

        // Copia propriedades do DTO para a entidade, ignorando o ID (não deve ser alterado)
        BeanUtils.copyProperties(employeeDTO, employee, "id");
        employee.setRole(role);

        // Salva e retorna o funcionário atualizado como DTO
        return convertToDto(employeeRepository.save(employee));
    }


    public void deleteEmployee(Long id) {
        // Verifica se o funcionário existe antes de tentar deletar
        if (!employeeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employee not found with id " + id);
        }
        employeeRepository.deleteById(id); // Exclui do banco
    }


    private EmployeeDTO convertToDto(Employee employee) {
        // Cria novo DTO e copia propriedades da entidade Employee
        EmployeeDTO dto = new EmployeeDTO();
        BeanUtils.copyProperties(employee, dto);

        // Cria RoleDTO a partir da entidade Role associada
        RoleDTO roleDTO = new RoleDTO();
        BeanUtils.copyProperties(employee.getRole(), roleDTO);

        // Associa o RoleDTO ao EmployeeDTO
        dto.setRole(roleDTO);

        return dto; // Retorna o DTO completo
    }
}
