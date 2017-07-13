package earth.proxy

import ratpack.exec.Promise

/**
 * Proxies all requests to Docker API
 *
 * @since 0.1.0
 */
interface ProxyService {

    /**
   * Sends a request to Docker
   *
   * @param method Http method
   * @param path path to the resource
   * @param query request query parameters
   * @param body request content
   * @param headers request headers
   * @since 0.1.0
   */
  Promise<ProxyResponse> proxyRequest(
    String method, String path, String query, byte[] body, Map<String,?> headers)
}
