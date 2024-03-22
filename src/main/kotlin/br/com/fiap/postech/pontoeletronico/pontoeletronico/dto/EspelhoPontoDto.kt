package br.com.fiap.postech.pontoeletronico.pontoeletronico.dto

data class EspelhoPontoDto(
        val registros: List<VisualizacaoRegistrosResponseDto>,
        val totalHoras: Long
)
