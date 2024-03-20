package br.com.fiap.postech.pontoeletronico.pontoeletronico.dto

import java.time.LocalDateTime

data class RegistroPontoResponseDto(
        val tipo: String,
        val hora: LocalDateTime,
        val nomeUsuario: String
)