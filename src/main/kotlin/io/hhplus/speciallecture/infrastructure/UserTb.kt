package io.hhplus.speciallecture.infrastructure

import jakarta.persistence.Entity
import com.fasterxml.jackson.annotation.JsonIgnore
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.*

@Entity
@Table
class UserTb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    var userId: Long? = null
   
    @Column(name = "email")
    @field:Schema(description = "이메일")
    var email: String? = null
   
    @Column(name = "password")
    @JsonIgnore
    var password: String? = null
   
    @Column(name = "name")
    @field:Schema(description = "이름")
    var name: String? = null

}