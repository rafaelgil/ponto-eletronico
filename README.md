# ponto-eletronico 2

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

### Arquitetura Infra
![hacka-fiap](https://github.com/rafaelgil/ponto-eletronico/assets/2104773/0b455684-1aab-41a3-8aa6-7a1fc2122ea0)

### Ponto Eletronico MVP
![Ponto_Eletronico_-_MVP_2](https://github.com/rafaelgil/ponto-eletronico/assets/2104773/c94cc951-1ead-485a-88b5-8bc3bca5ef7e)

### Ponto Eletronico V2
![Ponto_Eletronico_-_V2](https://github.com/rafaelgil/ponto-eletronico/assets/2104773/831bcad0-7b01-4f5a-81ea-151b8a667af3)


