package br.com.fiap.postech.pontoeletronico.pontoeletronico

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.time.LocalDateTime

@Entity
class RegistroPonto(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "colaborador_id")
        val colaborador: Colaborador,

        val tipo: String,
        val hora: LocalDateTime = LocalDateTime.now()
)