Run the docker-compose.yml file

```
docker compose -f ./docker-compose.yml up
```

Run the application

Execute below cURL for deposit

```
curl --location 'localhost:8080/api/v1/transfer' \
--header 'Content-Type: application/json' \
--data '{
    "customerId": "159357",
    "accountNo":"123456",
    "amount":100
}'
```

Execute below cURL for withdrawal

```
curl --location 'localhost:8080/api/v1/transfer' \
--header 'Content-Type: application/json' \
--data '{
    "customerId": "159357",
    "accountNo":"123456",
    "amount":-100
}'
```

Get Balance and recent transaction of account using below cURL

```
curl --location --request GET 'localhost:8080/api/v1/account/balance/123456' \
--header 'Content-Type: application/json' \
--data '{
    "customerId": "159357",
    "accountNo":"123456",
    "amount":100
}'
```
