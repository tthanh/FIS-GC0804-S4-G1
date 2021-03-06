package models;

import entities.Nations;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class NationModel {

    @PersistenceContext
    EntityManager em;

    public List<Nations> getAll() {
        return em.createNamedQuery("Nations.findAll").getResultList();
    }

    public Nations getById(int nid) {
        List<Nations> nations = em.createNamedQuery("Nations.findByNid").setParameter("nid", nid).getResultList();
        return nations.size() > 0 ? nations.get(0) : null;
    }
}
