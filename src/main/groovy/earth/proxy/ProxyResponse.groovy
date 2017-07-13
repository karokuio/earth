package earth.proxy

/**
 * Represents any given response from the Docker API
 *
 * @since 0.1.0
 */
class ProxyResponse {

  /**
   * Http response status
   *
   * @since 0.1.0
   */
  Integer status

  /**
   * All headers from the response
   *
   * @since 0.1.0
   */
  Map<String, Object> headers

  /**
   * Content in bytes
   *
   * @since 0.1.0
   */
  byte[] content
}
