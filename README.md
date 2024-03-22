# ponto-eletronico

## Endpoints

### Autenticar
```bash
curl --location 'http://localhost:8080/autenticar' \
--header 'Content-Type: application/json' \
--data '{
    "matricula": "123456789",
    "senha": "fiap123"
}'
```

### Registrar ponto
```bash
curl --request POST \
  --url 'http://localhost:8080/registrar-ponto?matricula=123456789' \
  --header 'Content-Type: application/json'
```

### Relatório de Ponto
```bash
curl --location 'http://localhost:8080/registrar-ponto?colaboradorId=1&data=2024-03-22' \
--header 'Authorization: eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMjM0NTY3ODkiLCJleHAiOjE3MTEwNzg2NDB9.RgS1-jcvLoFcc3UDU4aLLfhzAxd_ETUThM80DbwKOzYxxF_MxYixB47gk793If_o-1gvlQJ0PlzXPHFmbXLPsg'
```

### Relatório Espelho de Ponto
```bash
curl --location 'http://localhost:8080/registrar-ponto/espelho?colaboradorId=1&ano=2024&mes=03' \
--header 'Authorization: eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMjM0NTY3ODkiLCJleHAiOjE3MTEwNzg2NDB9.RgS1-jcvLoFcc3UDU4aLLfhzAxd_ETUThM80DbwKOzYxxF_MxYixB47gk793If_o-1gvlQJ0PlzXPHFmbXLPsg'
```