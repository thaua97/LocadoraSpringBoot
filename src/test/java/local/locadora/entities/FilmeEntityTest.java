/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.locadora.entities;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.ConstraintViolation;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Iterator;
import java.util.Set;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 *
 * @author TBorges
 */
public class FilmeEntityTest {
    
    private static Validator validator;
    
    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    
    @Test
    public void naoPermiteEspacoNoInicioENoFimDoNomeDoFilme() {
        
        Filme f1 = new Filme();
        Filme f2 = new Filme();
        Filme f3 = new Filme();
        
        try {
            
            f1.setNome("  O alto da Comparecida");
            f2.setNome("  O alto da Comparecida  ");
            f3.setNome("O alto da Comparecida  ");
            
            fail();
            
        } catch (Exception e) {
            Object ExceptionCliente = null;
            Assert.assertSame(ExceptionCliente, e);
        }

    }
    
    @Test
    public void estoqueTemQueSerPositivo() {
        
        Filme f = new Filme();
        f.setEstoque(-1000);
        
        Set <ConstraintViolation <Filme>> violacoes = validator.validate(f);
        Iterator it = violacoes.iterator();
        
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String deuZica = x.getMessage();
        
        assertThat(deuZica, is("O Estoque deve ser positivo"));
    }
    
    @Test
    public void NomeDoFilmeEntreDoisACemCaracteres() {
        
        Filme f = new Filme();
        f.setNome("T");
        
        Set<ConstraintViolation <Filme>> violacoes = validator.validate(f);
        Iterator it = violacoes.iterator();
        
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String deuPT = x.getMessage();
    
         assertThat(deuPT, is("Um filme deve possuir entre 2 e 100 caracteres"));
    }
    
    @Test
    public void NomeNaoPodePassarComMaisDeCemCaracteres(){
        Filme f = new Filme();
        f.setNome("MussumIpsumcacildsvidislitroabertis.MéfaizelementumgirarzisnisierosvermeioDelegadisgentefinisbibendumegestasrecCasamentissfaialandrissepiruliempjoijdoiajsdoiasjdoiasjdoiasjdo");
        
        Set<ConstraintViolation <Filme>> violacoes = validator.validate(f);
        Iterator it = violacoes.iterator();
        
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String deuRuim = x.getMessage();
        assertThat(deuRuim, is("Um filme deve possuir entre 2 e 100 caracteres"));
        
    }
    
    @Test
    public void precoDeveTerMaximoDeDoisDigitos() {
        Filme f = new Filme();
        f.setPrecoLocacao(9999.777);
        
        Set<ConstraintViolation<Filme>> violacoes = validator.validate(f);
        Iterator it = violacoes.iterator();
        
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String erou = x.getMessage();
        
        assertThat(erou, is("O Preço deve ter no máximo dois dígitos"));
    }
    
    @Test
    public void valorDaLocacaoDeveNaoPodeSerNegativo() {
        Filme f = new Filme();
        f.setPrecoLocacao(-2.56);
        
        Set<ConstraintViolation<Filme>> violacoes = validator.validate(f);
        Iterator it = violacoes.iterator();
        
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String erou = x.getMessage();
        
        assertThat(erou, is("O Valor da locação deve ser positivo"));
    }
}
