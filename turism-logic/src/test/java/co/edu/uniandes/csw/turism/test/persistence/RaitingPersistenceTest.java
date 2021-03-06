/*
The MIT License (MIT)

Copyright (c) 2015 Los Andes University

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package co.edu.uniandes.csw.turism.test.persistence;

import co.edu.uniandes.csw.turism.entities.RaitingEntity;
import co.edu.uniandes.csw.turism.entities.TripEntity;
import co.edu.uniandes.csw.turism.persistence.RaitingPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author da.salinas3247
 */
@RunWith(Arquillian.class)
public class RaitingPersistenceTest {
    
    /**
     * 
     * @return 
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(RaitingEntity.class.getPackage())
                .addPackage(RaitingPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * Entidad padre que contiene a  la entidad Raiting
     */
    TripEntity fatherEntity;

    /**
     * Especialización de CrudPersistence para implementar 
     * la persistencia de raiting
     */
    @Inject
    private RaitingPersistence raitingPersistence;

    /**
     * 
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * 
     */
    @Inject
    UserTransaction utx;

    /**
     * Configuración inicial de la prueba.
     *
     * 
     */
    @Before
    public void configTest() {
        try {
            utx.begin();
            em.joinTransaction();
            clearData();
            insertData();
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * Limpia las tablas que están implicadas en la prueba.
     *
     * 
     */
    private void clearData() {
        em.createQuery("delete from RaitingEntity").executeUpdate();
        em.createQuery("delete from TripEntity").executeUpdate();
    }

    /**
     * 
     */
    private List<RaitingEntity> data = new ArrayList<RaitingEntity>();

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
     *
     * 
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
            fatherEntity = factory.manufacturePojo(TripEntity.class);
            fatherEntity.setId(1L);
            em.persist(fatherEntity);
        for (int i = 0; i < 3; i++) {
            RaitingEntity entity = factory.manufacturePojo(RaitingEntity.class);
            
            entity.setTrip(fatherEntity);
            em.persist(entity);
            data.add(entity);
        }
    }
    /**
     * Prueba para crear un Raiting.
     *
     * 
     */
    @Test
    public void createRaitingTest() {
		PodamFactory factory = new PodamFactoryImpl();
        RaitingEntity newEntity = factory.manufacturePojo(RaitingEntity.class);
        newEntity.setTrip(fatherEntity);
        RaitingEntity result = raitingPersistence.create(newEntity);

        Assert.assertNotNull(result);

        RaitingEntity entity = em.find(RaitingEntity.class, result.getId());

        Assert.assertEquals(newEntity.getName(), entity.getName());
        Assert.assertEquals(newEntity.getValue(), entity.getValue());
        Assert.assertEquals(newEntity.getDate(), entity.getDate());
        Assert.assertEquals(newEntity.getTextComment(), entity.getTextComment());
    }

    /**
     * Prueba para consultar un Raiting.
     *
     * 
     */
    @Test
    public void getRaitingTest() {
        RaitingEntity entity = data.get(0);
        RaitingEntity newEntity = raitingPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getName(), newEntity.getName());
        Assert.assertEquals(entity.getValue(), newEntity.getValue());
        Assert.assertEquals(entity.getDate(), newEntity.getDate());
        Assert.assertEquals(entity.getTextComment(), newEntity.getTextComment());
    }

    /**
     * Prueba para eliminar un Raiting.
     *
     * 
     */
    @Test
    public void deleteRaitingTest() {
        RaitingEntity entity = data.get(0);
        raitingPersistence.delete(entity.getId());
        RaitingEntity deleted = em.find(RaitingEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar un Raiting.
     *
     * 
     */
    @Test
    public void updateRaitingTest() {
        RaitingEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        RaitingEntity newEntity = factory.manufacturePojo(RaitingEntity.class);

        newEntity.setId(entity.getId());

        raitingPersistence.update(newEntity);

        RaitingEntity resp = em.find(RaitingEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getName(), resp.getName());
        Assert.assertEquals(newEntity.getValue(), resp.getValue());
        Assert.assertEquals(newEntity.getDate(), resp.getDate());
        Assert.assertEquals(newEntity.getTextComment(), resp.getTextComment());
    }
    
}
