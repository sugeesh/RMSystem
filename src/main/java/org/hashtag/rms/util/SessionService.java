package org.hashtag.rms.util;

import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.Serializable;

/**
 * Created by Buddhi on 3/1/2017.
 */
public class SessionService {

    private static Session session;
    private static Serializable currentSerializedUser;

    public static Session getSession() {
        if (session == null) {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("User");
            EntityManager em = emf.createEntityManager();
            session = (Session) em.getDelegate();
        }
        return session;
    }

    public static void setCurrentUserSerialized(Serializable savedUser) {
        setCurrentSerializedUser(savedUser);
    }

    public static Serializable getCurrentSerializedUser() {
        return currentSerializedUser;
    }

    public static void setCurrentSerializedUser(Serializable currentSerializedUser) {
        SessionService.currentSerializedUser = currentSerializedUser;
    }
}
