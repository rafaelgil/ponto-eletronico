package br.com.fiap.postech.pontoeletronico.pontoeletronico.controller


import br.com.fiap.postech.pontoeletronico.pontoeletronico.dto.ErrorResponseDto
import br.com.fiap.postech.pontoeletronico.pontoeletronico.exception.AutenticacaoException
import br.com.fiap.postech.pontoeletronico.pontoeletronico.exception.DadosColaboradorException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class ControllerAdvice() {

    @ExceptionHandler(AutenticacaoException::class)
    fun handleAutenticacaoException(ex: AutenticacaoException, request: WebRequest): ResponseEntity<ErrorResponseDto> {
        val erro = ErrorResponseDto(
            ex.localizedMessage,
            HttpStatus.UNAUTHORIZED.value()
        )

        return ResponseEntity(erro, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(DadosColaboradorException::class)
    fun handleDadosColaboradorException(ex: DadosColaboradorException, request: WebRequest): ResponseEntity<ErrorResponseDto> {
        val erro = ErrorResponseDto(
            ex.localizedMessage,
            HttpStatus.NOT_FOUND.value()
        )

        return ResponseEntity(erro, HttpStatus.NOT_FOUND)
    }
}