package com.thiefspin.jwt

import play.api.libs.json._

/**
 * Represent JWT Header
 *
 * @param alg         is algorithm that used to encrypt token
 * @param extraHeader is represent
 */
final case class JWTHeader(alg: Algorithm, extraHeader: JsObject = Json.obj())