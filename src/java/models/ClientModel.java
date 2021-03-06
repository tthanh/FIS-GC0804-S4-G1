package models;

import entities.Clients;
import helpers.PasswordHelper;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ClientModel {

    @PersistenceContext
    EntityManager em;

    public Clients authenticate(String username, String password) {
        try {
            Clients client = em.createNamedQuery("Clients.findByUsername", Clients.class).setParameter("username", username).getSingleResult();
            String salt = client.getPasswordDigest().split("_")[1];
            boolean result = client.getPasswordDigest().equals(PasswordHelper.hashPassword(password, salt));
            return result ? client : null;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean register(Clients client) {
        try {
            em.persist(client);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean clientExists(int cid) {
        long result = (long) em.createNamedQuery("Clients.clientExists").setParameter("cid", cid).getSingleResult();
        return result > 0;
    }

    public boolean emailExists(String email) {
        long result = (long) em.createNamedQuery("Clients.uniqueEmail").setParameter("email", email).getSingleResult();
        return result > 0;
    }

    public Clients getByCid(int cid) {
        List<Clients> clients = em.createNamedQuery("Clients.findByCid").setParameter("cid", cid).getResultList();
        return clients.size() > 0 ? clients.get(0) : null;
    }

    public List<Clients> getListClient() {
        List<Clients> clients = em.createNamedQuery("Clients.findAll").getResultList();
        return clients;

    }

    public List<Clients> findRange(int[] range) {
        List<Clients> clients = em.createNamedQuery("Clients.findAll")
                .setMaxResults(range[1] - range[0])
                .setFirstResult(range[0])
                .getResultList();
        return clients;

    }

    public List<Clients> getListClientByName(String name) {
        List<Clients> clients = em.createNamedQuery("Clients.findByName").setParameter("name", name).getResultList();
        return clients;

    }

    public List<Clients> getClientsByName(String name, int[] range) {
        List<Clients> clients = em.createNamedQuery("Clients.findByName").setParameter("name", name)
                .setMaxResults(range[1] - range[0])
                .setFirstResult(range[0])
                .getResultList();
        return clients;
    }

    public List<Clients> getListClientByMail(String mail) {
        List<Clients> clients = em.createNamedQuery("Clients.findByEmail").setParameter("email", mail).getResultList();
        return clients;

    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<Clients> rt = cq.from(Clients.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        javax.persistence.Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    public boolean update(Clients client) {
        try {
            Clients update_client = em.find(Clients.class, client.getCid());
            if (update_client == null) {
                return false;
            }
            update_client.setName(client.getName());
            update_client.setEmail(client.getEmail());
            update_client.setClientAddress(client.getClientAddress());
            update_client.setContactDetails(client.getContactDetails());
            update_client.setCompanyName(client.getCompanyName());
            em.merge(update_client);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean changePassword(Clients client) {
        try {
            Clients update_client = em.find(Clients.class, client.getCid());
            if (update_client == null) {
                return false;
            }
            update_client.setPasswordDigest(client.getPasswordDigest());
            em.merge(update_client);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
