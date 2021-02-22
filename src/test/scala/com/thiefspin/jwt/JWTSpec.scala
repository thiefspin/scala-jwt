package com.thiefspin.jwt

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import play.api.libs.json._

class JWTSpec extends AnyWordSpec with Matchers {

  private val secret: String = "test-secret"
  private val payload: JsValue = Json.parse("""{"id":1,"email":"email@email.com","name":"firstname","surname":"lastname","password":"password","created":"2021-02-18T20:18:03.392+02:00","lastLogin":"2021-02-18T20:18:03.415+02:00"}""")
  private val payload2: JsValue = Json.parse("""{"id":2,"email":"email@email.com","name":"firstname","surname":"lastname","password":"password","created":"2021-02-18T20:18:03.392+02:00","lastLogin":"2021-02-18T20:18:03.415+02:00"}""")
  //private val expectedToken: String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwiZW1haWwiOiJlbWFpbEBlbWFpbC5jb20iLCJuYW1lIjoiZmlyc3RuYW1lIiwic3VybmFtZSI6Imxhc3RuYW1lIiwicGFzc3dvcmQiOiJwYXNzd29yZCIsImNyZWF0ZWQiOiIyMDIxLTAyLTE4VDIwOjE4OjAzLjM5MiswMjowMCIsImxhc3RMb2dpbiI6IjIwMjEtMDItMThUMjA6MTg6MDMuNDE1KzAyOjAwIn0.77-9Fe-_vTTvv702Ue-_vUvvv73vv71ENWwI77-9Tijvv71QNyDvv73vv73vv70B77-977-9Ce-_vd6R"

  private val encoded = JWT.encode(secret, payload)
  private val encoded2 = JWT.encode(secret, payload2)

  "JWT " should {
    "encode a token correctly " in {
      assert(encoded != encoded2)
    }

    "decode a token correctly with default algorithm" in {
      JWT.decode(encoded, Some(secret)) match {
        case JWTResult.JWT(_, result) =>
          assert(payload == result)
        case _ => assert(false)
      }
      JWT.decode(encoded2, Some(secret)) match {
        case JWTResult.JWT(_, result) =>
          assert(payload2 == result)
        case _ => assert(false)
      }
    }
  }

  "encode and decode a token with HmacSHA384" in {
    val token = JWT.encode(secret, payload, algorithm = Some(Algorithm.HS384))
    JWT.decode(token, Some(secret)) match {
      case JWTResult.JWT(_, result) =>
        assert(payload == result)
      case _ => assert(false)
    }
  }

  "encode and decode a token with HmacSHA512" in {
    val token = JWT.encode(secret, payload, algorithm = Some(Algorithm.HS512))
    JWT.decode(token, Some(secret)) match {
      case JWTResult.JWT(_, result) =>
        assert(payload == result)
      case _ => assert(false)
    }
  }

  "fail to decode an empty token " in {
    JWT.decode("", Some(secret)) match {
      case JWTResult.JWT(_, _) =>
        assert(false)
      case e => assert(e == JWTResult.EmptyJWT)
    }
  }

  "encode the header correctly " in {
    val jsHeader = Json.parse("""{"headerField": "theValue"}""").as[JsObject]
    val token = JWT.encode(secret, payload, header = jsHeader, algorithm = Some(Algorithm.HS256))
    JWT.decode(token, Some(secret)) match {
      case JWTResult.JWT(header, result) =>
        assert(payload == result)
        assert(header.extraHeader == jsHeader)
        assert(header.alg == Algorithm.HS256)
      case _ => assert(false)
    }
  }

  "return too many segments error " in {
    JWT.decode("csddsc.sdcsd.sdcsdc.sdcsd.sdcsd", Some(secret)) match {
      case JWTResult.TooManySegments => {
        assert(true)
      }
      case e => assert(false)
    }
  }

  "return not enough segments error " in {
    JWT.decode("csddsc.sdcsd", Some(secret)) match {
      case JWTResult.NotEnoughSegments => {
        assert(true)
      }
      case e => assert(false)
    }
  }

}
