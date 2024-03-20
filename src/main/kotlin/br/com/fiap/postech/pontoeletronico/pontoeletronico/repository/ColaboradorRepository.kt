package br.com.fiap.postech.pontoeletronico.pontoeletronico.repository

import br.com.fiap.postech.pontoeletronico.pontoeletronico.Colaborador
import org.springframework.data.jpa.repository.JpaRepository

interface ColaboradorRepository : JpaRepository<Colaborador, Long>