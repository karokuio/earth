package earth.proxy

import ratpack.exec.Promise
import com.netflix.client.ClientFactory
import com.netflix.niws.client.http.RestClient
import com.netflix.client.http.HttpRequest
import com.netflix.client.http.HttpResponse
import com.google.common.io.ByteStreams
import com.netflix.config.ConfigurationManager

/**
 * Default implementation of the {@link ProxyService} using Netflix's
 * Ribbon library
 *
 * @since 0.1.0
 */
class RibbonProxyService implements ProxyService {

  @Override
  Promise<ProxyResponse> proxyRequest(
    String method,
    String path,
    String query,
    byte[] body,
    Map<String,?> headers) {

    ConfigurationManager.loadPropertiesFromResources("docker-client.properties")
    RestClient client = ClientFactory.getNamedClient("docker-client") as RestClient

    String realPath = path - "api/v1/docker/"
    HttpRequest request = HttpRequest
      .newBuilder()
      .uri(new URI("/${realPath}?${query ?: ''}"))
      .build()

    HttpResponse response = client.executeWithLoadBalancer(request)

    return Promise.value(
      new ProxyResponse(status: response.status,
                        headers: headers.collectEntries(RibbonProxyService.&toHeader) as Map<String,?>,
                        content: ByteStreams.toByteArray(response.inputStream),))
  }

  /**
   * Converts a {@link Map.Entry} instance in a {@link Map}
   *
   * @param entry the map entry
   * @return a {@link Map} instance
   * @since 0.1.0
   */
  static Map<String,?> toHeader(Map.Entry entry) {
    return [("${entry.key}"): entry.value] as Map<String,?>
  }
}
