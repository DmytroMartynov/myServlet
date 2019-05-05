package dmytro.db;

import dmytro.model.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class StudentDao {

    private SessionFactory sessionFactory;
private int id = 0;
    public StudentDao() {
        java.util.Properties properties = new java.util.Properties();
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream input = classLoader.getResourceAsStream("local.properties");
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sessionFactory = new Configuration().configure().addProperties(properties).buildSessionFactory();
    }

    public void close() {
        sessionFactory.close();
    }

    public void removeAll() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            String hql = String.format("DELETE FROM %s", Student.class.getSimpleName());
            Query query = session.createQuery(hql);
            query.executeUpdate();
            transaction.commit();
        }
    }

    public void removeStudentById(int id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Object persistentInstance = session.load(Student.class, id);
            if (persistentInstance != null) {
                session.delete(persistentInstance);
                transaction.commit();
            }

        }

    }
    public void removeStudent(Student user) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(user);
            transaction.commit();
        }
    }

    public void removeStudentByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Student student = new Student();
            student.setName(name);
            session.delete(student);
            transaction.commit();
        }
    }

    public void add(Student student) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            student.setId(id++);
            session.save(student);
            transaction.commit();
        }
    }

    public void updateStudent(Student student) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(student);
            transaction.commit();
        }
    }
    public Student getByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            return session
                    .createQuery("FROM Student WHERE name = :name ", Student.class)
                    .setParameter("name", name)
                    .list().get(0);
        }
    }

    public Student getStudents(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Student WHERE _id = '" + id + "'", Student.class).getSingleResult();
        }
    }

    public List<Student> getAllStudents() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Student", Student.class).list();
        }
    }

    public List<Student> getStudentsByAge(int fromAge, int toAge) {
        Calendar fromDate = new GregorianCalendar();
        Calendar toDate = new GregorianCalendar();
        fromDate.add(Calendar.YEAR, -fromAge);
        toDate.add(Calendar.YEAR, -toAge);
        Timestamp beginDate = new Timestamp(toDate.getTimeInMillis());
        Timestamp endDate = new Timestamp(fromDate.getTimeInMillis());

        try (Session session = sessionFactory.openSession()) {
            return session
                    .createQuery("FROM Student WHERE birth_date BETWEEN :from AND :to ORDER BY birth_date", Student.class)
                    .setParameter("from", beginDate)
                    .setParameter("to", endDate)
                    .list();
        }
}
    }

