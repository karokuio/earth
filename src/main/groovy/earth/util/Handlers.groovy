package earth.util

import ratpack.exec.Promise
import ratpack.handling.Context
import ratpack.http.MutableHeaders

/**
 * Utilities for handlers
 *
 * @since 0.1.0
 */
class Handlers {

  /**
   * Extracts a given header by its name from the current
   * {@link Context}
   *
   * @param ctx the context we want the header from
   * @param name the name of the header
   * @return a {@link Promise} with the content of the header
   * @since 0.1.0
   */
  static Promise<String> header(Context ctx, String name) {
    return Promise.value(ctx.request.headers.get(name))
  }

  /**
   * Utility function to remove the 'Bearer ' literal from
   * the authorization header in order to get only the token
   * string
   *
   * @param token an authorization header content
   * @return only the token string
   * @since 0.1.0
   */
  static String removeBearerFromToken(String token) {
    return token - 'Bearer '
  }

  /**
   * Apply CORS headers to allow front end developers to
   * work with the backend
   *
   * @param ctx Ratpack's {@link Context}
   * @since 0.1.0
   */
  static void applyCors(Context ctx) {
    MutableHeaders headers = ctx.response.headers
    headers.set('Access-Control-Allow-Origin', '*')
    headers.set('Access-Control-Allow-Headers', 'x-requested-with, origin, content-type, accept')
    ctx.next()
  }
}
