package sgsits.cse.dis.user.model;

import javax.persistence.*;

@Entity
@Table(name = "third_year_be_student")
public class ThirdYearBeStudent {
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
