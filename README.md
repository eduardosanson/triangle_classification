# triangle-classification
# Introduction

Triangle classification is an Api to classify the triangle's type EQUILATERAL, ISOSCELES and SCALENE.

## Use Cases

There are a reason to use Triangle Classification Api, for you to know to classify the triangle type.  

## Classify 

```http
POST /v1/classify/triangle
```

| Parameter    | Type        | Location | Description |
|:-------------|:------------|:---------|:------------|
| `x-api-key`  | `string`    | header   | **Required**. Your classify API key |
| `firstSide`  | `integer`   | body     | **Required**. |
| `secondSide` | `integer`   | body     | **Required**. |
| `secondSide` | `integer`   | body     | **Required**. |

## Responses

```json
{
  "type" : "string"
}
```

## History

To find api history for request and response you can execute the following http request

```http
GET /v1/requests
```

| Parameter    | Type        | Location | Description |
|:-------------|:------------|:---------|:------------|
| `x-api-key`  | `string`    | header   | **Required**. Your classify API key |

## Responses
Response with a list of history requests and response


```json
[
    {
        "id": "string",
        "request": {
            "firstSide": "integer",
            "secondSide": "integer",
            "thirdSide": "integer"
        },
        "response": {
            "type": "string"
        }
    }
]
```
### Dependencies
[mvn 3.6.3](https://maven.apache.org/docs/3.6.3/release-notes.html) \
[java 11.0.12](https://docs.aws.amazon.com/corretto/latest/corretto-11-ug/what-is-corretto-11.html) \
[terraform v1.1.2](https://www.terraform.io/intro)

### Test
To run all tests execute the command below 

```shell script
mvn test
```
### Deploy

#### Production
```shell script
make build-deploy
make ini-prod
make apply 
```