package sgsits.cse.dis.user.model;

import javax.persistence.*;

@Entity
@Table(name = "first_year_me_student")
public class FirstYearMeStudent extends StudentInformation{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
