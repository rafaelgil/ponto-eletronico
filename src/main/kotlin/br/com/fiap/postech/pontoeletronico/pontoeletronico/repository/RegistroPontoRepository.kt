package br.com.fiap.postech.pontoeletronico.pontoeletronico.repository

import br.com.fiap.postech.pontoeletronico.pontoeletronico.Colaborador
import br.com.fiap.postech.pontoeletronico.pontoeletronico.RegistroPonto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDate

interface RegistroPontoRepository : JpaRepository<RegistroPonto, Long> {
    @Query("SELECT rp FROM RegistroPonto rp WHERE rp.colaborador = :colaborador AND FUNCTION('DATE', rp.hora) = :data ORDER BY rp.hora DESC")
    fun findTopByColaboradorAndDateOrderByHoraDesc(colaborador: Colaborador, data: LocalDate): List<RegistroPonto>
}