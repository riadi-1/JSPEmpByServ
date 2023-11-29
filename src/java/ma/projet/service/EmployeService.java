/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.projet.service;

import java.util.List;
import ma.projet.beans.Employe;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

/**
 *
 * @author Ouissal
 */
public class EmployeService implements IDao<Employe>{

    @Override
    public boolean create(Employe o) {
         Session session  = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(o);
        session.getTransaction().commit();

        return true; 
    }

    @Override
    public boolean update(Employe o) {
       
        Session session  = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(o);
        session.getTransaction().commit();

        return true;
    }

    @Override
    public boolean delete(Employe o) {
        Session session  = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(o);
        session.flush();
        session.getTransaction().commit();

        return true;
    }

    @Override
    public Employe getById(int id) {
        Employe employe  = null;
        Session session  = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        employe  = (Employe) session.get(Employe.class, id);
        session.getTransaction().commit();

        return employe;
    }

    @Override
    public List<Employe> getAll() {
List <Employe> employes = null;
        Session session  = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        employes  = session.createQuery("from Employe").list();
        System.out.print("from employees");
        System.out.print(employes);
        session.getTransaction().commit();

        return employes;    
    }
     public List<Employe> getChefs() {
List <Employe> employes = null;
        Session session  = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        employes  = session.createQuery("from Employe").list();
        System.out.print("from chefs");
        System.out.print(employes);
        session.getTransaction().commit();
        return employes;    
    }
    public List<Object[]> nbEmployes(){
        List<Object[]> employes = null;
        Session session  = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        employes  = session.createQuery("select count(m.service.nom), m.service.nom from Employe m group by m.service.nom").list();
         
        session.getTransaction().commit();
        return employes;
    }
}
