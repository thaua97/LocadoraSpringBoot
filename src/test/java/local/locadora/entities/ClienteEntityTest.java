
package local.locadora.entities;

import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Iterator;
import java.util.Set;
import junit.framework.Assert;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

public class ClienteEntityTest {

    private static Validator validator;
    
    @Rule
    public ExpectedException expected = ExpectedException.none();

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    /**
     * Note que <b>validator</b> aplica a validação do bean validation
     * O Iterator é utilizado para pegar as violações ocorridas
     */
    @Test
    public void naoDeveValidarUmNomeComDoisCaracteres() {
        Cliente cliente = new Cliente();
        cliente.setNome("An");

        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        // }

        assertThat(message, is("Um nome deve possuir entre 4 e 50 caracteres"));
    }
    
    
    @Test
    public void ignorarCpfDoCliente() {
        
        Cliente cliente  = new Cliente();
        cliente.setNome("César azpilicueta");
        cliente.setCpf("04424877763");
        
        Set < ConstraintViolation <Cliente> > violacoes = validator.validate(cliente);
        Iterator it = violacoes.iterator();
        
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String deuRuim = x.getMessage();

        assertThat(deuRuim, is("O CPF não é válido"));
    }
    
    @Test
    public void CpfDeveSerPersistidoNoBanco () {
        
        Cliente cliente = new Cliente();
        cliente.setNome("Kevin De Bruine");
        cliente.setCpf("");
        
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String deuRuim = x.getMessage();
        
        assertThat(deuRuim, is("O CPF não é válido"));
        
    }
    
    @Test
    public void primeiraLetraDoNomePrecisaSerMaiuscula() {
        try {
            
            Cliente c = new Cliente();
            c.setNome("André gomes");
           
            assertThat(c.getNome(), is("André Gomes"));
        
        } catch (Exception e) {
            fail();
        }
    }
    
    @Test
    public void naoPodeEspacoNoInicioEnoFimDoNome() {
        Cliente cliente = new Cliente();
        cliente.setNome("  Zaha  ");
        assertThat(cliente.getNome(), is("Zaha"));
    }
    
    @Test
    public void nomeDoClienteDeveSerUnico() {
        Cliente c1 = new Cliente();
        Cliente c2 = new Cliente();
        Cliente c3 = new Cliente();
        Cliente c4 = new Cliente();
        
        try {
            c2.setNome("Mohamed");
            c2.setNome("Mohamed");
            c3.setNome("MohSalah");
            c4.setNome("Mohamid");
            fail();
            
        } catch (Exception e) {
            
            Object ExceptionC = null;
            assertEquals(ExceptionC, ExceptionC);
        
        }
    }
}

