package br.com.fiap.postech.pontoeletronico.pontoeletronico.dto

data class VisualizacaoRegistrosResponseDto(
        val registros: List<RegistroPontoResponseDto>,
        val horasTrabalhadas: Long,
        val observacao: String
)