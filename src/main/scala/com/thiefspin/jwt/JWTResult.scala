package com.thiefspin.jwt

import play.api.libs.json.JsObject

/**
 * Represent result from decode operation
 */
sealed trait JWTResult

object JWTResult {

  /**
   * Represent JWT data
   *
   * @param header  is the header for jwt
   * @param payload is the data for jwt
   */
  final case class JWT(header: JWTHeader, payload: JsObject) extends JWTResult

  /**
   * Represent Failure Result from decode operation in case JWT has too many segments
   */
  case object TooManySegments extends JWTResult

  /**
   * Represent Failure Result from decode operation in case JWT has not enough segments
   */
  case object NotEnoughSegments extends JWTResult

  /**
   * Represent Failure Result from decode operation in case JWT is empty
   */
  case object EmptyJWT extends JWTResult

  /**
   * Represent Failure Result from decode operation in case JWT has invalid Signature
   */
  case object InvalidSignature extends JWTResult

  /**
   * Represent Failure Result from decode operation in case JWT has invalid header
   */
  case object InvalidHeader extends JWTResult

  /**
   * Catch exceptions in here
   *
   * @param e Throwable
   */

  final case class JWTError(e: Throwable) extends JWTResult

}