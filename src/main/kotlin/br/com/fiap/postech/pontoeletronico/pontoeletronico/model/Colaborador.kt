package br.com.fiap.postech.pontoeletronico.pontoeletronico

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Colaborador(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,
        val nome: String,
        val matricula: String,
        val usuario: String,
        val senha: String
)