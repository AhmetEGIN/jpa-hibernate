package org.example.entities;

import jakarta.persistence.*;

@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;


    // Person bir passport'a sahip
    // iki farklı relationship var. one-directional ve bi-directional
    // one-directional -> sadece bir entity diğer entity'yi bilir.
    // Mesela bir person'da pasaport saklarsın ama pasaport'u kayıt ettiğin yerde person ile ilgili bir bilgi bulamazsın

    // One-directional ilişkiler sadece ilişki sahibi tarafa yazılır

    // Cascade -> Bu entity'de bir işlem yapıldığı zaman karşı taraftaki entity içince aynı işlem yapılıp yapılacağını
    // belirtir. Farklı seçenekleri mevcuttur. Bu seçenekler entityManger operasyonlarıdır
    // CascadeType.Persist -> Person persist edilirse passport'ta persist edilmeli

    // FetchType -> Default değeri EAGER'dır.
    // Bİr person Select edildiğinde Passport bilgileri de select edilir. Otomatik olarak join atılır
    // Eğer Lazy değerini verirsek passport'a ihtiyacımız olduğumuz zaman select edilir.
    // EAGER seçtiğimiz zaman daha fazla data'ya sahip oluruz belki o datalara ihtiyacımız olmayabilir.
    // Ama EAGER sayesinde sadece bir select query göndeririz
    // Lazy kullandığımızda ise her ilişki için bir query göndermemiz gerekir.

    //OneToOne parametrelerinde biri de targetEntity'dir. ->
//    Mesela Passport yerine bir super type kullandık. TargetEntity ile hangi class ile ilişki kurulacağı belirtilir

    // optional() veri tabanında bu verinin null olup olamayacağı durumları belirtir.
    // örneğin bir person passport'a sahip olmayabilir.
    // optional'ın default değeri true'dur.
    // eğer bir field zorunluysa bunu false yapmalıyız

    // orphanRemovel -> parent silindiği zaman child entity'nin veri tabanından silinip silinmeyeceğini belirtir
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "passport_id")
    private Passport passport;
//    JoinColumn, ilişki sahibi tarafında olur. Yani ForeignKey olacak olan taraftır
//    Karşı tarafta ise mappedBy attribute'unu ekleriz
    public Passport getPassport() {
        return passport;
    }

    public void setPassport(Passport passport) {
        this.passport = passport;
    }
// Bi-directional ->




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", passport=" + passport +
                '}';
    }
}
