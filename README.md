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

### Registro de Ponto
```bash
curl --request POST \
  --url 'http://localhost:8080/registrar-ponto?matricula=123456789' \
  --header 'Content-Type: application/json'
```

### Consulta de Registros
```bash
curl --location 'http://localhost:8080/registrar-ponto?colaboradorId=1&data=2024-03-22' \
--header 'Authorization: eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMjM0NTY3ODkiLCJleHAiOjE3MTEwNzg2NDB9.RgS1-jcvLoFcc3UDU4aLLfhzAxd_ETUThM80DbwKOzYxxF_MxYixB47gk793If_o-1gvlQJ0PlzXPHFmbXLPsg'
```

### Espelho de Ponto
```bash
curl --location 'http://localhost:8080/registrar-ponto/espelho?colaboradorId=1&ano=2024&mes=03&email=teste@fiap.com' \
--header 'Authorization: eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMjM0NTY3ODkiLCJleHAiOjE3MTEwNzg2NDB9.RgS1-jcvLoFcc3UDU4aLLfhzAxd_ETUThM80DbwKOzYxxF_MxYixB47gk793If_o-1gvlQJ0PlzXPHFmbXLPsg'
```

### Arquitetura Infra
![hacka-fiap](https://github.com/rafaelgil/ponto-eletronico/assets/2104773/0b455684-1aab-41a3-8aa6-7a1fc2122ea0)

### Ponto Eletronico MVP
![Ponto_Eletronico_-_MVP_1](https://github.com/rafaelgil/ponto-eletronico/assets/2104773/1e2e7eeb-54a5-4601-9d02-bb61a70d4704)

### Ponto Eletronico V2
![Ponto_Eletronico_-_V2](https://github.com/rafaelgil/ponto-eletronico/assets/2104773/831bcad0-7b01-4f5a-81ea-151b8a667af3)


