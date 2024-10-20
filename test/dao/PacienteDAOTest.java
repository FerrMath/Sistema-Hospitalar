/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import modelo.Paciente;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PacienteDAOTest {
    
    @Mock
    PacienteDAO instance;
            
    
    public PacienteDAOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testCadastrarPacienteComDadosCorretamentePreenchidos() throws SQLException {
        System.out.println("cadastrarPaciente");
        Paciente p = new Paciente();
        p.setNome("Paciente Teste da Silva");
        p.setDataNascimento(new Date());
        p.setCpf("123.456.789.00");
        p.setEndereco("XYZ 123 - Fim do mundo");
        p.setTelefone("(12)912345678");
        p.setEmail("paciente@email.com");
        p.setIdConvenio(1);
        
        instance.cadastrarPaciente(p);
        verify(instance).cadastrarPaciente(p);
        
    }
    
    @Test
    public void testLevantarErroSeTentarCadastrarPacienteSemTodosOsDadosObrigatorios() throws SQLException {
        Paciente p = new Paciente();
        p.setDataNascimento(new Date());
        p.setIdConvenio(2);
        
        assertThrows(SQLException.class, () -> instance.cadastrarPaciente(p));
    }

    @Test
    public void testBuscarTodosOsPacientesCadastradosSemFiltro() throws Exception {
        ArrayList<Paciente> pacientes = new ArrayList<>();

        Paciente paciente1 = new Paciente();
        paciente1.setNome("João Silva");
        paciente1.setEndereco("Rua A, 123");
        paciente1.setDataNascimento(new Date(1990-1900, 5, 12));
        paciente1.setTelefone("11987654321");
        paciente1.setCpf("123.456.789-00");
        paciente1.setRg("12.345.678-9");
        paciente1.setEmail("joao@gmail.com");
        paciente1.setConvenio(1);

        Paciente paciente2 = new Paciente();
        paciente2.setNome("Maria Oliveira");
        paciente2.setEndereco("Rua B, 456");
        paciente2.setDataNascimento(new Date(1985-1900, 3, 23));
        paciente2.setTelefone("11998765432");
        paciente2.setCpf("234.567.890-11");
        paciente2.setRg("23.456.789-0");
        paciente2.setEmail("maria@gmail.com");
        paciente2.setConvenio(2);
        
        pacientes.add(paciente1);
        pacientes.add(paciente2);
        
        when(instance.buscarPaciente()).thenReturn(pacientes);
        ArrayList<Paciente> returned = instance.buscarPaciente();
        
        verify(instance).buscarPaciente();
        assertEquals(pacientes, returned);
    }

    @Test
    public void testBuscaPacientesUsandoFiltro() throws Exception {
        ArrayList<Paciente> pacientes = new ArrayList<>();
        
        Paciente paciente1 = new Paciente();
        paciente1.setNome("João Silva");
        paciente1.setEndereco("Rua A, 123");
        paciente1.setDataNascimento(new Date(1990-1900, 5, 12));
        paciente1.setTelefone("11987654321");
        paciente1.setCpf("123.456.789-00");
        paciente1.setRg("12.345.678-9");
        paciente1.setEmail("joao@gmail.com");
        paciente1.setConvenio(1);

        Paciente paciente2 = new Paciente();
        paciente2.setNome("Maria Oliveira");
        paciente2.setEndereco("Rua B, 456");
        paciente2.setDataNascimento(new Date(1985-1900, 3, 23));
        paciente2.setTelefone("11998765432");
        paciente2.setCpf("234.567.890-11");
        paciente2.setRg("23.456.789-0");
        paciente2.setEmail("maria@gmail.com");
        paciente2.setConvenio(2);
        
        paciente1.setIdPaciente(1);
        paciente2.setIdPaciente(2);
        
        
        pacientes.add(paciente1);
        pacientes.add(paciente2);
        
        String query = "where ID_PACIENTE =" + paciente1.getIdPaciente() + "";
        

        
        when(instance.buscarPacienteFiltro(anyString())).thenAnswer(invocation -> 
            new ArrayList<>(pacientes.stream()
                .filter(e -> e.getIdPaciente() == paciente1.getIdPaciente())
                .toList())
        );
        
        ArrayList<Paciente> returned = instance.buscarPacienteFiltro(query);
        
        verify(instance).buscarPacienteFiltro(anyString());
        
        assertNotNull(returned);
        assertEquals(1, returned.size());
        assertEquals(paciente1, returned.get(0));
    }
    
}
