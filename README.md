# JWT

This library provides you with the functionality to encode and decode JWT tokens with a range of different algorithms.

## Basics Usage

Note that this library uses Play Json. For more info: https://www.playframework.com/documentation/2.8.x/ScalaJson#Json

### Encode a payload

```scala
import play.api.libs.json._

val secret: String = "very-secret"
val payload: JsValue = Json.parse("""{"id":1, "name": "john"}""")

//By default the algorithm used is HmacSHA256
val encoded: String = JWT.encode(secret, payload)
```

### Decode a token

```scala
JWT.decode(encoded, Some(secret)) match {
  case JWTResult.JWT(header, payload) =>
  //payload will be a JsObject of {"id":1, "name": "john"}
  case JWTResult.TooManySegments =>
  case JWTResult.NotEnoughSegments =>
  case JWTResult.EmptyJWT =>
  case JWTResult.InvalidSignature =>
  case JWTResult.InvalidHeader =>
  case JWTResult.JWTError(e) =>
}
```
