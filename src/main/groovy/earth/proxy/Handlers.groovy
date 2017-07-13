package earth.proxy

import ratpack.http.TypedData
import ratpack.handling.Context

/**
 * Handlers responsible to proxy the Docker API
 *
 * @since 0.1.0
 */
class Handlers {

  /**
   * Receives a request and redispatches it to the Docker API
   *
   * @param ctx Ratpack's {@link Context}
   * @since 0.1.0
   */
  static void proxy(Context ctx) {
    String path = ctx.request.path
    String method = ctx.request.method
    String query = ctx.request.query
    Map<String,?> headers = ctx
      .request
      .headers?.asMultiValueMap()?.getAll()

    ProxyService proxyService = ctx.get(ProxyService)

    ctx
      .request
      .body
      .flatMap { TypedData body ->
        proxyService.proxyRequest(method, path, query, body.bytes, headers)
      }.onError { Throwable err ->
        err.printStackTrace()
        ctx.response.status(400)
        ctx.response.send("Error on the remote call: ${err.message}")
      }.then { ProxyResponse response ->
        ctx.response.status(response.status)
        ctx.response.send(response.content)
      }
  }
}
