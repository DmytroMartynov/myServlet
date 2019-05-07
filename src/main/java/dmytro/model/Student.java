package dmytro.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "student")
public class Student {


    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "_id")
    private int id;
    @Column(name = "name", nullable = false, unique = false, length = 30)
    private String name;
    @Column(name = "birth_date", nullable = false, unique = false)
    private Timestamp birthDate;




    public Student(String name, String birthInString) {
        this.name = name;

        Date date = null;
        try {
            date = formatter.parse(birthInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert date != null;
        this.birthDate = new Timestamp(date.getTime());
    }
    public Student(int id, String name, String birthInString) {
        this.id = id;
        this.name = name;

        Date date = null;
        try {
            date = formatter.parse(birthInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.birthDate = new Timestamp(date.getTime());
    }

    public Student() {

    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate=" + formatter.format(birthDate) +
                '}';
    }


}
