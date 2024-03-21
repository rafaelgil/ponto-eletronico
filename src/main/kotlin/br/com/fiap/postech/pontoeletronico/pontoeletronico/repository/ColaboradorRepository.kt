package br.com.fiap.postech.pontoeletronico.pontoeletronico.repository

import br.com.fiap.postech.pontoeletronico.pontoeletronico.Colaborador
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.Optional

interface ColaboradorRepository : JpaRepository<Colaborador, Long> {
    @Query("SELECT c FROM Colaborador c WHERE c.matricula = :matricula")
    fun findByMatricula(matricula: String): Optional<Colaborador>
}