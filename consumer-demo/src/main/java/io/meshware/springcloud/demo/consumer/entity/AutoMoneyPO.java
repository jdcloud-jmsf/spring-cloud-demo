// package io.meshware.springcloud.demo.consumer.entity;
//
// import lombok.Data;
// import org.hibernate.annotations.DynamicInsert;
// import org.hibernate.annotations.DynamicUpdate;
// import org.hibernate.annotations.GenericGenerator;
// import org.springframework.data.annotation.CreatedDate;
//
// import javax.persistence.*;
// import java.sql.Timestamp;
//
// @Data
// @DynamicUpdate
// @DynamicInsert
// @Entity
// @Table(name = "money")
// public class AutoMoneyPO {
//     @Id
//     @GeneratedValue(strategy = GenerationType.AUTO, generator = "myid")
//     @GenericGenerator(name = "myid", strategy = "com.git.hui.boot.jpa.generator.ManulInsertGenerator")
//     @Column(name = "id")
//     private Integer id;
//
//     @Column(name = "name")
//     private String name;
//
//     @Column(name = "money")
//     private Long money;
//
//     @Column(name = "is_deleted")
//     private Byte isDeleted;
//
//     @Column(name = "create_at")
//     @CreatedDate
//     private Timestamp createAt;
//
//     @Column(name = "update_at")
//     @CreatedDate
//     private Timestamp updateAt;
//
// }